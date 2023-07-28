package pl.robocikd.testud.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import pl.robocikd.testud.model.Employee;
import pl.robocikd.testud.repository.EmployeeRepository;
import pl.robocikd.testud.service.impl.EmployeeService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeServiceImplTest {

    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    void givenEmployee_whenSaveEmployee_thenReturnEmployee() {
        // given
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Damian")
                .lastName("Robot")
                .email("daro@gmail.com")
                .build();
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);
        // then
        assertThat(savedEmployee).isNotNull();
    }

}