package com.busbuddy.busbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    private String companyId;


    private String companyName;
    private String companyAddress;
    @Indexed(unique = true)
    private String companyEmail;
    private String companyPhone;
    private String companyPassword;
}