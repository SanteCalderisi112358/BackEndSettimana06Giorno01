package ProgettoSettimana05.SpringBootII.Utente;

import java.util.UUID;

public class NotUtenteFoundException extends RuntimeException {
	public NotUtenteFoundException(UUID idUtente) {
		super("Utente con id: " + idUtente + " non trovato");
	}

	public NotUtenteFoundException(String message) {
		super(message);
	}
}
