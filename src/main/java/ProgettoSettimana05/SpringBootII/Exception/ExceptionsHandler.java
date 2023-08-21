package ProgettoSettimana05.SpringBootII.Exception;


import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ProgettoSettimana05.SpringBootII.Dispositivo.NotFoundDispositivoException;
import ProgettoSettimana05.SpringBootII.Utente.NotUtenteFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorsPayload handleBadRequest(BadRequestException e) {
		return new ErrorsPayload(e.getMessage(), new Date());
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorsPayload handleUnauthorized(UnauthorizedException e) {
		return new ErrorsPayload(e.getMessage(), new Date());
	}

	@ExceptionHandler(NotUtenteFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorsPayload handleNotFoundUtente(NotUtenteFoundException e) {
		return new ErrorsPayload(e.getMessage(), new Date());
	}

	@ExceptionHandler(NotFoundDispositivoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorsPayload handleNotFoundDispositivo(NotFoundDispositivoException e) {
		return new ErrorsPayload(e.getMessage(), new Date());
	}



	@ExceptionHandler(DeleteUtenteImpossibileRelazioneDispositivoException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorsPayload handleNoDelete(DeleteUtenteImpossibileRelazioneDispositivoException ex) {
		log.error(ex.getMessage());
		ex.printStackTrace();
		return new ErrorsPayload(ex.getMessage(), new Date());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorsPayload handleGeneric(Exception e) {
		log.error(e.getMessage());
		e.printStackTrace();
		return new ErrorsPayload("Errore generico, risolveremo il prima possibile", new Date());
	}
}