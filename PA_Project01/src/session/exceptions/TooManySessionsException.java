package session.exceptions;

/**
 * Exception throwed when a user attempts to log in into an account when there
 * is already 2 active sessions.
 * 
 * @author Tiago
 * @author Ruben
 */
public class TooManySessionsException extends RuntimeException{

    public TooManySessionsException() {
        super("Já existem 2 sessões iniciadas");
    }
    
}
