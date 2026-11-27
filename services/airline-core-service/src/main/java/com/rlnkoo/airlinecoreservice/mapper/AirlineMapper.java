package com.rlnkoo.airlinecoreservice.mapper;

import com.rlnkoo.airlinecoreservice.model.Airline;
import com.rlnkoo.embeddable.Support;
import com.rlnkoo.payload.request.AirlineRequest;
import com.rlnkoo.payload.response.AirlineResponse;

public class AirlineMapper {

    public static Airline toEntity(AirlineRequest request, Long ownerId) {
        if (request == null) return null;
        Airline airline = Airline.builder()
                .iataCode(request.getIataCode())
                .icaoCode(request.getIcaoCode())
                .name(request.getName())
                .alias(request.getAlias())
                .logoUrl(request.getLogoUrl())
                .website(request.getWebsite())
                .status(request.getStatus())
                .alliance(request.getAlliance())
                .headquartersCityId(request.getHeadquartersCityId())
                .ownerId(ownerId)
                .build();

        if (request.getSupportEmail() != null || request.getSupportPhone() != null || request.getSupportHours() != null) {
            airline.setSupport(
                    Support.builder()
                            .email(request.getSupportEmail())
                            .phone(request.getSupportPhone())
                            .hours(request.getSupportHours())
                            .build()
            );
        }
        return airline;
    }

    public static AirlineResponse toResponse(Airline airline) {
        if (airline == null) return null;

        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .support(airline.getSupport())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .ownerId(airline.getOwnerId())
                .updatedById(airline.getUpdatedById())
                .build();
    }

    public static void updateEntity(Airline airline, AirlineRequest airlineRequest) {
        if (airline == null || airlineRequest == null) return;

        airline.setIataCode(airlineRequest.getIataCode());
        airline.setIcaoCode(airlineRequest.getIcaoCode());
        airline.setName(airlineRequest.getName());
        airline.setAlias(airlineRequest.getAlias());
        airline.setLogoUrl(airlineRequest.getLogoUrl());
        airline.setWebsite(airlineRequest.getWebsite());
        airline.setStatus(airlineRequest.getStatus());
        airline.setAlliance(airlineRequest.getAlliance());
        airline.setHeadquartersCityId(airlineRequest.getHeadquartersCityId());

        if (airline.getSupport() == null) {
            airline.setSupport(new Support());
        }

        airline.getSupport().setEmail(airlineRequest.getSupportEmail());
        airline.getSupport().setPhone(airlineRequest.getSupportPhone());
        airline.getSupport().setHours(airlineRequest.getSupportHours());
    }
}