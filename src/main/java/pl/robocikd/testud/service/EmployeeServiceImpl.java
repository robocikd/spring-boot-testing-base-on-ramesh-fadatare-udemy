package pl.robocikd.testud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.robocikd.testud.exception.ResourceNotFoundException;
import pl.robocikd.testud.model.Employee;
import pl.robocikd.testud.repository.EmployeeRepository;
import pl.robocikd.testud.service.impl.EmployeeService;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exist with given email: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }
}
