package com.example.testProject.service.impl;

import com.example.testProject.entity.Company;
import com.example.testProject.repository.CompanyRepository;
import com.example.testProject.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public List<Company> getAllCompanyByLocation(String location) {
        return companyRepository.findAllByLocation(location);
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public void updateCompanyData(Company company) {
        companyRepository.updateCompanyData(
                company.getId(),
                company.getName(),
                company.getWebsite(),
                company.getLocation(),
                company.getBudget());
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public boolean uniqueName(String name) {
        return companyRepository.findByName(name) == null;
    }
}
