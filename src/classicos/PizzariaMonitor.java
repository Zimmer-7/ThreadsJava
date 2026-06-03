package classicos;

import java.util.LinkedList;
import java.util.Queue;

class BalcaoMonitor {
    private final Queue<String> pizzas = new LinkedList<>();
    private final int LIMITE_BALCAO = 3;

    // Monitor do Pizzaiolo (Produtor)
    public synchronized void colocarPizza(String sabor) throws InterruptedException {
        while (pizzas.size() == LIMITE_BALCAO) {
            System.out.println("Pizzaiolo: 'Balcão cheio! Vou esperar...'");
            wait(); // espera
        }
        
        pizzas.add(sabor);
        System.out.println("Pizzaiolo: Colocou a pizza de " + sabor + " no balcão.");
        notifyAll(); // Grita: "Motoboy, tem pizza pronta!"
    }

    // Monitor do Entregador (Consumidor)
    public synchronized void pegarPizza() throws InterruptedException {
        while (pizzas.isEmpty()) {
            System.out.println("Entregador: 'Sem entregas... Vou olhar o celular.'");
            wait(); // Fica aguardando
        }
        
        String sabor = pizzas.poll(); // Tira a pizza da fila
        System.out.println("Entregador: Saiu para entregar a pizza de " + sabor);
        notifyAll(); // notifica que tem  espaço no balcao
    }
}

public class PizzariaMonitor {
    public static void main(String[] args) {
        BalcaoMonitor balcao = new BalcaoMonitor();

        Thread pizzaiolo = new Thread(() -> {
            try {
                balcao.colocarPizza("Calabresa");
                balcao.colocarPizza("Mussarela");
                balcao.colocarPizza("Marguerita");
                balcao.colocarPizza("Frango com Catupiry"); // Esta vai fazer ele esperar (limite é 3)
            } catch (InterruptedException e) {}
        });

        Thread entregador = new Thread(() -> {
            try {
                Thread.sleep(2000); // Entregador demora para chegar
                for (int i = 0; i < 4; i++) {
                    balcao.pegarPizza();
                }
            } catch (InterruptedException e) {}
        });

        pizzaiolo.start();
        entregador.start();
    }
}