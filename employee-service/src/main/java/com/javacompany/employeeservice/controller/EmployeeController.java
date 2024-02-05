package com.javacompany.employeeservice.controller;

import com.javacompany.employeeservice.dto.EmployeeDTO;
import com.javacompany.employeeservice.dto.ResponseDTO;
import com.javacompany.employeeservice.entities.Employee;
import com.javacompany.employeeservice.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.savaEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getEmployeeWithDepartment(@PathVariable("id") Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @GetMapping("/dept/{deptId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeList(@PathVariable("deptId") String employeeId) {
        return ResponseEntity.ok(employeeService.findByDepartmentId(employeeId));
    }

    @PostMapping("/createemp")
    public ResponseEntity<ResponseDTO> createEmployee(@RequestBody ResponseDTO responseDTO) {
        ResponseDTO resultResponseDTO = employeeService.createEmployee(responseDTO);
        return ResponseEntity.ok(resultResponseDTO);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<ResponseDTO> updateEmployee(@RequestBody Employee employee, @PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employee));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(String.format("%s id employee is deleted successfully..!", employeeId));
    }

    @GetMapping
    public ResponseEntity<List<ResponseDTO>> getEmployeeListWithDepartments() {
        List<ResponseDTO> employeeListWithDepartments = employeeService.getEmployeeListWithDepartments();
        return ResponseEntity.ok(employeeListWithDepartments);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeWithDepartmentGet(@PathVariable("id") Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeByIdWithCheck(employeeId));
    }
}
