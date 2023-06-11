package ru.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests().anyRequest().authenticated();
    http.formLogin(login -> login
      // перебрасывать на /login
      .loginPage("/login")
      // после успешной аутентификации перебрасывать на /users
      .successForwardUrl("/users")
      // даем всем доступ на /login
      .permitAll());
  }

}
