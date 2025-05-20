package co.edu.uceva.analisisjni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalisisJniApplication {
    static {
        // Cargar la librería nativa
        System.loadLibrary("analisisnumerico");
    }

    public static void main(String[] args) {
        SpringApplication.run(AnalisisJniApplication.class, args);
    }
}
