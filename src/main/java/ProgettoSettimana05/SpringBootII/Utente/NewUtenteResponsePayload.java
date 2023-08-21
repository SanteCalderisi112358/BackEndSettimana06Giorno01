package ProgettoSettimana05.SpringBootII.Utente;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NewUtenteResponsePayload {
	private UUID id;

	@Override
	public String toString() {
		return "NewUtenteResponsePayload [id=" + id + "]";
	}
}
