package com.rlnkoo.locationservice.service.impl;

import com.rlnkoo.locationservice.mapper.CityMapper;
import com.rlnkoo.locationservice.model.City;
import com.rlnkoo.locationservice.repository.CityRepository;
import com.rlnkoo.locationservice.service.CityService;
import com.rlnkoo.payload.request.CityRequest;
import com.rlnkoo.payload.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) throws Exception {
        if (cityRepository.existsByCityCode(request.getCityCode())) {
            throw new Exception("City with given code does already exist");
        }

        City city = CityMapper.toEntity(request);
        City result = cityRepository.save(city);

        return CityMapper.toResponse(result);
    }

    @Override
    public CityResponse getCityById(Long id) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City does not exist with given ID")
        );

        return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest request) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City does not exist with given ID")
        );

        if (cityRepository.existsByCityCode(request.getCityCode())) {
            throw new Exception("City with given code does already exist");
        }

        City updatedCity = cityRepository.save(CityMapper.updateEntity(city, request));

        return CityMapper.toResponse(updatedCity);
    }

    @Override
    public void deleteCity(Long id) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City does not exist with given ID")
        );

        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword, pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String conutryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(conutryCode, pageable).map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }
}