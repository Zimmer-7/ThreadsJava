package filosofos;

import java.util.Random;

public class Filosofo extends Thread {
	
	public int nome;
	private Hashi h1;
	private Hashi h2;
	private Random rand;
	private boolean satisfeito = false;
	
	public Filosofo(int nome, Hashi h1, Hashi h2) {
		this.nome = nome;
		this.h1 = h1;
		this.h2 = h2;
		this.rand = new Random();
	}
	
	@Override
	public void run() {
		try {
			while(!satisfeito) {
				int meditacao = rand.nextInt(1000, 2000);
				sleep(meditacao);
				
				int chance = rand.nextInt(2);
				boolean sucesso = false;
				
				if(chance == 0) {
					h1.PegaHashi();
					h2.PegaHashi();
				} else {
					h2.PegaHashi();
					h1.PegaHashi();
				}
				System.out.println("Filosofo " + nome + " vai comer.");
				int comendo = rand.nextInt(3000, 5000);
				sleep(comendo);
				
				h1.SoltaHashi();
				h2.SoltaHashi();
				
				chance = rand.nextInt(2);
				if(chance == 0)
					satisfeito = true;
			}
			
		} catch (InterruptedException e) {
			System.out.println("Filosofo " + nome + " foi interrompido.");
		}
		
		System.out.println("Filosofo " + nome + " foi embora.");
		
	}
}
