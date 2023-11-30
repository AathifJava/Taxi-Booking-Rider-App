package com.nova.aathif.taxibookingapi.model;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rider_login_history")
public class RiderLoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_login_history_id")
    private int rider_login_history_id;
    @Column(name = "rider_id")
    private int riderId;
    @ManyToOne
    @JoinColumn(name = "rider_id", referencedColumnName = "rider_Id", updatable = false, insertable = false)
    private Rider rider;
    @Column(name = "date", length = 45)
    private String date;
    @Column(name = "time", length = 45)
    private String time;
    @Column(name = "status")
    private int status =1;
}