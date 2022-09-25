package pro.sky.java.course2.mockito.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.java.course2.mockito.Employee;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<String, Employee> employees;

    public EmployeeServiceImpl() {
        this.employees = new HashMap<>(Map.of(
                "Станислав Лем",
                new Employee("Станислав", "Лем", 2, 35000),
                "Борис Стругацкий",
                new Employee("Борис", "Стругацкий", 1, 73000),
                "Роберт Шекли",
                new Employee("Роберт", "Шекли",2, 119000),
                "Стивен Кинг",
                new Employee("Стивен", "Кинг", 4, 89000),
                "Айзек Азимов",
                new Employee("Айзек", "Азимов", 2, 91000),
                "Эдгар Берроуз",
                new Employee("Эдгар", "Берроуз", 3, 43000)
        ));
    }

    @Override
    public void addEmployee(String firstName, String lastName, int department, double salary) {
        if (findEmployee(firstName, lastName) != null) {
            throw new EmployeeAlreadyAddedException();
        }
        String firstNameCapitalized = StringUtils.capitalize(firstName);
        String lastNameCapitalized = StringUtils.capitalize(lastName);
        employees.put(firstNameCapitalized + " " + lastNameCapitalized, new Employee(firstNameCapitalized, lastNameCapitalized, department, salary));
    }

    @Override
    public void removeEmployee(String firstName, String lastName) {
        Employee employee = findEmployee(firstName, lastName);
        if ( employee == null) {
            throw new EmployeeNotFoundException();
        }
        employees.remove(StringUtils.capitalize(firstName) + " " + StringUtils.capitalize(lastName));
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        if(!StringUtils.isAlpha(firstName) || !StringUtils.isAlpha(lastName)) {
            throw new NameErrorException();
        }
        return employees.get(StringUtils.capitalize(firstName) + " " + StringUtils.capitalize(lastName));
    }

    @Override
    public Map<String, Employee> getEmployees() {
        return employees;
    }
}
