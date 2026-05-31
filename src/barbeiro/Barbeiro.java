package barbeiro;

public class Barbeiro implements Runnable {
    private final Barbearia barbearia;

    public Barbeiro(Barbearia barbearia) {
        this.barbearia = barbearia;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Barbeiro aguarda cliente ou dorme
                barbearia.barbeiroEspera();
                
                // Simula o tempo a realizar o corte de cabelo
                System.out.println("Barbeiro cortando");
                Thread.sleep(1000); 
                
                // Finaliza o corte
                barbearia.barbeiroTerminaCorte();
            } catch (InterruptedException e) {
                System.out.println("Barbeiro encerrou os cortes.");
                break;
            }
        }
    }
}