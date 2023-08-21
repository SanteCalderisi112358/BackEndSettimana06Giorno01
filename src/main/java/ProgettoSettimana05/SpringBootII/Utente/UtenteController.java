package ProgettoSettimana05.SpringBootII.Utente;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ProgettoSettimana05.SpringBootII.Exception.DeleteUtenteImpossibileRelazioneDispositivoException;



@RestController
@RequestMapping("/utenti")
public class UtenteController {
	@Autowired
	UtenteService utenteSrv;

	@GetMapping
	public Page<Utente> getUtenti(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return utenteSrv.find(page, size, sortBy);
	}

	@GetMapping("/{id}")
	public Utente findById(@PathVariable UUID id) {
		return utenteSrv.findById(id);

	}



	@PutMapping("/{id}")
	public Utente updateUtente(@PathVariable UUID id, @RequestBody UtenteRequestPayload body) {
		return utenteSrv.findByIdAndUpdate(id, body);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUtente(@PathVariable UUID id) {
		try {
			utenteSrv.findByIdAndDelete(id);
		} catch (DeleteUtenteImpossibileRelazioneDispositivoException ex) {
			System.err.println(ex.getMessage());
		}

	}
}

