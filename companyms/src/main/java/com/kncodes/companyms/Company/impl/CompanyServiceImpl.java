package com.kncodes.companyms.Company.impl;


import com.kncodes.companyms.Company.Company;
import com.kncodes.companyms.Company.CompanyRepository;
import com.kncodes.companyms.Company.CompanyService;
import com.kncodes.companyms.Company.clients.ReviewClient;
import com.kncodes.companyms.Company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company updatedCompany, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setDescription(updatedCompany.getDescription());
            company.setName(updatedCompany.getName());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Optional<Company> company = companyRepository.findById(reviewMessage.getCompanyId());

        if(company.isPresent())
        {
            Double rating = company.get().getRating();
            company.get().setRating((rating+reviewMessage.getRating())/2);
            companyRepository.save(company.get());
            System.out.println("data saved");
        }
    }
}
