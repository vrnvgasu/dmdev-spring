package ru.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters.StringToLocalDateConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
//    registry.addConverter(StringToLocalDateConverter.INSTANCE);
  }

}
