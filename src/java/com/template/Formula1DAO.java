package com.template;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.*;
import model.Conexao;
import model.dto.Formula1DTO;
/**
 *
 * @author ra2457054
 */
public class Formula1DAO {
    // CREATE
    public void cadastrarPiloto(Formula1DTO dto) {
        String sql = "INSERT INTO pilotos (nome, nacionalidade, equipe, ativo) VALUES (?, ?, ?, ?)";

        try (
                Connection con = new Conexao().conectaBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, dto.getNome());
            ps.setString(2, dto.getNacionalidade());
            ps.setString(3, dto.getEquipe());
            ps.setBoolean(4, dto.isAtivo());

            ps.executeUpdate();
            System.out.println("Piloto cadastrado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public void listarPilotos() {
        String sql = "SELECT * FROM pilotos";

        try (
                Connection con = new Conexao().conectaBD();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("nome") + " | " +
                                rs.getString("nacionalidade") + " | " +
                                rs.getString("equipe") + " | " +
                                rs.getBoolean("ativo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void atualizarPiloto(Formula1DTO dto) {
        String sql = "UPDATE pilotos SET nome=?, nacionalidade=?, equipe=?, ativo=? WHERE id=?";

        try (
                Connection con = new Conexao().conectaBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, dto.getNome());
            ps.setString(2, dto.getNacionalidade());
            ps.setString(3, dto.getEquipe());
            ps.setBoolean(4, dto.isAtivo());
            ps.setInt(5, dto.getId());

            ps.executeUpdate();
            System.out.println("Atualizado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletarPiloto(int id) {
        String sql = "DELETE FROM pilotos WHERE id=?";

        try (
                Connection con = new Conexao().conectaBD();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Deletado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

