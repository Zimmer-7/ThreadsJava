package bar;

import java.util.ArrayList;
import java.util.List;

public class Garcom extends Thread {
	
	public int nome;
	public int capacidade;
	public List<Cliente> pedidos;
	private Balcao b;
	
	public Garcom(int nome, int capacidade, Balcao b) {
		this.nome = nome;
		this.capacidade = capacidade;
		this.pedidos = new ArrayList<>();
		this.b = b;
	}
	
	@Override
	public void run() {
		try {
			while(b.aberto) {
				Cliente c = b.garcomPegaPedido();
				if (c == null) {
	                continue; 
	            }
				pedidos.add(c);
				
				System.out.println("Garçom " + nome + " anotou o pedido do cliente " + c.nome);
				
				if(pedidos.size() >= capacidade || (!pedidos.isEmpty() && b.ngmPedindo())) {
					b.garcomEncheu(this);
					synchronized (this) {
		                this.wait(); 
		            }
				}
			}
			
		} catch (InterruptedException e) {
			System.out.println("Garçom " + nome + " foi interrompido.");
		}
		
		System.out.println("Garçom " + nome + " foi embora.");
		
	}

	public void EntregaPedido() {
		for(int i = 0; i < pedidos.size(); i++) {
			Cliente c = pedidos.get(i);
			
			synchronized (c) {
	            c.servido = true;
	            System.out.println("Cliente " + c.nome + " servido.");
	            
	            c.notify();
	        }
		}
		pedidos.clear();
		synchronized (this) {
	        this.notify(); 
	    }
	}
}
