package ProgettoSettimana05.SpringBootII.Dispositivo;

import java.util.UUID;

public class NotFoundDispositivoException extends RuntimeException {
	public NotFoundDispositivoException(UUID idDispositivo) {
		super("Dispositivo con id: " + idDispositivo + " non trovato");
	}

	public NotFoundDispositivoException(String message) {
		super(message);
	}
}
