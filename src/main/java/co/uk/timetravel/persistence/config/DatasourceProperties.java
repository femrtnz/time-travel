package co.uk.timetravel.persistence.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "datasource")
public class DatasourceProperties {

    @NotEmpty
    private String driverClass;

    @NotEmpty
    private String jdbcURL;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String hbm2ddlStrategy;

    @NotEmpty
    private String dialect;

}
