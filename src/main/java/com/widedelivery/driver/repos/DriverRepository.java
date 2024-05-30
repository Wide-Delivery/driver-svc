package com.widedelivery.driver.repos;

import com.widedelivery.driver.models.DriverModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends MongoRepository<DriverModel, ObjectId> {

    @Query("{userId: ObjectId('?0')}")
    DriverModel findByUserId(ObjectId userId);
}
