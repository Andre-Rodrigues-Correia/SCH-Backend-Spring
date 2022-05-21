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
    @Autowired
    private LocalEventoRepository localEventoRepository;

    public void instantiateTestDatabase() throws ParseException {
        // Instanciando os objetos de modelo
        Uf uf1 = new Uf(null, "ES", "Espírito Santo");
        Uf uf2 = new Uf(null, "MG", "Minas Gerais");
        Uf uf3 = new Uf(null, "RJ", "Rio de Janeiro");

        // cidade
        Cidade cidade1 = new Cidade(null, "Cachoeiro de Itapemirim", uf1);
        Cidade cidade2 = new Cidade(null, "Belo Horizonte", uf2);
        Cidade cidade2 = new Cidade(null, "Volta Redonda", uf3);

        // Gerente
        Gerente gerente1 = new Gerente(null, "João da Silva", "11111111111", "joaosilva", "minhasenha123", cidade1);
        Gerente gerente2 = new Gerente(null, "Jorge dos Santos", "11111111112", "jorgesantos", "senhaforte321", cidade2);

        // Funcionario
        Funcionario funcionario1 = new Funcionario(null, "Euclides Campos", "11111111113", "euclides", "catfish12", cidade1);
        Funcionario funcionario2 = new Funcionario(null, "Carlos Machado", "11111111114", "cmachado", "meupaieterno", cidade1);
        Funcionario funcionario3 = new Funcionario(null, "Irineu Silva", "11111111115", "isilva", "HexadoBrasil", cidade1);

        // Cliente
        Cliente cliente1 = new Cliente(null, "Carlos Eduardo", "11111111115", "carlosedu", "ngmsabeminhasenha", cidade2);
        Cliente cliente2 = new Cliente(null, "Maria Joaquina", "11111111116", "mariajq", "cirilo321", cidade2);
        Cliente cliente3 = new Cliente(null, "Pedro Sampaio, "11111111117", "pedrosam", "dançarina", cidade3);
        
        //Veiculo
        Veiculo veiculo1 = new Veiculo(null, "BEE4R22", true, "Corolla", 8, cidade2);
        Veiculo veiculo2 = new Veiculo(null, "BAE4Z12", false, "C4 Pallas", 21, cidade2);
        Veiculo veiculo3 = new Veiculo(null, "JAO4Z12", true, "Crossfox", 10, cidade3);
        
        //Quarto
        Quarto quarto1 = new Quarto(null, "1", true, 4);
        Quarto quarto2 = new Quarto(null, "2", false, 2);
        Quarto quarto3 = new Quarto(null, "3", true, 8);
                                                                
        //Local Evento
        LocalEvento localEvento1 = new LocalEvento(null, "Salão de Festas", false, 300);
        LocalEvento localEvento2 = new LocalEvento(null, "Área de Churrasco", true, 50);
        LocalEvento localEvento3 = new LocalEvento(null, "Salão de Festas Pequeno", true, 100);

        ufRepository.saveAll(Arrays.asList(uf1, uf2, uf3));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
        gerenteRepository.saveAll(Arrays.asList(gerente1, gerente2));
        funcionarioRepository.saveAll(Arrays.asList(funcionario1, funcionario2, funcionario3));
        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2, cliente3));
        veiculoRepository.saveAll(Arrays.asList(veiculo1, veiculo2, veiculo3));
        quartoRepository.saveAll(Arrays.asList(quarto1, quarto2, quarto3));
        localEventoRepository.saveAll(Arrays.asList(localEvento1, localEvento2, localEvento3));
    }
}
