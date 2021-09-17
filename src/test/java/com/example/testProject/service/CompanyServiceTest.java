package com.example.testProject.service;

import com.example.testProject.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    void saveCompany() {
        Company companyToSave = new Company(null, "Company1", "http://Company1.com", "Minsk", 1000.50, null);
        companyService.saveCompany(companyToSave);
        Company savedCompany = companyService.getCompanyByName("Company1");

        assertNotNull(savedCompany);
        assertEquals(savedCompany.getName(), companyToSave.getName());
        assertEquals(savedCompany.getLocation(), companyToSave.getLocation());
        assertEquals(savedCompany.getWebsite(), companyToSave.getWebsite());
    }

    @Test
    void getCompanyById() {
        Company companyToSave = new Company(null, "Company2", "http://Company2.com", "Minsk", 1000.50, null);
        companyService.saveCompany(companyToSave);
        Company savedCompany = companyService.getCompanyByName("Company2");
        Company savedCompanyId = companyService.getCompanyById(savedCompany.getId()).orElse(null);

        assertNotNull(savedCompanyId);
        assertEquals(savedCompany.getName(), savedCompanyId.getName());
        assertEquals(savedCompany.getLocation(), savedCompanyId.getLocation());
        assertEquals(savedCompany.getWebsite(), savedCompanyId.getWebsite());
    }

    @Test
    void getCompanyByName() {
        Company companyToSave = new Company(null, "Company3", "http://Company3.com", "Minsk", 1000.50, null);
        companyService.saveCompany(companyToSave);
        Company savedCompany = companyService.getCompanyByName("Company3");

        assertNotNull(savedCompany);
        assertEquals(savedCompany.getName(), companyToSave.getName());
        assertEquals(savedCompany.getLocation(), companyToSave.getLocation());
        assertEquals(savedCompany.getWebsite(), companyToSave.getWebsite());
    }

    @Test
    void getAllCompanyByLocation() {
        Company companyToSave = new Company(null, "Company4", "http://Company4.com", "Minsk", 1000.50, null);
        companyService.saveCompany(companyToSave);
        List<Company> companies = companyService.getAllCompanyByLocation("Minsk");
        assertNotNull(companies);
    }

    @Test
    void getAllCompany() {
        Company companyToSave = new Company(null, "Company5", "http://Company5.com", "Minsk", 1000.50, null);
        companyService.saveCompany(companyToSave);
        List<Company> companies = companyService.getAllCompany();
        assertNotNull(companies);
    }

    @Test
    void updateCompanyData() {
        Company companyToSave = new Company(null, "Company6", "http://Company6.com", "Minsk", 1000.50, null);
        companyService.saveCompany(companyToSave);
        Company savedCompany = companyService.getCompanyByName("Company6");

        Company companyToUpdate = new Company(savedCompany.getId(), "Company7", "http://Company7.com", "Brest", 1000.50, null);
        companyService.updateCompanyData(companyToUpdate);
        Company updatedCompany = companyService.getCompanyByName("Company7");

        assertNotNull(updatedCompany);
        assertEquals(updatedCompany.getName(), companyToUpdate.getName());
        assertEquals(updatedCompany.getLocation(), companyToUpdate.getLocation());
        assertEquals(updatedCompany.getWebsite(), companyToUpdate.getWebsite());
    }

    @Test
    void deleteCompany() {
        Company companyToSave = new Company(null, "Company8", "http://Company8.com", "Minsk", 1000.50, null);
        companyService.saveCompany(companyToSave);
        Company savedCompany = companyService.getCompanyByName("Company8");
        companyService.deleteCompany(savedCompany.getId());
        assertNull(companyService.getCompanyByName(savedCompany.getName()));
    }

    @Test
    void uniqueName() {
        Company companyToSave = new Company(null, "Company9", "http://Company9.com", "Minsk", 1000.50, null);
        companyService.saveCompany(companyToSave);
        assertFalse(companyService.uniqueName(companyToSave.getName()));
    }
}