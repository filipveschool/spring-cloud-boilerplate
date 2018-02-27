package com.filip.organizationservice.service;

import com.filip.organizationservice.events.source.SimpleSourceBean;
import com.filip.organizationservice.model.Organization;
import com.filip.organizationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    public Organization getOrganization(String organizationId) {
        return organizationRepository.findById(organizationId).get();
    }

    public void saveOrganization(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);
        simpleSourceBean.publishOrgChange("SAVE", organization.getId());
    }

    public void updateOrganization(Organization organization) {
        organizationRepository.save(organization);
        simpleSourceBean.publishOrgChange("UPDATE", organization.getId());
    }

    public void deleteOrganization(Organization organization) {
        organizationRepository.delete(organization);
        simpleSourceBean.publishOrgChange("DELETE", organization.getId());
    }

    public List<Organization> getAllOrganizations(){
        //return organizationRepository.getAllOrganizations();
        return organizationRepository.findAll();
    }

}
