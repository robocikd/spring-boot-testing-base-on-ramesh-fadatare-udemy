package pl.robocikd.testud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.robocikd.testud.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:anyParamName")
    Employee findByJPQLNamedParams(String firstName, @Param("anyParamName") String lastName);

    @Query(nativeQuery = true, value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2")
    Employee findByNativeSQL(String firstName, String lastName);

    @Query(nativeQuery = true, value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName")
    Employee findByNativeSQLNamedParams(@Param("firstName") String anyFirstName, String lastName);
}
