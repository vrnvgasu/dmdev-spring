package ru.edu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.edu.database.repository.CrudRepository;
import ru.web.config.WebConfiguration;

// можно подключить xml и читать бины также оттуда
//@ImportResource("classpath:application.xml")
@Import(WebConfiguration.class)
@Configuration
// вместо <context:property-placeholder location="classpath:application.properties"/>
// можно file или http
@PropertySource("classpath:application.properties")
// вместо <context:component-scan
@ComponentScan(basePackages = "ru.edu", useDefaultFilters = false, includeFilters = {
  @Filter(type = FilterType.ANNOTATION, value = Component.class),
  @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
  @Filter(type = FilterType.REGEX, pattern = "ru\\.edu\\..+Repository")
})
public class ApplicationConfiguration {

}
