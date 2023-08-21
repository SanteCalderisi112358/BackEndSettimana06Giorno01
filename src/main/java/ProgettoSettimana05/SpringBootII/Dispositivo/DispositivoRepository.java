package ProgettoSettimana05.SpringBootII.Dispositivo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ProgettoSettimana05.SpringBootII.Utente.Utente;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {
	List<Dispositivo> findByUtente(Utente utente);
}
