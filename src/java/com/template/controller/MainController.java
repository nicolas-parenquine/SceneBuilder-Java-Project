package com.template.controller;

import com.template.model.Formula1DAO;
import com.template.model.Formula1DTO;
import com.template.util.DialogUtil;
import com.template.validator.PilotoValidator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        colEquipe.setCellValueFactory(new PropertyValueFactory<>("equipe"));
        colAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));

        try {

            carregarPilotos();
            txtNome.requestFocus();

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Erro ao carregar pilotos", e);

            DialogUtil.mostrarErro("Erro ao carregar pilotos.");
        }
    }

    @FXML
    private void carregarCampos(MouseEvent event) {

        Formula1DTO piloto = tblPiloto.getSelectionModel().getSelectedItem();

        if (piloto != null) {

            txtNome.setText(piloto.getNome());
            txtNacionalidade.setText(piloto.getNacionalidade());
            txtEquipe.setText(piloto.getEquipe());
            cbAtivo.setSelected(piloto.isAtivo());

            mostrarMensagem("Piloto selecionado.", "#3498DB");
        }
    }

    // CREATE
    @FXML
    private void btnCriarAction(ActionEvent event) {

        try {

            Formula1DTO formula1DTO = new Formula1DTO();

            formula1DTO.setNome(txtNome.getText());
            formula1DTO.setNacionalidade(txtNacionalidade.getText());
            formula1DTO.setEquipe(txtEquipe.getText());
            formula1DTO.setAtivo(cbAtivo.isSelected());

            if (!PilotoValidator.validarCamposNulos(formula1DTO)) {

                DialogUtil.mostrarAviso(
                        "Preencha todos os campos antes de prosseguir."
                );

                return;
            }

            if (!PilotoValidator.validarDadosPiloto(formula1DTO)) {

                DialogUtil.mostrarAviso(
                        "Os campos devem possuir pelo menos 3 caracteres."
                );

                return;
            }

            Formula1DAO formula1DAO = new Formula1DAO();

            if (formula1DAO.cadastrarPiloto(formula1DTO)) {

                carregarPilotos();
                limparCamposFormulario();

                mostrarMensagem(
                        "Piloto cadastrado com sucesso!",
                        "#2ECC71"
                );

                txtNome.requestFocus();
            }

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Erro ao cadastrar piloto", e);

            DialogUtil.mostrarErro("Erro ao cadastrar piloto.");
        }
    }

    // UPDATE
    @FXML
    private void btnAtualizarAction(ActionEvent event) {

        try {

            Formula1DTO piloto =
                    tblPiloto.getSelectionModel().getSelectedItem();

            if (piloto != null) {

                piloto.setNome(txtNome.getText());
                piloto.setNacionalidade(txtNacionalidade.getText());
                piloto.setEquipe(txtEquipe.getText());
                piloto.setAtivo(cbAtivo.isSelected());

                if (!PilotoValidator.validarCamposNulos(piloto)) {

                    DialogUtil.mostrarAviso(
                            "Preencha todos os campos antes de prosseguir."
                    );

                    return;
                }

                if (!PilotoValidator.validarDadosPiloto(piloto)) {

                    DialogUtil.mostrarAviso(
                            "Os campos devem possuir pelo menos 3 caracteres."
                    );

                    return;
                }

                Formula1DAO formula1DAO = new Formula1DAO();

                if (formula1DAO.atualizarPiloto(piloto)) {

                    carregarPilotos();
                    limparCamposFormulario();

                    mostrarMensagem(
                            "Piloto atualizado com sucesso!",
                            "#3498DB"
                    );

                    txtNome.requestFocus();
                }

            } else {

                mostrarMensagem(
                        "Selecione um piloto para atualizar.",
                        "#747D8C"
                );
            }

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Erro ao atualizar piloto", e);

            DialogUtil.mostrarErro("Erro ao atualizar piloto.");
        }
    }

    // DELETE
    @FXML
    private void btnDeletarAction(ActionEvent event) {

        try {

            Formula1DTO piloto =
                    tblPiloto.getSelectionModel().getSelectedItem();

            if (piloto != null) {

                Formula1DAO formula1DAO = new Formula1DAO();

                if (formula1DAO.deletarPiloto(piloto.getId())) {

                    carregarPilotos();
                    limparCamposFormulario();

                    mostrarMensagem(
                            "Piloto deletado com sucesso!",
                            "#E74C3C"
                    );

                    txtNome.requestFocus();
                }

            } else {

                mostrarMensagem(
                        "Selecione um piloto para deletar.",
                        "#747D8C"
                );
            }

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Erro ao deletar piloto", e);

            DialogUtil.mostrarErro("Erro ao deletar piloto.");
        }
    }

    // CLEAR
    @FXML
    private void btnLimparAction(ActionEvent event) {

        try {

            limparCamposFormulario();

            mostrarMensagem(
                    "Campos limpos.",
                    "#747D8C"
            );

            txtNome.requestFocus();

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Erro ao limpar campos", e);

            DialogUtil.mostrarErro("Erro ao limpar campos.");
        }
    }

    // LIST
    private void carregarPilotos() {

        Formula1DAO formula1DAO = new Formula1DAO();

        ObservableList<Formula1DTO> listaPilotos =
                FXCollections.observableArrayList();

        listaPilotos.addAll(formula1DAO.listarPilotos());

        tblPiloto.setItems(listaPilotos);
    }

    private void limparCamposFormulario() {

        tblPiloto.getSelectionModel().clearSelection();

        txtNome.clear();
        txtNacionalidade.clear();
        txtEquipe.clear();

        cbAtivo.setSelected(false);
    }

    private void mostrarMensagem(String texto, String cor) {

        if (lblMensagem != null) {

            lblMensagem.setText(texto);
            lblMensagem.setTextFill(Color.web(cor));
        }
    }
}

