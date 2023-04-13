package ua.foxminded.springbootjdbcapi.dao;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("ua.foxminded.springbootjdbcapi")
public class SpringJdbcConfig {

    @Bean
    public DataSource postgresDataSource() {
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName("org.postgresql.Driver");
	dataSource.setUrl("jdbc:postgresql://localhost:5433/school_console_app");
	dataSource.setUsername("postgres");
	dataSource.setPassword("1234");

	return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(postgresDataSource());
    }
}
