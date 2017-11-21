package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService)
                .sessionManagement()
                    .invalidSessionUrl("/")
                .and().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").anonymous()
                .antMatchers("/login").anonymous()
                .antMatchers(HttpMethod.POST, "/registerUser").anonymous()
                .antMatchers(HttpMethod.GET, "/tournament").authenticated()
                .antMatchers(HttpMethod.GET, "/ranking").authenticated()
                .antMatchers(HttpMethod.GET, "/tournament/**").permitAll()
                .antMatchers(HttpMethod.GET, "/ranking/**").permitAll()
                .antMatchers(HttpMethod.POST, "/create/**").authenticated()
                .antMatchers(HttpMethod.POST, "/update/**").authenticated()
                .antMatchers(HttpMethod.POST, "/remove/**").authenticated()
                .antMatchers(HttpMethod.POST, "/delete/**").authenticated()
                .antMatchers(HttpMethod.POST, "/swap/**").authenticated()
                .antMatchers(HttpMethod.POST, "/comment/**").authenticated()
                .antMatchers(HttpMethod.POST, "/reply/**").authenticated()
                .antMatchers(HttpMethod.POST, "/accept/**").authenticated()
                .antMatchers(HttpMethod.POST, "/reject/**").authenticated()
                .antMatchers("/**").permitAll()
                .and().formLogin().usernameParameter("j_username")
                .passwordParameter("j_password")
                .defaultSuccessUrl("/", false)
                .loginPage("/login")
                .and().rememberMe()
                .rememberMeParameter("j_rememberme")
                .userDetailsService(userDetailsService)
                .key("Xk5JrZuGN5uwD9jPBeTD2GaPgGJ7Bp832svkugEkF6kS3cxqcOTxw892ZRg7bE7OPqC8XyJFphxKGxg70uKDnfbKp4CtGyz8lEbXbsoCtwEabtyqTgaYnsOGkui1ul7K") //TODO: input stream
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf().disable();
    }

    @Override
    public void configure (final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/css/**", "/resources/js/**", "/img/**", "/favicon.ico", "/403");
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
