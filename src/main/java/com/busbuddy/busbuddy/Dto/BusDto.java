package com.busbuddy.busbuddy.Dto;

import com.busbuddy.busbuddy.Model.Location;
import com.busbuddy.busbuddy.Model.Route;
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
