package com.smithpalacehotel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.text.ParseException;
import com.smithpalacehotel.services.*;

@Configuration
@Profile("test")
public class TestConfig {
    @Autowired
    private _DBService dbService;

    @Bean
    public Boolean instatiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }
}
