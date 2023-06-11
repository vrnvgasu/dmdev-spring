package ru.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.edu.database.entity.Role;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
//    http.authorizeRequests().anyRequest().authenticated();
    // проверяем переход по ссылкам на права пользователя
    http.authorizeHttpRequests(urlConfig -> urlConfig
      // даем всем доступ
      .antMatchers("/login", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
      // доступ только админам
      .antMatchers("/users/{\\d+}/delete").hasAuthority(Role.ADMIN.getAuthority())
      .antMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())

      .anyRequest().authenticated()
    );

    http.logout(logout -> logout
      .logoutUrl("/logout")
      .logoutSuccessUrl("/login")
      .deleteCookies("JSESSIONID"));

    http.formLogin(login -> login
      // перебрасывать на /login
      .loginPage("/login")
      // после успешной аутентификации перебрасывать на /users
      .successForwardUrl("/users"));

    // HTTP-Basic-Authentication с дефолтными настройками
//    http.httpBasic(Customizer.withDefaults());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
