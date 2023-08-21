package ProgettoSettimana05.SpringBootII.Exception;

import java.util.UUID;

public class DeleteUtenteImpossibileRelazioneDispositivoException extends RuntimeException {
	public DeleteUtenteImpossibileRelazioneDispositivoException(UUID id) {
		super("La chiave (id)=(" + id + ") è ancora referenziata dalla tabella 'dispositivi'.");
	}
}
