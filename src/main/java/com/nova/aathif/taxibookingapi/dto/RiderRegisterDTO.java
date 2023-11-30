package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RiderRegisterDTO {
    private String mobile;
    private String password;
    private String name;
    private int vehicle_type_id;
    private String  plate_no;
    private String  lisence_no;
    private String nic;
    private String address;
    private String notification_key;

}
