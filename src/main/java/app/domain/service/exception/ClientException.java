package app.domain.service.exception;

public class ClientException extends RuntimeException{
    public ClientException(String message) {
        super(message);
    }
}
