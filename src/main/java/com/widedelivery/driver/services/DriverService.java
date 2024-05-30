package com.widedelivery.driver.services;

import com.widedelivery.driver.models.DriverModel;
import com.widedelivery.driver.models.DriverTripModel;
import com.widedelivery.driver.models.PreCreatedDriverModel;
import com.widedelivery.driver.models.PreCreatedTripModel;
import com.widedelivery.driver.proto.DriverOuterClass;

import java.util.List;

public interface DriverService {
    DriverModel makeDriverFromUser(PreCreatedDriverModel driver);
    DriverModel getDriverByUserId(String userId);
    DriverModel getDriverById(String driverId);
    DriverModel updateDriverInfo(DriverOuterClass.Driver driverToUpdate);

    DriverTripModel createDriverTrip(PreCreatedTripModel newTrip);
    DriverTripModel getDriverTripById(String tripId);
    List<DriverTripModel> getDriverTrips(String driverId);
    DriverTripModel updateDriverTripInfo(DriverTripModel tripToUpdate);
    boolean removeDriverTrip(String tripId);
}
