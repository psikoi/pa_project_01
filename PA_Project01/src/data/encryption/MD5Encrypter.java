
package data.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains a static method that encrypts a piece of text using MD5. This method
 * is used when saving users to file, encrypting their passwords.
 * 
 * @author Tiago
 * @author Ruben
 */
public class MD5Encrypter {
    private static MessageDigest messageDigest;
    
    /**
     * Using MD5, this method encrypts the text and returns the result.
     * @param text - text being encrypted.
     * @return encrypted text.
     */
    public static String md5Encryption(String text){
         try {
        messageDigest = MessageDigest.getInstance("MD5");
        byte[] passBytes = text.getBytes();
        messageDigest.reset();
        byte[] digested = messageDigest.digest(passBytes);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException ex) {
        Logger.getLogger(MD5Encrypter.class.getName()).log(Level.SEVERE, null, ex);
    }
        return null;
    }
}