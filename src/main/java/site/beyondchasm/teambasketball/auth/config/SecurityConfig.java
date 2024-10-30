package site.beyondchasm.teambasketball.auth.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.beyondchasm.teambasketball.auth.enums.UserRole;
import site.beyondchasm.teambasketball.auth.filter.JwtFilter;
import site.beyondchasm.teambasketball.auth.service.JwtTokenService;
import site.beyondchasm.teambasketball.exception.ExceptionHandlerFilter;
import site.beyondchasm.teambasketball.user.service.UserService;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final JwtTokenService jwtTokenService;
  private final UserService userService;

  @Bean
  public AuthenticationManager authenticationManager(
      final AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
    return http.cors(withDefaults())
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger 경로 허용
            .requestMatchers("/api/users/**").hasAuthority(UserRole.USER.getRole())
            .requestMatchers("/api/notifications/**").permitAll()
            .anyRequest().authenticated())
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
        .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
        .addFilterBefore(new JwtFilter(jwtTokenService, userService),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new ExceptionHandlerFilter(), JwtFilter.class)
        .build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring()
        .requestMatchers("/api/auth/oauth/**", "/api/auth/tokens/refresh", "/swagger-ui/**",
            "/v3/api-docs/**");
  }
}
