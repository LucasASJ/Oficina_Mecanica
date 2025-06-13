package com.oficinamecanica.exceptions;

public class VeiculoAssociadoException extends RuntimeException {
    public VeiculoAssociadoException(String cpfCliente, String placaVeiculo) {
        super("O cliente com CPF " + cpfCliente + " não pode ser removido pois possui o veículo de placa " + placaVeiculo + ".");
    }
}

