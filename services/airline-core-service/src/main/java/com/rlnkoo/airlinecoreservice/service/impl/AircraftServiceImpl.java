package com.rlnkoo.airlinecoreservice.service.impl;

import com.rlnkoo.airlinecoreservice.model.Aircraft;
import com.rlnkoo.airlinecoreservice.model.Airline;
import com.rlnkoo.airlinecoreservice.repository.AirlineRepository;
import com.rlnkoo.airlinecoreservice.service.AircraftService;
import com.rlnkoo.payload.request.AircraftRequest;
import com.rlnkoo.payload.response.AircraftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AirlineRepository airlineRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest aircraft, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found"));

        Aircraft aircraft =
        return null;
    }

    @Override
    public AircraftResponse getById(Long id) {
        return null;
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) {
        return List.of();
    }

    @Override
    public AircraftResponse updateAircraft(AircraftRequest aircraft, Long ownerId) {
        return null;
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) {
    }
}
