package pl.robocikd.testud.service.impl;

import pl.robocikd.testud.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();
}
