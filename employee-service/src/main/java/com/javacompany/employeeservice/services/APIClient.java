package com.javacompany.employeeservice.services;

import com.javacompany.employeeservice.dto.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(value = "DEPARTMENT-SERVICE",url = "http://localhost:8080/dept")
@FeignClient(name = "DEPARTMENT-SERVICE") // for load balancing
public interface APIClient {

    @GetMapping("/dept/{deptCode}")
        // doubt
    DepartmentDTO getEmployeeByDepartmentCode(@PathVariable("deptCode") String deptCode);

    @PostMapping("/dept")
    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    @GetMapping("/dept/exists/{deptName}")
    Boolean existsByDepartmentName(@PathVariable("deptName") String departmentName);

    @GetMapping("/dept/search/{deptName}")
    DepartmentDTO searchByDepartmentName(@PathVariable("deptName") String departmentName);

//    @GetMapping("/dept/")
//    List<EmployeeDTO> findEmployeeListByDepartmentName(@RequestParam("deptName") String deptName);

}