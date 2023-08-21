package ProgettoSettimana05.SpringBootII.Dispositivo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ProgettoSettimana05.SpringBootII.Exception.BadRequestException;
import ProgettoSettimana05.SpringBootII.Utente.NotUtenteFoundException;
import ProgettoSettimana05.SpringBootII.Utente.TipoUtente;
import ProgettoSettimana05.SpringBootII.Utente.Utente;
import ProgettoSettimana05.SpringBootII.Utente.UtenteLoginSuccessful;
import ProgettoSettimana05.SpringBootII.Utente.UtenteService;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {
	@Autowired
	DispositivoService dispositivoSrv;
	@Autowired
	UtenteService utenteSrv;
	@GetMapping
	public Page<Dispositivo> getUtenti(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return dispositivoSrv.find(page, size, sortBy);
	}

	@GetMapping("/{id}")
	public Dispositivo findById(@PathVariable UUID id) {
		return dispositivoSrv.findById(id);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Dispositivo saveDispositivo(@RequestBody DispositivoRequestPayload body)
			throws NotUtenteFoundException {
		Utente utente = utenteSrv.findById(body.getUtente());
		if (utente != null) {
			Dispositivo nuovoDispositivo = dispositivoSrv.checkAndCreate(body);
			return nuovoDispositivo;
		} else {
			throw new NotUtenteFoundException(body.getUtente());
		}


	}

	@PutMapping("/{id}")
	public Dispositivo updateDispositivo(@PathVariable UUID id, @RequestBody DispositivoRequestPayload body) {
		
		
			return dispositivoSrv.findByIdAndUpdate(id, body);
		
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDispositivo(@PathVariable UUID id, @RequestBody UtenteLoginSuccessful login)
			throws BadRequestException {
		if (login.getTipoUtente() == TipoUtente.ADMIN) {
			dispositivoSrv.findByIdAndDelete(id);
		} else {
			throw new BadRequestException("Non sei un amministratore e non puoi eliminare l'utente con id " + id);
		}


	}

	@DeleteMapping("/rimuovi/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDispositivoFromUtente(@PathVariable UUID id) {

		dispositivoSrv.removeDispositivoFromUtente(id);

	}
}
