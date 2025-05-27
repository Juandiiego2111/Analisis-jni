package co.edu.uceva.analisisjni;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/analisis-service")
public class AnalisisRestController {

    // Declaraciones nativas para los métodos de C
    private native double puntoFijoNativo(double x0, double tol, int maxIter, String funcion);
    private native double biseccionNativo(double a, double b, double tol, int maxIter, String funcion);
    private native double newtonRaphsonNativo(String fx, String dfx, double x0, double tol, int maxIter);

    @PostMapping("/punto-fijo")
    public ResponseEntity<Resultado> puntoFijo(
            @RequestParam double x0,
            @RequestParam double tol,
            @RequestParam int maxIter,
            @RequestParam String funcion) {

        try {
            validarParametros(tol, maxIter);
            double resultado = puntoFijoNativo(x0, tol, maxIter, funcion);
            return ResponseEntity.ok(new Resultado("puntoFijo", resultado, true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new Resultado("puntoFijo", Double.NaN, false, e.getMessage()));
        }
    }

    @PostMapping("/biseccion")
    public ResponseEntity<Resultado> biseccion(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam double tol,
            @RequestParam int maxIter,
            @RequestParam String funcion) {

        try {
            validarParametros(tol, maxIter);
            if (a >= b) {
                throw new IllegalArgumentException("El valor 'a' debe ser menor que 'b'");
            }
            double resultado = biseccionNativo(a, b, tol, maxIter, funcion);
            return ResponseEntity.ok(new Resultado("biseccion", resultado, true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new Resultado("biseccion", Double.NaN, false, e.getMessage()));
        }
    }

    @PostMapping("/newton-raphson")
    public ResponseEntity<Resultado> newtonRaphson(
            @RequestParam String fx,
            @RequestParam String dfx,
            @RequestParam double x0,
            @RequestParam double tol,
            @RequestParam int maxIter) {

        try {
            validarParametros(tol, maxIter);
            double resultado = newtonRaphsonNativo(fx, dfx, x0, tol, maxIter);
            return ResponseEntity.ok(new Resultado("newtonRaphson", resultado, true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new Resultado("newtonRaphson", Double.NaN, false, e.getMessage()));
        }
    }


    private void validarParametros(double tol, int maxIter) {
        if (tol <= 0) {
            throw new IllegalArgumentException("La tolerancia debe ser mayor que cero");
        }
        if (maxIter <= 0) {
            throw new IllegalArgumentException("El número máximo de iteraciones debe ser mayor que cero");
        }
    }

    public static class Resultado {
        private final String metodo;
        private final double resultado;
        private final boolean exito;
        private final String error;

        public Resultado(String metodo, double resultado, boolean exito, String error) {
            this.metodo = metodo;
            this.resultado = resultado;
            this.exito = exito;
            this.error = error;
        }

        // Getters
        public String getMetodo() { return metodo; }
        public double getResultado() { return resultado; }
        public boolean isExito() { return exito; }
        public String getError() { return error; }
    }
}