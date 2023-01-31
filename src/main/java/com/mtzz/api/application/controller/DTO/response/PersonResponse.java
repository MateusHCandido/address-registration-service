package com.mtzz.api.application.controller.DTO.response;

import com.mtzz.api.application.entities.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse
{
    private Long personId;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String birthDate;
    private Long primaryAddress;
    private List<Address> addresses = new ArrayList<>();
}
