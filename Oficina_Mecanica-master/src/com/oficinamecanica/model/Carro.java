package com.oficinamecanica.model;

public class Carro extends Veiculo {
    private int numeroPortas;
    private String tipoCombustivel;

    public Carro(String placa, String marca, String modelo, int ano, Cliente proprietario, int numeroPortas, String tipoCombustivel) {
        super(placa, marca, modelo, ano, proprietario);
        this.numeroPortas = numeroPortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getNumeroPortas() {
        return numeroPortas;
    }

    public void setNumeroPortas(int numeroPortas) {
        this.numeroPortas = numeroPortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    @Override
    public String getDetalhesEspecificos() {
        return "Tipo: Carro, Portas: " + numeroPortas + ", Combustível: " + tipoCombustivel;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + getDetalhesEspecificos();
    }
}