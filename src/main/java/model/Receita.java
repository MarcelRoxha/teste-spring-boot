package model;

public class Receita {
	
	private String descriao;
	private String nomeCliente;
	private String endereco;
	private String destinoPagamento;
	private String contatoTelefonico;
	private String email;
	
	public Receita() {
		super();
	}
	
	public String getDescriao() {
		return descriao;
	}
	public void setDescriao(String descriao) {
		this.descriao = descriao;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getDestinoPagamento() {
		return destinoPagamento;
	}
	public void setDestinoPagamento(String destinoPagamento) {
		this.destinoPagamento = destinoPagamento;
	}
	public String getContatoTelefonico() {
		return contatoTelefonico;
	}
	public void setContatoTelefonico(String contatoTelefonico) {
		this.contatoTelefonico = contatoTelefonico;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
