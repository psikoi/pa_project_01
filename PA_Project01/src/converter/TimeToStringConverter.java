package converter;

/**
 * TimeToStringConverter contains a static method that converts milliseconds into
 * hours, minutes and seconds in a string format.
 * 
 * @author Tiago
 * @author Ruben
 */
public class TimeToStringConverter {
    
    public static String getTimePlayed(long time) {
        Integer seconds = (int) (time / 1000) % 60;
        Integer minutes = (int) ((time / (1000 * 60)) % 60);
        Integer hours = (int) ((time / (1000 * 60 * 60)) % 24);
        
        String secondsString = seconds.toString();
        String minutesString = minutes.toString();
        String hoursString = hours.toString();
        
        return hoursString + " Horas, " + minutesString + " Minutos, " + secondsString + " Segundos.";
    }
    
}
