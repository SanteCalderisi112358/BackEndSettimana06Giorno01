package ProgettoSettimana05.SpringBootII.Utente;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class UtenteRequestPayload {

	private String nome;
	private String cognome;
	private String username;
	private String email;
	private String password;
	private TipoUtente tipoUtente;

	public UtenteRequestPayload(String nome, String cognome, String username, String email, String password,
			TipoUtente tipoUtente) {

		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.password = password;
		this.tipoUtente = tipoUtente;
	}

	@Override
	public String toString() {
		return "UtenteRequestPayload [nome=" + nome + ", cognome=" + cognome + ", username=" + username + ", email="
				+ email + ", Tipo Utente= " + tipoUtente + "]";
	}


}
