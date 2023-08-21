package ProgettoSettimana05.SpringBootII.Utente;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ProgettoSettimana05.SpringBootII.Dispositivo.Dispositivo;
import ProgettoSettimana05.SpringBootII.Dispositivo.DispositivoRepository;
import ProgettoSettimana05.SpringBootII.Dispositivo.StatoDispositivo;
import ProgettoSettimana05.SpringBootII.Exception.BadRequestException;
import ProgettoSettimana05.SpringBootII.Exception.DeleteUtenteImpossibileRelazioneDispositivoException;

@Service
public class UtenteService {

	@Autowired
	DispositivoRepository dispositivoRepo;
	private final UtenteRepository utenteRepo;

	@Autowired
	public UtenteService(UtenteRepository utenteRepo) {

		this.utenteRepo = utenteRepo;
	}

	public Utente create(UtenteRequestPayload body) {

		utenteRepo.findByEmail(body.getEmail()).ifPresent(user -> {
			throw new BadRequestException("E-mail già in uso. Registrarsi con un'altra e-mail");
		});
		Utente newUser = new Utente(body.getNome(), body.getCognome(), body.getUsername(), body.getEmail(),
				body.getPassword(), body.getTipoUtente());
		return utenteRepo.save(newUser);
	}



	public Page<Utente> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return utenteRepo.findAll(pageable);
	}

	public List<Utente> findNoPage() {

		return utenteRepo.findAll();
	}

	public Utente findById(UUID id) throws NotUtenteFoundException {
		return utenteRepo.findById(id).orElseThrow(() -> new NotUtenteFoundException(id));
	}

	public Utente findByIdAndUpdate(UUID id, UtenteRequestPayload body) throws NotUtenteFoundException {
		Utente found = this.findById(id);
		found.setEmail(body.getEmail());
		found.setNome(body.getNome());
		found.setCognome(body.getCognome());
		found.setUsername(body.getUsername());

		return utenteRepo.save(found);
	}

	public void findByIdAndDelete(UUID id)
			throws NotUtenteFoundException, DeleteUtenteImpossibileRelazioneDispositivoException {
		Utente found = this.findById(id);

		if (found != null) {

			List<Dispositivo> dispositiviAssociati = dispositivoRepo.findByUtente(found);
			dispositiviAssociati.forEach(dispositivo -> {
				dispositivo.setStatoDispositivo(StatoDispositivo.DISPONIBILE);
				dispositivo.setUtente(null);
				dispositivoRepo.save(dispositivo);

			});

			utenteRepo.delete(found);
		} else {
			throw new NotUtenteFoundException(id);
		}

	}

	public Utente findByEmail(String email) {
		return utenteRepo.findByEmail(email)
				.orElseThrow(() -> new NotUtenteFoundException("Utente con email " + email + " non trovato"));
	}

}

