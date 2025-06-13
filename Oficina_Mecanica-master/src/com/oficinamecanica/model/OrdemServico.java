package com.oficinamecanica.model;

import com.oficinamecanica.interfaces.IDetalhavel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemServico implements IDetalhavel {
    private static int contadorOS = 0;
    private int osId;
    private Date dataAbertura;
    private Cliente cliente;
    private Veiculo veiculo;
    private Mecanico mecanicoResponsavel;
    private List<Servico> servicosRealizados;
    private String status; // Ex: "Aberta", "Em Andamento", "Concluída", "Cancelada"
    private double valorTotal;

    public OrdemServico(Cliente cliente, Veiculo veiculo, Mecanico mecanicoResponsavel) {
        this.osId = ++contadorOS;
        this.dataAbertura = new Date(); // Data atual
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.mecanicoResponsavel = mecanicoResponsavel;
        this.servicosRealizados = new ArrayList<>();
        this.status = "Aberta";
        this.valorTotal = 0.0;
    }

    // Getters e Setters
    public int getOsId() {
        return osId;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Mecanico getMecanicoResponsavel() {
        return mecanicoResponsavel;
    }

    public void setMecanicoResponsavel(Mecanico mecanicoResponsavel) {
        this.mecanicoResponsavel = mecanicoResponsavel;
    }

    public List<Servico> getServicosRealizados() {
        return servicosRealizados;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorTotal() {
        calcularValorTotal(); // Garante que o valor total esteja sempre atualizado
        return valorTotal;
    }

    // Métodos para gerenciar serviços
    public void adicionarServico(Servico servico) {
        this.servicosRealizados.add(servico);
        calcularValorTotal();
    }

    public void removerServico(Servico servico) {
        this.servicosRealizados.remove(servico);
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        this.valorTotal = 0;
        for (Servico s : servicosRealizados) {
            this.valorTotal += s.getPreco();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OS Nº: ").append(osId).append("\n");
        sb.append("Data Abertura: ").append(dataAbertura).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append(" (CPF: ").append(cliente.getCpf()).append(")\n");
        sb.append("Veículo: ").append(veiculo.getMarca()).append(" ").append(veiculo.getModelo()).append(" (Placa: ").append(veiculo.getPlaca()).append(")\n");
        if (mecanicoResponsavel != null) {
            sb.append("Mecânico Responsável: ").append(mecanicoResponsavel.getNome()).append("\n");
        }
        sb.append("Serviços:\n");
        if (servicosRealizados.isEmpty()) {
            sb.append("  Nenhum serviço adicionado.\n");
        } else {
            for (Servico s : servicosRealizados) {
                sb.append("  - ").append(s.toString()).append("\n");
            }
        }
        sb.append("Valor Total: R$").append(String.format("%.2f", getValorTotal())).append("\n");
        return sb.toString();
    }

    @Override
    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Detalhes da Ordem de Serviço ---\n");
        sb.append("OS Nº: ").append(osId).append("\n");
        sb.append("Data Abertura: ").append(dataAbertura).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append(" (CPF: ").append(cliente.getCpf()).append(")\n");
        sb.append("Veículo: ").append(veiculo.getMarca()).append(" ").append(veiculo.getModelo()).append(" (Placa: ").append(veiculo.getPlaca()).append(")\n");
        if (mecanicoResponsavel != null) {
            sb.append("Mecânico Responsável: ").append(mecanicoResponsavel.getNome()).append("\n");
        }
        sb.append("Serviços:\n");
        if (servicosRealizados.isEmpty()) {
            sb.append("  Nenhum serviço adicionado.\n");
        } else {
            for (Servico s : servicosRealizados) {
                sb.append("  - ").append(s.toString()).append("\n");
            }
        }
        sb.append("Valor Total: R$").append(String.format("%.2f", getValorTotal())).append("\n");
        return sb.toString();
    }
}