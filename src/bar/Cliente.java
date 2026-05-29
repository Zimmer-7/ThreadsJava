package bar;

import java.util.Random;

public class Cliente extends Thread{
	
	public int nome;
	public boolean servido = false;
	private boolean satisfeito = false;
	private Random rand;
	private Balcao b;
	
	public Cliente(int nome, Balcao b) {
		this.nome = nome;
		this.rand = new Random();
		this.b = b;
	}
	
	@Override
	public void run() {
		while(b.aberto && !satisfeito) {
			b.fazerPedido(this);
			EsperaPedido();
			ConsomePedido();
		}
		System.out.println("Cliente " + nome + " foi embora.");
	}
	
	public void EsperaPedido() {
		synchronized (this) {
            while (!servido && b.aberto) {
                try {
                    System.out.println("Cliente " + nome + " aguardando...");
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println("Cliente " + nome + " teve a espera interrompida.");
                }
            }
        }
			
	}
	
	public void ConsomePedido() {
		System.out.println("Cliente " + nome + " consumindo");
		int time = rand.nextInt(2000, 4000); 
		try {
		sleep(time);
			} catch (InterruptedException e) {
			e.printStackTrace();
		}
		servido = false;
		System.out.println("Cliente " + nome + " consumiu");
		
		int chance = rand.nextInt(2);
		if(chance == 0)
			satisfeito = true;
		
	}
}
