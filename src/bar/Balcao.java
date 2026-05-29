package bar;

import java.util.ArrayList;
import java.util.List;

public class Balcao {
	private final List<Cliente> clientesPedindo = new ArrayList<>();
    private final List<Garcom> garconsCheios = new ArrayList<>();
    public boolean aberto = true;
    private int rodadas = 0;
    private int maxRodadas;
    
    public Balcao(int maxRodadas) {
    	this.maxRodadas = maxRodadas;
    }
    
    public synchronized void fechar() {
        this.aberto = false;
        notifyAll();
    }

    // Cliente
    public synchronized void fazerPedido(Cliente c) {
        clientesPedindo.add(c);
        notifyAll();
    }

    // Garçom
    public synchronized Cliente garcomPegaPedido() throws InterruptedException {
        while (aberto && clientesPedindo.isEmpty()) {
            wait();
        }
        
        if (clientesPedindo.isEmpty()) return null;
        
        return clientesPedindo.remove(0);
    }

    public synchronized void garcomEncheu(Garcom g) {
        garconsCheios.add(g);
        notifyAll();
    }
    
    public boolean ngmPedindo() {
    	return clientesPedindo.isEmpty();
    }

    // Bartender
    public synchronized Garcom bartenderPegaGarcom() throws InterruptedException {
        while (aberto && garconsCheios.isEmpty()) {
            wait();
        }
        if (garconsCheios.isEmpty()) return null;
        return garconsCheios.remove(0);
    }
    
    public synchronized void aumentaRodadas() {
    	rodadas++;
    	System.out.println("Rodada " + rodadas);
    	if(rodadas >= maxRodadas) {
    		fechar();
    		notifyAll();
    		for (Garcom g : garconsCheios) {
                synchronized (g) {
                    g.notify();
                }
            }
    	}
    		
    }

}
