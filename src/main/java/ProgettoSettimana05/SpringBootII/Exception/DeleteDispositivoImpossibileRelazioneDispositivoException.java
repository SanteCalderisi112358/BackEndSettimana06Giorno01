package ProgettoSettimana05.SpringBootII.Exception;

import java.util.UUID;

public class DeleteDispositivoImpossibileRelazioneDispositivoException extends RuntimeException {
	public DeleteDispositivoImpossibileRelazioneDispositivoException(UUID id) {
		super("La chiave (id)=(" + id + ") è ancora referenziata dalla tabella 'dispositivi'.");
	}
}
