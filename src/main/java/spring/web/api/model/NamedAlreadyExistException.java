package spring.web.api.model;

public class NamedAlreadyExistException extends ClientFaultException {

    public NamedAlreadyExistException(String message) {
        super(message);
    }

}