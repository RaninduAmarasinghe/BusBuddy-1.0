package com.busbuddy.busbuddy.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

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
