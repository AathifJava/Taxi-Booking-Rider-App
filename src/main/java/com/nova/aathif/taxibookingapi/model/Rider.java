package com.nova.aathif.taxibookingapi.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rider")
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_id")
    private int riderId;
    @Column(name = "rider_name", length = 145)
    private String riderName;
    @Column(name = "common_name", length = 150)
    private String CommonName;
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    @Column(name = "nic", length = 45)
    private String nic;
    @Column(name = "dob", length = 45)
    private String dob;
    @Column(name = "mobile", length = 45)
    private String mobile;
    @Column(name = "home_mobile", length = 45)
    private String homeMobile;
    @Column(name = "profile_image",columnDefinition = "TEXT")
    private String profileImage;
    @Column(name = "email")
    private String email;
    @Column(name = "licence_no", length = 45)
    private String licenceNo;
    @Column(name = "username", length = 250)
    private String username;
    @Column(name = "password", length = 250)
    private String password;
    @Column(name = "verification", length = 45)
    private String verification;
    @Column(name = "gender")
    private int gender = 0;
    @Column(name = "birthday", length = 45)
    private String birthday;
    @Column(name = "register_date", length = 45)
    private String registerDate;
    @Column(name = "register_time", length = 45)
    private String registerTime;
    @Column(name = "notification_key", length = 200)
    private String notificationKey;
    @Column(name = "admin_verified")
    private int adminVerified =0;
    @Column(name = "admin_verified_date", length = 45)
    private String adminVerifiedDate;
    @Column(name = "admin_verified_time", length = 45)
    private String adminVerifiedTime;
    @Column(name = "admin_verified_id", length = 45)
    private String adminVerifiedId;
    @Column(name = "im_live")
    private int imLive =0;
    @Column(name = "licence_image_1",columnDefinition = "TEXT")
    private String licenceImage1;
    @Column(name = "licence_image_2",columnDefinition = "TEXT")
    private String licenceImage2;
    @Column(name = "total_cash_earn", length = 45)
    private String totalCashEarn = "0";
    @Column(name = "total_card_earn", length = 45)
    private String totalCardEarn = "0";
    @Column(name = "company_paid", length = 45)
    private String companyPaid = "0";
    @Column(name = "balance", length = 45)
    private String balance = "0";
    @Column(name = "current_lat", length = 50)
    private String currentLat;
    @Column(name = "current_lon", length = 50)
    private String currentLon;
    @Column(name = "status")
    private int status=0;

}
