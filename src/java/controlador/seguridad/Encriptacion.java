/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.seguridad;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author jeisson
 */
public class Encriptacion {
    
    public static byte[] encriptar (String pass) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
keyGenerator.init(128);
Key key = keyGenerator.generateKey();
key = new SecretKeySpec("passwordprolevel".getBytes(), 0, 16, "AES");
Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
aes.init(Cipher.ENCRYPT_MODE, key);
byte[] encriptado = aes.doFinal(pass.getBytes("utf-8"));
return encriptado;
    }
    public static String desencriptar(byte[] encriptado) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
keyGenerator.init(128);
Key key = keyGenerator.generateKey();
key = new SecretKeySpec("passwordprolevel".getBytes(), 0, 16, "AES");
Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
aes.init(Cipher.DECRYPT_MODE, key);
byte[] desencriptado = aes.doFinal(encriptado);
return new String(desencriptado,"utf-8");
    }
    
    public static String arrayBitesAstring(byte[] conversor) throws UnsupportedEncodingException{
        String  pass = new String(conversor,"utf-8");
        return pass;
    }
    public static byte[] StringAarrayBites(String conversor) throws UnsupportedEncodingException{
        byte[] deVuelta = conversor.getBytes("utf-8");
        return deVuelta;
    }
}
