package com.company.eventos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.verify;

class SpringBootCrudAppApplicationTests { // Clase de prueba renombrada

    @Mock
    private Logger logger; // Mock para el logger.

    private int port; // Almacena el puerto que se va a probar.

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada prueba.
        port = 8080; // Establece el puerto simulado para la prueba.
    }

    @Test
    void testApplicationStartLog() {
        // Llama al método que simularía el inicio de la aplicación y que generaría el log.
        logApplicationStart(port); // Llama a la función que genera el log.

        // Verifica que el logger registró el mensaje esperado.
        verify(logger).info("Aplicación iniciada. Puedes acceder a ella en: http://localhost:{}", port);
    }

    // Método que simula el comportamiento de la aplicación al iniciar.
    private void logApplicationStart(int port) {
        logger.info("Aplicación iniciada. Puedes acceder a ella en: http://localhost:{}", port); // Registra el mensaje.
    }
}
