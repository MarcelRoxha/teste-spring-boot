package model;

public class Fornecedor {
	
	private String razaoSocial;
	private String cnpj;
	private String endereco;
	private String contaBancaria;
	private String formadePagamento;
	private String contatoTelefonico;
	private String email;
	private int quantidadeServicos;
    
    
    
	public Fornecedor() {
		super();
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getContaBancaria() {
		return contaBancaria;
	}
	public void setContaBancaria(String contaBancaria) {
		this.contaBancaria = contaBancaria;
	}
	public String getFormadePagamento() {
		return formadePagamento;
	}
	public void setFormadePagamento(String formadePagamento) {
		this.formadePagamento = formadePagamento;
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
	public int getQuantTipoServico() {
		return quantidadeServicos;
	}
	public void setQuantTipoServico(int quantidadeServicos) {
		this.quantidadeServicos = quantidadeServicos;
	}

}
