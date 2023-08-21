package ProgettoSettimana05.SpringBootII.Dispositivo;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NewDispositivoResponsePayload {
	private UUID id;

	@Override
	public String toString() {
		return "NewDispositivoResponsePayload [id=" + id + "]";
	}

}
