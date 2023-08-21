package ProgettoSettimana05.SpringBootII;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import ProgettoSettimana05.SpringBootII.Dispositivo.DispositivoService;
import ProgettoSettimana05.SpringBootII.Dispositivo.NotFoundDispositivoException;
import ProgettoSettimana05.SpringBootII.Security.AuthController;
import ProgettoSettimana05.SpringBootII.Utente.NotUtenteFoundException;
import ProgettoSettimana05.SpringBootII.Utente.TipoUtente;
import ProgettoSettimana05.SpringBootII.Utente.Utente;
import ProgettoSettimana05.SpringBootII.Utente.UtenteRequestPayload;
import ProgettoSettimana05.SpringBootII.Utente.UtenteService;

@Component
public class MainRunner implements CommandLineRunner {
	@Autowired
	UtenteService utenteSrv;
	@Autowired
	DispositivoService dispositivoSrv;
	@Autowired
	AuthController authCtrl;

	@Autowired
	PasswordEncoder bcrypt;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(Locale.ITALIAN);
		/* ISTANZIO 5 DIPENDENTI RANDOM CON FAKER E LI SALVO NEL DB */
		for (int i = 0; i < 5; i++) {
			String name = faker.name().firstName();
			String surname = faker.name().lastName();
			String email = faker.internet().emailAddress();
			String username = faker.lordOfTheRings().character();
			String password = String.valueOf(faker.number().numberBetween(1000, 9999));

			TipoUtente tipoUtente = TipoUtente.values()[new Random().nextInt(TipoUtente.values().length)];
			UtenteRequestPayload user = new UtenteRequestPayload(name, surname, username, email, password, tipoUtente);
			// System.err.println(user.toString());
			// authCtrl.saveUser(user);


		}

		System.err.println(bcrypt.encode("ciao"));
		/*
		 * INIZIALIZZAZIONE 10 SMARTPHONE, 10 LAPTOT E 10 TABLET E LI SALVO NEL DB il
		 * loro numero è inferiore rispetto a quello voluto a causa dei controlli in
		 * create() di dispositivoSrv
		 */
		/*
		 * Mi ritorno tutti i dipendenti dal DB in modo da assegnare in modo random uno
		 * di loro ad un dispositivo
		 */
		List<Utente> listaUtenti = utenteSrv.findNoPage();
		System.err.println("DIPENDENTI");
		listaUtenti.forEach(u -> {
			if (u.getPassword().length() > 50) {
				System.out
						.println("La password dell'utente " + u.getNome() + " " + u.getCognome() + " è già hashata.");
			} else {
				String nome = u.getNome();
				String cognome = u.getCognome();
				String email = u.getEmail();
				String username = u.getUsername();
				TipoUtente tipoUtente = u.getTipoUtente();
				String password = (String) u.setPassword(bcrypt.encode(u.getPassword()));
				UtenteRequestPayload user = new UtenteRequestPayload(nome, cognome, username, email,
						password, tipoUtente);
				authCtrl.saveUser(user);
				System.out.println("La password dell'utente " + u.getNome() + " " + u.getCognome()
						+ " è stata hashata.");
			}
		});

		/* 10 SMARTPHONE */
//		for (int i = 0; i < 10; i++) {
//			String nome = faker.backToTheFuture().character();
//			String marca = faker.beer().name();
//			TipoDispositivo tipoDispositivo = TipoDispositivo.SMARTPHONE;
//			StatoDispositivo statoDispositivo = StatoDispositivo.values()[new Random()
//					.nextInt(StatoDispositivo.values().length)];
//			Utente utente = listaUtenti.get(faker.number().numberBetween(0, listaUtenti.size() - 1));
//			DispositivoRequestPayload nuovoDispositivo = new DispositivoRequestPayload(nome, marca, tipoDispositivo,
//					statoDispositivo, utente.getId());
//			try {
//				dispositivoSrv.checkAndCreate(nuovoDispositivo);
//			} catch (NotFoundDispositivoException e) {
//				System.err.println(e.getMessage());
//			}
//
//			System.err.println(nuovoDispositivo.toString());
//		}
		/* 10 LAPTOT */
//		for (int i = 0; i < 10; i++) {
//			String nome = faker.backToTheFuture().character();
//			String marca = faker.beer().name();
//			TipoDispositivo tipoDispositivo = TipoDispositivo.LAPTOT;
//			StatoDispositivo statoDispositivo = StatoDispositivo.values()[new Random()
//					.nextInt(StatoDispositivo.values().length)];
//			Utente utente = listaUtenti.get(faker.number().numberBetween(0, listaUtenti.size() - 1));
//			DispositivoRequestPayload nuovoDispositivo = new DispositivoRequestPayload(nome, marca, tipoDispositivo,
//					statoDispositivo, utente.getId());
//			try {
//				dispositivoSrv.checkAndCreate(nuovoDispositivo);
//			} catch (NotFoundDispositivoException e) {
//				System.err.println(e.getMessage());
//			}
//		}
		/* 10 TABLET */
//		for (int i = 0; i < 10; i++) {
//			String nome = faker.backToTheFuture().character();
//			String marca = faker.beer().name();
//			TipoDispositivo tipoDispositivo = TipoDispositivo.TABLET;
//			StatoDispositivo statoDispositivo = StatoDispositivo.values()[new Random()
//					.nextInt(StatoDispositivo.values().length)];
//			Utente utente = listaUtenti.get(faker.number().numberBetween(0, listaUtenti.size() - 1));
//			DispositivoRequestPayload nuovoDispositivo = new DispositivoRequestPayload(nome, marca, tipoDispositivo,
//					statoDispositivo, utente.getId());
//			try {
//				dispositivoSrv.checkAndCreate(nuovoDispositivo);
//			} catch (NotFoundDispositivoException e) {
//				System.err.println(e.getMessage());
//			}
//		}
		/* METODO PER ELIMINARE ASSEGNAZIONE DI UN DISPOSITIVO DA UN DIPENDENTE */
		try {
			dispositivoSrv.removeDispositivoFromUtente(UUID.fromString("208e30ad-c711-4108-a231-36d9daf523d8"));

		} catch (NotFoundDispositivoException ex) {
			System.err.println(ex.getMessage());
		} catch (NotUtenteFoundException ex) {
			System.err.println(ex.getMessage());
		}



	}



}
