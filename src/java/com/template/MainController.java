package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final Logger logger =
            Logger.getLogger(MainController.class.getName());

    @FXML private TextField txtNome;
    @FXML private TextField txtNacionalidade;
    @FXML private TextField txtEquipe;
    @FXML private CheckBox cbAtivo;
    @FXML private Label lblMensagem;

    @FXML private TableView<Formula1DTO> tblPiloto;

    @FXML private TableColumn<Formula1DTO, Integer> colId;
    @FXML private TableColumn<Formula1DTO, String> colNome;
    @FXML private TableColumn<Formula1DTO, String> colNacionalidade;
    @FXML private TableColumn<Formula1DTO, String> colEquipe;
    @FXML private TableColumn<Formula1DTO, Boolean> colAtivo;

    private final Formula1DAO dao = new Formula1DAO();

    private final ObservableList<Formula1DTO> listaPilotos =
            FXCollections.observableArrayList();

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
            logger.log(Level.SEVERE, "Erro ao carregar pilotos na inicialização", e);
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

            if (lblMensagem != null) {
                lblMensagem.setTextFill(Color.web("#3498DB"));
                lblMensagem.setText("Piloto selecionado.");
            }
            logger.info("Campos preenchidos via clique na tabela para o piloto ID: " + piloto.getId());
        }
    }

    @FXML
    private void btnCriarAction(ActionEvent event) {
        try {
            Formula1DTO dto = new Formula1DTO();
            dto.setNome(txtNome.getText());
            dto.setNacionalidade(txtNacionalidade.getText());
            dto.setEquipe(txtEquipe.getText());
            dto.setAtivo(cbAtivo.isSelected());

            if (dao.cadastrarPiloto(dto)) {
                carregarPilotos();
                limparCamposFormulario();

                exibirMensagem("Piloto cadastrado com sucesso!", "#2ECC71");
                txtNome.requestFocus();

                logger.info("Piloto criado");
            }
        } catch (Exception e) {
            exibirMensagem("Erro ao cadastrar piloto.", "#E74C3C");
            logger.log(Level.SEVERE, "Erro ao criar piloto", e);
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        try {
            Formula1DTO piloto = tblPiloto.getSelectionModel().getSelectedItem();

            if (piloto != null) {
                piloto.setNome(txtNome.getText());
                piloto.setNacionalidade(txtNacionalidade.getText());
                piloto.setEquipe(txtEquipe.getText());
                piloto.setAtivo(cbAtivo.isSelected());

                if (dao.atualizarPiloto(piloto)) {
                    carregarPilotos();
                    limparCamposFormulario();

                    // UX: Mensagem de Sucesso e Foco devolvido ao primeiro campo
                    exibirMensagem("Piloto atualizado com sucesso!", "#3498DB");
                    txtNome.requestFocus();

                    logger.info("Piloto updated");
                }
            } else {
                exibirMensagem("Selecione um piloto na tabela para atualizar.", "#747D8C");
            }
        } catch (Exception e) {
            exibirMensagem("Erro ao atualizar piloto.", "#E74C3C");
            logger.log(Level.SEVERE, "Erro ao atualizar piloto", e);
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        try {
            Formula1DTO piloto = tblPiloto.getSelectionModel().getSelectedItem();

            if (piloto != null) {
                if (dao.deletarPiloto(piloto.getId())) {
                    carregarPilotos();
                    limparCamposFormulario();

                    // UX: Mensagem de Sucesso e Foco devolvido ao primeiro campo
                    exibirMensagem("Piloto deletado com sucesso!", "#E74C3C");
                    txtNome.requestFocus();

                    logger.info("Piloto deletado");
                }
            } else {
                exibirMensagem("Selecione um piloto na tabela para deletar.", "#747D8C");
            }
        } catch (Exception e) {
            exibirMensagem("Erro ao deletar piloto.", "#E74C3C");
            logger.log(Level.SEVERE, "Erro ao deletar piloto", e);
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        try {
            limparCamposFormulario();

            // UX: Limpa o texto de feedback e joga o cursor de volta para o Nome
            if (lblMensagem != null) lblMensagem.setText("");
            txtNome.requestFocus();

            logger.info("Campos limpos com sucesso.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao limpar os campos", e);
        }
    }

    private void carregarPilotos() {
        listaPilotos.clear();
        listaPilotos.addAll(dao.listarPilotos());
        tblPiloto.setItems(listaPilotos);
    }

    private void limparCamposFormulario() {
        tblPiloto.getSelectionModel().clearSelection();
        txtNome.clear();
        txtNacionalidade.clear();
        txtEquipe.clear();
        cbAtivo.setSelected(false);
    }

    private void exibirMensagem(String texto, String corHex) {
        if (lblMensagem != null) {
            lblMensagem.setTextFill(Color.web(corHex));
            lblMensagem.setText(texto);
        }
    }
}