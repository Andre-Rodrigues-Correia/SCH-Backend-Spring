package com.smithpalacehotel.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import com.smithpalacehotel.repository.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smithpalacehotel.models.*;

@Service
public class DBService {
    @Autowired
    private UfRepository ufRepository;

    public void instantiateTestDatabase() throws ParseException {
        // Instanciando os objetos de modelo
        Uf uf1 = new Uf(null, "ES", "Espírito Santo");
        Uf uf2 = new Uf(null, "MG", "Minas Gerais");

        ufRepository.saveAll(Arrays.asList(uf1, uf2));
    }
}
