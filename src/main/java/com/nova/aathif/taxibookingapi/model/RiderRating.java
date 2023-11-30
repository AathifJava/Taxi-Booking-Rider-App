package com.nova.aathif.taxibookingapi.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rider_rating")
public class RiderRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_rating_id")
    private int riderRatingId;
    @Column(name = "customer_trip_id")
    private int customerTripId ;
    @Column(name = "date", length = 45)
    private String date;
    @Column(name = "time", length = 45)
    private String time;
    @Column(name = "star_rate", length = 45)
    private String starRate;
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
    @Column(name = "status")
    private int status =1;
}