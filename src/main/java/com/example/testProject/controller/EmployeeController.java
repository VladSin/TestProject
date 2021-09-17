package com.example.testProject.controller;

import com.example.testProject.dto.request.EmployeeRequestDto;
import com.example.testProject.dto.response.EmployeeResponseDto;
import com.example.testProject.entity.Employee;
import com.example.testProject.exeption.UsernameNotFoundException;
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
@Api(value = "employee")
@RequestMapping(value = "/employee/")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("page")
    @ApiOperation(value = "Page Employees", tags = "pageEmployees")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<Page<Employee>> pageEmployees(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Employee> page;
        page = employeeService.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("save")
    @ApiOperation(value = "Creating Employee", response = EmployeeResponseDto.class, tags = "saveEmployee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<EmployeeResponseDto> saveEmployee(@RequestBody EmployeeRequestDto request) throws UsernameNotFoundException {

        if (request == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (employeeService.checkEmail(request.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Employee employee = new Employee();
        if (request.getDepartment() != null) {
            employee.setDepartment(departmentService.getDepartmentByName(request.getDepartment()));
        }
        employee.setUsername(request.getUsername());
        employee.setEmail(request.getEmail());
        employee.setMarried(request.isMarried());
        employee.setSalary(request.getSalary());
        employeeService.saveEmployee(employee);

        EmployeeResponseDto response = EmployeeResponseDto.fromEmployee(employeeService.getEmployeeByEmail(request.getEmail()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/id/{id}")
    @ApiOperation(value = "Get Employee By Id", response = EmployeeResponseDto.class, tags = "getEmployeeById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable(name = "id") Long id) {
        Employee employee = employeeService.getEmployeeById(id).orElse(null);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        EmployeeResponseDto response = EmployeeResponseDto.fromEmployee(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/username/{username}")
    @ApiOperation(value = "Get Employee By Username", response = EmployeeResponseDto.class, tags = "getEmployeeByUsername")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<EmployeeResponseDto> getEmployeeByUsername(@PathVariable(name = "username") String username) {
        Employee employee = employeeService.getEmployeeByUsername(username);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        EmployeeResponseDto response = EmployeeResponseDto.fromEmployee(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/email/{email}")
    @ApiOperation(value = "Get Employee By Email", response = EmployeeResponseDto.class, tags = "getEmployeeByEmail")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<EmployeeResponseDto> getEmployeeByEmail(@PathVariable(name = "email") String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        EmployeeResponseDto response = EmployeeResponseDto.fromEmployee(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/all")
    @ApiOperation(value = "Get all Employees", response = EmployeeResponseDto.class, tags = "getEmployees")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<EmployeeResponseDto>> getEmployees() {
        List<EmployeeResponseDto> response = employeeService.getAll().stream()
                .map(EmployeeResponseDto::fromEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/all/married/{married}")
    @ApiOperation(value = "Get all Employees by Married", response = EmployeeResponseDto.class, tags = "getAllByMarried")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<EmployeeResponseDto>> getAllByMarried(@PathVariable(name = "married") boolean married) {
        List<EmployeeResponseDto> response = employeeService.getAllByMarried(married).stream()
                .map(EmployeeResponseDto::fromEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/all/salary/after/{salary}")
    @ApiOperation(value = "Get all Employees by Salary After", response = EmployeeResponseDto.class, tags = "getAllBySalaryAfter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<EmployeeResponseDto>> getAllBySalaryAfter(@PathVariable(name = "salary") double salary) {
        List<EmployeeResponseDto> response = employeeService.getAllBySalaryAfter(salary).stream()
                .map(EmployeeResponseDto::fromEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/all/salary/before/{salary}")
    @ApiOperation(value = "Get all Employees by Salary Before", response = EmployeeResponseDto.class, tags = "getAllBySalaryBefore")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<EmployeeResponseDto>> getAllBySalaryBefore(@PathVariable(name = "salary") double salary) {
        List<EmployeeResponseDto> response = employeeService.getAllBySalaryBefore(salary).stream()
                .map(EmployeeResponseDto::fromEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/all/salary/between/{minSalary}/{maxSalary}")
    @ApiOperation(value = "Get all Employees by Salary Between", response = EmployeeResponseDto.class, tags = "getAllBySalaryBetween")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<EmployeeResponseDto>> getAllBySalaryBetween(@PathVariable(name = "minSalary") double minSalary,
                                                                           @PathVariable(name = "maxSalary") double maxSalary) {
        List<EmployeeResponseDto> response = employeeService.getAllBySalaryBetween(minSalary, maxSalary).stream()
                .map(EmployeeResponseDto::fromEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get/all/department/{department}")
    @ApiOperation(value = "Get all Employees by Department", response = EmployeeResponseDto.class, tags = "getAllByDepartment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<EmployeeResponseDto>> getAllByDepartment(@PathVariable(name = "department") String department) {
        List<EmployeeResponseDto> response = employeeService.getAllByDepartment(departmentService.getDepartmentByName(department)).stream()
                .map(EmployeeResponseDto::fromEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "update/{id}")
    @ApiOperation(value = "Updating Employee", response = EmployeeResponseDto.class, tags = "updateEmployee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "NO_CONTENT")})
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeRequestDto request) {
        if (employeeService.getEmployeeById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        employeeService.updateEmployeeData(EmployeeRequestDto.fromRequestEmployee(request,
                departmentService.getDepartmentByName(request.getDepartment())));

        EmployeeResponseDto response = EmployeeResponseDto.fromEmployee(employeeService.getEmployeeByEmail(
                request.getEmail()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "Deleting Employee", tags = "deleteEmployee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")})
    public void deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
    }
}
