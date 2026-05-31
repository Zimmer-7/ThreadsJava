package filosofos;

import java.util.ArrayList;
import java.util.List;

public class Mesa {

	private static List<Filosofo> filosofos;
	private static List<Hashi> hashis;
	
	public static void main (String[] args) {
		filosofos = new ArrayList<>();
		hashis = new ArrayList<>();
		
		for(int i=0; i<5; i++) {
			Hashi h = new Hashi();
			hashis.add(h);
		}
		
		for(int i=0; i<5; i++) {
			Hashi h1 = hashis.get(i);
			Hashi h2;
			if(i!=4)
				h2 = hashis.get(i+1);
			else
				h2 = hashis.get(0);
			
			Filosofo g = new Filosofo(i, h1, h2);
			filosofos.add(g);
		}
		
		for(int i=0; i<5; i++) {
			filosofos.get(i).start();
		}
		
		try {
			for(int i=0; i<5; i++) {
				filosofos.get(i).join();
			}
		} catch (InterruptedException e) {
			System.out.println("interrupcao");
		}
	}

}
