package com.busbuddy.busbuddy.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
    private String driverName;
    private String driverEmail;
    private String driverPhone;
    private String driverPassword;
    private String companyId;
    private String busId;
}
