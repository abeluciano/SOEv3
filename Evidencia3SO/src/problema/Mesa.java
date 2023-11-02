package problema;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Mesa {
    private boolean[] tenedores;
    private int fideos;
    private boolean fideosAgotados;

    public Mesa(int numTenedores, int cantidadFideos) {
        this.tenedores = new boolean[numTenedores];
        this.fideos = cantidadFideos;
        this.fideosAgotados = false;
    }

    public synchronized void cogerTenedores(int comensal) {
        int tenedorIzquierda = tenedorIzquierda(comensal);
        int tenedorDerecha = tenedorDerecha(comensal);

        while (!fideosAgotados && (tenedores[tenedorIzquierda] || tenedores[tenedorDerecha])) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!fideosAgotados) {
            tenedores[tenedorIzquierda] = true;
            tenedores[tenedorDerecha] = true;
        }
    }

    public synchronized void dejarTenedores(int comensal) {
        int tenedorIzquierda = tenedorIzquierda(comensal);
        int tenedorDerecha = tenedorDerecha(comensal);

        tenedores[tenedorIzquierda] = false;
        tenedores[tenedorDerecha] = false;

        if (fideosAgotados) {
            notifyAll(); // Notificar a todos los filósofos para que detengan su ejecución
        }
    }

    public int tenedorIzquierda(int i) {
        return i;
    }

    public int tenedorDerecha(int i) {
        if (i == 0) {
            return tenedores.length - 1;
        } else {
            return i - 1;
        }
    }

    public synchronized int getFideos() {
    	if (fideos < 0) {
    		return 0;
    	}
        return fideos;
    }

    public synchronized void comerFideos(int cantidad) {
        fideos -= cantidad;
        if (fideos <= 0) {
            fideosAgotados = true;
            notifyAll(); // Notificar a todos los filósofos para que detengan su ejecución
        }
    }

    public synchronized boolean isFideosAgotados() {
        return fideosAgotados;
    }
}