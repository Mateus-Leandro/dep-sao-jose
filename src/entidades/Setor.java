package entidades;

public class Setor {
	Integer codSetor;
	String nome;
	
	public Setor() {
	}
	
	public Setor(Integer codSetor, String nome) {
		this.codSetor = codSetor;
		this.nome = nome;
	}

	public Integer getCodSetor() {
		return codSetor;
	}

	public void setCodSetor(Integer codSetor) {
		this.codSetor = codSetor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codSetor == null) ? 0 : codSetor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Setor other = (Setor) obj;
		if (codSetor == null) {
			if (other.codSetor != null)
				return false;
		} else if (!codSetor.equals(other.codSetor))
			return false;
		return true;
	}
	
}
