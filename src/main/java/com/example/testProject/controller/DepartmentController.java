package com.example.testProject.controller;

import com.example.testProject.dto.request.DepartmentRequestDto;
import com.example.testProject.dto.response.DepartmentResponseDto;
import com.example.testProject.entity.Department;
import com.example.testProject.entity.Employee;
import com.example.testProject.service.CompanyService;
import com.example.testProject.service.DepartmentService;
import com.example.testProject.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "department")
@RequestMapping(value = "/department/")
public class DepartmentController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(CompanyService companyService, EmployeeService employeeService, DepartmentService departmentService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("page")
    @ApiOperation(value = "Page Departments", tags = "pageDepartments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<Page<Department>> pageDepartments(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Department> page;
        page = departmentService.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("save")
    @ApiOperation(value = "Creating Department", response = DepartmentResponseDto.class, tags = "saveDepartment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<DepartmentResponseDto> saveDepartment(@RequestBody DepartmentRequestDto request) {

        if (request == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (departmentService.uniqueName(request.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Department department = new Department();
        department.setName(request.getName());
        department.setWebsite(request.getWebsite());
        department.setLocation(request.getLocation());
        department.setCompany(companyService.getCompanyByName(request.getCompany()));
        departmentService.saveDepartment(department);

        DepartmentResponseDto response = DepartmentResponseDto.fromDepartment(departmentService.getDepartmentByName(request.getName()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/all")
    @ApiOperation(value = "Get Departments", response = DepartmentResponseDto.class, tags = "getDepartments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<DepartmentResponseDto>> getDepartments() {
        List<DepartmentResponseDto> response = departmentService.getAllDepartment().stream()
                .map(DepartmentResponseDto::fromDepartment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/locations/{locations}")
    @ApiOperation(value = "Get Departments By Location", response = DepartmentResponseDto.class, tags = "getDepartmentsByLocation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<DepartmentResponseDto>> getDepartmentsByLocation(@PathVariable(name = "locations") String locations) {
        List<DepartmentResponseDto> response = departmentService.getAllDepartmentByLocation(locations).stream()
                .map(DepartmentResponseDto::fromDepartment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/id/{id}")
    @ApiOperation(value = "Get Department By Id", response = DepartmentResponseDto.class, tags = "getDepartmentById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable(name = "id") Long id) {
        Department department = departmentService.getDepartmentById(id).orElse(null);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        DepartmentResponseDto response = DepartmentResponseDto.fromDepartment(department);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/name/{name}")
    @ApiOperation(value = "Get Department By Name", response = DepartmentResponseDto.class, tags = "getDepartmentByName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<DepartmentResponseDto> getDepartmentByName(@PathVariable(name = "name") String name) {
        Department department = departmentService.getDepartmentByName(name);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        DepartmentResponseDto response = DepartmentResponseDto.fromDepartment(department);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "update/{id}")
    @ApiOperation(value = "Updating Department", response = DepartmentResponseDto.class, tags = "updateDepartment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable("id") Long id,
                                                                  @RequestBody DepartmentRequestDto request) {
        if (departmentService.getDepartmentById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        departmentService.updateDepartmentData(DepartmentRequestDto.fromRequestDepartment(request,
                companyService.getCompanyByName(request.getCompany())));
        DepartmentResponseDto response = DepartmentResponseDto.fromDepartment(departmentService.getDepartmentByName(request.getName()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "Delet Department", response = DepartmentResponseDto.class, tags = "deleteDepartment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public void deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
    }
}
