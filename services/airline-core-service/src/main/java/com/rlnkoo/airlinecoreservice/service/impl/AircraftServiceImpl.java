package com.rlnkoo.airlinecoreservice.service.impl;

import com.rlnkoo.airlinecoreservice.mapper.AircraftMapper;
import com.rlnkoo.airlinecoreservice.model.Aircraft;
import com.rlnkoo.airlinecoreservice.model.Airline;
import com.rlnkoo.airlinecoreservice.repository.AircraftRepository;
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
    private final AircraftRepository aircraftRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found"));

        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if (aircraftRepository.existsByCode(aircraft.getCode())) {
            throw new Exception("Aircraft already exists");
        }

        if (aircraft.getSeatingCapacity() < aircraft.getTotalSeats()) {
            throw new Exception("Seating capacity cannot exceed total seats");
        }

        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new Exception("Aircraft does not exist with this id"));

        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("This owner does not have airline"));

        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("This owner does not have airline"));

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());

        if (aircraft == null) {
            throw new Exception("Aircraft does not exist with this id");
        }

        if (request.getCode() != null && !aircraft.getCode().equals(request.getCode()) && aircraftRepository.existsByCode(request.getCode())) {
            throw new Exception("Aircraft already exists");
        }

        AircraftMapper.updateEntity(aircraft, request);

        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("This owner does not have airline"));

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());

        if (aircraft == null) {
            throw new Exception("Aircraft does not exist with this id");
        }

        aircraftRepository.delete(aircraft);
    }
}