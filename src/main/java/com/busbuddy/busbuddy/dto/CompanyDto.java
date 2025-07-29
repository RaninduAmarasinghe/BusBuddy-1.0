package com.busbuddy.busbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private String companyId;
    private String companyName;
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;
    private String companyPassword;
}
