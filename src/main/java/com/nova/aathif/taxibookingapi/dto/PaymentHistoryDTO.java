package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryDTO {
    private String verification;
    private String start_date;
    private String end_date;
}
