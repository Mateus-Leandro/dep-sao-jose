package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.DB;
import entities.Cliente;

public class ClienteDAO {

	private Connection conn;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ClienteDAO() {
	}

	public Cliente inserirCliente(Cliente cliente) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);
			cliente.setIdCliente(null);
			ps = conn.prepareStatement(
					"INSERT INTO clientes " + "(nome, apelido, documento, inscricaoEstadual, cep, cidade, tipoLogradouro, endereco, "
							+ "referencia, numero, bairro, email, celular, telefone, dataCadastro)"
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getApelido());
			ps.setString(3, cliente.getCpf_cnpj());
			ps.setString(4, cliente.getInscricao_estadual());
			ps.setString(5, cliente.getCep());
			ps.setString(6, cliente.getCidade());
			ps.setString(7, cliente.getEndereco());
			ps.setString(8, cliente.getReferencia());
			ps.setString(9, cliente.getNumero());
			ps.setString(10, cliente.getBairro());
			ps.setString(11, cliente.getEmail());
			ps.setString(12, cliente.getCelular());
			ps.setString(13, cliente.getTelefone());
			ps.setDate(14, new java.sql.Date(System.currentTimeMillis()));

			ps.execute();
			conn.commit();
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				cliente.setIdCliente(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cliente;
	}
	
	
	
	public ArrayList<Cliente> listarClientes(ArrayList<Cliente> clientes){
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			ps = conn.prepareStatement("SELECT * FROM clientes");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("idClientes"));
				cliente.setNome(rs.getString("nome"));
				cliente.setApelido(rs.getString("apelido"));
				cliente.setCpf_cnpj(rs.getString("documento"));
				cliente.setCep(rs.getString("cep"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setReferencia(rs.getString("referencia"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setBairro(rs.getString("bairro"));
				cliente.setEmail(rs.getString("email"));
				cliente.setCelular(rs.getString("celular"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setDataCadastro(rs.getDate("dataCadastro"));
				clientes.add(cliente);
			}
			return clientes;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
