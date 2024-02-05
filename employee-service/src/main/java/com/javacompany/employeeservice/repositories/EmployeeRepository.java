package com.javacompany.employeeservice.repositories;

import com.javacompany.employeeservice.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(String departmentId);
}
