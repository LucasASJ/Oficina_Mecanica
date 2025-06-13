package com.oficinamecanica.model;

import com.oficinamecanica.interfaces.IDetalhavel;

public class Cliente extends Pessoa implements IDetalhavel {
    private static int contadorClientes = 0;
    private int clienteId;

    public Cliente(String nome, String cpf, String telefone) {
        super(nome, cpf, telefone);
        this.clienteId = ++contadorClientes;
    }

    public int getClienteId() {
        return clienteId;
    }

    @Override
    public String getTipoPessoa() {
        return "Cliente";
    }

    @Override
    public String toString() {
        return "[ID Cliente: " + clienteId + "] " + super.toString();
    }

    @Override
    public String gerarRelatorio() {
        return "--- Detalhes do Cliente ---\n" +
               "ID: " + clienteId + "\n" +
               "Nome: " + getNome() + "\n" +
               "CPF: " + getCpf() + "\n" +
               "Telefone: " + getTelefone() + "\n";
    }
}