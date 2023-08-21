package ProgettoSettimana05.SpringBootII.Dispositivo;

import java.util.UUID;

import ProgettoSettimana05.SpringBootII.Utente.Utente;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "dispositivi")
public class Dispositivo {
	@Id
	@GeneratedValue
	private UUID id;
	private String nome;
	private String marca;
	@Enumerated(EnumType.STRING)
	private TipoDispositivo tipoDispositivo;
	@Enumerated(EnumType.STRING)
	private StatoDispositivo statoDispositivo;
	@ManyToOne
	private Utente utente;

	public Dispositivo(String nome, String marca, TipoDispositivo tipoDispositivo, StatoDispositivo statoDispositivo,
			Utente utente) {

		this.nome = nome;
		this.marca = marca;
		this.tipoDispositivo = tipoDispositivo;
		this.statoDispositivo = statoDispositivo;
		this.utente = utente;
	}

	@Override
	public String toString() {
		return "Dispositivo [id=" + id + ", nome=" + nome + ", marca=" + marca + ", tipoDispositivo=" + tipoDispositivo
				+ ", statoDispositivo=" + statoDispositivo + ", " + utente + "]";
	}

}
