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
    
    // Método de Transferência 
    public synchronized void transferir(Conta origem, Conta destino, int valor) {
        if (origem.saldo >= valor) {
            origem.saldo -= valor;  
            destino.saldo += valor; 
            System.out.println("Sucesso! " + origem.dono + " transferiu R$" + valor + " para " + destino.dono);
        } else {
            System.out.println("Deu ruim! " + origem.dono + " tentou transferir R$" + valor + " mas a conta ta lisa!!");
        }
    }

    // Método de Depósito
    public synchronized void depositar(Conta conta, int valor) {
        conta.saldo += valor;
        System.out.println("Sucesso! Depósito de R$" + valor + " na conta de " + conta.dono);
    }

    // Método de Saque
    public synchronized void sacar(Conta conta, int valor) {
        if (conta.saldo >= valor) {
            conta.saldo -= valor;
            System.out.println("Sucesso! " + conta.dono + " sacou R$" + valor);
        } else {
            System.out.println("Deu ruim! " + conta.dono + " tentou sacar R$" + valor + " mas a conta ta lisa!!");
        }
    }

    // Método de Crédito de Juros (Rendimento)
    public synchronized void creditarJuros(Conta conta, double taxa) {
        int juros = (int) (conta.saldo * taxa);
        conta.saldo += juros;
        System.out.println("Sucesso! Juros de R$" + juros + " (" + (taxa*100) + "%) creditados para " + conta.dono);
    }
}

public class Banco {
    public static void main(String[] args) throws InterruptedException {
        Conta contaJoao = new Conta("João", 100);
        Conta contaMaria = new Conta("Maria", 100);
        
        SistemaBancario sistema = new SistemaBancario();

        System.out.println("--- DISPARANDO AÇÕES SIMULTÂNEAS ---");

        // I
        // Transferência, depósito, crédito de juros
        Thread t1_transferencia = new Thread(() -> sistema.transferir(contaJoao, contaMaria, 50));
        Thread t2_deposito       = new Thread(() -> sistema.depositar(contaJoao, 100));
        Thread t3_juros          = new Thread(() -> sistema.creditarJuros(contaMaria, 0.10)); // 10% de juros

        // II
        // Saque, Depósito, crédito de juros, transferência
        Thread t4_saque          = new Thread(() -> sistema.sacar(contaJoao, 30));
        Thread t5_deposito       = new Thread(() -> sistema.depositar(contaMaria, 50));
        Thread t6_juros          = new Thread(() -> sistema.creditarJuros(contaJoao, 0.05)); // 5% de juros
        Thread t7_transferencia = new Thread(() -> sistema.transferir(contaMaria, contaJoao, 40));

        // Envia todas
        t1_transferencia.start();
        t2_deposito.start();
        t3_juros.start();
        t4_saque.start();
        t5_deposito.start();
        t6_juros.start();
        t7_transferencia.start();

    
        t1_transferencia.join();
        t2_deposito.join();
        t3_juros.join();
        t4_saque.join();
        t5_deposito.join();
        t6_juros.join();
        t7_transferencia.join();

        System.out.println("--------------------------------");
        System.out.println("Saldo Final João: R$" + contaJoao.saldo);
        System.out.println("Saldo Final Maria: R$" + contaMaria.saldo);
    }
}