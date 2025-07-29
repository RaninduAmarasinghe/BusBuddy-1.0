package com.busbuddy.busbuddy.dto;

import com.busbuddy.busbuddy.model.Location;
import com.busbuddy.busbuddy.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {
    private String busId;          // Optional for update
    private String busNumber;
    private String companyId;
    private String driverId;
    private String status;
    private Location location;
    private List<Route> routes;
}
