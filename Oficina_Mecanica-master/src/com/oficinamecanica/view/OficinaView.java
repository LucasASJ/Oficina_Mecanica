package com.oficinamecanica.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OficinaView {
    private Scanner scanner;

    public OficinaView() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenuPrincipal() {
        System.out.println("\n--- Oficina Mecânica ---");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Veículos");
        System.out.println("3. Gerenciar Mecânicos");
        System.out.println("4. Gerenciar Ordens de Serviço");
        System.out.println("0. Sair");
<<<<<<< HEAD
        System.out.print("Escolha uma opçao: ");
=======
        System.out.print("Escolha uma opção: ");
>>>>>>> e26d9a511dea740029b7ad7dbb63621ff1591393
        return lerInt();
    }

    public int mostrarMenuClientes() {
        System.out.println("\n--- Gerenciar Clientes ---");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Listar Clientes");
        System.out.println("3. Buscar Cliente por CPF");
        System.out.println("4. Atualizar Cliente");
        System.out.println("5. Remover Cliente");
        System.out.println("0. Voltar ao Menu Principal");
<<<<<<< HEAD
        System.out.print("Escolha uma opçao: ");
=======
        System.out.print("Escolha uma opção: ");
>>>>>>> e26d9a511dea740029b7ad7dbb63621ff1591393
        return lerInt();
    }
    
    public int mostrarMenuVeiculos() {
        System.out.println("\n--- Gerenciar Veículos ---");
        System.out.println("1. Cadastrar Veículo");
        System.out.println("2. Listar Veículos");
        System.out.println("3. Buscar Veículo por Placa");
        System.out.println("4. Atualizar Veículo");
        System.out.println("5. Remover Veículo"); 
        System.out.println("0. Voltar ao Menu Principal");
<<<<<<< HEAD
        System.out.print("Escolha uma opçao: ");
=======
        System.out.print("Escolha uma opção: ");
>>>>>>> e26d9a511dea740029b7ad7dbb63621ff1591393
        return lerInt();
    }

    public int mostrarMenuMecanicos() {
        System.out.println("\n--- Gerenciar Mecânicos ---");
        System.out.println("1. Cadastrar Mecânico");
        System.out.println("2. Listar Mecânicos");
        System.out.println("3. Atualizar Mecânico");
        System.out.println("4. Remover Mecânico"); 
        System.out.println("0. Voltar ao Menu Principal");
<<<<<<< HEAD
        System.out.print("Escolha uma opçao: ");
=======
        System.out.print("Escolha uma opção: ");
>>>>>>> e26d9a511dea740029b7ad7dbb63621ff1591393
        return lerInt();
    }

    public int mostrarMenuOrdensServico() {
        System.out.println("\n--- Gerenciar Ordens de Serviço ---");
        System.out.println("1. Criar Nova Ordem de Serviço");
        System.out.println("2. Adicionar Serviço a uma OS");
        System.out.println("3. Listar Ordens de Serviço");
        System.out.println("4. Buscar Ordem de Serviço por ID");
        System.out.println("5. Atualizar Status da OS");
        System.out.println("6. Listar Ordens de Serviço por Status");
        System.out.println("7. Remover Ordem de Serviço");
        System.out.println("0. Voltar ao Menu Principal");
<<<<<<< HEAD
        System.out.print("Escolha uma opçao: ");
=======
        System.out.print("Escolha uma opção: ");
>>>>>>> e26d9a511dea740029b7ad7dbb63621ff1591393
        return lerInt();
    }

    public String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public int lerInt(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine();
            }
        }
    }
    
    public int lerInt() {
        while (true) {
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine();
            }
        }
    }

    public double lerDouble(String mensagem) {
         while (true) {
            try {
                System.out.print(mensagem);
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número (ex: 10.50).");
                scanner.nextLine();
            }
        }
    }

    public boolean lerBoolean(String mensagem) {
        while(true) {
            System.out.print(mensagem + " (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s")) {
                return true;
            } else if (resposta.equals("n")) {
                return false;
            } else {
                System.out.println("Resposta inválida. Digite 's' para sim ou 'n' para não.");
            }
        }
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void fecharScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}