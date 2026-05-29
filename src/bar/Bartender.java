package bar;

import java.util.Random;

public class Bartender extends Thread {
	
	private Random rand;
	private Balcao b;
	
	public Bartender(Balcao b) {
		this.rand = new Random();
		this.b = b;
	}
	
	@Override
	public void run() {
		try {
			while(b.aberto) {
				Garcom g = b.bartenderPegaGarcom();
				if (g == null) {
                    break;
                }
				PreparaPedido(g);
				b.aumentaRodadas();
			}
		} catch (InterruptedException e) {
			System.out.println("Bartender interrompido.");
		}
		System.out.println("Bartender foi embora.");
		
	}
	
	public void PreparaPedido(Garcom g) {
		System.out.println("Preparando bebidas para o Garçom " + g.nome);
		for(int i = 0; i < g.pedidos.size(); i++) {
			int time = rand.nextInt(1000, 3000); 
			try {
				sleep(time);
			} catch (InterruptedException e) {
				System.out.println("Bartender interrompido no preparo.");
			}
		}
		System.out.println("Hora de entregar garçom " + g.nome);
		g.EntregaPedido();
		
	}
}
