package ru.edu.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import ru.edu.database.entity.Role;
import ru.edu.service.UserService;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserService userService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf().disable();
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

    // подключили авторизацию по OAuth2
    http.oauth2Login(config -> config
      .loginPage("/login")
      .defaultSuccessUrl("/users")
      // меняем объект создания аутентификации
      .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())));

    // HTTP-Basic-Authentication с дефолтными настройками
//    http.httpBasic(Customizer.withDefaults());
  }

  private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
    return userRequest -> {
      String email = userRequest.getIdToken().getClaim("email");
      UserDetails userDetails = userService.loadUserByUsername(email);
      // TODO if userDetails==null, то userService.create()

      DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

      Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());
      return (OidcUser) Proxy.newProxyInstance(
        SecurityConfiguration.class.getClassLoader(),
        new Class[]{UserDetails.class, OidcUser.class},
        (proxy, method, args) -> userDetailsMethods.contains(method)
          ? method.invoke(userDetails, args)
          : method.invoke(oidcUser, args)
      );
    };
  }

}
