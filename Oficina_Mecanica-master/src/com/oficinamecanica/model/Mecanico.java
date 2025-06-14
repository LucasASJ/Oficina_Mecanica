package com.oficinamecanica.model;

public class Mecanico extends Pessoa {
    private static int contadorMecanicos = 0;
    private int mecanicoId;
    private String especialidade;

    public Mecanico(String nome, String cpf, String telefone, String especialidade) {
        super(nome, cpf, telefone);
        this.especialidade = especialidade;
        this.mecanicoId = ++contadorMecanicos;
    }

    public int getMecanicoId() {
        return mecanicoId;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String getTipoPessoa() {
        return "Mecânico";
    }

    @Override
    public String toString() {
        return "[ID Mecânico: " + mecanicoId + "] " + super.toString() + ", Especialidade: " + especialidade;
    }
}