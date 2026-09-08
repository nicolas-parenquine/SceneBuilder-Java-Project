package com.template.service;

import com.template.model.dto.Formula1DTO;

import java.util.List;

public interface IPilotoService {
    List<Formula1DTO> listarPilotos();
    void cadastrarPiloto(Formula1DTO piloto);
    void atualizarPiloto(Formula1DTO piloto);
    void deletarPiloto(int id);
}
