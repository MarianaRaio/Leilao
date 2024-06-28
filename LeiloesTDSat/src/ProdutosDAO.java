/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        
        conn = new conectDAO().connectDB();
        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Produto não foi salvo!");
        }

    }

    public ArrayList<ProdutosDTO> ListarProdutos() {
        listagem.clear();

        conn = new conectDAO().connectDB();
        try {
            prep = conn.prepareStatement("SELECT * FROM produtos");
            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO produtos = new ProdutosDTO();
                produtos.setId(resultset.getInt("id"));
                produtos.setNome(resultset.getString("nome"));
                produtos.setValor(resultset.getInt("valor"));
                produtos.setStatus(resultset.getString("status"));

                listagem.add(produtos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();

                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return listagem;
    }
    public void VenderProduto(int id){
        conn = new conectDAO().connectDB();
        try{
            prep = conn.prepareStatement("UPDATE produtos SET status = ? WHERE id = ?");
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
            
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public ArrayList<ProdutosDTO> ListarProdutosVendidos() {
        ArrayList<ProdutosDTO> ProdutosVendidos = new ArrayList<>();
        conn = new conectDAO().connectDB();
        try {
            prep = conn.prepareStatement("SELECT * FROM produtos WHERE status = ?");
            prep.setString(1, "Vendido");
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                ProdutosVendidos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ProdutosVendidos;
     }
               
}
