package com.mtzz.api.application.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long addressId;

    @Column(nullable = false, length = 50)
    private String streetAddress;

    @Column(nullable = false, length = 8)
    private String cep;

    @Column(nullable = false, length = 8)
    private String number;

    @Column(nullable = false, length = 30)
    private String city;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;


    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", streetAddress='" + streetAddress + '\'' +
                ", cep='" + cep + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", person=" + person +
                '}';
    }
}
