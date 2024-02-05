package com.javacompany.organizationservice.services.impl;

import com.javacompany.organizationservice.dtos.OrganizationDTO;
import com.javacompany.organizationservice.entities.Organization;
import com.javacompany.organizationservice.repositories.OrganizationRepository;
import com.javacompany.organizationservice.services.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrganizationRepository organizationRepository;
    @Override
    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
        Organization organization = modelMapper.map(organizationDTO, Organization.class);
        Organization saveOrganization = organizationRepository.save(organization);
        return modelMapper.map(saveOrganization,OrganizationDTO.class);
    }

    @Override
    public OrganizationDTO getOrganization(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return modelMapper.map(organization,OrganizationDTO.class);
    }
}
