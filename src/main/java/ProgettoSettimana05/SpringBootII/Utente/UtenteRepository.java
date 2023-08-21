package ProgettoSettimana05.SpringBootII.Utente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ProgettoSettimana05.SpringBootII.Dispositivo.Dispositivo;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
	Optional<Utente> findByEmail(String email);

	@Query("SELECT d FROM Dispositivo d JOIN d.utente u WHERE u.id = :idUtente")
	List<Dispositivo> findDispositiviByUtenteId(UUID idUtente);
}
