package com.smithpalacehotel.services;

import java.text.ParseException;
import java.util.Arrays;
import com.smithpalacehotel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smithpalacehotel.models.*;

@Service
public class _DBService {
    @Autowired
    private UfRepository ufRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private GerenteRepository gerenteRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

    public void instantiateTestDatabase() throws ParseException {
        // Instanciando os objetos de modelo
        Uf uf1 = new Uf(null, "ES", "Espírito Santo");
        Uf uf2 = new Uf(null, "MG", "Minas Gerais");

        // cidade
        Cidade cidade1 = new Cidade(null, "Cachoeiro de Itapemirim", uf1);
        Cidade cidade2 = new Cidade(null, "Belo Horizonte", uf1);

        // Gerente
        Gerente gerente1 = new Gerente(null, "João da Silva", "11111111111", "joaosilva", "minhasenha123", cidade1);
        Gerente gerente2 = new Gerente(null, "Jorge dos Santos", "11111111112", "jorgesantos", "senhaforte321", cidade2);

        // Funcionario
        Funcionario funcionario1 = new Funcionario(null, "Euclides Campos", "11111111113", "euclides", "catfish12", cidade1);
        Funcionario funcionario2 = new Funcionario(null, "Carlos Machado", "11111111114", "cmachado", "meupaieterno", cidade1);

        // Cliente
        Cliente cliente1 = new Cliente(null, "Carlos Eduardo", "11111111115", "carlosedu", "ngmsabeminhasenha", cidade2);
        Cliente cliente2 = new Cliente(null, "Maria Joaquina", "11111111116", "mariajq", "cirilo321", cidade2);
        
        //Veiculo
        Veiculo veiculo1 = new Veiculo(null, "BEE4R22", true, "Corolla", 8, cidade2);
        Veiculo veiculo2 = new Veiculo(null, "BAE4Z12", false, "C4 Pallas", 21, cidade2);

        ufRepository.saveAll(Arrays.asList(uf1, uf2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2));
        gerenteRepository.saveAll(Arrays.asList(gerente1, gerente2));
        funcionarioRepository.saveAll(Arrays.asList(funcionario1, funcionario2));
        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
        veiculoRepository.saveAll(Arrays.asList(veiculo1, veiculo2));
    }
}
