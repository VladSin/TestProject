package com.example.testProject.dto.request;

import com.example.testProject.entity.Company;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyRequestDto {

    @ApiModelProperty(notes = "Company id", name = "id")
    private Long id;

    @ApiModelProperty(notes = "Company name", name = "name")
    private String name;

    @ApiModelProperty(notes = "Company website", name = "website")
    private String website;

    @ApiModelProperty(notes = "Company location", name = "location")
    private String location;

    @ApiModelProperty(notes = "Company budget", name = "budget")
    private double budget;

    public static Company fromRequestCompany(CompanyRequestDto request) {
        Company company = new Company();
        company.setId(request.getId());
        company.setName(request.getName());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setBudget(request.getBudget());
        return company;
    }
}
