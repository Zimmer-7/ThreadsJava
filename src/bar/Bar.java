package bar;

import java.util.ArrayList;
import java.util.List;

public class Bar {
	
	private static List<Garcom> garcons;
	private static List<Cliente> clientes;
	public static Bartender bartender;
	public static Balcao balcao;
	
	public static void main (String[] args) {
		
		int qttdC = 5;
		int qttdG = 2;
		int capacidade = 2;
		int rodadas = 3;
		
		garcons = new ArrayList<>();
		clientes = new ArrayList<>();
		
		balcao = new Balcao(rodadas);
		
		bartender = new Bartender(balcao);
		
		for(int i=0; i<qttdC; i++) {
			Cliente c = new Cliente(i, balcao);
			clientes.add(c);
		}
		
		for(int i=0; i<qttdG; i++) {
			Garcom g = new Garcom(i, capacidade, balcao);
			garcons.add(g);
		}
		
		bartender.start();
		for(int i=0; i<qttdC; i++) {
			clientes.get(i).start();
		}
		for(int i=0; i<qttdG; i++) {
			garcons.get(i).start();
		}
		
		try {
			bartender.join();
			for(int i=0; i<qttdC; i++) {
				clientes.get(i).join();
			}
			for(int i=0; i<qttdG; i++) {
				garcons.get(i).join();
			}
		} catch (InterruptedException e) {
			System.out.println("interrupcao");
		}
	}

}
