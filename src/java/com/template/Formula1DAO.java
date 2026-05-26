package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Formula1DAO {

    private static final Logger logger =
            Logger.getLogger(Formula1DAO.class.getName());

    // CREATE
    public boolean cadastrarPiloto(Formula1DTO dto) {

        String sql =
                "INSERT INTO pilotos (nome, nacionalidade, equipe, ativo) VALUES (?, ?, ?, ?)";

        try (
                Connection con = new Conexao().conectaBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, dto.getNome());
            ps.setString(2, dto.getNacionalidade());
            ps.setString(3, dto.getEquipe());
            ps.setBoolean(4, dto.isAtivo());

            int linhasAfetadas = ps.executeUpdate();

            logger.info("Piloto cadastrado com sucesso");

            return linhasAfetadas > 0;

        } catch (SQLException e) {

            logger.log(Level.SEVERE,
                    "Erro ao cadastrar piloto", e);

            return false;
        }
    }

    // READ
    public List<Formula1DTO> listarPilotos() {

        String sql = "SELECT * FROM pilotos";

        List<Formula1DTO> listaPilotos =
                new ArrayList<>();

        try (
                Connection con = new Conexao().conectaBD();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Formula1DTO dto =
                        new Formula1DTO();

                dto.setId(rs.getInt("id"));
                dto.setNome(rs.getString("nome"));
                dto.setNacionalidade(
                        rs.getString("nacionalidade")
                );
                dto.setEquipe(rs.getString("equipe"));
                dto.setAtivo(rs.getBoolean("ativo"));

                listaPilotos.add(dto);
            }

            logger.info("Lista de pilotos carregada");

        } catch (SQLException e) {

            logger.log(Level.SEVERE,
                    "Erro ao listar pilotos", e);
        }

        return listaPilotos;
    }

    // UPDATE
    public boolean atualizarPiloto(Formula1DTO dto) {

        String sql =
                "UPDATE pilotos SET nome=?, nacionalidade=?, equipe=?, ativo=? WHERE id=?";

        try (
                Connection con = new Conexao().conectaBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, dto.getNome());
            ps.setString(2, dto.getNacionalidade());
            ps.setString(3, dto.getEquipe());
            ps.setBoolean(4, dto.isAtivo());
            ps.setInt(5, dto.getId());

            int linhasAfetadas =
                    ps.executeUpdate();

            logger.info("Piloto atualizado");

            return linhasAfetadas > 0;

        } catch (SQLException e) {

            logger.log(Level.SEVERE,
                    "Erro ao atualizar piloto", e);

            return false;
        }
    }

    // DELETE
    public boolean deletarPiloto(int id) {

        String sql =
                "DELETE FROM pilotos WHERE id=?";

        try (
                Connection con = new Conexao().conectaBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            int linhasAfetadas =
                    ps.executeUpdate();

            logger.info("Piloto deletado");

            return linhasAfetadas > 0;

        } catch (SQLException e) {

            logger.log(Level.SEVERE,
                    "Erro ao deletar piloto", e);

            return false;
        }
    }
}