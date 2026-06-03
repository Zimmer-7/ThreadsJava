package bar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bar {
	
	private static List<Garcom> garcons;
	private static List<Cliente> clientes;
	public static Bartender bartender;
	public static Balcao balcao;
	
	public static void main (String[] args) {
		
		Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a quantidade de clientes: ");
        int qttdC = scanner.nextInt();

        System.out.print("Digite a quantidade de garcons: ");
        int qttdG = scanner.nextInt();

        System.out.print("Digite a capacidade do garcom: ");
        int capacidade = scanner.nextInt();

        System.out.print("Digite o número de rodadas: ");
        int rodadas = scanner.nextInt();
        
        scanner.close();
		
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
