package co.uk.timetravel.services;

import co.uk.timetravel.domain.TravelDetailsDTO;
import co.uk.timetravel.errors.ParadoxException;
import co.uk.timetravel.persistence.TravelDetailsRepository;
import co.uk.timetravel.persistence.entity.TravelDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TravelService {

    private final TravelDetailsRepository repository;

    public TravelService(TravelDetailsRepository repository) {
        this.repository = repository;
    }

    public void evaluateAndPersist(TravelDetailsDTO travelDetailsDTO) {
        validateDate(travelDetailsDTO);

        final TravelDetails travelDetails = TravelDetails.builder()
                .date(travelDetailsDTO.getDate())
                .pgi(travelDetailsDTO.getPgi())
                .place(travelDetailsDTO.getPlace())
                .build();
        repository.save(travelDetails);

    }

    private void validateDate(TravelDetailsDTO travelDetailsDTO) {
        final Optional<TravelDetails> travelDetailsList = repository.findByPgiAndPlaceAndDate(
                travelDetailsDTO.getPgi(), travelDetailsDTO.getPlace(), travelDetailsDTO.getDate());
        if (travelDetailsList.isPresent()) {
            throw new ParadoxException();
        }
    }
}
