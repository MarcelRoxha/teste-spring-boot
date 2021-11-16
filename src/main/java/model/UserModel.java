package model;

public class UserModel {
	
	 	private String user_id;
	    private String nomeUser;
	    private String emailUser;
	    private String situacaoUsuario;
	    private double valorTotalEntradaMensal;
	    private double valorTotalSaidaMensal;
	    private int quantidadeTotalLancamentosEntradaMensal;
	    private int quantidadeTotalLancamentosSaidaMensal;
	    
	    
	    
		public UserModel() {
			super();
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public String getNomeUser() {
			return nomeUser;
		}
		public void setNomeUser(String nomeUser) {
			this.nomeUser = nomeUser;
		}
		public String getEmailUser() {
			return emailUser;
		}
		public void setEmailUser(String emailUser) {
			this.emailUser = emailUser;
		}
		public String getSituacaoUsuario() {
			return situacaoUsuario;
		}
		public void setSituacaoUsuario(String situacaoUsuario) {
			this.situacaoUsuario = situacaoUsuario;
		}
		public double getValorTotalEntradaMensal() {
			return valorTotalEntradaMensal;
		}
		public void setValorTotalEntradaMensal(double valorTotalEntradaMensal) {
			this.valorTotalEntradaMensal = valorTotalEntradaMensal;
		}
		public double getValorTotalSaidaMensal() {
			return valorTotalSaidaMensal;
		}
		public void setValorTotalSaidaMensal(double valorTotalSaidaMensal) {
			this.valorTotalSaidaMensal = valorTotalSaidaMensal;
		}
		public int getQuantidadeTotalLancamentosEntradaMensal() {
			return quantidadeTotalLancamentosEntradaMensal;
		}
		public void setQuantidadeTotalLancamentosEntradaMensal(int quantidadeTotalLancamentosEntradaMensal) {
			this.quantidadeTotalLancamentosEntradaMensal = quantidadeTotalLancamentosEntradaMensal;
		}
		public int getQuantidadeTotalLancamentosSaidaMensal() {
			return quantidadeTotalLancamentosSaidaMensal;
		}
		public void setQuantidadeTotalLancamentosSaidaMensal(int quantidadeTotalLancamentosSaidaMensal) {
			this.quantidadeTotalLancamentosSaidaMensal = quantidadeTotalLancamentosSaidaMensal;
		}
	    
	    


}
