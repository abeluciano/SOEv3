package problema;

import java.util.Random;

public class Filosofo extends Thread {
    private Mesa mesa;
    private int comensal;
    private int estomago;
    private long tiempoPensado;
    private long tiempoComiendo;

    public Filosofo(Mesa m, int comensal) {
        this.mesa = m;
        this.comensal = comensal;
        this.estomago = 0;
    }

    public int generarNumeroAleatorio(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public void run() {
        while (!mesa.isFideosAgotados()) {
            this.pensando();
            mesa.cogerTenedores(this.comensal);
            if (!mesa.isFideosAgotados()) {
                this.comiendo();
                mesa.dejarTenedores(this.comensal);
            }
        }
    }

    public void mostrarDatos() {
        System.out.println("Filosofo " + comensal + " estomago: " + this.estomago);
        System.out.println("Filosofo " + comensal + " tiempo comiendo: " + this.tiempoComiendo + " seg");
        System.out.println("Filosofo " + comensal + " tiempo pensando: " + this.tiempoPensado + " seg");
    }

    public void pensando() {
        long inicio = 0, fin = 0;
        try {
            inicio = System.currentTimeMillis();
            sleep(generarNumeroAleatorio(1000, 5000));
            fin = System.currentTimeMillis();
        } catch (InterruptedException ex) {}
        
        long tiempoPensadoActual = (fin - inicio);
        System.out.println("Filosofo " + comensal + " esta pensando: " 
        + (tiempoPensadoActual/1000)+ "seg");
        this.tiempoPensado += tiempoPensadoActual/1000;
        
    }

    public void comiendo() {
    	long inicio = 0, fin = 0;
        int numeroFideos = generarNumeroAleatorio(1, 4);
        this.mesa.comerFideos(numeroFideos);
        System.out.println("Filosofo " + comensal + " incremento valor de estomago :" + "("  + this.estomago + "+" +  +numeroFideos + "=" + (this.estomago + numeroFideos) + ")");
        this.estomago += numeroFideos;
        try {
        	inicio = System.currentTimeMillis();
            sleep(generarNumeroAleatorio(1000, 5000));
            fin = System.currentTimeMillis();
        } catch (InterruptedException ex) {}
        
        long tiempoComiendoActual = (fin - inicio);
        System.out.println("Filosofo " + comensal + " esta comiendo: "
        + (tiempoComiendoActual/1000)+ "seg");
        this.tiempoComiendo += tiempoComiendoActual/1000;
    }
}