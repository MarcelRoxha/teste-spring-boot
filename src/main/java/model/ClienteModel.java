package model;

public class ClienteModel {
	
	private String identificador;
    private String nome;
    private String cpf;
    private String usuariocliente;
    private String emailCliente;
    private String telefone;
    private String celular;
    private String obs;
    private int quantidadeEmpresaCadastradas;
    private String status;
    
    public ClienteModel() {
    	
    }
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCPF() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getUsuariocliente() {
		return usuariocliente;
	}
	public void setUsuariocliente(String usuariocliente) {
		this.usuariocliente = usuariocliente;
	}
	public String getEmailCliente() {
		return emailCliente;
	}
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public int getQuantidadeEmpresaCadastradas() {
		return quantidadeEmpresaCadastradas;
	}

	public void setQuantidadeEmpresaCadastradas(int quantidadeEmpresaCadastradas) {
		this.quantidadeEmpresaCadastradas = quantidadeEmpresaCadastradas;
	}

    

}
