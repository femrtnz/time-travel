package co.uk.timetravel.domain;

import co.uk.timetravel.validation.PGIValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelDetailsDTO {

    @PGIValid
    private String pgi;

    @NotEmpty(message = "Place must not be empty or null")
    private String place;

    @NotNull(message = "Date must not be empty or null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

}
