package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderNotificationDTO {
    private int riderId;
    private String notification;
    private String notificationTopic;
    private int showToUser;
}
