/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class MD5Encrypter {
    private static MessageDigest messageDigest;
    
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
