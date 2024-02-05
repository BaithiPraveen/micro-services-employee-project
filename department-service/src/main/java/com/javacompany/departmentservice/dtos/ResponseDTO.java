package com.javacompany.departmentservice.dtos;

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
}
