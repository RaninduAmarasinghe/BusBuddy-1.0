package com.busbuddy.busbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {
    private Integer routeNumber;
    private String startPoint;
    private String endPoint;
    private List<String> departureTimes;
    private List<String> arrivalTimes;
}
