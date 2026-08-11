package com.template.validator;

import com.template.model.Formula1DTO;

public class PilotoValidator {

    // Valida se os campos estao nulos ou vazios
    public static boolean validarCamposNulos(Formula1DTO piloto) {

        return piloto != null
                && piloto.getNome() != null
                && !piloto.getNome().trim().isEmpty()
                && piloto.getNacionalidade() != null
                && !piloto.getNacionalidade().trim().isEmpty()
                && piloto.getEquipe() != null
                && !piloto.getEquipe().trim().isEmpty();
    }


    // Verifica se os textos possuem pelo menos 3 caracteres
    public static boolean validarDadosPiloto(Formula1DTO piloto) {

        if (piloto == null) {
            return false;
        }

        if (piloto.getNome() == null
                || piloto.getNacionalidade() == null
                || piloto.getEquipe() == null) {

            return false;
        }

        if (piloto.getNome().trim().length() < 3) {
            return false;
        }

        if (piloto.getNacionalidade().trim().length() < 3) {
            return false;
        }

        if (piloto.getEquipe().trim().length() < 3) {
            return false;
        }

        return true;
    }
}