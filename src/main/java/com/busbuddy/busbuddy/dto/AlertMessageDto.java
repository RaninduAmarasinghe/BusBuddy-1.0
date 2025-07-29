package com.busbuddy.busbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertMessageDto {
    private String busId;
    private String companyId;
    private String senderName;
    private String contactNumber;
    private String message;
    private String type;
}
