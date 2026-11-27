package com.rlnkoo.airlinecoreservice.service;

import com.rlnkoo.payload.request.AircraftRequest;
import com.rlnkoo.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {

    AircraftResponse createAircraft(AircraftRequest aircraft, Long ownerId) throws Exception;

    AircraftResponse getById(Long id);

    List<AircraftResponse> listAllAircraftByOwner(Long ownerId);

    AircraftResponse updateAircraft(AircraftRequest aircraft, Long ownerId);

    void deleteAircraft(Long id,  Long ownerId);
}