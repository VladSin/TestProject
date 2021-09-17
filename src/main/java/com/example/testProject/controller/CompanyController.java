package com.example.testProject.controller;

import com.example.testProject.dto.request.CompanyRequestDto;
import com.example.testProject.dto.response.CompanyResponseDto;
import com.example.testProject.dto.response.DepartmentResponseDto;
import com.example.testProject.entity.Company;
import com.example.testProject.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "company")
@RequestMapping(value = "/company/")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("save")
    @ApiOperation(value = "Creating Company", response = CompanyResponseDto.class, tags = "saveCompany")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<CompanyResponseDto> saveCompany(@RequestBody CompanyRequestDto request) {

        if (request == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (companyService.uniqueName(request.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Company company = new Company();
        company.setName(request.getName());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setBudget(request.getBudget());
        companyService.saveCompany(company);

        CompanyResponseDto response = CompanyResponseDto.fromCompany(companyService.getCompanyByName(request.getName()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/all")
    @ApiOperation(value = "Get Companies", response = CompanyResponseDto.class, tags = "getCompanies")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<CompanyResponseDto>> getCompanies() {
        List<CompanyResponseDto> response = companyService.getAllCompany().stream()
                .map(CompanyResponseDto::fromCompany)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/locations/{locations}")
    @ApiOperation(value = "Get Companies By Location", response = CompanyResponseDto.class, tags = "getCompaniesByLocation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<CompanyResponseDto>> getCompaniesByLocation(@PathVariable(name = "locations") String locations) {
        List<CompanyResponseDto> response = companyService.getAllCompanyByLocation(locations).stream()
                .map(CompanyResponseDto::fromCompany)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/id/{id}")
    @ApiOperation(value = "Get Employee By Id", response = CompanyResponseDto.class, tags = "getEmployeeById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<CompanyResponseDto> getEmployeeById(@PathVariable(name = "id") Long id) {
        Company company = companyService.getCompanyById(id).orElse(null);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CompanyResponseDto response = CompanyResponseDto.fromCompany(company);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/name/{name}")
    @ApiOperation(value = "Get Company By Name", response = CompanyResponseDto.class, tags = "getCompanyByName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<CompanyResponseDto> getCompanyByName(@PathVariable(name = "name") String name) {
        Company company = companyService.getCompanyByName(name);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CompanyResponseDto response = CompanyResponseDto.fromCompany(company);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "update/{id}")
    @GetMapping("get/name/{name}")
    @ApiOperation(value = "Updating Company", response = CompanyResponseDto.class, tags = "updateCompany")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable("id") Long id,
                                                            @RequestBody CompanyRequestDto request) {
        if (companyService.getCompanyById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        companyService.updateCompanyData(CompanyRequestDto.fromRequestCompany(request));
        CompanyResponseDto response = CompanyResponseDto.fromCompany(companyService.getCompanyByName(request.getName()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("delete/{id}")
    @GetMapping("get/name/{name}")
    @ApiOperation(value = "Deleting Company", response = CompanyResponseDto.class, tags = "deleteCompany")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public void deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
    }
}
