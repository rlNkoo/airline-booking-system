package com.rlnkoo.payload.response;

import com.rlnkoo.embeddable.Address;
import com.rlnkoo.embeddable.GeoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {

    private Long id;
    private String iataCode;
    private String name;
    private String detailedName;
    private String timezone;
    private Address address;
    private CityResponse city;
    private GeoCode geoCode;
}
