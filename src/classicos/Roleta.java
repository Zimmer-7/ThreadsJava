package classicos;

class Contador {
    int total = 0;

    // fila pra não entrar mais de um na vez
    public synchronized void somar() {
        total++; 
    }
}

public class Roleta {
    public static void main(String[] args) throws InterruptedException {
        Contador contador = new Contador();
        
        int qtdCatracas = 3; // qntdade
        Thread[] catracas = new Thread[qtdCatracas];
        
        // guarda a soma dos números que sortearmos
        int totalEsperadoGeral = 0; 

        // Cria
        for (int i = 0; i < qtdCatracas; i++) {
            
            // Sorteia um número de 1 a 500 para a catraca
            int pessoasNaCatraca = (int) (Math.random() * 500) + 1;
            totalEsperadoGeral += pessoasNaCatraca; 
            
            int idCatraca = i + 1; // Para imprimir as catrracas

            // Define o que a Thread dessa catraca vai fazer
            catracas[i] = new Thread(() -> {
                for (int j = 0; j < pessoasNaCatraca; j++) {
                    contador.somar();
                }
                System.out.println("Catraca " + idCatraca + " registrou " + pessoasNaCatraca + " pessoas.");
            });
            
            // Liga a catraca
            catracas[i].start();
        }

        // Aguarda todas as catracas terminarem de girar
        for (int i = 0; i < qtdCatracas; i++) {
            catracas[i].join();
        }

    
        System.out.println("-----------------------------------");
        System.out.println("Soma dos sorteios (Esperado) : " + totalEsperadoGeral);
        System.out.println("Memória do Contador (Real)   : " + contador.total);
    }
}