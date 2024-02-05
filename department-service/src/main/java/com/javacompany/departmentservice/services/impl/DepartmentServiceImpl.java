package com.javacompany.departmentservice.services.impl;

import com.javacompany.departmentservice.dtos.EmployeeDTO;
import com.javacompany.departmentservice.entities.Department;
import com.javacompany.departmentservice.repositories.DepartmentRepository;
import com.javacompany.departmentservice.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).get();
    }

    @Override
    public List<EmployeeDTO> findEmployeeListByDepartmentName(String departmentName) {
        String baseEmployeeURL = "http://localhost:8081/emp/dept/";
        Department department = departmentRepository.findByDepartmentName(departmentName);
        ResponseEntity<List<EmployeeDTO>> listResponseEntity =
                restTemplate.exchange(baseEmployeeURL + department.getDepartmentCode(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeDTO>>() {
                        });
        return listResponseEntity.getBody();
    }

    @Override
    public Boolean existsByDepartmentName(String departmentName) {
        return departmentRepository.existsByDepartmentName(departmentName);
    }

    @Override
    public Department searchByDepartmentName(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }

    @Override
    public Department getEmployeeByDepartmentCode(String deptCode) {
        return departmentRepository.findByDepartmentCode(deptCode);
    }


}
