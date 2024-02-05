package com.javacompany.departmentservice.repositories;

import com.javacompany.departmentservice.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByDepartmentName(String departmentName);

    Boolean existsByDepartmentName(String departmentName);

    Department findByDepartmentCode(String departmentCode);
}