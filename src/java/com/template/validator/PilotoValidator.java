package com.template.validator;

import com.template.model.dto.Formula1DTO;

import java.util.ArrayList;
import java.util.List;

public class PilotoValidator implements IPilotoValidator {

    public void validar(Formula1DTO piloto) {

        if (piloto == null) {
            throw new IllegalArgumentException(
                    "Piloto não informado."
            );
        }

        List<Validator<String>> validadores = new ArrayList<>();

        // Nome
        validadores.add(
                new CampoObrigatorioValidator(
                        "Nome",
                        piloto.getNome()
                )
        );

        validadores.add(
                new TamanhoMinimoValidator(
                        "Nome",
                        piloto.getNome(),
                        3
                )
        );

        // Nacionalidade
        validadores.add(
                new CampoObrigatorioValidator(
                        "Nacionalidade",
                        piloto.getNacionalidade()
                )
        );

        validadores.add(
                new TamanhoMinimoValidator(
                        "Nacionalidade",
                        piloto.getNacionalidade(),
                        3
                )
        );

        // Equipe
        validadores.add(
                new CampoObrigatorioValidator(
                        "Equipe",
                        piloto.getEquipe()
                )
        );

        validadores.add(
                new TamanhoMinimoValidator(
                        "Equipe",
                        piloto.getEquipe(),
                        3
                )
        );

        // Executa as validações
        for (Validator<String> validador : validadores) {

            if (!validador.validar(validador.getValor())) {

                throw new IllegalArgumentException(
                        validador.getMensagemErro()
                );
            }
        }
    }
}
