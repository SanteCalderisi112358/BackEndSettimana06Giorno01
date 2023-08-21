package ProgettoSettimana05.SpringBootII.Security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ProgettoSettimana05.SpringBootII.Dispositivo.DispositivoService;
import ProgettoSettimana05.SpringBootII.Exception.BadRequestException;
import ProgettoSettimana05.SpringBootII.Exception.UnauthorizedException;
import ProgettoSettimana05.SpringBootII.Utente.TipoUtente;
import ProgettoSettimana05.SpringBootII.Utente.Utente;
import ProgettoSettimana05.SpringBootII.Utente.UtenteLoginPayload;
import ProgettoSettimana05.SpringBootII.Utente.UtenteLoginSuccessful;
import ProgettoSettimana05.SpringBootII.Utente.UtenteRequestPayload;
import ProgettoSettimana05.SpringBootII.Utente.UtenteService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UtenteService usersService;

	@Autowired
	JWTTools jwtTools;

	@Autowired
	DispositivoService dispositivoSrv;

	@PostMapping("/registrazione")
	@ResponseStatus(HttpStatus.CREATED)
	public Utente saveUser(@RequestBody UtenteRequestPayload body) {
		Utente created = usersService.create(body);

		return created;
	}

	@PostMapping("/login")
	public ResponseEntity<UtenteLoginSuccessful> login(@RequestBody UtenteLoginPayload body)
			throws UnauthorizedException {


		Utente user = usersService.findByEmail(body.getEmail());

		if (body.getPassword().equals(user.getPassword())) {


			String token = jwtTools.createToken(user);
			String nome = user.getNome();
			String cognome = user.getCognome();
			String username = user.getUsername();
			TipoUtente tipoUtente = user.getTipoUtente();
			UtenteLoginSuccessful loginAvvenuto = new UtenteLoginSuccessful(token, nome, cognome, username, tipoUtente);
			return new ResponseEntity<>(loginAvvenuto, HttpStatus.OK);

		} else {

			throw new UnauthorizedException("Credenziali non valide!");
		}
	}

	@DeleteMapping("admin/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDispositivo(@PathVariable UUID id, @RequestBody UtenteLoginSuccessful login)
			throws BadRequestException {
		if (login.getTipoUtente() == TipoUtente.ADMIN) {
			dispositivoSrv.findByIdAndDelete(id);
		} else {
			throw new BadRequestException("Non sei un amministratore e non puoi eliminare l'utente con id " + id);
		}


	}

}
