package problema;

public class Principal {

    public static void main(String[] args) {
        Mesa m = new Mesa(5, 20);
        Filosofo[] f = new Filosofo[5];

        for (int i = 0; i < 5; i++) {
            f[i] = new Filosofo(m, i);
            f[i].start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                f[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Estado del plato de fideos: " + m.getFideos());
        for (int i = 0; i < 5; i++) {
            f[i].mostrarDatos();
        }
    }
}