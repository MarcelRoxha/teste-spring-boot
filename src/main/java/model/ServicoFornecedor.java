package model;

public class ServicoFornecedor {
	
	
	private String identificadorFornecedor;
	private String codigoContaDebito;
	private String codigoContaCredito;
	private String descricaoServico;
	
	public ServicoFornecedor() {
		super();
	}
	
	public String getIdentificadorFornecedor() {
		return identificadorFornecedor;
	}
	public void setIdentificadorFornecedor(String identificadorFornecedor) {
		this.identificadorFornecedor = identificadorFornecedor;
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
	public String getDescricaoServico() {
		return descricaoServico;
	}
	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}
	
	

}
