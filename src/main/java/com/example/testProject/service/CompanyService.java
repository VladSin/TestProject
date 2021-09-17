package com.example.testProject.service;

import com.example.testProject.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CompanyService {

    Page<Company> findAll(Pageable pageable);

    void saveCompany(Company company);

    Optional<Company> getCompanyById(Long id);

    Company getCompanyByName(String name);

    List<Company> getAllCompanyByLocation(String location);

    List<Company> getAllCompany();

    void updateCompanyData(Company company);

    void deleteCompany(Long id);

    boolean uniqueName(String name);
}
