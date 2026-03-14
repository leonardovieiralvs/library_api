package io.github.lsouza.config;

import io.github.lsouza.security.CustomUserDetailsService;
import io.github.lsouza.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(config -> config.loginPage("/login").permitAll())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.GET, "/autores/**").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");

                    authorize.requestMatchers(HttpMethod.GET, "/livros/**").permitAll();
                    authorize.requestMatchers("/livros/**").hasRole("ADMIN");

                    authorize.requestMatchers("/usuarios/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);

    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        return new CustomUserDetailsService(usuarioService);
    }


/*                                          USERS em memoria
    @Bean
    public UserDetailsService userDetailsService (PasswordEncoder encoder) {
        UserDetails user1 = User.builder()
                .username("lvs")
                .password(encoder.encode("12345"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("bira")
                .password(encoder.encode("12345"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
 */
}
