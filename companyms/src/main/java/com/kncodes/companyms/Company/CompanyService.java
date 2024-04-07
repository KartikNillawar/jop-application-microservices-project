package com.kncodes.companyms.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompany();

    boolean updateCompany(Company company, Long id);

    void createCompany(Company company);

    boolean deleteCompanyById(Long id);

    Company getCompanyById(Long id);
}
