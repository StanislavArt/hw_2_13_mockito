package pro.sky.java.course2.mockito.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class EmployeeAlreadyAddedException extends ServiceException {
    public EmployeeAlreadyAddedException() {
        super("Такой сотрудник уже был добавлен в список");
    }
}
