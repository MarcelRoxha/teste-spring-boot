package model;

public class ContaEntradaCaixa {
	
private String	identificadorEmpresa;
private String  historico ;
private String  codigoContaCaixa;
private String  saldoCaixa;


public ContaEntradaCaixa() {
	super();
}

public String getIdentificadorEmpresa() {
	return identificadorEmpresa;
}
public void setIdentificadorEmpresa(String identificadorEmpresa) {
	this.identificadorEmpresa = identificadorEmpresa;
}
public String getHistorico() {
	return historico;
}
public void setHistorico(String historico) {
	this.historico = historico;
}
public String getCodigoContaCaixa() {
	return codigoContaCaixa;
}
public void setCodigoContaCaixa(String codigoContaCaixa) {
	this.codigoContaCaixa = codigoContaCaixa;
}
public String getSaldoCaixa() {
	return saldoCaixa;
}
public void setSaldoCaixa(String saldoCaixa) {
	this.saldoCaixa = saldoCaixa;
}



}
