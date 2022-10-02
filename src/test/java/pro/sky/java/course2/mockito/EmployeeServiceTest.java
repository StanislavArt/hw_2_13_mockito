package pro.sky.java.course2.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.sky.java.course2.mockito.service.*;

public class EmployeeServiceTest {
    private final EmployeeService out = new EmployeeServiceImpl();

    @Test
    public void addEmployee() {
        String firstNameTest = "Лев";
        String lastNameTest = "Толстой";
        int departmentTest = 1;
        double salaryTest = 10_000;

        Employee employeeExpected = new Employee(firstNameTest, lastNameTest, departmentTest, salaryTest);
        out.addEmployee(firstNameTest.toLowerCase(), lastNameTest.toLowerCase(), departmentTest, salaryTest);

        Employee employeeActual = out.getEmployees().get(firstNameTest + " " + lastNameTest);
        Assertions.assertEquals(employeeExpected, employeeActual);
        Assertions.assertEquals(employeeExpected.getDepartment(), employeeActual.getDepartment());
        Assertions.assertEquals(employeeExpected.getSalary(), employeeActual.getSalary());
    }

    @Test
    public void addEmployeeException() {
        Assertions.assertThrows(EmployeeAlreadyAddedException.class, () -> out.addEmployee("Айзек", "Азимов", 1, 1000));
    }

    @Test
    public void removeEmployee() {
        String firstNameTest = "Стивен";
        String lastNameTest = "Кинг";

        Employee actual = out.getEmployees().get(firstNameTest + " " + lastNameTest);
        Assertions.assertNotNull(actual);
        out.removeEmployee(firstNameTest, lastNameTest);
        actual = out.getEmployees().get(firstNameTest + " " + lastNameTest);
        Assertions.assertNull(actual);
    }

    @Test
    public void removeEmployeeException() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> out.removeEmployee("А", "А"));
    }

    @Test
    public void findEmployee() {
        String firstNameTest = "Роберт";
        String lastNameTest = "Шекли";

        Employee employeeExpected = out.getEmployees().get(firstNameTest + " " + lastNameTest);
        Assertions.assertNotNull(employeeExpected);
        Employee employeeActual = out.findEmployee(firstNameTest, lastNameTest);
        Assertions.assertSame(employeeExpected, employeeActual);
    }

    @Test
    public void findEmployeeException() {
        Assertions.assertThrows(NameErrorException.class, () -> out.findEmployee("Роберт1", "Шекли"));
    }
}
