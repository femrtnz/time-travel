package co.uk.timetravel;

import co.uk.timetravel.persistence.TravelDetailsRepository;
import co.uk.timetravel.persistence.config.DatasourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties({ DatasourceProperties.class })
@EnableJpaRepositories(basePackageClasses = TravelDetailsRepository.class)
public class TimeTravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeTravelApplication.class, args);
	}
}
