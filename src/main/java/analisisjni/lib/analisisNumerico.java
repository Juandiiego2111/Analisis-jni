package analisisjni.lib;

public class analisisNumerico {

    // Bloque estático para cargar la librería nativa
    static {
        try {
            System.loadLibrary("analisisnumerico");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Error al cargar la librería nativa: " + e.getMessage());
            System.exit(1);
        }
    }

    // Métodos nativos (implementados en C)
    private native double puntoFijoNativo(double x0, double tol, int maxIter, String funcion);
    private native double biseccionNativo(double a, double b, double tol, int maxIter, String funcion);
    private native double newtonRaphsonNativo(String fx, String dfx, double x0, double tol, int maxIter);

    /**
     * Método para calcular una raíz usando el método del Punto Fijo
     * @param x0 Valor inicial
     * @param tol Tolerancia permitida
     * @param maxIter Número máximo de iteraciones
     * @param funcion Función g(x) en formato string
     * @return Resultado del cálculo
     */
    public Resultado puntoFijo(double x0, double tol, int maxIter, String funcion) {
        try {
            validarParametros(tol, maxIter);
            validarFuncion(funcion);

            double resultado = puntoFijoNativo(x0, tol, maxIter, funcion);
            return new Resultado("Punto Fijo", resultado, true, null);
        } catch (IllegalArgumentException e) {
            return new Resultado("Punto Fijo", Double.NaN, false, e.getMessage());
        } catch (Exception e) {
            return new Resultado("Punto Fijo", Double.NaN, false, "Error en el cálculo: " + e.getMessage());
        }
    }

    /**
     * Método para calcular una raíz usando el método de Bisección
     * @param a Límite inferior del intervalo
     * @param b Límite superior del intervalo
     * @param tol Tolerancia permitida
     * @param maxIter Número máximo de iteraciones
     * @param funcion Función f(x) en formato string
     * @return Resultado del cálculo
     */
    public Resultado biseccion(double a, double b, double tol, int maxIter, String funcion) {
        try {
            validarParametros(tol, maxIter);
            validarIntervalo(a, b);
            validarFuncion(funcion);

            double resultado = biseccionNativo(a, b, tol, maxIter, funcion);
            return new Resultado("Bisección", resultado, true, null);
        } catch (IllegalArgumentException e) {
            return new Resultado("Bisección", Double.NaN, false, e.getMessage());
        } catch (Exception e) {
            return new Resultado("Bisección", Double.NaN, false, "Error en el cálculo: " + e.getMessage());
        }
    }

    /**
     * Método para calcular una raíz usando el método de Newton-Raphson
     * @param fx Función f(x) en formato string
     * @param dfx Derivada f'(x) en formato string
     * @param x0 Valor inicial
     * @param tol Tolerancia permitida
     * @param maxIter Número máximo de iteraciones
     * @return Resultado del cálculo
     */
    public Resultado newtonRaphson(String fx, String dfx, double x0, double tol, int maxIter) {
        try {
            validarParametros(tol, maxIter);
            validarFuncion(fx);
            validarFuncion(dfx);

            double resultado = newtonRaphsonNativo(fx, dfx, x0, tol, maxIter);
            return new Resultado("Newton-Raphson", resultado, true, null);
        } catch (IllegalArgumentException e) {
            return new Resultado("Newton-Raphson", Double.NaN, false, e.getMessage());
        } catch (Exception e) {
            return new Resultado("Newton-Raphson", Double.NaN, false, "Error en el cálculo: " + e.getMessage());
        }
    }

    // Métodos de validación privados
    private void validarParametros(double tol, int maxIter) {
        if (tol <= 0) {
            throw new IllegalArgumentException("La tolerancia debe ser mayor que cero");
        }
        if (maxIter <= 0) {
            throw new IllegalArgumentException("El número máximo de iteraciones debe ser mayor que cero");
        }
    }

    private void validarIntervalo(double a, double b) {
        if (a >= b) {
            throw new IllegalArgumentException("El intervalo [a,b] no es válido (a debe ser menor que b)");
        }
    }

    private void validarFuncion(String funcion) {
        if (funcion == null || funcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La función no puede estar vacía");
        }
    }

    /**
     * Clase interna para encapsular los resultados de los cálculos
     */
    public static class Resultado {
        private final String metodo;
        private final double valor;
        private final boolean exito;
        private final String mensajeError;

        public Resultado(String metodo, double valor, boolean exito, String mensajeError) {
            this.metodo = metodo;
            this.valor = valor;
            this.exito = exito;
            this.mensajeError = mensajeError;
        }

        // Getters
        public String getMetodo() { return metodo; }
        public double getValor() { return valor; }
        public boolean isExito() { return exito; }
        public String getMensajeError() { return mensajeError; }

        @Override
        public String toString() {
            return exito ?
                    String.format("%s: resultado = %.6f", metodo, valor) :
                    String.format("%s: ERROR - %s", metodo, mensajeError);
        }
    }

    /**
     * Método para probar la conexión con la librería nativa
     * @return true si la conexión es exitosa
     */
    public native boolean testConnection();
}