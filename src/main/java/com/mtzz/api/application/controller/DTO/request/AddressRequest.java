package com.mtzz.api.application.controller.DTO.request;

import com.mtzz.api.application.util.DataFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest extends DataFormat
{
    private Long id;
    private String streetAddress;
    private String cep;
    private String number;
    private String city;
    private Long personId;
}

