package com.busbuddy.busbuddy.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.UUID;

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