package com.template.validator;

import com.template.model.Formula1DTO;

public class PilotoValidator {

    /**
     * Valida todos os dados necessários
     * para um piloto.
     */
    public void validar(Formula1DTO piloto) {

        if (piloto == null) {

            throw new IllegalArgumentException(
                    "Piloto não informado."
            );
        }

        if (campoVazio(piloto.getNome())
                || campoVazio(piloto.getNacionalidade())
                || campoVazio(piloto.getEquipe())) {

            throw new IllegalArgumentException(
                    "Preencha todos os campos antes de prosseguir."
            );
        }

        if (piloto.getNome().trim().length() < 3
                || piloto.getNacionalidade().trim().length() < 3
                || piloto.getEquipe().trim().length() < 3) {

            throw new IllegalArgumentException(
                    "Os campos devem possuir pelo menos 3 caracteres."
            );
        }
    }

    /**
     * Verifica se um campo é nulo ou vazio.
     */
    private boolean campoVazio(String valor) {

        return valor == null
                || valor.trim().isEmpty();
    }
}