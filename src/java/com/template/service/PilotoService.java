package com.template.service;

import com.template.model.dao.Formula1DAO;
import com.template.model.dto.Formula1DTO;
import com.template.validator.PilotoValidator;

import java.util.List;

public class PilotoService implements IPilotoService{

    private final Formula1DAO dao;
    private final PilotoValidator validator;

    public PilotoService() {

        this.dao = new Formula1DAO();
        this.validator = new PilotoValidator();
    }

    /**
     * Retorna todos os pilotos cadastrados.
     */
    public List<Formula1DTO> listarPilotos() {

        return dao.listarPilotos();
    }

    /**
     * Cadastra um novo piloto.
     */
    public void cadastrarPiloto(
            Formula1DTO piloto) {

        validator.validar(piloto);

        boolean sucesso =
                dao.cadastrarPiloto(piloto);

        if (!sucesso) {

            throw new RuntimeException(
                    "Não foi possível cadastrar o piloto."
            );
        }
    }

    /**
     * Atualiza um piloto existente.
     */
    public void atualizarPiloto(
            Formula1DTO piloto) {

        validator.validar(piloto);

        boolean sucesso =
                dao.atualizarPiloto(piloto);

        if (!sucesso) {

            throw new RuntimeException(
                    "Não foi possível atualizar o piloto."
            );
        }
    }

    /**
     * Deleta um piloto pelo ID.
     */
    public void deletarPiloto(int id) {

        boolean sucesso =
                dao.deletarPiloto(id);

        if (!sucesso) {

            throw new RuntimeException(
                    "Não foi possível deletar o piloto."
            );
        }
    }
}