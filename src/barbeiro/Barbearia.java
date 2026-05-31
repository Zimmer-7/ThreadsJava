package barbeiro;

public class Barbearia {
    // variaveis de estado da barbearia
    private final int totalCadeirasEspera;
    private int pessoasNaFila = 0;
    private boolean barbeiroOcupado = false;

    public Barbearia(int totalCadeirasEspera) {
        this.totalCadeirasEspera = totalCadeirasEspera;
    }

    // VISÃO DO CLIENTE  
    public synchronized void clienteEntra(int id) throws InterruptedException {
        // cliente verifica se esta cheia
        if (pessoasNaFila == totalCadeirasEspera) {
            System.out.println("-> Cliente " + id + " viu a barbearia lotada e foi embora.");
            return; // Vai embora sem cortar
        }

        // Tem vaga, o cliente entra e senta.
        pessoasNaFila++;
        System.out.println("-> Cliente " + id + " sentou na espera. (Fila: " + pessoasNaFila + ")");
        
        // espera a vez (se o barbeiro estiver ocupado)
        while (barbeiroOcupado) {
            wait(); // Fica "congelado" aguardando ser chamado
        }

        // A cadeira do barbeiro liberou e o cliente foi
        pessoasNaFila--;
        barbeiroOcupado = true; // SENTA NA CADEIRA
        System.out.println("-> Cliente " + id + " sentou na cadeira para cortar o cabelo.");
        
        // AVISA O BARBEIRO QUE ELE SENTOU NA CADEIRA
        notifyAll(); 
    }

    // VISÃO DO BARBEIRO 
    public synchronized void barbeiroEspera() throws InterruptedException {
        // Se a cadeira tá vazia E não tem ninguem na fila, ele ronca
        if (!barbeiroOcupado && pessoasNaFila == 0) {
            System.out.println("=> Barbeiro: 'Ninguem aqui... Vou tirar um ronco. Zzz...'");
        }
        
        // O Barbeiro ESPERA OBRIGATORIAMENTE um cliente sentar na cadeira
        while (!barbeiroOcupado) {
            wait(); // Fica "congelado" até o cliente sentar e dar o notifyAll()
        }
        
        System.out.println("=> Barbeiro: 'Vamo lançar o corte na régua.'");
    }

    public synchronized void barbeiroTerminaCorte() {
        // acaba o corte
        barbeiroOcupado = false;
        System.out.println("=> Barbeiro: 'Proximo!'");
        
        // chama o próximo cliente da fila
        notifyAll();
    }
}