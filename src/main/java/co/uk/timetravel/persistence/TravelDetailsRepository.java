package co.uk.timetravel.persistence;

import co.uk.timetravel.persistence.entity.TravelDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TravelDetailsRepository extends JpaRepository<TravelDetails, BigInteger> {

    List<TravelDetails> findAllByPgi(String pgi);

    Optional<TravelDetails> findByPgiAndPlaceAndDate(String pgi, String place, LocalDate localDate);
}
