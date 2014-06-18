package org.JavaArt.TicketManager.config;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 18.06.2014
 * Time: 13:26
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class AppConfiguration {


    @Bean
    public javax.sql.DataSource getDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername("postgres");
        dataSource.setPassword("1111");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/Tickets");
        return dataSource;
    }
}
