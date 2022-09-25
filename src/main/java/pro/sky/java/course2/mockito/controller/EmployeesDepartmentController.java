package pro.sky.java.course2.mockito.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.java.course2.mockito.Employee;
import pro.sky.java.course2.mockito.service.EmployeeService;
import pro.sky.java.course2.mockito.service.EmployeesDepartmentService;
import pro.sky.java.course2.mockito.service.ServiceException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class EmployeesDepartmentController {
    private final EmployeesDepartmentService employeesDepartmentService;
    private final EmployeeService employeeService;

    public EmployeesDepartmentController(EmployeesDepartmentService employeesDepartmentService, EmployeeService employeeService) {
        this.employeesDepartmentService = employeesDepartmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("/max-salary")
    public Employee getEmployeeMaxSalaryByDepartment(@RequestParam("departmentId") int department) {
        return employeesDepartmentService.getEmployeeMaxSalaryByDepartment(employeeService, department);
    }

    @GetMapping("/min-salary")
    public Employee getEmployeeMinSalaryByDepartment(@RequestParam("departmentId") int department) {
        return employeesDepartmentService.getEmployeeMinSalaryByDepartment(employeeService, department);
    }

    @GetMapping("/all")
    //public Map<Integer, List<Employee>> getEmployeesByDepartmentGrouping() {
    public String getEmployeesByDepartmentGrouping() {
        Map<Integer, List<Employee>> employees = employeesDepartmentService.getEmployeesByDepartmentGrouping(employeeService);

        final StringBuilder result = new StringBuilder();
        employees.forEach( (department, listEmployees) -> {
                result.append("<b>Сотрудники подразделения " + department + ":</b><br>");
                listEmployees.stream().forEach(employee -> result.append(employee.toString() + "<br>"));
                result.append("<br>");
            }
        );
        return result.toString();
    }

    @GetMapping("/all-by-department")
    public List<Employee> getEmployeesByDepartment(@RequestParam("departmentId") int department) {
        return employeesDepartmentService.getEmployeesByDepartment(employeeService, department);
    }

    @ExceptionHandler(ServiceException.class)
    public String handleException(ServiceException e) {
        String result = e.getMessage() + "<br><br>Содержимое ResponseStatus: " + e.getClass().getAnnotation(ResponseStatus.class).value();
        return result;
    }

}
