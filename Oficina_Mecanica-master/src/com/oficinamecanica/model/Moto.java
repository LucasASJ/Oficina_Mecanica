package com.oficinamecanica.model;

public class Moto extends Veiculo {
    private int cilindradas;
    private boolean partidaEletrica;

    public Moto(String placa, String marca, String modelo, int ano, Cliente proprietario, int cilindradas, boolean partidaEletrica) {
        super(placa, marca, modelo, ano, proprietario);
        this.cilindradas = cilindradas;
        this.partidaEletrica = partidaEletrica;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }

    public boolean isPartidaEletrica() {
        return partidaEletrica;
    }

    public void setPartidaEletrica(boolean partidaEletrica) {
        this.partidaEletrica = partidaEletrica;
    }

    @Override
    public String getDetalhesEspecificos() {
        return "Tipo: Moto, Cilindradas: " + cilindradas + ", Partida Elétrica: " + (partidaEletrica ? "Sim" : "Não");
    }

    @Override
    public String toString() {
        return super.toString() + ", " + getDetalhesEspecificos();
    }
}