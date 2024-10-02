package com.company.eventos; // Paquete que agrupa clases relacionadas con la aplicación.

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SpringBootCrudAppApplication {

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCrudAppApplication.class, args); // Inicia la aplicación de Spring Boot.
    }

    /**
     * Configura y carga las variables de entorno desde un archivo .env.
     *
     * @param env El entorno configurable de la aplicación.
     * @return El objeto Dotenv cargado con las variables de entorno.
     */
    @Bean // Indica que el método devuelve un objeto gestionado por el contenedor de Spring.
    public Dotenv dotenv(ConfigurableEnvironment env) {
        Dotenv dotenv = Dotenv.configure().load(); // Carga las variables de entorno desde el archivo .env.
        // Almacena las entradas de Dotenv en el entorno de la aplicación.
        dotenv.entries().forEach(entry -> env.getSystemProperties().put(entry.getKey(), entry.getValue()));
        return dotenv; // Retorna el objeto Dotenv.
    }
}

// Clase que escucha el evento de inicio de la aplicación y registra un mensaje.
@Component // Marca esta clase como un componente de Spring que será gestionado por el contenedor.
class StartupMessageListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StartupMessageListener.class); // Logger para registrar mensajes de inicio.

    @Value("${server.port}") // Inyecta el valor de la propiedad 'server.port' desde el archivo de propiedades.
    private int port; // Almacena el puerto en el que la aplicación está escuchando.

    /**
     * Método que se llama cuando la aplicación está lista.
     *
     * @param event El evento que indica que la aplicación está lista.
     */
    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        // Registra un mensaje indicando que la aplicación ha iniciado y en qué puerto se puede acceder.
        logger.info("Aplicación iniciada. Puedes acceder a ella en: http://localhost:{}", port);
    }
}
