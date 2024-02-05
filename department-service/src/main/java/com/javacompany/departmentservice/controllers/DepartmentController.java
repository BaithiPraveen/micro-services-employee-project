package com.javacompany.departmentservice.controllers;

import com.javacompany.departmentservice.dtos.EmployeeDTO;
import com.javacompany.departmentservice.entities.Department;
import com.javacompany.departmentservice.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long departmentId){
//        Department department = departmentService.getDepartmentById(departmentId);
//        return ResponseEntity.ok(department);
//    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDTO>> findEmployeeListByDepartmentName(@RequestParam("deptName") String deptName) {
        return ResponseEntity.ok(departmentService.findEmployeeListByDepartmentName(deptName));
    }

    @GetMapping("/exists/{deptName}")
    public ResponseEntity<Boolean> existsByDepartmentName(@PathVariable("deptName") String departmentName) {
        return ResponseEntity.ok(departmentService.existsByDepartmentName(departmentName));
    }

    @GetMapping("/search/{deptName}")
    public ResponseEntity<Department> searchByDepartmentName(@PathVariable("deptName") String departmentName) {
        return ResponseEntity.ok(departmentService.searchByDepartmentName(departmentName));
    }

    @GetMapping("/{deptCode}")
    public ResponseEntity<Department> getEmployeeByDepartmentCode(@PathVariable("deptCode") String deptCode) {
        return ResponseEntity.ok(departmentService.getEmployeeByDepartmentCode(deptCode));
    }
}
