package com.template.validator;

public class TamanhoMinimoValidator implements Validator<String> {

    private final String nomeCampo;
    private final int tamanhoMinimo;
    private final String valor;

    public TamanhoMinimoValidator(
            String nomeCampo,
            String valor,
            int tamanhoMinimo
    ) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
        this.tamanhoMinimo = tamanhoMinimo;
    }

    @Override
    public boolean validar(String valor) {
        return valor != null
                && valor.trim().length() >= tamanhoMinimo;
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo
                + " deve possuir pelo menos "
                + tamanhoMinimo
                + " caracteres.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}
