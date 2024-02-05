package com.javacompany.organizationservice.services;

import com.javacompany.organizationservice.dtos.OrganizationDTO;

public interface OrganizationService {

    OrganizationDTO createOrganization (OrganizationDTO organizationDTO);

    OrganizationDTO getOrganization(String organizationCode);

}
