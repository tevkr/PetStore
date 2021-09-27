package ru.mirea.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Главный класс веб-приложения
 * @author Яновский Владислав
 */
@EnableWebMvc
@SpringBootApplication
public class PetstoreApplication {
    /**
     * Входная точка веб-приложения
     * @param args Массив аргументов переданных при запуске веб-приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(PetstoreApplication.class, args);
    }

}
