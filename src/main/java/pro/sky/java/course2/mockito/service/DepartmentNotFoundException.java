package pro.sky.java.course2.mockito.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends ServiceException {
    public DepartmentNotFoundException() {
        super("Указанного подразделения в списке нет!");
    }
}
