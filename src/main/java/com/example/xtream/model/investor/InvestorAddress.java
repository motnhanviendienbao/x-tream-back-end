package com.example.xtream.model.investor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class InvestorAddress {

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "street_name_1")
    private String streetName1;

    @Column(name = "street_name_2")
    private String streetName2;

    @Column(name = "city")
    private String city;

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "district")
    private String district;

    @Column(name = "post_code")
    private String postCode;

}
