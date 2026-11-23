package com.rlnkoo.locationservice.service.impl;

import com.rlnkoo.locationservice.mapper.AirportMapper;
import com.rlnkoo.locationservice.model.Airport;
import com.rlnkoo.locationservice.model.City;
import com.rlnkoo.locationservice.repository.AirportRepository;
import com.rlnkoo.locationservice.repository.CityRepository;
import com.rlnkoo.locationservice.service.AirportService;
import com.rlnkoo.payload.request.AirportRequest;
import com.rlnkoo.payload.response.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {
        if (airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with the provided IATA code already exists");
        }

        City city = cityRepository.findById(request.getCityId()).orElseThrow(() -> new Exception("City not found"));
        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);

        return AirportMapper.toResponse(savedAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport does not exist with provided ID"));

        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport existingAirport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport does not exist with provided ID"));

        if (request.getIataCode() != null && !existingAirport.getIataCode().equals(request.getIataCode())
            && airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with IATA Code already exists");
        }

        AirportMapper.updateEntity(request, existingAirport);
        Airport updatedAirport = airportRepository.save(existingAirport);

        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport does not exist with provided ID"));

        airportRepository.delete(airport);

    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
