package pro.sky.java.course2.mockito;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.mockito.service.DepartmentNotFoundException;
import pro.sky.java.course2.mockito.service.EmployeeService;
import pro.sky.java.course2.mockito.service.EmployeesDepartmentServiceImpl;

import java.util.*;

import static java.util.Map.of;

@ExtendWith(MockitoExtension.class)
public class EmployeesDepartmentServiceTest {

    @Mock
    private EmployeeService employeeServiceMock;

    @InjectMocks
    private EmployeesDepartmentServiceImpl out;

    @BeforeEach
    public void initEmployeeService() {
        Map<String, Employee> employeesReturn = new HashMap<>(of(
                "Станислав Лем",
                new Employee("Станислав", "Лем", 2, 35000),
                "Борис Стругацкий",
                new Employee("Борис", "Стругацкий", 1, 73000),
                "Роберт Шекли",
                new Employee("Роберт", "Шекли",2, 119000)
        ));
        Mockito.when(employeeServiceMock.getEmployees()).thenReturn(employeesReturn);
    }

    @Test
    public void getEmployeeMaxSalaryByDepartment() {
        Employee expected = employeeServiceMock.getEmployees().get("Роберт Шекли");
        Employee actual = out.getEmployeeMaxSalaryByDepartment(employeeServiceMock, 2);
        Assertions.assertSame(expected, actual);
    }

    @Test
    public void getEmployeeMaxSalaryByDepartmentException() {
        Assertions.assertThrows(DepartmentNotFoundException.class, () -> out.getEmployeeMaxSalaryByDepartment(employeeServiceMock, 5));
    }

    @Test
    public void getEmployeeMinSalaryByDepartment() {
        Employee expected = employeeServiceMock.getEmployees().get("Станислав Лем");
        Employee actual = out.getEmployeeMinSalaryByDepartment(employeeServiceMock, 2);
        Assertions.assertSame(expected, actual);
    }

    @Test
    public void getEmployeeMinSalaryByDepartmentException() {
        Assertions.assertThrows(DepartmentNotFoundException.class, () -> out.getEmployeeMaxSalaryByDepartment(employeeServiceMock, 5));
    }

    @Test
    public void getEmployeesByDepartment() {
        List<Employee> expected = new ArrayList<>(List.of(
                employeeServiceMock.getEmployees().get("Роберт Шекли"),
                employeeServiceMock.getEmployees().get("Станислав Лем")
        ));
        expected.sort(Comparator.comparing(Employee::toString));

        List<Employee> actual = out.getEmployeesByDepartment(employeeServiceMock, 2);
        actual.sort(Comparator.comparing(Employee::toString));
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void getEmployeesByDepartmentGrouping() {
        // подготовка ожидаемых данных
        List<Employee> employeesOfDepartmentExpected;
        Map<Integer, List<Employee>> expected = new HashMap<>();

        employeesOfDepartmentExpected = new ArrayList<>(List.of(
                employeeServiceMock.getEmployees().get("Роберт Шекли"),
                employeeServiceMock.getEmployees().get("Станислав Лем")
        ));
        employeesOfDepartmentExpected.sort(Comparator.comparing(Employee::toString));
        expected.put(2, employeesOfDepartmentExpected);

        employeesOfDepartmentExpected = new ArrayList<>(List.of(
                employeeServiceMock.getEmployees().get("Борис Стругацкий")
        ));
        expected.put(1, employeesOfDepartmentExpected);

        // получение фактических данных
        Map<Integer, List<Employee>> actual = out.getEmployeesByDepartmentGrouping(employeeServiceMock);

        Assertions.assertEquals(expected, actual);
    }
}
