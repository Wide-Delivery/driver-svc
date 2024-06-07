package com.widedelivery.driver.models;

import com.widedelivery.driver.proto.MakeDriverFromUserInput;
import com.widedelivery.driver.proto.RpcMakeDriverFromUser;
import com.widedelivery.driver.proto.TruckInput;
import com.widedelivery.driver.proto.TruckOuterClass;
import lombok.*;
import org.bson.types.ObjectId;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreCreatedDriverModel {

    private ObjectId userId;

    private boolean mayBeLoader;

    private int searchRadius;

    private PreCreatedTruckModel truck;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreCreatedTruckModel {
        @NonNull
        private String truckBrand;

        @NonNull
        private String truckModel;

        @NonNull
        private String truckPlate;

        @NonNull
        private String truckSerialNumber;

        @NonNull
        private String truckColor;

        private double freeSpaceLength;

        private double freeSpaceWidth;

        private double freeSpaceHeight;

        public static PreCreatedTruckModel getFromGrpcRequest(TruckInput createTruckInput) {
            PreCreatedTruckModel newTruck = new PreCreatedTruckModel();
            newTruck.setTruckBrand(createTruckInput.getTruckBrand());
            newTruck.setTruckModel(createTruckInput.getTruckModel());
            newTruck.setTruckPlate(createTruckInput.getTruckPlate());
            newTruck.setTruckSerialNumber(createTruckInput.getTruckSerialNumber());
            newTruck.setTruckColor(createTruckInput.getTruckColor());
            newTruck.setFreeSpaceLength(createTruckInput.getFreeSpaceLength());
            newTruck.setFreeSpaceWidth(createTruckInput.getFreeSpaceWidth());
            newTruck.setFreeSpaceHeight(createTruckInput.getFreeSpaceHeight());
            // TODO determine truck type
            return newTruck;
        }

        public DriverModel.Truck getTruckModel() {
            DriverModel.Truck newTruck = new DriverModel.Truck();
            newTruck.setTruckBrand(this.truckBrand);
            newTruck.setTruckModel(this.truckModel);
            newTruck.setTruckPlate(this.truckPlate);
            newTruck.setTruckSerialNumber(this.truckSerialNumber);
            newTruck.setTruckColor(this.truckColor);
            newTruck.setFreeSpaceLength(this.freeSpaceLength);
            newTruck.setFreeSpaceWidth(this.freeSpaceWidth);
            newTruck.setFreeSpaceHeight(this.freeSpaceHeight);
            return newTruck;
        }
    }

    public static PreCreatedDriverModel getFromGrpcRequest(MakeDriverFromUserInput createOrderInput) {
        PreCreatedDriverModel newDriver = new PreCreatedDriverModel();
        newDriver.setUserId(new ObjectId(createOrderInput.getUserId()));
        newDriver.setSearchRadius(createOrderInput.getSearchRadius());
        newDriver.setMayBeLoader(createOrderInput.getMayBeLoader());
        newDriver.setTruck(PreCreatedTruckModel.getFromGrpcRequest(createOrderInput.getTruck()));
        return newDriver;
    }

    public DriverModel getDriverModel() {
        DriverModel newDriver = new DriverModel();
        newDriver.setUserId(this.userId);
        newDriver.setMayBeLoader(this.mayBeLoader);
        newDriver.setSearchRadius(this.searchRadius);
        newDriver.setTruck(this.truck.getTruckModel());
        return newDriver;
    }
}
