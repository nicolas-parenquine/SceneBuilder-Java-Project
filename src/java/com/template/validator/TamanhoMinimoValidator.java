package com.template.validator;

public class TamanhoMinimoValidator implements Validator<String> {

    private final String nomeCampo;
    private final int tamanhoMinimo;
    private String valor;

    public TamanhoMinimoValidator(String nomeCampo, int tamanhoMinimo) {
        this.nomeCampo = nomeCampo;
        this.tamanhoMinimo = tamanhoMinimo;
    }

    @Override
    public boolean validar(String valor) {
        this.valor = valor;

        return valor != null && valor.length() >= tamanhoMinimo;
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo +
                " deve possuir pelo menos " + tamanhoMinimo + " caracteres.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

