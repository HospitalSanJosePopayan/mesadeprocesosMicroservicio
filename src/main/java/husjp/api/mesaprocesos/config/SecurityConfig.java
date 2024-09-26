package husjp.api.mesaprocesos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import husjp.api.mesaprocesos.service.impl.UserDetailServiceImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private BasicAuthCredentialsFilter basicAuthCredentialsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(basicAuthCredentialsFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> {
                        authorizeRequests.requestMatchers(AUTH_WHITLIST).permitAll();
//                        Area servicio
                        authorizeRequests.requestMatchers(HttpMethod.GET,"AreaServicio").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");
                        authorizeRequests.requestMatchers(HttpMethod.GET,"AreaServicio/{id}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");

                      // Procesos
                        authorizeRequests.requestMatchers(HttpMethod.GET,"procesos").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");
                        authorizeRequests.requestMatchers(HttpMethod.POST,"procesos").hasAnyRole("ADMIN","MESADEPROCESOS_COORD");
                        authorizeRequests.requestMatchers(HttpMethod.PUT,"procesos/{id}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD");
                        authorizeRequests.requestMatchers(HttpMethod.GET,"procesos/{idarea}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");
                        //SUBPROCESOS
                        authorizeRequests.requestMatchers(HttpMethod.GET,"subprocesos").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");
                        authorizeRequests.requestMatchers(HttpMethod.POST,"subprocesos").hasAnyRole("ADMIN","MESADEPROCESOS_COORD");
                        authorizeRequests.requestMatchers(HttpMethod.PUT,"subprocesos/{id}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD");
                        authorizeRequests.requestMatchers(HttpMethod.GET,"subprocesos/{idproceso}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");
                        //UsuariosProcesos;
                        authorizeRequests.requestMatchers(HttpMethod.GET,"usuarioprocesos").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");
                        authorizeRequests.requestMatchers(HttpMethod.GET,"usuarioprocesos/{documento}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");
                        authorizeRequests.requestMatchers(HttpMethod.GET,"usuarioprocesos/area/{idArea}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD", "MESADEPROCESOS_USER");
                        authorizeRequests.requestMatchers(HttpMethod.POST,"usuarioprocesos").hasAnyRole("ADMIN","MESADEPROCESOS_COORD");
                        authorizeRequests.requestMatchers(HttpMethod.PUT,"usuarioprocesos/{id}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD");
                        authorizeRequests.requestMatchers(HttpMethod.PUT,"usuarioprocesos/estado/{proceso}").hasAnyRole("ADMIN","MESADEPROCESOS_USER");
                        authorizeRequests.requestMatchers(HttpMethod.DELETE,"usuarioprocesos/{id}").hasAnyRole("ADMIN","MESADEPROCESOS_COORD");
                        authorizeRequests.requestMatchers(HttpMethod.DELETE,"usuarioprocesos/transferir").hasAnyRole("ADMIN","MESADEPROCESOS_COORD");
                        
                        authorizeRequests.anyRequest().authenticated();
                    }
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    //123456 PASSWORD
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails userDetails = User.withUsername("admin")
//                .password("$2a$12$VjeG91WALEUJd6ARRMzZWeEU0kAdf2flxgqLO2oR9a25Y/9i1GcNi")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] AUTH_WHITLIST = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };
}
