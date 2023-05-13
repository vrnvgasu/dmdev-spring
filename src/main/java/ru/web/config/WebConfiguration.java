package ru.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("web") // только когда профайл web
@Configuration
public class WebConfiguration {

}
