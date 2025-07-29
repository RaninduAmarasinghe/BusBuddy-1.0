package com.busbuddy.busbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "admin")
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private String id;
    private String adminName;
    private String adminPassword;
}
