package com.rlnkoo.airlinecoreservice.service;

import com.rlnkoo.enums.AirlineStatus;
import com.rlnkoo.payload.request.AirlineRequest;
import com.rlnkoo.payload.response.AirlineDropdownItem;
import com.rlnkoo.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest request, Long ownerId);

    AirlineResponse getAirlineByOwner(Long ownerId);

    AirlineResponse updateAirline(AirlineRequest request, Long ownerId);

    AirlineResponse getAirlineById(Long id);

    Page<AirlineResponse> getAllAirlines(Pageable pageable);

    void deleteAirline(Long id, Long ownerId);

    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status);

    List<AirlineDropdownItem> getAirlineDropdown();
}
