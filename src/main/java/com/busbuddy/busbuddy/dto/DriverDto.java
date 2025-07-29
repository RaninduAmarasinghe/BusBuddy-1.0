package com.busbuddy.busbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
    private String  driverName;
    private String driverEmail;
   // private String nic;
    private String driverPhone;
    private String driverPassword;
    private String companyId;
    private String busId;

}
