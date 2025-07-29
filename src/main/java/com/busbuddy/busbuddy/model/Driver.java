package com.busbuddy.busbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    private String id;

    @Indexed(unique = true)
    private String driverId;

    private String driverName;

    @Indexed(unique = true)
    private String driverEmail;

    private String driverPhone;

    private String driverPassword;

    //private String driverNic;

    private String companyId;
    private String companyName;
    private String busId;
}
