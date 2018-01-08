package session.exceptions;

/**
 * Exception throwed when a user attempts to log in into an account that already
 * has an active session
 * 
 * @author Tiago
 * @author Ruben
 */
public class AlreadyLoggedInException extends RuntimeException{

    public AlreadyLoggedInException() {
        super("O utilizador já tem sessão iniciada");
    }
    
}
