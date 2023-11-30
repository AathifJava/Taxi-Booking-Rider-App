package com.nova.aathif.taxibookingapi.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_trip")
public class CustomerTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_trip_id")
    private int customerTripId;
    @Column(name = "customer_id")
    private int customerId;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_Id", updatable = false, insertable = false)
    private Customer customer;
    @Column(name = "vehicle_type_id")
    private int vehicleTypeId;
    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", referencedColumnName = "vehicle_type_Id", updatable = false, insertable = false)
    private VehicleType VehicleType;
    @Column(name = "rider_id")
    private int riderId = 0;
    @Column(name = "start_point", length = 850)
    private String startPoint;
    @Column(name = "end_point", length = 850)
    private String endPoint;
    @Column(name = "start_lat", length = 45)
    private String startLat;
    @Column(name = "start_lon", length = 45)
    private String startLon;
    @Column(name = "end_lat", length = 45)
    private String endLat;
    @Column(name = "end_lot", length = 45)
    private String endLot;
    @Column(name = "customer_note", length = 450)
    private String customerNote;
    @Column(name = "rider_note", columnDefinition = "TEXT")
    private String riderNote;
    @Column(name = "arrived")
    private int arrived = 0;
    @Column(name = "arrived_date", length = 45)
    private String arrivedDate;
    @Column(name = "arrived_time", length = 45)
    private String arrivedTime;
    @Column(name = "trip_date", length = 45)
    private String tripDate;
    @Column(name = "trip_time", length = 45)
    private String tripTime;
    @Column(name = "trip_accept_time", length = 45)
    private String tripAcceptTime;
    @Column(name = "trip_accept_date", length = 45)
    private String tripAcceptDate;
    @Column(name = "trip_start_date", length = 45)
    private String tripStartDate;
    @Column(name = "trip_start_time", length = 45)
    private String tripStartTime;
    @Column(name = "trip_end_date", length = 45)
    private String tripEndDate;
    @Column(name = "trip_end_time", length = 45)
    private String tripEndTime;
    @Column(name = "reject_user_type")
    private int rejectUserType = 0;
    @Column(name = "reject_reason", length = 45)
    private String rejectReason;
    @Column(name = "reject_date", length = 45)
    private String rejectDate;
    @Column(name = "reject_time", length = 45)
    private String rejectTime;
    @Column(name = "trip_distance", columnDefinition = "DOUBLE")
    private String tripDistance = "0";
    @Column(name = "km_rate", columnDefinition = "DOUBLE")
    private String kmRate = "0";
    @Column(name = "company_commission_rate", length = 45)
    private String companyCommissionRate;
    @Column(name = "payment_done")
    private int paymentDone = 0;
    @Column(name = "payment_type")
    private int paymentType = 0;
    @Column(name = "trip_charge", columnDefinition = "DOUBLE")
    private String tripCharge = "0";
    @Column(name = "company_income", length = 45)
    private String companyIncome;
    @Column(name = "driver_income", length = 45)
    private String driverIncome;
    @Column(name = "trip_status")
    private int tripStatus = 0;
}
