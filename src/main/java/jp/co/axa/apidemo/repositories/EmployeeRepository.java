package jp.co.axa.apidemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.axa.apidemo.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
