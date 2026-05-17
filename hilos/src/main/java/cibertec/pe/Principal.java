package cibertec.pe;

public class Principal {
    public static void main(String[] args) {
        HiloNumeroLetra h1 = new HiloNumeroLetra(1);
        HiloNumeroLetra h2 = new HiloNumeroLetra(2);

        Thread t1 = new Thread(h1);
        Thread t2 = new Thread(h2);

        t1.start();
        t2.start();

    }
}