package co.uk.timetravel.services;

import co.uk.timetravel.errors.ParadoxException;
import co.uk.timetravel.persistence.TravelDetailsRepository;
import co.uk.timetravel.domain.TravelDetailsDTO;
import co.uk.timetravel.persistence.entity.TravelDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class TravelServiceTests {

    @Autowired
    private TravelService travelService;

    @Autowired
    private TravelDetailsRepository repository;

    @Test
    void given_travelDetails_when_callSave_then_shouldPersist() {
        LocalDate now = LocalDate.now();
        String pgi = "ABCDEF";
        String place = "London";
        travelService.save(TravelDetailsDTO.builder().date(now).pgi(pgi).place(place).build());

        final List<TravelDetails> travelDetails = repository.findAllByPgi(pgi);
        assertFalse(travelDetails.isEmpty());

        final TravelDetails details = travelDetails.stream().findAny().get();
        assertEquals(now, details.getDate());
        assertEquals(pgi, details.getPgi());
        assertEquals(place, details.getPlace());
    }

    @Test
    void given_differentTravelDetails_when_callSave_then_shouldPersist() {
        LocalDate now = LocalDate.now();
        String pgi = "ABCDEF";
        String place = "London";
        travelService.save(TravelDetailsDTO.builder().date(now).pgi(pgi).place(place).build());
        travelService.save(TravelDetailsDTO.builder().date(now.plusDays(1)).pgi(pgi).place(place).build());
        travelService.save(TravelDetailsDTO.builder().date(now.plusDays(2)).pgi(pgi).place(place).build());

        final List<TravelDetails> travelDetails = repository.findAllByPgi(pgi);
        assertFalse(travelDetails.isEmpty());
        assertEquals(3, travelDetails.size());

        assertTrue(travelDetails.stream().anyMatch(x -> x.getDate().equals(now)));
        assertTrue(travelDetails.stream().anyMatch(x -> x.getDate().equals(now.plusDays(1))));
        assertTrue(travelDetails.stream().anyMatch(x -> x.getDate().equals(now.plusDays(2))));
    }

    @Test
    void given_duplicatedPlace_when_callSave_then_throwException() {
        LocalDate now = LocalDate.now();
        String pgi = "ABCDEF";
        String place = "London";
        TravelDetailsDTO travelDetailsDTO = TravelDetailsDTO.builder().date(now).pgi(pgi).place(place).build();
        travelService.save(travelDetailsDTO);

       assertThrows(ParadoxException.class, () -> travelService.save(travelDetailsDTO));

    }
}
