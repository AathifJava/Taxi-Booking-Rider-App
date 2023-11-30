package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpDTO {
    private String mobile;
    private String code;
}
