package co.edu.uceva.analisisjni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalisisJniApplication {

    static {
        try {
            System.loadLibrary("analisisnumerico");
            System.out.println("Librería nativa cargada: libanalisisnumerico.so");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Error cargando librería nativa: " + e.getMessage());
            System.err.println("Verifica que libanalisisnumerico.so esté en:");
            System.err.println("- java.library.path");
            System.err.println("- /usr/local/lib/");
            System.err.println("- O en la ruta especificada en Dockerfile");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AnalisisJniApplication.class, args);
    }
}