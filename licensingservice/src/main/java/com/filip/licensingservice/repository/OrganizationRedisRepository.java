package com.filip.licensingservice.repository;

import com.filip.licensingservice.model.Organization;

public interface OrganizationRedisRepository {

    void saveOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganization(String organizationId);

    Organization findOrganization(String organizationId);

}
