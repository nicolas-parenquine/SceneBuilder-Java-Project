package com.template.util;

import javafx.scene.control.Alert;

public class DialogUtil {

    public static void mostrarErro(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }

    public static void mostrarAviso(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }
}