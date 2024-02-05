package com.javacompany.departmentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class DepartmentDTO {

    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
}
