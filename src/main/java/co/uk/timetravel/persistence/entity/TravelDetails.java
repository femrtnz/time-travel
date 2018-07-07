package co.uk.timetravel.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Table
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column
    private String pgi;

    @Column
    private String place;

    @Column
    private LocalDate date;


}
