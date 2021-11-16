package model;

public class AcumuladoMesReferenciaSaida {

	

    private String nomeUser;
    private int mesReferenciaSaida;
    private String emailUser;
    private double valorTotalEntradaMensal;
    private int quantidadeTotalLancamentosEntradaMensal;
    private String created;
    
    
    
    
	public AcumuladoMesReferenciaSaida() {
		super();
	}
	public String getNomeUser() {
		return nomeUser;
	}
	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}
	public int getMesReferenciaSaida() {
		return mesReferenciaSaida;
	}
	public void setMesReferenciaSaida(int mesReferenciaSaida) {
		this.mesReferenciaSaida = mesReferenciaSaida;
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
