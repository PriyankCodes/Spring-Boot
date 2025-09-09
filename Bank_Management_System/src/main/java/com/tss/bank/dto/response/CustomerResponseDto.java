package com.tss.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String city;
    private String state;
    private Long userId;
}
