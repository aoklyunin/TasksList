package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Настройки веб-безопасности
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Сервис для работы с пользователями
     */
    private final UserService userService;
    /**
     * Объект для работы с паролями
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Конструктор сервиса пользователей
     *
     * @param userService - сервис для работы с пользователями
     */
    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Задать настройки безопасности
     *
     * @param httpSecurity - объект веб-безопасности
     * @throws Exception - исклбючение
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // не использовать csrf-токен
                .csrf()
                .disable()
                // позволяет ограничивать запросы
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/auth/register", "/auth/login").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin/**").hasRole("ADMIN")
                //Доступ только для всех авторизованных пользователей
                .antMatchers("/taskList", "/tasks/**").authenticated()
                //Доступ разрешен всем
                .antMatchers("/", "/js/**", "/css/**", "/img/**", "/contact").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                // Настройка для входа в систему
                .formLogin()
                // страница регистрации
                .loginPage("/auth/login")
                //Перенаправление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                // настройка выхода
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }

    /**
     * Глобальные настройки
     *
     * @param auth объект для настройки
     * @throws Exception - исключение
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}