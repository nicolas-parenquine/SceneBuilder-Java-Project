package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController {

    private static final Logger logger =
            Logger.getLogger(MainController.class.getName());

    @FXML private TextField txtNome;
    @FXML private TextField txtNacionalidade;
    @FXML private TextField txtEquipe;
    @FXML private TextField txtAtivo;

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

        carregarTabela();

        tblPiloto.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, piloto) -> {

                    if (piloto != null) {
                        txtNome.setText(piloto.getNome());
                        txtNacionalidade.setText(piloto.getNacionalidade());
                        txtEquipe.setText(piloto.getEquipe());
                        txtAtivo.setText(String.valueOf(piloto.isAtivo()));
                    }
                });
    }

    @FXML
    private void btnCriarAction(ActionEvent event) {

        try {

            Formula1DTO dto = new Formula1DTO();

            dto.setNome(txtNome.getText());
            dto.setNacionalidade(txtNacionalidade.getText());
            dto.setEquipe(txtEquipe.getText());
            dto.setAtivo(Boolean.parseBoolean(txtAtivo.getText()));

            if (dao.cadastrarPiloto(dto)) {
                carregarTabela();
                limparCampos();
                logger.info("Piloto criado");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar piloto", e);
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {

        try {

            Formula1DTO piloto =
                    tblPiloto.getSelectionModel().getSelectedItem();

            if (piloto != null) {

                piloto.setNome(txtNome.getText());
                piloto.setNacionalidade(txtNacionalidade.getText());
                piloto.setEquipe(txtEquipe.getText());
                piloto.setAtivo(Boolean.parseBoolean(txtAtivo.getText()));

                if (dao.atualizarPiloto(piloto)) {
                    carregarTabela();
                    limparCampos();
                    logger.info("Piloto atualizado");
                }
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar piloto", e);
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {

        try {

            Formula1DTO piloto =
                    tblPiloto.getSelectionModel().getSelectedItem();

            if (piloto != null &&
                    dao.deletarPiloto(piloto.getId())) {

                carregarTabela();
                limparCampos();

                logger.info("Piloto deletado");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao deletar piloto", e);
        }
    }

    private void carregarTabela() {

        listaPilotos.clear();
        listaPilotos.addAll(dao.listarPilotos());

        tblPiloto.setItems(listaPilotos);
    }

    private void limparCampos() {

        txtNome.clear();
        txtNacionalidade.clear();
        txtEquipe.clear();
        txtAtivo.clear();
    }
}