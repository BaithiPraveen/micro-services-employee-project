package com.javacompany.employeeservice.services;

import com.javacompany.employeeservice.dto.EmployeeDTO;
import com.javacompany.employeeservice.dto.ResponseDTO;
import com.javacompany.employeeservice.entities.Employee;

import java.util.List;

public interface EmployeeService {

    Employee savaEmployee(Employee employee);

    ResponseDTO getEmployeeById(Long employeeId);

    List<EmployeeDTO> findByDepartmentId(String departmentId);

    ResponseDTO createEmployee(ResponseDTO responseDTO);

    ResponseDTO updateEmployee(Long employeeId, Employee employee);

    void deleteEmployee(Long employeeId);

    List<ResponseDTO> getEmployeeListWithDepartments();

    EmployeeDTO getEmployeeByIdWithCheck(Long employeeId);
}
