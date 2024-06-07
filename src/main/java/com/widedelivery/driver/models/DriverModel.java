package com.widedelivery.driver.models;

import com.widedelivery.driver.proto.DriverOuterClass;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "drivers")
public class DriverModel {

    @Id
    @Setter
    private ObjectId driverId;

//    @NonNull
    @Setter
    private ObjectId userId; // Reference to the user ID

    @Setter
    private boolean mayBeLoader;

    @Setter
    private int searchRadius;

    @Setter
    private Truck truck;

    @CreatedDate
    @Setter
    private Instant createdAt;

    @LastModifiedDate
    @Setter
    private Instant updatedAt;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Truck {
//        @NonNull
        private String truckBrand;

//        @NonNull
        private String truckModel;

//        @NonNull
        private String truckPlate;

//        @NonNull
        private String truckSerialNumber;

//        @NonNull
        private String truckColor;

//        @NonNull
        private TruckType truckType;

        private double freeSpaceLength;

        private double freeSpaceWidth;

        private double freeSpaceHeight;

        private int transportationsCount;

        private boolean isAvailableNow;

        public com.widedelivery.driver.proto.Truck getGrpcMessage() {
            return com.widedelivery.driver.proto.Truck
                    .newBuilder()
                    .setTruckBrand(this.truckBrand)
                    .setTruckModel(this.truckModel)
                    .setTruckPlate(this.truckPlate)
                    .setTruckSerialNumber(this.truckSerialNumber)
                    .setTruckColor(this.truckColor)
                    .setTruckType(com.widedelivery.driver.proto.TruckType.valueOf(this.truckType.name()))
                    .setFreeSpaceLength(this.freeSpaceLength)
                    .setFreeSpaceWidth(this.freeSpaceWidth)
                    .setFreeSpaceHeight(this.freeSpaceHeight)
                    .setTransportationsCount(this.transportationsCount)
                    .setIsAvailableNow(this.isAvailableNow)
                    .build();
        }

        @Override
        public String toString() {
            return "Truck{" +
                    "truckBrand='" + truckBrand + '\'' +
                    ", truckModel='" + truckModel + '\'' +
                    ", truckPlate='" + truckPlate + '\'' +
                    ", truckSerialNumber='" + truckSerialNumber + '\'' +
                    ", truckColor='" + truckColor + '\'' +
                    ", truckType=" + truckType +
                    ", freeSpaceLength=" + freeSpaceLength +
                    ", freeSpaceWidth=" + freeSpaceWidth +
                    ", freeSpaceHeight=" + freeSpaceHeight +
                    ", transportationsCount=" + transportationsCount +
                    ", isAvailableNow=" + isAvailableNow +
                    '}';
        }
    }



    public DriverOuterClass.Driver getGrpcMessage() {
        return DriverOuterClass.Driver
                .newBuilder()
                .setDriverId(this.driverId.toString())
                .setUserId(this.userId.toString())
                .setMayBeLoader(this.mayBeLoader)
                .setSearchRadius(this.searchRadius)
                .setTruck(this.truck.getGrpcMessage())
                .build();
    }

    @Override
    public String toString() {
        return "DriverModel{" +
                "driverId='" + driverId + '\'' +
                ", userId='" + userId + '\'' +
                ", mayBeLoader=" + mayBeLoader +
                ", searchRadius=" + searchRadius +
                ", truck=" + truck +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
