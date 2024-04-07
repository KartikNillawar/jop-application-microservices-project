package com.kncodes.companyms.Company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompany(),HttpStatus.OK);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<String> updateCompany(@PathVariable Long companyId, @RequestBody Company updateCompany) {
        boolean isUpdated = companyService.updateCompany(updateCompany, companyId);
        if (isUpdated) {
            return new ResponseEntity<>("The company details are updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("The Company details are not updated", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        try {
            companyService.createCompany(company);
            return new ResponseEntity<>("Company created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Company not created", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id)
    {
        boolean isDeleted = companyService.deleteCompanyById(id);
        if(isDeleted)
            return new ResponseEntity<>("Company Successfully Deleted",HttpStatus.OK
            );
        else
            return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND
            );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id)
    {
        Company company = companyService.getCompanyById(id);
        if(company!=null)
            return new ResponseEntity<>(company,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
