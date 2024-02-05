package com.javacompany.organizationservice.controller;

import com.javacompany.organizationservice.dtos.OrganizationDTO;
import com.javacompany.organizationservice.services.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO organizationDTO){
        return ResponseEntity.ok(organizationService.createOrganization(organizationDTO));
    }

    @GetMapping("/{organizationCode}")
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable("organizationCode") String organizationCode){
        return ResponseEntity.ok(organizationService.getOrganization(organizationCode));
    }

}
