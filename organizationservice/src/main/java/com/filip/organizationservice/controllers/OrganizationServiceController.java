package com.filip.organizationservice.controllers;

import com.filip.organizationservice.model.Organization;
import com.filip.organizationservice.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/organizations")
public class OrganizationServiceController {

    @Autowired
    private OrganizationService organizationService;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceController.class);

    @GetMapping(value = "/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        logger.debug("Looking up data for org {}", organizationId);

        Organization org = organizationService.getOrganization(organizationId);
        org.setContactName(org.getContactName());
        return org;
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {
        organizationService.updateOrganization(organization);
    }

    @PostMapping(value = "/{organizationId}")
    public void saveOrganization(@RequestBody Organization organization) {
        organizationService.saveOrganization(organization);
    }

    @DeleteMapping(value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {
        organizationService.deleteOrganization(organization);
    }

}
