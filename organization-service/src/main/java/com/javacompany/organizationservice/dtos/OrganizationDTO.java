package com.javacompany.organizationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

    private Long id;
    private String organizationName;
    private String organizationCode;
    private String organizationDescription;
    private LocalDateTime createdDate;

}
