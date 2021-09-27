package ru.mirea.petstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import ru.mirea.petstore.Services.UserService;

/**
 * Класс-конфигурация позволяет настраивать параметры путем переопределения методов.
 * @author Яновский Владислав
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Класс-сервис для передачи данных из табилцы Бд с пользователями в контроллер
     */
    private final UserService userService;

    /**
     * Конструктор устанавливающий userService
     * @param userService Класс-сервис для передачи данных из табилцы Бд с пользователями в контроллер
     */
    public SpringSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод настраивает доступ к различным ресурсам сайта
     * @param auth Позволяет легко создавать аутентификацию в памяти, аутентификацию LDAP, аутентификацию на основе JDBC, добавлять службы UserDetailsService и добавлять службы AuthenticationProvider
     * @throws Exception Используется для обработки исключений программы
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * Метод настраивает доступ к различным ресурсам сайта
     * @param http Позволяет настраивать веб-безопасность для определенных http-запросов
     * @throws Exception Используется для обработки исключений программы
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/products").permitAll()
                .antMatchers("/product").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .antMatchers("/login", "/sign").permitAll()
                .antMatchers("/logout").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().and().logout().logoutSuccessUrl("/").and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession();
//        http.csrf()
//                .disable()
//                .formLogin()
//                .loginPage("/login.html")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/homepage.html", true)
//                .and()
//                .logout().logoutUrl("/baeldung/logout")
//                .addLogoutHandler(new HeaderWriterLogoutHandler(
//                        new ClearSiteDataHeaderWriter(
//                                ClearSiteDataHeaderWriter.Directive.CACHE,
//                                ClearSiteDataHeaderWriter.Directive.COOKIES,
//                                ClearSiteDataHeaderWriter.Directive.STORAGE)));
    }
}