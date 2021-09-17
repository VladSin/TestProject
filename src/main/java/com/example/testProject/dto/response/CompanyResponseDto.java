package com.example.testProject.dto.response;

import com.example.testProject.entity.Company;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyResponseDto {

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

    public static CompanyResponseDto fromCompany(Company company) {
        CompanyResponseDto response = new CompanyResponseDto();
        response.setId(company.getId());
        response.setName(company.getName());
        response.setWebsite(company.getWebsite());
        response.setLocation(company.getLocation());
        response.setBudget(company.getBudget());
        return response;
    }
}
