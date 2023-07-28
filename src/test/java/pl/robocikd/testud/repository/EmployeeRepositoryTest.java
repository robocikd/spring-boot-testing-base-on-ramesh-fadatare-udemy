package pl.robocikd.testud.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.robocikd.testud.model.Employee;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setup() {
        employee = Employee.builder()
                .firstName("Damian")
                .lastName("RobocikD")
                .email("www@wp.pl")
                .build();
    }

    @Test
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given

        // when
        Employee savedEmployee = employeeRepository.save(employee);
        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    void givenEmployeesList_whenFindAll_thenEmployeesList() {
        // given
        Employee employee1 = Employee.builder()
                .firstName("Kris")
                .lastName("Mono")
                .email("www@km.pl")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        // when
        List<Employee> employeeList = employeeRepository.findAll();
        // then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).hasSize(2);
    }

    @Test
    void givenEmployee_whenFindById_thenReturnEmployee() {
        // given
        employeeRepository.save(employee);
        // when
        Employee byId = employeeRepository.findById(employee.getId()).get();
        // then
        assertThat(byId).isNotNull();
    }

    @Test
    void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {
        // given
        employeeRepository.save(employee);
        // when
        Employee byEmail = employeeRepository.findByEmail(employee.getEmail()).get();
        // then
        assertThat(byEmail).isNotNull();
    }

    @Test
    void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        // when
        savedEmployee.setEmail("adress@gmail.com");
        savedEmployee.setFirstName("Agnes");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        // then
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo("adress@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Agnes");
    }

    @Test
    void givenEmployee_whenDelete_thenRemoveEmployee() {
        // given
        employeeRepository.save(employee);
        // when
        employeeRepository.delete(employee);
        Optional<Employee> byId = employeeRepository.findById(employee.getId());
        // then
        assertThat(byId).isEmpty();
    }

    @Test
    void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee() {
        // given
        employeeRepository.save(employee);
        String firstName = "Damian";
        String lastName = "RobocikD";
        // when
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        // then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployee() {
        // given
        employeeRepository.save(employee);
        String firstName = "Damian";
        String lastName = "RobocikD";
        // when
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        // then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void givenFirstNameAndLastName_whenFindByJPQLNativeSQL_thenReturnEmployee() {
        // given
        employeeRepository.save(employee);
        String firstName = "Damian";
        String lastName = "RobocikD";
        // when
        Employee savedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);
        // then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void givenFirstNameAndLastName_whenFindByJPQLNativeSQLNamedParams_thenReturnEmployee() {
        // given
        employeeRepository.save(employee);
        String firstName = "Damian";
        String lastName = "RobocikD";
        // when
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(firstName, lastName);
        // then
        assertThat(savedEmployee).isNotNull();
    }
}