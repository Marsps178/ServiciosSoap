package cibertec.pe;

public class HiloNumeroLetra implements Runnable {
        private int tipo;

        public HiloNumeroLetra(int tipo) {
            this.tipo = tipo;
        }

        @Override
        public void run() {
            while (true) {
                switch (tipo) {
                    case 1:
                        for (int i = 0; i < 30; i++) {
                            System.out.print(i);
                        }
                        break;
                    case 2:
                        for (char c = 'a'; c < 'z'; c++) {
                            System.out.print(c);
                        }
                        break;
                    default:
                        break;
                }
                
            }
        }
}

