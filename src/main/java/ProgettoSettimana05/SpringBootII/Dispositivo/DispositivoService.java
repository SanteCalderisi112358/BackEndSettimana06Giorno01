package ProgettoSettimana05.SpringBootII.Dispositivo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ProgettoSettimana05.SpringBootII.Exception.DeleteDispositivoImpossibileRelazioneDispositivoException;
import ProgettoSettimana05.SpringBootII.Utente.NotUtenteFoundException;
import ProgettoSettimana05.SpringBootII.Utente.Utente;
import ProgettoSettimana05.SpringBootII.Utente.UtenteRepository;
import ProgettoSettimana05.SpringBootII.Utente.UtenteService;

@Service
public class DispositivoService {
	@Autowired
	DispositivoRepository dispositivoRepo;
	@Autowired
	UtenteService utenteSrv;
	@Autowired
	UtenteRepository utenteRepo;
	public Dispositivo checkAndCreate(DispositivoRequestPayload body) {
		if (body.getStatoDispositivo().equals(StatoDispositivo.ASSEGNATO)
				|| body.getStatoDispositivo().equals(StatoDispositivo.DISMESSO)
				|| body.getStatoDispositivo().equals(StatoDispositivo.IN_MANUTENZIONE)) {
			throw new NotFoundDispositivoException(
					"Il dispositivo è " + body.getStatoDispositivo() + " e non è assegnabile al dipendente");
		} else {
			Utente utente = utenteSrv.findById(body.getUtente());
			Dispositivo nuovoDispositivo = new Dispositivo(body.getNome(), body.getMarca(), body.getTipoDispositivo(),
					StatoDispositivo.ASSEGNATO, utente);
			System.err.println(
					"Il disposito " + body.getNome() + " è stato assegnato al dipendente " + body.getUtente()
							+ ".\nLo stato del dispositivo è passato da 'DISPONIBILE' a 'ASSEGNATO'");
			return dispositivoRepo.save(nuovoDispositivo);
		}

	}

	public Page<Dispositivo> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return dispositivoRepo.findAll(pageable);
	}

	public List<Dispositivo> findNoPage() {

		return dispositivoRepo.findAll();
	}

	public Dispositivo findById(UUID id)
			throws NotFoundDispositivoException, DeleteDispositivoImpossibileRelazioneDispositivoException {
		return dispositivoRepo.findById(id).orElseThrow(() -> new NotFoundDispositivoException(id));
	}

	public Dispositivo findByIdAndUpdate(UUID id, DispositivoRequestPayload body)
			throws NotFoundDispositivoException, NotUtenteFoundException {
		Dispositivo found = this.findById(id);
		if (found != null) {

			Utente utente = utenteSrv.findById(body.getUtente());
			if (utente != null) {
				found.setNome(body.getNome());
		found.setMarca(body.getMarca());
		found.setStatoDispositivo(body.getStatoDispositivo());
		found.setTipoDispositivo(body.getTipoDispositivo());
		found.setUtente(utente);
		return dispositivoRepo.save(found);
	} else {
		throw new NotUtenteFoundException(body.getUtente());
	}

} else {
	throw new NotFoundDispositivoException(id);
}

	}

	public void removeDispositivoFromUtente(UUID idDispositivo)
			throws NotFoundDispositivoException, NotUtenteFoundException {
		Dispositivo dispositivo = this.findById(idDispositivo);
		if (dispositivo != null) {
			Utente utente = utenteSrv.findById(dispositivo.getUtente().getId());
			dispositivo.setUtente(null);
			dispositivo.setStatoDispositivo(StatoDispositivo.DISPONIBILE);

			
			dispositivoRepo.save(dispositivo);
			System.err.println(dispositivo.toString() + " è stato tolto sottratto a " + utente.toString()
					+ " e il suo stato è passato da 'ASSEGNATO' a 'DISPONIBILE'");
		} else {
			throw new NotFoundDispositivoException(idDispositivo);
		}
	}

	public void findByIdAndDelete(UUID id) throws NotFoundDispositivoException {
		Dispositivo found = this.findById(id);
		dispositivoRepo.delete(found);
	}



}
