package com.oficinamecanica.controller;

import com.oficinamecanica.exceptions.CpfDuplicadoException;
import com.oficinamecanica.exceptions.VeiculoAssociadoException;
import com.oficinamecanica.exceptions.OrdemServicoAssociadaException;
import com.oficinamecanica.model.*;
import com.oficinamecanica.persistencia.OficinaData;
import com.oficinamecanica.persistencia.PersistenciaController;
import com.oficinamecanica.view.OficinaView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OficinaController {
    private List<Cliente> clientes;
    private List<Mecanico> mecanicos;
    private List<Veiculo> veiculos;
    private List<OrdemServico> ordensServico;
    private OficinaView view;
    private PersistenciaController persistenciaController;

    public OficinaController() {
        this.view = new OficinaView();
        this.persistenciaController = new PersistenciaController();
        
        OficinaData dados = persistenciaController.carregarDados();
        
        this.clientes = dados.getClientes() != null ? dados.getClientes() : new ArrayList<>();
        this.mecanicos = dados.getMecanicos() != null ? dados.getMecanicos() : new ArrayList<>();
        this.veiculos = dados.getVeiculos() != null ? dados.getVeiculos() : new ArrayList<>();
        this.ordensServico = dados.getOrdensServico() != null ? dados.getOrdensServico() : new ArrayList<>();
    }

    public void iniciar() {
        int opcao;
        do {
            opcao = view.mostrarMenuPrincipal();
            switch (opcao) {
                case 1:
                    gerenciarClientes();
                    break;
                case 2:
                    gerenciarVeiculos();
                    break;
                case 3:
                    gerenciarMecanicos();
                    break;
                case 4:
                    gerenciarOrdensServico();
                    break;
                case 0:
                    view.mostrarMensagem("Saindo do sistema...");
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        } while (opcao != 0);
        
        persistenciaController.salvarDados(clientes, mecanicos, veiculos, ordensServico);
        
        view.fecharScanner();
    }

    private void gerenciarClientes() {
        int opcaoCliente;
        do {
            opcaoCliente = view.mostrarMenuClientes();
            switch (opcaoCliente) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarClientePorCpf();
                    break;
                case 4:
                    atualizarCliente();
                    break;
                case 5:
                    removerCliente();
                    break;
                case 0:
                    view.mostrarMensagem("Voltando ao menu principal...");
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        } while (opcaoCliente != 0);
    }

    private void removerCliente() {
        view.mostrarMensagem("\n--- Remover Cliente ---");
        String cpf = view.lerString("Digite o CPF do cliente que deseja remover: ");

        Optional<Cliente> clienteOpt = buscarClientePorCpfInterno(cpf);

        if (!clienteOpt.isPresent()) {
            view.mostrarMensagem("Cliente com CPF " + cpf + " não encontrado.");
            return;
        }

        Cliente clienteParaRemover = clienteOpt.get();

        for (Veiculo v : veiculos) {
            if (v.getProprietario().getCpf().equals(clienteParaRemover.getCpf())) {
                throw new VeiculoAssociadoException(clienteParaRemover.getCpf(), v.getPlaca());
            }
        }

        for (OrdemServico os : ordensServico) {
            if (os.getCliente().getCpf().equals(clienteParaRemover.getCpf())) {
                throw new OrdemServicoAssociadaException("O cliente com CPF " + clienteParaRemover.getCpf() + " não pode ser removido pois está associado à Ordem de Serviço Nº" + os.getOsId() + ".");
            }
        }

        boolean confirmar = view.lerBoolean("Tem certeza que deseja remover o cliente " + clienteParaRemover.getNome() + "? Esta ação não pode ser desfeita.");
        
        if (confirmar) {
            clientes.remove(clienteParaRemover);
            view.mostrarMensagem("Cliente removido com sucesso!");
        } else {
            view.mostrarMensagem("Remoção cancelada.");
        }
    }

    private void cadastrarCliente() {
        view.mostrarMensagem("\n--- Cadastro de Cliente ---");
        String nome = view.lerString("Nome: ");
        String cpf = view.lerString("CPF: ");
        String telefone = view.lerString("Telefone: ");
        
        if (buscarClientePorCpfInterno(cpf).isPresent()) {
            throw new CpfDuplicadoException(cpf);
        }

        Cliente cliente = new Cliente(nome, cpf, telefone);
        clientes.add(cliente);
        view.mostrarMensagem("Cliente cadastrado com sucesso! ID: " + cliente.getClienteId());
    }

    private void listarClientes() {
        view.mostrarMensagem("\n--- Lista de Clientes ---");
        if (clientes.isEmpty()) {
            view.mostrarMensagem("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : clientes) {
            view.mostrarMensagem(c.toString() + " - Tipo: " + c.getTipoPessoa());
        }
    }
    
    private Optional<Cliente> buscarClientePorCpfInterno(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    private void buscarClientePorCpf() {
        view.mostrarMensagem("\n--- Buscar Cliente por CPF ---");
        String cpf = view.lerString("Digite o CPF do cliente: ");
        Optional<Cliente> clienteOpt = buscarClientePorCpfInterno(cpf);
        
        if (clienteOpt.isPresent()) {
            view.mostrarMensagem("Cliente encontrado:");
            view.mostrarMensagem(clienteOpt.get().toString());
        } else {
            view.mostrarMensagem("Cliente com CPF " + cpf + " não encontrado.");
        }
    }
    
    private void atualizarCliente() {
        view.mostrarMensagem("\n--- Atualizar Cliente ---");
        String cpf = view.lerString("Digite o CPF do cliente que deseja atualizar: ");
        Optional<Cliente> clienteOpt = buscarClientePorCpfInterno(cpf);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            view.mostrarMensagem("Cliente encontrado: " + cliente.toString());
            
            String novoNome = view.lerString("Digite o novo nome (ou deixe em branco para manter o atual: " + cliente.getNome() + "): ");
            if (!novoNome.trim().isEmpty()) {
                cliente.setNome(novoNome);
            }

            String novoTelefone = view.lerString("Digite o novo telefone (ou deixe em branco para manter o atual: " + cliente.getTelefone() + "): ");
            if (!novoTelefone.trim().isEmpty()) {
                cliente.setTelefone(novoTelefone);
            }

            view.mostrarMensagem("Cliente atualizado com sucesso!");
        } else {
            view.mostrarMensagem("Cliente com CPF " + cpf + " não encontrado.");
        }
    }

    private void gerenciarMecanicos() {
        int opcaoMecanico;
        do {
            opcaoMecanico = view.mostrarMenuMecanicos();
            switch (opcaoMecanico) {
                case 1:
                    cadastrarMecanico();
                    break;
                case 2:
                    listarMecanicos();
                    break;
                case 3:
                    atualizarMecanico();
                    break;
                case 4:
                    removerMecanico();
                    break;
                case 0:
                    view.mostrarMensagem("Voltando ao menu principal...");
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        } while (opcaoMecanico != 0);
    }

    private void removerMecanico() {
        view.mostrarMensagem("\n--- Remover Mecânico ---");
        listarMecanicos();
        if (mecanicos.isEmpty()) {
            return;
        }

        int id = view.lerInt("Digite o ID do mecânico que deseja remover: ");
        Optional<Mecanico> mecanicoOpt = buscarMecanicoPorIdInterno(id);

        if (!mecanicoOpt.isPresent()) {
            view.mostrarMensagem("Mecânico com ID " + id + " não encontrado.");
            return;
        }

        Mecanico mecanicoParaRemover = mecanicoOpt.get();
        
        for (OrdemServico os : ordensServico) {
            if (os.getMecanicoResponsavel() != null && os.getMecanicoResponsavel().getMecanicoId() == mecanicoParaRemover.getMecanicoId()) {
                view.mostrarMensagem("ERRO: Este mecânico não pode ser removido pois está responsável pela Ordem de Serviço Nº" + os.getOsId() + ".");
                return;
            }
        }
        
        boolean confirmar = view.lerBoolean("Tem certeza que deseja remover o mecânico " + mecanicoParaRemover.getNome() + "?");
        
        if (confirmar) {
            mecanicos.remove(mecanicoParaRemover);
            view.mostrarMensagem("Mecânico removido com sucesso!");
        } else {
            view.mostrarMensagem("Remoção cancelada.");
        }
    }

    private void cadastrarMecanico() {
        view.mostrarMensagem("\n--- Cadastro de Mecânico ---");
        String nome = view.lerString("Nome: ");
        String cpf = view.lerString("CPF: ");
        String telefone = view.lerString("Telefone: ");
        String especialidade = view.lerString("Especialidade: ");
        Mecanico mecanico = new Mecanico(nome, cpf, telefone, especialidade);
        mecanicos.add(mecanico);
        view.mostrarMensagem("Mecânico cadastrado com sucesso! ID: " + mecanico.getMecanicoId());
    }

    private void listarMecanicos() {
        view.mostrarMensagem("\n--- Lista de Mecânicos ---");
        if (mecanicos.isEmpty()) {
            view.mostrarMensagem("Nenhum mecânico cadastrado.");
            return;
        }
        for (Mecanico m : mecanicos) {
            view.mostrarMensagem(m.toString());
        }
    }
    
    private Optional<Mecanico> buscarMecanicoPorIdInterno(int id) {
        for (Mecanico m : mecanicos) {
            if (m.getMecanicoId() == id) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    private void atualizarMecanico() {
        view.mostrarMensagem("\n--- Atualizar Mecânico ---");
        listarMecanicos();
        if (mecanicos.isEmpty()) {
            return;
        }

        int id = view.lerInt("Digite o ID do mecânico que deseja atualizar: ");
        Optional<Mecanico> mecanicoOpt = buscarMecanicoPorIdInterno(id);

        if (mecanicoOpt.isPresent()) {
            Mecanico mecanico = mecanicoOpt.get();
            view.mostrarMensagem("Mecânico encontrado: " + mecanico.toString());

            String novoNome = view.lerString("Digite o novo nome (ou deixe em branco para manter): ");
            if (!novoNome.trim().isEmpty()) {
                mecanico.setNome(novoNome);
            }

            String novoTelefone = view.lerString("Digite o novo telefone (ou deixe em branco para manter): ");
            if (!novoTelefone.trim().isEmpty()) {
                mecanico.setTelefone(novoTelefone);
            }

            String novaEspecialidade = view.lerString("Digite a nova especialidade (ou deixe em branco para manter): ");
            if (!novaEspecialidade.trim().isEmpty()) {
                mecanico.setEspecialidade(novaEspecialidade);
            }

            view.mostrarMensagem("Mecânico atualizado com sucesso!");
        } else {
            view.mostrarMensagem("Mecânico com ID " + id + " não encontrado.");
        }
    }

     private void gerenciarVeiculos() {
        int opcaoVeiculo;
        do {
            opcaoVeiculo = view.mostrarMenuVeiculos();
            switch (opcaoVeiculo) {
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    listarVeiculos();
                    break;
                case 3:
                    buscarVeiculoPorPlaca();
                    break;
                case 4:
                    atualizarVeiculo();
                    break;
                case 5:
                    removerVeiculo();
                    break;
                case 0:
                    view.mostrarMensagem("Voltando ao menu principal...");
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        } while (opcaoVeiculo != 0);
    }

    private void removerVeiculo() {
        view.mostrarMensagem("\n--- Remover Veículo ---");
        listarVeiculos();
        if (veiculos.isEmpty()) {
            return;
        }

        String placa = view.lerString("Digite a placa do veículo que deseja remover: ");
        Optional<Veiculo> veiculoOpt = buscarVeiculoPorPlacaInterno(placa);

        if (!veiculoOpt.isPresent()) {
            view.mostrarMensagem("Veículo com placa " + placa + " não encontrado.");
            return;
        }

        Veiculo veiculoParaRemover = veiculoOpt.get();

        for (OrdemServico os : ordensServico) {
            if (os.getVeiculo().getPlaca().equalsIgnoreCase(veiculoParaRemover.getPlaca())) {
                throw new OrdemServicoAssociadaException("O veículo com placa " + veiculoParaRemover.getPlaca() + " não pode ser removido pois está associado à Ordem de Serviço Nº" + os.getOsId() + ".");
            }
        }

        boolean confirmar = view.lerBoolean("Tem certeza que deseja remover o veículo " + veiculoParaRemover.getMarca() + " " + veiculoParaRemover.getModelo() + "?");
        
        if (confirmar) {
            veiculos.remove(veiculoParaRemover);
            view.mostrarMensagem("Veículo removido com sucesso!");
        } else {
            view.mostrarMensagem("Remoção cancelada.");
        }
    }

    private void cadastrarVeiculo() {
        view.mostrarMensagem("\n--- Cadastro de Veículo ---");
        String cpfProprietario = view.lerString("CPF do proprietário: ");
        Optional<Cliente> proprietarioOpt = buscarClientePorCpfInterno(cpfProprietario);

        if (!proprietarioOpt.isPresent()) {
            view.mostrarMensagem("Cliente com CPF " + cpfProprietario + " não encontrado. Cadastre o cliente primeiro.");
            return;
        }
        Cliente proprietario = proprietarioOpt.get();

        String placa = view.lerString("Placa: ");
        if (buscarVeiculoPorPlacaInterno(placa).isPresent()) {
            view.mostrarMensagem("Erro: Já existe um veículo cadastrado com esta placa.");
            return;
        }
        String marca = view.lerString("Marca: ");
        String modelo = view.lerString("Modelo: ");
        int ano = view.lerInt("Ano: ");

        Veiculo veiculo = null;
        String tipoVeiculo = view.lerString("Tipo de Veículo (Carro/Moto): ").toLowerCase();
        try {
            if (tipoVeiculo.equals("carro")) {
                int numPortas = view.lerInt("Número de portas: ");
                String tipoCombustivel = view.lerString("Tipo de combustível: ");
                veiculo = new Carro(placa, marca, modelo, ano, proprietario, numPortas, tipoCombustivel);
            } else if (tipoVeiculo.equals("moto")) {
                int cilindradas = view.lerInt("Cilindradas: ");
                boolean partidaEletrica = view.lerBoolean("Partida elétrica?");
                veiculo = new Moto(placa, marca, modelo, ano, proprietario, cilindradas, partidaEletrica);
            } else {
                view.mostrarMensagem("Tipo de veículo inválido.");
                return;
            }
            veiculos.add(veiculo);
            view.mostrarMensagem("Veículo cadastrado com sucesso!");
        } catch (Exception e) {
            view.mostrarMensagem("Ocorreu um erro ao cadastrar o veículo: " + e.getMessage());
        }
    }

    private void listarVeiculos() {
        view.mostrarMensagem("\n--- Lista de Veículos ---");
        if (veiculos.isEmpty()) {
            view.mostrarMensagem("Nenhum veículo cadastrado.");
            return;
        }
        for (Veiculo v : veiculos) {
            view.mostrarMensagem(v.toString());
        }
    }
    
    private Optional<Veiculo> buscarVeiculoPorPlacaInterno(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }
    
    private void buscarVeiculoPorPlaca() {
        view.mostrarMensagem("\n--- Buscar Veículo por Placa ---");
        String placa = view.lerString("Digite a placa do veículo: ");
        Optional<Veiculo> veiculoOpt = buscarVeiculoPorPlacaInterno(placa);
        
        if (veiculoOpt.isPresent()) {
            view.mostrarMensagem("Veículo encontrado:");
            view.mostrarMensagem(veiculoOpt.get().toString());
        } else {
            view.mostrarMensagem("Veículo com placa " + placa + " não encontrado.");
        }
    }

    private void atualizarVeiculo() {
        view.mostrarMensagem("\n--- Atualizar Veículo ---");
        String placa = view.lerString("Digite a placa do veículo que deseja atualizar: ");
        Optional<Veiculo> veiculoOpt = buscarVeiculoPorPlacaInterno(placa);

        if (veiculoOpt.isPresent()) {
            Veiculo veiculo = veiculoOpt.get();
            view.mostrarMensagem("Veículo encontrado: " + veiculo.toString());

            String novaMarca = view.lerString("Digite a nova marca (ou deixe em branco para manter): ");
            if (!novaMarca.trim().isEmpty()) {
                veiculo.setMarca(novaMarca);
            }

            String novoModelo = view.lerString("Digite o novo modelo (ou deixe em branco para manter): ");
            if (!novoModelo.trim().isEmpty()) {
                veiculo.setModelo(novoModelo);
            }
            
            view.mostrarMensagem("Veículo atualizado com sucesso!");
        } else {
            view.mostrarMensagem("Veículo com placa " + placa + " não encontrado.");
        }
    }

    private void gerenciarOrdensServico() {
        int opcaoOS;
        do {
            opcaoOS = view.mostrarMenuOrdensServico();
            switch (opcaoOS) {
                case 1:
                    criarOrdemServico();
                    break;
                case 2:
                    adicionarServicoOS();
                    break;
                case 3:
                    listarOrdensServico();
                    break;
                case 4:
                    buscarOrdemServicoPorId();
                    break;
                case 5:
                    atualizarStatusOS();
                    break;
                case 6:
                    listarOrdensServicoPorStatus();
                    break;
                case 7: 
                    removerOrdemDeServico();
                    break;
                case 0:
                    view.mostrarMensagem("Voltando ao menu principal...");
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        } while (opcaoOS != 0);
    }
    private void removerOrdemDeServico() {
        view.mostrarMensagem("\n--- Remover Ordem de Serviço ---");
        listarOrdensServico();
        if (ordensServico.isEmpty()) {
            return;
        }

        int id = view.lerInt("Digite o ID da Ordem de Serviço que deseja remover: ");
        Optional<OrdemServico> osOpt = buscarOSPorIdInterno(id);

        if (!osOpt.isPresent()) {
            view.mostrarMensagem("Ordem de Serviço com ID " + id + " não encontrada.");
            return;
        }

        OrdemServico osParaRemover = osOpt.get();

        boolean confirmar = view.lerBoolean("Tem certeza que deseja remover a OS Nº" + osParaRemover.getOsId() + "?");
        
        if (confirmar) {
            ordensServico.remove(osParaRemover);
            view.mostrarMensagem("Ordem de Serviço removida com sucesso!");
        } else {
            view.mostrarMensagem("Remoção cancelada.");
        }
    }

    private void criarOrdemServico() {
        view.mostrarMensagem("\n--- Criar Nova Ordem de Serviço ---");
        if (clientes.isEmpty()) {
            view.mostrarMensagem("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }
        if (veiculos.isEmpty()) {
            view.mostrarMensagem("Nenhum veículo cadastrado. Cadastre um veículo primeiro.");
            return;
        }

        String cpfCliente = view.lerString("CPF do Cliente para a OS: ");
        Optional<Cliente> clienteOpt = buscarClientePorCpfInterno(cpfCliente);
        if (!clienteOpt.isPresent()) {
            view.mostrarMensagem("Cliente não encontrado.");
            return;
        }
        Cliente cliente = clienteOpt.get();

        String placaVeiculo = view.lerString("Placa do Veículo para a OS: ");
        Optional<Veiculo> veiculoOpt = buscarVeiculoPorPlacaInterno(placaVeiculo);
        if (!veiculoOpt.isPresent()) {
            view.mostrarMensagem("Veículo não encontrado.");
            return;
        }
        Veiculo veiculo = veiculoOpt.get();
        
        if (!veiculo.getProprietario().getCpf().equals(cliente.getCpf())) {
            view.mostrarMensagem("Erro: O veículo com placa " + placaVeiculo + 
                                 " não pertence ao cliente " + cliente.getNome() + ".");
            return;
        }

        Mecanico mecanicoResponsavel = null;
        if (!mecanicos.isEmpty()) {
            listarMecanicos();
            int idMecanico = view.lerInt("ID do Mecânico Responsável (ou 0 se não houver um específico agora): ");
            if (idMecanico != 0) {
                 Optional<Mecanico> mecanicoOpt = buscarMecanicoPorIdInterno(idMecanico);
                 if(mecanicoOpt.isPresent()){
                    mecanicoResponsavel = mecanicoOpt.get();
                 } else {
                    view.mostrarMensagem("Mecânico com ID " + idMecanico + " não encontrado. OS será criada sem mecânico específico.");
                 }
            }
        } else {
            view.mostrarMensagem("Nenhum mecânico cadastrado. A OS será criada sem mecânico específico.");
        }


        OrdemServico os = new OrdemServico(cliente, veiculo, mecanicoResponsavel);
        ordensServico.add(os);
        view.mostrarMensagem("Ordem de Serviço Nº" + os.getOsId() + " criada com sucesso!");
    }
    
    private Optional<OrdemServico> buscarOSPorIdInterno(int idOS) {
        for (OrdemServico os : ordensServico) {
            if (os.getOsId() == idOS) {
                return Optional.of(os);
            }
        }
        return Optional.empty();
    }

    private void adicionarServicoOS() {
        view.mostrarMensagem("\n--- Adicionar Serviço a uma OS ---");
        if (ordensServico.isEmpty()) {
            view.mostrarMensagem("Nenhuma Ordem de Serviço cadastrada.");
            return;
        }
        int idOS = view.lerInt("ID da Ordem de Serviço: ");
        Optional<OrdemServico> osOpt = buscarOSPorIdInterno(idOS);

        if (!osOpt.isPresent()) {
            view.mostrarMensagem("Ordem de Serviço com ID " + idOS + " não encontrada.");
            return;
        }
        OrdemServico os = osOpt.get();
        
        if(os.getStatus().equalsIgnoreCase("Concluída") || os.getStatus().equalsIgnoreCase("Cancelada")){
            view.mostrarMensagem("Não é possível adicionar serviços a uma OS " + os.getStatus().toLowerCase() + ".");
            return;
        }

        String descServico = view.lerString("Descrição do Serviço: ");
        double precoServico = view.lerDouble("Preço do Serviço: R$");
        
        try {
            Servico servico = new Servico(descServico, precoServico);
            os.adicionarServico(servico);
            view.mostrarMensagem("Serviço adicionado com sucesso à OS Nº" + os.getOsId());
        } catch (IllegalArgumentException e) {
            view.mostrarMensagem("Erro ao adicionar serviço: " + e.getMessage());
        }
    }

    private void listarOrdensServico() {
        view.mostrarMensagem("\n--- Lista de Ordens de Serviço ---");
        if (ordensServico.isEmpty()) {
            view.mostrarMensagem("Nenhuma Ordem de Serviço cadastrada.");
            return;
        }
        for (OrdemServico os : ordensServico) {
            view.mostrarMensagem(os.toString());
            view.mostrarMensagem("------------------------------------");
        }
    }
    
    private void buscarOrdemServicoPorId() {
        view.mostrarMensagem("\n--- Buscar Ordem de Serviço por ID ---");
        int idOS = view.lerInt("Digite o ID da OS: ");
        Optional<OrdemServico> osOpt = buscarOSPorIdInterno(idOS);
        
        if (osOpt.isPresent()) {
            view.mostrarMensagem("Ordem de Serviço encontrada:");
            view.mostrarMensagem(osOpt.get().toString());
        } else {
            view.mostrarMensagem("Ordem de Serviço com ID " + idOS + " não encontrada.");
        }
    }
    
    private void atualizarStatusOS() {
        view.mostrarMensagem("\n--- Atualizar Status da Ordem de Serviço ---");
        int idOS = view.lerInt("ID da Ordem de Serviço para atualizar o status: ");
        Optional<OrdemServico> osOpt = buscarOSPorIdInterno(idOS);

        if (!osOpt.isPresent()) {
            view.mostrarMensagem("Ordem de Serviço com ID " + idOS + " não encontrada.");
            return;
        }
        OrdemServico os = osOpt.get();
        view.mostrarMensagem("Status atual: " + os.getStatus());
        String novoStatus = view.lerString("Digite o novo status (Ex: Em Andamento, Concluída, Cancelada): ");
        os.setStatus(novoStatus);
        view.mostrarMensagem("Status da OS Nº" + os.getOsId() + " atualizado para: " + novoStatus);
    }
    
    private void listarOrdensServicoPorStatus() {
        view.mostrarMensagem("\n--- Listar OS por Status ---");
        if (ordensServico.isEmpty()) {
            view.mostrarMensagem("Nenhuma Ordem de Serviço cadastrada.");
            return;
        }
        String status = view.lerString("Digite o status para filtrar (Ex: Aberta, Concluída): ");
        listarOrdensServico(status);
    }

    private void listarOrdensServico(String status) {
        view.mostrarMensagem("\n--- Lista de Ordens de Serviço (Status: " + status + ") ---");
        List<OrdemServico> filtradas = new ArrayList<>();
        for (OrdemServico os : ordensServico) {
            if (os.getStatus().equalsIgnoreCase(status)) {
                filtradas.add(os);
            }
        }

        if (filtradas.isEmpty()) {
            view.mostrarMensagem("Nenhuma Ordem de Serviço encontrada com o status '" + status + "'.");
            return;
        }

        for (OrdemServico os : ordensServico) {
            view.mostrarMensagem(os.toString());
            view.mostrarMensagem("------------------------------------");
        }
    }
}