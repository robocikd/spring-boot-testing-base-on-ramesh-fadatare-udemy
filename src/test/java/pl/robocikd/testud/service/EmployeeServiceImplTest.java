package pl.robocikd.testud.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.robocikd.testud.exception.ResourceNotFoundException;
import pl.robocikd.testud.model.Employee;
import pl.robocikd.testud.repository.EmployeeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Damian")
                .lastName("Robot")
                .email("daro@gmail.com")
                .build();
    }

    @Test
    void givenEmployee_whenSaveEmployee_thenReturnEmployee() {
        // given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);
        // then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void givenExistingEmail_whenSaveEmployee_thenThrowException() {
        // given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        // when
        assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));
        assertThatThrownBy(() -> {
            employeeService.saveEmployee(employee);
        }).isInstanceOf(ResourceNotFoundException.class);

        Throwable thrown = catchThrowable(() -> employeeService.saveEmployee(employee));
        // then
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void givenEmployeesList_whenGetAllEmployees_thenReturnEmployees() {
        // given
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Damian")
                .lastName("Robot")
                .email("daro@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));
        // when
        List<Employee> employeeList = employeeService.getAllEmployees();
        // then
        assertThat(employeeList).hasSize(2);
    }

    @Test
    void givenEmployeesList_whenGetAllEmployees_thenReturnEmptyList() {
        // given
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        // when
        List<Employee> employeeList = employeeService.getAllEmployees();
        // then
        assertThat(employeeList).isEmpty();
    }
}