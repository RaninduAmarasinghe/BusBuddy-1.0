package com.busbuddy.busbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreationDto {
    private String adminName;
    private String adminPassword;

}