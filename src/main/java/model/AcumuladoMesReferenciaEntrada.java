package model;

public class AcumuladoMesReferenciaEntrada {

    private String identificador;
    private String nomeUser;
    private int mesReferenciaEntrada;
    private String emailUser;
    private double valorTotalEntradaMensal;
    private int quantidadeTotalLancamentosEntradaMensal;
    private String created;
    
    
    
    
	public AcumuladoMesReferenciaEntrada() {
		super();
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getNomeUser() {
		return nomeUser;
	}
	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}
	public int getMesReferenciaEntrada() {
		return mesReferenciaEntrada;
	}
	public void setMesReferenciaEntrada(int mesReferenciaEntrada) {
		this.mesReferenciaEntrada = mesReferenciaEntrada;
	}
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	public double getValorTotalEntradaMensal() {
		return valorTotalEntradaMensal;
	}
	public void setValorTotalEntradaMensal(double valorTotalEntradaMensal) {
		this.valorTotalEntradaMensal = valorTotalEntradaMensal;
	}
	public int getQuantidadeTotalLancamentosEntradaMensal() {
		return quantidadeTotalLancamentosEntradaMensal;
	}
	public void setQuantidadeTotalLancamentosEntradaMensal(int quantidadeTotalLancamentosEntradaMensal) {
		this.quantidadeTotalLancamentosEntradaMensal = quantidadeTotalLancamentosEntradaMensal;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
    
    
}
