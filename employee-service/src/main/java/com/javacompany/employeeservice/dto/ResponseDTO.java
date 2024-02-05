package com.javacompany.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private EmployeeDTO employee;

    private DepartmentDTO department;

    private OrganizationDTO organization;
}
