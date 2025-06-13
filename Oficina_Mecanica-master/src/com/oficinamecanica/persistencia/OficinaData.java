package com.oficinamecanica.persistencia;

import com.oficinamecanica.model.Cliente;
import com.oficinamecanica.model.Mecanico;
import com.oficinamecanica.model.OrdemServico;
import com.oficinamecanica.model.Veiculo;

import java.util.List;

public class OficinaData {
    private List<Cliente> clientes;
    private List<Mecanico> mecanicos;
    private List<Veiculo> veiculos;
    private List<OrdemServico> ordensServico;

    // Getters e Setters para que a biblioteca Gson possa acessá-los
    public List<Cliente> getClientes() { return clientes; }
    public void setClientes(List<Cliente> clientes) { this.clientes = clientes; }
    public List<Mecanico> getMecanicos() { return mecanicos; }
    public void setMecanicos(List<Mecanico> mecanicos) { this.mecanicos = mecanicos; }
    public List<Veiculo> getVeiculos() { return veiculos; }
    public void setVeiculos(List<Veiculo> veiculos) { this.veiculos = veiculos; }
    public List<OrdemServico> getOrdensServico() { return ordensServico; }
    public void setOrdensServico(List<OrdemServico> ordensServico) { this.ordensServico = ordensServico; }
}