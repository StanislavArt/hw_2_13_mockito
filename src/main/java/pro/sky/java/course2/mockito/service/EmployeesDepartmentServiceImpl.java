package pro.sky.java.course2.mockito.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course2.mockito.Employee;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeesDepartmentServiceImpl implements EmployeesDepartmentService {
    @Override
    public Employee getEmployeeMaxSalaryByDepartment(EmployeeService employeeService, int department) {
        Collection<Employee> employees = employeeService.getEmployees().values();
        return employees.stream()
                .filter(employee -> employee.getDepartment() == department)
                .max(Comparator.comparing(employee -> employee.getSalary()))
                .orElseThrow(DepartmentNotFoundException::new);
    }

    @Override
    public Employee getEmployeeMinSalaryByDepartment(EmployeeService employeeService, int department) {
        Collection<Employee> employees = employeeService.getEmployees().values();
        return employees.stream()
                .filter(employee -> employee.getDepartment() == department)
                .min(Comparator.comparing(employee -> employee.getSalary()))
                .orElseThrow(DepartmentNotFoundException::new);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(EmployeeService employeeService, int department) {
        Collection<Employee> employees = employeeService.getEmployees().values();
        return employees.stream()
                .filter(employee -> employee.getDepartment() == department)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<Employee>> getEmployeesByDepartmentGrouping(EmployeeService employeeService) {
        Collection<Employee> employees = employeeService.getEmployees().values();

        // создаем ассоц.массив
        Map<Integer, List<Employee>> employeesByDepartmentGrouping = employees.stream()
                .map(employee -> employee.getDepartment())
                .distinct()
                .collect(Collectors.toMap( e -> e, e -> new ArrayList<>()));

        // заполняем ассоц.массив
        employees.stream()
                .forEach(employee -> {
                    List<Employee> value = employeesByDepartmentGrouping.get(employee.getDepartment());
                    value.add(employee);
                    employeesByDepartmentGrouping.put(employee.getDepartment(), value);
                });

        return employeesByDepartmentGrouping;
    }
}
