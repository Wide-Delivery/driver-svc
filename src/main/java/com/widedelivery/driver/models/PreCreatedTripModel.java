package com.widedelivery.driver.models;

import com.google.protobuf.Timestamp;
import com.widedelivery.driver.proto.CreateDriverTripInput;
import lombok.*;
import org.bson.types.ObjectId;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreCreatedTripModel {

    private ObjectId driverId;

    @NonNull
    private String departureLongitude;

    @NonNull
    private String departureLatitude;

    @NonNull
    private Timestamp departureTime;

    @NonNull
    private String destinationLongitude;

    @NonNull
    private String destinationLatitude;

    @NonNull
    private Timestamp destinationTime;

    private double freeSpaceLength;

    private double freeSpaceWidth;

    private double freeSpaceHeight;

    private DriverTripStatus status;

    public static PreCreatedTripModel getFromGrpcRequest(CreateDriverTripInput createDriverTripInput) {
        PreCreatedTripModel newTrip = new PreCreatedTripModel();
        newTrip.setDriverId(new ObjectId(createDriverTripInput.getDriverId()));
        newTrip.setDepartureLongitude(createDriverTripInput.getDepartureLongitude());
        newTrip.setDepartureLatitude(createDriverTripInput.getDepartureLatitude());
        newTrip.setDepartureTime(createDriverTripInput.getDepartureTime());
        newTrip.setDestinationLongitude(createDriverTripInput.getDestinationLongitude());
        newTrip.setDestinationLatitude(createDriverTripInput.getDestinationLatitude());
        newTrip.setDestinationTime(createDriverTripInput.getDestinationTime());
        newTrip.setFreeSpaceLength(createDriverTripInput.getFreeSpaceLength());
        newTrip.setFreeSpaceWidth(createDriverTripInput.getFreeSpaceWidth());
        newTrip.setFreeSpaceHeight(createDriverTripInput.getFreeSpaceHeight());

        newTrip.setStatus(DriverTripStatus.EXPECTED);
        return newTrip;
    }

    @Override
    public String toString() {
        return "PreCreatedTripModel{" +
                "driverId='" + driverId + '\'' +
                ", departureLongitude='" + departureLongitude + '\'' +
                ", departureLatitude='" + departureLatitude + '\'' +
                ", departureTime=" + departureTime +
                ", destinationLongitude='" + destinationLongitude + '\'' +
                ", destinationLatitude='" + destinationLatitude + '\'' +
                ", destinationTime=" + destinationTime +
                ", freeSpaceLength=" + freeSpaceLength +
                ", freeSpaceWidth=" + freeSpaceWidth +
                ", freeSpaceHeight=" + freeSpaceHeight +
                ", status=" + status +
                '}';
    }
}
