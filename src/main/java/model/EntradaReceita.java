package model;

public class EntradaReceita {
	
	private String identificadorReceita;
	private String codigoContaDebito;
	private String codigoContaCredito;
	private String descricaoReceita;
	
	public EntradaReceita() {
		super();
	}
	public String getIdentificadorReceita() {
		return identificadorReceita;
	}
	public void setIdentificadorReceita(String identificadorReceita) {
		this.identificadorReceita = identificadorReceita;
	}
	public String getCodigoContaDebito() {
		return codigoContaDebito;
	}
	public void setCodigoContaDebito(String codigoContaDebito) {
		this.codigoContaDebito = codigoContaDebito;
	}
	public String getCodigoContaCredito() {
		return codigoContaCredito;
	}
	public void setCodigoContaCredito(String codigoContaCredito) {
		this.codigoContaCredito = codigoContaCredito;
	}
	public String getDescricaoReceita() {
		return descricaoReceita;
	}
	public void setDescricaoReceita(String descricaoReceita) {
		this.descricaoReceita = descricaoReceita;
	}

}
