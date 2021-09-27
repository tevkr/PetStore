package ru.mirea.petstore;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Класс реализующий WebMvcConfigurer для добавления обработчиков для обслуживания статических ресурсов
 * @author Яновский Владислав
 */
@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    /**
     * Расположение ресурсов веб-приложения
     */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

    /**
     * Добавляет обработчики для обслуживания статических ресурсов, таких как изображения, файлы js и css, из определенных расположений в корневом каталоге веб-приложения, пути к классам и других
     * @param registry Хранит регистрации обработчиков ресурсов для обслуживания статических ресурсов, таких как изображения, файлы css и другие
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS).resourceChain(false);
    }
}