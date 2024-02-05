package com.javacompany.departmentservice.services;

import com.javacompany.departmentservice.dtos.EmployeeDTO;
import com.javacompany.departmentservice.entities.Department;

import java.util.List;

public interface DepartmentService {

    Department saveDepartment(Department department);

    Department getDepartmentById(Long departmentId);

    List<EmployeeDTO> findEmployeeListByDepartmentName(String departmentName);

    Boolean existsByDepartmentName(String departmentName);

    Department searchByDepartmentName(String departmentName);

    Department getEmployeeByDepartmentCode(String departmentCode);
}
