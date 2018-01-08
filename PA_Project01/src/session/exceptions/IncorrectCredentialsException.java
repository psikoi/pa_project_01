package session.exceptions;

/**
 * Exception throwed when a user attempts to log in into an account using 
 * incorrect credentials.
 * 
 * @author Tiago
 * @author Ruben
 */
public class IncorrectCredentialsException extends RuntimeException{
    public IncorrectCredentialsException(){
        super("A palavra-passe inserida é incorreta");
    }
}
