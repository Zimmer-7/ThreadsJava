package classicos;

class Conta {
    String dono;
    int saldo;

    public Conta(String dono, int saldoInicial) {
        this.dono = dono;
        this.saldo = saldoInicial;
    }
}

class SistemaBancario {
    
    // synch garante que rode apenas um porvez
    public synchronized void transferir(Conta origem, Conta destino, int valor) {
        
        if (origem.saldo >= valor) {
            origem.saldo -= valor;  // Tira de um
            destino.saldo += valor; // Coloca no outro
            System.out.println("Sucesso! " + origem.dono + " transferiu R$" + valor + " para " + destino.dono);
        } else {
            System.out.println("Deu ruim! " + origem.dono + " a conta ta lisa!!");
        }
        
    }
}

public class Banco {
    public static void main(String[] args) throws InterruptedException {
        Conta contaJoao = new Conta("João", 100);
        Conta contaMaria = new Conta("Maria", 100);
        
        SistemaBancario sistema = new SistemaBancario();

        // transferem no msm tempo 
        Thread transacao1 = new Thread(() -> sistema.transferir(contaJoao, contaMaria, 50));
        Thread transacao2 = new Thread(() -> sistema.transferir(contaMaria, contaJoao, 120));
        Thread transacao3 = new Thread(() -> sistema.transferir(contaJoao, contaMaria, 50)); // Vai dar falha

        // envia simultaneo
        transacao1.start();
        transacao2.start();
        transacao3.start();

        // espera a transação terminar
        transacao1.join();
        transacao2.join();
        transacao3.join();

        // resul
        System.out.println("--------------------------------");
        System.out.println("Saldo Final João: R$" + contaJoao.saldo);
        System.out.println("Saldo Final Maria: R$" + contaMaria.saldo);
    }
}
