package com.oficinamecanica.model;

public abstract class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private Cliente proprietario; // Relacionamento com Cliente

    public Veiculo(String placa, String marca, String modelo, int ano, Cliente proprietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.proprietario = proprietario;
    }

    // Getters e Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

    // Método abstrato para detalhes específicos (Polimorfismo)
    public abstract String getDetalhesEspecificos();

    @Override
    public String toString() {
        return "Placa: " + placa + ", Marca: " + marca + ", Modelo: " + modelo + ", Ano: " + ano +
               ", Proprietário: " + (proprietario != null ? proprietario.getNome() : "N/A");
    }
}