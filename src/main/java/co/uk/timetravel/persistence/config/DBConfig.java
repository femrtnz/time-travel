package co.uk.timetravel.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DBConfig {

    private static final String ENTITY_PACKAGE = "co.uk.timetravel.persistence.entity";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_STRATEGY = "hibernate.hbm2ddl.auto";
    private final DatasourceProperties datasourceProperties;

    public DBConfig(DatasourceProperties datasourceProperties) {
        this.datasourceProperties = datasourceProperties;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(datasourceProperties.getDriverClass());
        dataSource.setUsername(datasourceProperties.getUsername());
        dataSource.setPassword(datasourceProperties.getPassword());
        dataSource.setUrl(datasourceProperties.getJdbcURL());
        return dataSource;
    }

    @Bean
    public Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.put(HIBERNATE_DIALECT, datasourceProperties.getDialect());
        properties.put(HIBERNATE_STRATEGY, datasourceProperties.getHbm2ddlStrategy());
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGE);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        return hibernateJpaVendorAdapter;
    }

}
