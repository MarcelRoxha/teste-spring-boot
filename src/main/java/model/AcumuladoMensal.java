package model;

public class AcumuladoMensal {
	
	private double valorTotalDebitoEmpresa;
	private double valotTotalCreditoEmpresa;
	private int quantidadeTotalLancamentoDebito;
	private int quantidadeTotalLancamentoCredito;
	private double saldo;	
	
	public AcumuladoMensal() {
		super();
	}
	
	public double getValorTotalDebitoEmpresa() {
		return valorTotalDebitoEmpresa;
	}
	public void setValorTotalDebitoEmpresa(double valorTotalDebitoEmpresa) {
		this.valorTotalDebitoEmpresa = valorTotalDebitoEmpresa;
	}
	public double getValotTotalCreditoEmpresa() {
		return valotTotalCreditoEmpresa;
	}
	public void setValotTotalCreditoEmpresa(double valotTotalCreditoEmpresa) {
		this.valotTotalCreditoEmpresa = valotTotalCreditoEmpresa;
	}
	public int getQuantidadeTotalLancamentoDebito() {
		return quantidadeTotalLancamentoDebito;
	}
	public void setQuantidadeTotalLancamentoDebito(int quantidadeTotalLancamentoDebito) {
		this.quantidadeTotalLancamentoDebito = quantidadeTotalLancamentoDebito;
	}
	public int getQuantidadeTotalLancamentoCredito() {
		return quantidadeTotalLancamentoCredito;
	}
	public void setQuantidadeTotalLancamentoCredito(int quantidadeTotalLancamentoCredito) {
		this.quantidadeTotalLancamentoCredito = quantidadeTotalLancamentoCredito;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	
	
	
}
