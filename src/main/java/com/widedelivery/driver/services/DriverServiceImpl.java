package com.widedelivery.driver.services;

import com.widedelivery.driver.models.*;
import com.widedelivery.driver.proto.DriverOuterClass;
import com.widedelivery.driver.proto.TruckOuterClass;
import com.widedelivery.driver.repos.DriverRepository;
import com.widedelivery.driver.repos.DriverTripRepository;
import com.widedelivery.driver.repos.ItemRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    DriverTripRepository driverTripRepository;

    @Autowired
    ItemRepository itemRepository;

    private TruckType getTruckType(DriverModel.Truck truck) {
        double freeSpaceLength = truck.getFreeSpaceLength();
        double freeSpaceWidth = truck.getFreeSpaceWidth();
        double freeSpaceHeight = truck.getFreeSpaceHeight();

        int smallCount = 0;
        int mediumCount = 0;

        // Check for SMALL criteria
        if (freeSpaceLength <= 120) smallCount++;
        if (freeSpaceWidth <= 50) smallCount++;
        if (freeSpaceHeight <= 20) smallCount++;

        // Check for MEDIUM criteria
        if (freeSpaceLength <= 250) mediumCount++;
        if (freeSpaceWidth <= 150) mediumCount++;
        if (freeSpaceHeight <= 150) mediumCount++;

        // Determine the truck type based on the counts
        if (smallCount == 3) {
            return TruckType.SMALL;
        } else if (smallCount > 0) {
            return TruckType.MEDIUM;
        } else if (mediumCount == 3) {
            return TruckType.MEDIUM;
        } else {
            return TruckType.LARGE;
        }
    }

    @Override
    public DriverModel makeDriverFromUser(PreCreatedDriverModel driver) {
        DriverModel driverModel = driver.getDriverModel();
        TruckType truckType = getTruckType(driverModel.getTruck());
        driverModel.getTruck().setTruckType(truckType);

        if (driverRepository.findByUserId(driverModel.getUserId()) != null) {
            throw new IllegalArgumentException("Driver already exists");
        }

        return driverRepository.save(driverModel);
    }

    @Override
    public DriverModel getDriverByUserId(String userId) {
        DriverModel driver = driverRepository.findByUserId(new ObjectId(userId));
        return driver;
    }

    @Override
    public DriverModel getDriverById(String driverId) {
        Optional<DriverModel> optionalDriverModel = driverRepository.findById(new ObjectId(driverId));
        return optionalDriverModel.orElse(null);
    }

    @Override
    public DriverModel updateDriverInfo(DriverOuterClass.Driver driverToUpdate) {
        Optional<DriverModel> optionalExistingDriver = driverRepository.findById(new ObjectId(driverToUpdate.getDriverId()));
        if (optionalExistingDriver.isEmpty()) {
            // TODO Handle driver not found case
            return null;
        }

        DriverModel existingDriver = optionalExistingDriver.get();

        if (driverToUpdate.getSearchRadius() != existingDriver.getSearchRadius()) {
            existingDriver.setSearchRadius(driverToUpdate.getSearchRadius());
        }
        if (driverToUpdate.getMayBeLoader() != existingDriver.isMayBeLoader()) {
            existingDriver.setMayBeLoader(driverToUpdate.getMayBeLoader()); //TODO be carefull on client api
        }
        if (driverToUpdate.getTruck().hashCode() != existingDriver.getTruck().hashCode()) { // TODO not hash code
            TruckOuterClass.Truck truckToUpdate = driverToUpdate.getTruck();
            DriverModel.Truck existedTruck = existingDriver.getTruck();
            if (!truckToUpdate.getTruckBrand().equals(existedTruck.getTruckBrand())) {
                existedTruck.setTruckBrand(truckToUpdate.getTruckBrand());
            }
            if (!truckToUpdate.getTruckModel().equals(existedTruck.getTruckModel())) {
                existedTruck.setTruckModel(truckToUpdate.getTruckModel());
            }
            if (!truckToUpdate.getTruckPlate().equals(existedTruck.getTruckPlate())) {
                existedTruck.setTruckPlate(truckToUpdate.getTruckPlate());
            }
            if (!truckToUpdate.getTruckSerialNumber().equals(existedTruck.getTruckSerialNumber())) {
                existedTruck.setTruckSerialNumber(truckToUpdate.getTruckSerialNumber());
            }
            if (!truckToUpdate.getTruckColor().equals(existedTruck.getTruckColor())) {
                existedTruck.setTruckColor(truckToUpdate.getTruckColor());
            }
            if (truckToUpdate.getFreeSpaceLength() != existedTruck.getFreeSpaceLength()) {
                existedTruck.setFreeSpaceLength(truckToUpdate.getFreeSpaceLength());
            }
            if (truckToUpdate.getFreeSpaceWidth() != existedTruck.getFreeSpaceWidth()) {
                existedTruck.setFreeSpaceWidth(truckToUpdate.getFreeSpaceWidth());
            }
            if (truckToUpdate.getFreeSpaceHeight() != existedTruck.getFreeSpaceHeight()) {
                existedTruck.setFreeSpaceHeight(truckToUpdate.getFreeSpaceHeight());
            }
        }

        return driverRepository.save(existingDriver);
    }


    @Override
    public DriverTripModel createDriverTrip(PreCreatedTripModel newTrip) {
        DriverTripModel driverTripModel = new DriverTripModel();
        driverTripModel.setDriverId(newTrip.getDriverId().toString());
        driverTripModel.setDepartureLongitude(newTrip.getDepartureLongitude());
        driverTripModel.setDepartureLatitude(newTrip.getDepartureLatitude());
        driverTripModel.setDepartureTime(newTrip.getDepartureTime());
        driverTripModel.setDestinationLongitude(newTrip.getDestinationLongitude());
        driverTripModel.setDestinationLatitude(newTrip.getDestinationLatitude());
        driverTripModel.setDestinationTime(newTrip.getDestinationTime());
        driverTripModel.setFreeSpaceLength(newTrip.getFreeSpaceLength());
        driverTripModel.setFreeSpaceWidth(newTrip.getFreeSpaceWidth());
        driverTripModel.setFreeSpaceHeight(newTrip.getFreeSpaceHeight());
        driverTripModel.setStatus(newTrip.getStatus());
        driverTripModel.setCreatedAt(Instant.now());
        driverTripModel.setUpdatedAt(Instant.now());

        return driverTripRepository.save(driverTripModel);
    }

    @Override
    public DriverTripModel getDriverTripById(String tripId) {
        Optional<DriverTripModel> optionalDriverTripModel = driverTripRepository.findById(new ObjectId(tripId));
        return optionalDriverTripModel.orElse(null);
    }

    @Override
    public List<DriverTripModel> getDriverTrips(String driverId) {
        return driverTripRepository.findByDriverId(driverId);
    }

    @Override
    public DriverTripModel updateDriverTripInfo(DriverTripModel tripToUpdate) {
        tripToUpdate.setUpdatedAt(Instant.now());
        return driverTripRepository.save(tripToUpdate);
    }

    @Override
    public boolean removeDriverTrip(String tripId) {
        driverTripRepository.deleteById(new ObjectId(tripId));
        return true;
    }
}
