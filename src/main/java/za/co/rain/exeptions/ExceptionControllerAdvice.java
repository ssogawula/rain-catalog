package za.co.rain.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(ServiceLayerExeption.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public MessageDetail handleSeriviceExceptionHandler(HttpServletRequest request, ServiceLayerExeption e) {
		return new MessageDetail(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public MessageDetail handleResourceNotFoundExceptionHandler(HttpServletRequest request, ResourceNotFoundException e) {
		return new MessageDetail(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public MessageDetail handleExceptionHandler(HttpServletRequest request, Exception e) {
		return new MessageDetail(e.getMessage(), request.getRequestURI());
	}
}
