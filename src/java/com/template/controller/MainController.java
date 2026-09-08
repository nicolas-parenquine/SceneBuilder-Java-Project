package com.template.controller;

import com.template.model.dto.Formula1DTO;
import com.template.service.PilotoService;
import com.template.util.DialogUtil;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final Logger logger =
            Logger.getLogger(MainController.class.getName());

    private final PilotoService pilotoService =
            new PilotoService();

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNacionalidade;

    @FXML
    private TextField txtEquipe;

    @FXML
    private CheckBox cbAtivo;

    @FXML
    private Label lblMensagem;

    @FXML
    private TableView<Formula1DTO> tblPiloto;

    @FXML
    private TableColumn<Formula1DTO, Integer> colId;

    @FXML
    private TableColumn<Formula1DTO, String> colNome;

    @FXML
    private TableColumn<Formula1DTO, String> colNacionalidade;

    @FXML
    private TableColumn<Formula1DTO, String> colEquipe;

    @FXML
    private TableColumn<Formula1DTO, Boolean> colAtivo;

    @FXML
    private void initialize() {

        configurarTabela();

        try {
            carregarPilotos();
            txtNome.requestFocus();

        } catch (Exception e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao carregar pilotos",
                    e
            );

            DialogUtil.mostrarErro(
                    "Erro ao carregar pilotos."
            );
        }
    }

    /**
     * Configura as colunas da tabela.
     */
    private void configurarTabela() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        colNacionalidade.setCellValueFactory(
                new PropertyValueFactory<>("nacionalidade")
        );

        colEquipe.setCellValueFactory(
                new PropertyValueFactory<>("equipe")
        );

        colAtivo.setCellValueFactory(
                new PropertyValueFactory<>("ativo")
        );
    }

    /**
     * Carrega os dados do piloto selecionado
     * nos campos do formulário.
     */
    @FXML
    private void carregarCampos(MouseEvent event) {

        Formula1DTO piloto =
                tblPiloto.getSelectionModel()
                        .getSelectedItem();

        if (piloto == null) {
            return;
        }

        txtNome.setText(
                piloto.getNome()
        );

        txtNacionalidade.setText(
                piloto.getNacionalidade()
        );

        txtEquipe.setText(
                piloto.getEquipe()
        );

        cbAtivo.setSelected(
                piloto.isAtivo()
        );

        mostrarMensagem(
                "Piloto selecionado.",
                "#3498DB"
        );
    }

    /**
     * Cadastra um novo piloto.
     */
    @FXML
    private void btnCriarAction(ActionEvent event) {

        try {

            Formula1DTO piloto =
                    obterPilotoDoFormulario();

            pilotoService.cadastrarPiloto(piloto);

            atualizarTelaAposOperacao(
                    "Piloto cadastrado com sucesso!",
                    "#2ECC71"
            );

        } catch (IllegalArgumentException e) {

            DialogUtil.mostrarAviso(
                    e.getMessage()
            );

        } catch (Exception e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao cadastrar piloto",
                    e
            );

            DialogUtil.mostrarErro(
                    "Erro ao cadastrar piloto."
            );
        }
    }

    /**
     * Atualiza o piloto selecionado.
     */
    @FXML
    private void btnAtualizarAction(ActionEvent event) {

        Formula1DTO piloto =
                tblPiloto.getSelectionModel()
                        .getSelectedItem();

        if (piloto == null) {

            mostrarMensagem(
                    "Selecione um piloto para atualizar.",
                    "#747D8C"
            );

            return;
        }

        try {

            preencherPilotoComFormulario(piloto);

            pilotoService.atualizarPiloto(piloto);

            atualizarTelaAposOperacao(
                    "Piloto atualizado com sucesso!",
                    "#3498DB"
            );

        } catch (IllegalArgumentException e) {

            DialogUtil.mostrarAviso(
                    e.getMessage()
            );

        } catch (Exception e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao atualizar piloto",
                    e
            );

            DialogUtil.mostrarErro(
                    "Erro ao atualizar piloto."
            );
        }
    }

    /**
     * Deleta o piloto selecionado.
     */
    @FXML
    private void btnDeletarAction(ActionEvent event) {

        Formula1DTO piloto =
                tblPiloto.getSelectionModel()
                        .getSelectedItem();

        if (piloto == null) {

            mostrarMensagem(
                    "Selecione um piloto para deletar.",
                    "#747D8C"
            );

            return;
        }

        try {

            pilotoService.deletarPiloto(
                    piloto.getId()
            );

            atualizarTelaAposOperacao(
                    "Piloto deletado com sucesso!",
                    "#E74C3C"
            );

        } catch (Exception e) {

            logger.log(
                    Level.SEVERE,
                    "Erro ao deletar piloto",
                    e
            );

            DialogUtil.mostrarErro(
                    "Erro ao deletar piloto."
            );
        }
    }

    /**
     * Limpa os campos do formulário.
     */
    @FXML
    private void btnLimparAction(ActionEvent event) {

        limparCamposFormulario();

        mostrarMensagem(
                "Campos limpos.",
                "#747D8C"
        );

        txtNome.requestFocus();
    }

    /**
     * Cria um DTO com os dados preenchidos
     * no formulário.
     */
    private Formula1DTO obterPilotoDoFormulario() {

        Formula1DTO piloto =
                new Formula1DTO();

        preencherPilotoComFormulario(piloto);

        return piloto;
    }

    /**
     * Transfere os dados da tela para o DTO.
     */
    private void preencherPilotoComFormulario(
            Formula1DTO piloto) {

        piloto.setNome(
                txtNome.getText()
        );

        piloto.setNacionalidade(
                txtNacionalidade.getText()
        );

        piloto.setEquipe(
                txtEquipe.getText()
        );

        piloto.setAtivo(
                cbAtivo.isSelected()
        );
    }

    /**
     * Carrega os pilotos na tabela.
     */
    private void carregarPilotos() {

        tblPiloto.setItems(
                FXCollections.observableArrayList(
                        pilotoService.listarPilotos()
                )
        );
    }

    /**
     * Atualiza a tela depois de uma operação
     * de cadastro, alteração ou exclusão.
     */
    private void atualizarTelaAposOperacao(
            String mensagem,
            String cor) {

        carregarPilotos();

        limparCamposFormulario();

        mostrarMensagem(
                mensagem,
                cor
        );

        txtNome.requestFocus();
    }

    /**
     * Limpa os campos do formulário.
     */
    private void limparCamposFormulario() {

        tblPiloto.getSelectionModel()
                .clearSelection();

        txtNome.clear();

        txtNacionalidade.clear();

        txtEquipe.clear();

        cbAtivo.setSelected(false);
    }

    /**
     * Exibe uma mensagem na tela.
     */
    private void mostrarMensagem(
            String texto,
            String cor) {

        if (lblMensagem != null) {

            lblMensagem.setText(texto);

            lblMensagem.setTextFill(
                    Color.web(cor)
            );
        }
    }
}