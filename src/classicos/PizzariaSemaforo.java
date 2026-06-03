package classicos;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class BalcaoSemaforo {
    private final Queue<String> pizzas = new LinkedList<>();
    
    // semaforos são contadores de "permissões"
    private final Semaphore espacosVazios = new Semaphore(3); // 3 3 vagas
    private final Semaphore pizzasProntas = new Semaphore(0); // Começa com 0 pizzas 
    private final Semaphore exclusaoMutua = new Semaphore(1); // so um acessa por vez

    public void colocarPizza(String sabor) throws InterruptedException {
        espacosVazios.acquire(); // Gasta 1 vaga. (Se não tiver vaga, ele congela aqui)
        
        exclusaoMutua.acquire(); // Tranca o balcão
        pizzas.add(sabor);
        System.out.println("Pizzaiolo: Colocou a pizza de " + sabor + " no balcão.");
        exclusaoMutua.release(); // Destranca o balcão
        
        pizzasProntas.release(); // Cria 1 permissão de pizza pronta para o motoboy
    }

    public void pegarPizza() throws InterruptedException {
        pizzasProntas.acquire(); // pega 1 pizza pronta. (Se tiver 0, ele congela aqui)
        
        exclusaoMutua.acquire(); // bloqueia o balcao
        String sabor = pizzas.poll();
        System.out.println("Entregador: Saiu para entregar a pizza de " + sabor);
        exclusaoMutua.release(); // libera o balcao
        
        espacosVazios.release(); // libera 1 vaga
    }
}

public class PizzariaSemaforo {
    public static void main(String[] args) {
        BalcaoSemaforo balcao = new BalcaoSemaforo();

        Thread pizzaiolo = new Thread(() -> {
            try {
                balcao.colocarPizza("Calabresa");
                balcao.colocarPizza("Mussarela");
                balcao.colocarPizza("Marguerita");
                balcao.colocarPizza("Bacon"); 
            } catch (InterruptedException e) {}
        });

        Thread entregador = new Thread(() -> {
            try {
                Thread.sleep(2000); // Motoboy demora um pouco
                for (int i = 0; i < 4; i++) balcao.pegarPizza();
            } catch (InterruptedException e) {}
        });

        pizzaiolo.start();
        entregador.start();
    }
}