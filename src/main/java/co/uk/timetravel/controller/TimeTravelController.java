package co.uk.timetravel.controller;

import co.uk.timetravel.domain.TravelDetailsDTO;
import co.uk.timetravel.services.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(path = "/time-travel-service",
        produces = APPLICATION_JSON_UTF8_VALUE,
        consumes = APPLICATION_JSON_UTF8_VALUE)
public class TimeTravelController {

    private final TravelService travelService;

    public TimeTravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping
    public ResponseEntity<TravelDetailsDTO> travelDetails(@RequestBody @Valid TravelDetailsDTO travelDetailsDTO) {
        travelService.evaluateAndPersist(travelDetailsDTO);
        return new ResponseEntity<>(travelDetailsDTO, HttpStatus.CREATED);
    }

}