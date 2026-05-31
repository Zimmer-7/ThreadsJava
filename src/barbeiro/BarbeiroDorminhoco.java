package barbeiro;

public class BarbeiroDorminhoco {
    public static void main(String[] args) {
        // define o numero de cadeiras de espera na barbearia
        int N = 1; 
        Barbearia barbearia = new Barbearia(N);

        // inicia a thread do Barbeiro
        Thread barbeiro = new Thread(new Barbeiro(barbearia));
        barbeiro.start();

        // inicia as threads dos Clientes (eles devem chegar em tempos aleatórios)
        int totalClientesParaSimular = 10;
        for (int i = 1; i <= totalClientesParaSimular; i++) {
            Thread cliente = new Thread(new Cliente(barbearia, i));
            cliente.start();
            try {
                // Intervalo aleatorio entre a chegada de cada cliente
                Thread.sleep((int) (Math.random() * 1500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}