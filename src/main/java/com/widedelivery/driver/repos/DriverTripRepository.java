package com.widedelivery.driver.repos;

import com.widedelivery.driver.models.DriverModel;
import com.widedelivery.driver.models.DriverTripModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverTripRepository extends MongoRepository<DriverTripModel, ObjectId> {
    List<DriverTripModel> findByDriverId(String driverId);
}
