package entities.credenciaisDb;

public class CredenciaisDb {
	private Integer NS;

	private String usuario;
	private String nomeBanco;
	private String senha;

	public CredenciaisDb(Integer NS) {
		this.NS = NS;
		pegaCredenciais(NS);
	}

	public void pegaCredenciais(Integer NS) {
		switch (NS) {
			case 1:
				this.usuario = "dep";
				this.nomeBanco = "banco_deposito";
				this.senha = "dep@saojose";
				break;
		}
	}

	public String getUsuario() {
		return usuario;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public String getSenha() {
		return senha;
	}

}
