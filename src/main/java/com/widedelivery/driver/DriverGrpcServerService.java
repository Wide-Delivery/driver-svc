package com.widedelivery.driver;

import com.widedelivery.driver.models.DriverModel;
import com.widedelivery.driver.models.DriverTripModel;
import com.widedelivery.driver.models.PreCreatedDriverModel;
import com.widedelivery.driver.models.PreCreatedTripModel;
import com.widedelivery.driver.proto.*;
import com.widedelivery.driver.service.*;
import com.widedelivery.driver.services.DriverService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@GrpcService
public class DriverGrpcServerService extends DriverServiceGrpc.DriverServiceImplBase {
    private static final Logger logger = Logger.getLogger(DriverGrpcServerService.class.getName());

    @Autowired
    DriverService driverService;

    @Override
    public void makeDriverFromUser(MakeDriverFromUserInput request, StreamObserver<DriverOuterClass.Driver> responseObserver) {
        PreCreatedDriverModel preCreatedDriverModel = PreCreatedDriverModel.getFromGrpcRequest(request);

        try {
            DriverModel driverModel = driverService.makeDriverFromUser(preCreatedDriverModel);
            DriverOuterClass.Driver driverGrpcMessage = driverModel.getGrpcMessage();
            responseObserver.onNext(driverGrpcMessage);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.ALREADY_EXISTS.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getDriverByUserId(UserId request, StreamObserver<DriverOuterClass.Driver> responseObserver) {
        DriverModel driverModel = driverService.getDriverByUserId(request.getUserId());
        if (driverModel != null) {
            DriverOuterClass.Driver driverGrpcMessage = driverModel.getGrpcMessage();
            responseObserver.onNext(driverGrpcMessage);
        } else {
            responseObserver.onError(new Exception("Driver not found"));
        }
        responseObserver.onCompleted();
    }

    @Override
    public void updateDriverInfo(DriverOuterClass.Driver request, StreamObserver<DriverOuterClass.Driver> responseObserver) {
        DriverModel updatedDriverModel = driverService.updateDriverInfo(request);
        if (updatedDriverModel != null) {
            DriverOuterClass.Driver updatedDriverGrpcMessage = updatedDriverModel.getGrpcMessage();
            responseObserver.onNext(updatedDriverGrpcMessage);
        } else {
            responseObserver.onError(new Exception("Driver update failed"));
        }
        responseObserver.onCompleted();    }

    @Override
    public void getDriverById(DriverId request, StreamObserver<DriverOuterClass.Driver> responseObserver) {
        DriverModel driverModel = driverService.getDriverById(request.getDriverId());
        if (driverModel != null) {
            DriverOuterClass.Driver driverGrpcMessage = driverModel.getGrpcMessage();
            responseObserver.onNext(driverGrpcMessage);
        } else {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Driver not found").asRuntimeException());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void createDriverTrip(CreateDriverTripInput request, StreamObserver<DriverTrip> responseObserver) {
        PreCreatedTripModel preCreatedTripModel = PreCreatedTripModel.getFromGrpcRequest(request);

        DriverTripModel driverTripModel = driverService.createDriverTrip(preCreatedTripModel);
        DriverTrip driverTripGrpcMessage = driverTripModel.getGrpcMessage();

        responseObserver.onNext(driverTripGrpcMessage);
        responseObserver.onCompleted();
    }

    @Override
    public void getDriverTrips(DriverId request, StreamObserver<DriverTrips> responseObserver) {
        List<DriverTripModel> driverTripModels = driverService.getDriverTrips(request.getDriverId());
        List<DriverTrip> driverTrips = driverTripModels
                .stream()
                .map(DriverTripModel::getGrpcMessage)
                .collect(Collectors.toList());

        DriverTrips response = DriverTrips.newBuilder().addAllTrips(driverTrips).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDriverTripById(DriverTripId request, StreamObserver<DriverTrip> responseObserver) {
        DriverTripModel driverTripModel = driverService.getDriverTripById(request.getDriverTripId());
        if (driverTripModel != null) {
            DriverTrip driverTripGrpcMessage = driverTripModel.getGrpcMessage();
            responseObserver.onNext(driverTripGrpcMessage);
        } else {
            responseObserver.onError(new Exception("Driver Trip not found"));
        }
        responseObserver.onCompleted();
    }

    @Override
    public void updateDriverTrip(UpdateDriverTripInput request, StreamObserver<DriverTrip> responseObserver) {
        super.updateDriverTrip(request, responseObserver);
    }

    @Override
    public void deleteDriverTrip(DriverTripId request, StreamObserver<GenericResponse> responseObserver) {
        boolean isDeleted = driverService.removeDriverTrip(request.getDriverTripId());
        if (isDeleted) {
            responseObserver.onNext(GenericResponse.newBuilder().setStatus("success").setMessage("Driver Trip deleted").build());
        } else {
            responseObserver.onError(new Exception("Driver Trip deletion failed"));
        }
        responseObserver.onCompleted();
    }
}
