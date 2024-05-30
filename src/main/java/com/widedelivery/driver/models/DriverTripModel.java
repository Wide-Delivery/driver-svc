package com.widedelivery.driver.models;

import com.google.protobuf.Timestamp;
import com.widedelivery.driver.proto.DriverTrip;
import com.widedelivery.order.proto.Order;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trips")
public class DriverTripModel {

    @Id
    private ObjectId id;

    @Setter
    private String driverId;

    @Setter
    private String departureLongitude;

    @Setter
    private String departureLatitude;

    @Setter
    private Timestamp departureTime;

    @Setter
    private String destinationLongitude;

    @Setter
    private String destinationLatitude;

    @Setter
    private Timestamp destinationTime;

    @Setter
    private double freeSpaceLength;

    @Setter
    private double freeSpaceWidth;

    @Setter
    private double freeSpaceHeight;

    @Setter
    private DriverTripStatus status;

    @Setter
    private List<Order> acceptedOrders;

    @CreatedDate
    @Setter
    private Instant createdAt;

    @LastModifiedDate
    @Setter
    private Instant updatedAt;

    @Version
    private Integer version;

    public DriverTrip getGrpcMessage() {
        return DriverTrip.newBuilder()
                .setTripId(this.id.toString())
                .setDriverId(this.driverId)
                .setDepartureLongitude(this.departureLongitude)
                .setDepartureLatitude(this.departureLatitude)
                .setDepartureTime(Timestamp.newBuilder().setSeconds(this.departureTime.getSeconds()).build())
                .setDestinationLongitude(this.destinationLongitude)
                .setDestinationLatitude(this.destinationLatitude)
                .setDestinationTime(Timestamp.newBuilder().setSeconds(this.destinationTime.getSeconds()).build())
                .setFreeSpaceLength(this.freeSpaceLength)
                .setFreeSpaceWidth(this.freeSpaceWidth)
                .setFreeSpaceHeight(this.freeSpaceHeight)
                .setStatus(com.widedelivery.driver.proto.DriverTripStatus.valueOf(this.status.name()))
                .setCreatedAt(Timestamp.newBuilder().setSeconds(this.createdAt.getEpochSecond()).build())
                .setUpdatedAt(Timestamp.newBuilder().setSeconds(this.updatedAt.getEpochSecond()).build())
                .build();
    }

    @Override
    public String toString() {
        return "DriverTripModel{" +
                "id=" + id +
                ", driverId='" + driverId + '\'' +
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
                ", acceptedOrders=" + acceptedOrders +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", version=" + version +
                '}';
    }
}
