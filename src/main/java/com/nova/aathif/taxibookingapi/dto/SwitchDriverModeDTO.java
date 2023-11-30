package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Access;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchDriverModeDTO {
    private String verification;
    private int mode;
}
