package com.company.eventos;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/Home").setViewName("Home");
        registry.addViewController("/proyectos/editar").setViewName("/proyectos/formulario");
        registry.addViewController("/proyectos").setViewName("/proyectos/lista");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("login");
        registry.addViewController("/login?logout=true").setViewName("login");
    }
}
