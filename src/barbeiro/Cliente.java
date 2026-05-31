package barbeiro;

public class Cliente implements Runnable {
    private final Barbearia barbearia;
    // id do cliente
    private final int id;

    public Cliente(Barbearia barbearia, int id) {
        this.barbearia = barbearia;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // tenta entrar no monitor e fazer o processo
            barbearia.clienteEntra(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}