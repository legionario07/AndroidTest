package testandroid.com.br.androidtest.utils;

import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by Paulo on 13/08/2018.
 */

public class EncryptUtil {

    private static String ENCRYPTACAO = "SHA-256";
    private static String CODIFICACAO = "UTF-8";

    public static String getEncrypt(String senha){

        try {
            MessageDigest digest = MessageDigest.getInstance(ENCRYPTACAO);
            byte[] hash = digest.digest(senha.getBytes(CODIFICACAO));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            senha = hexString.toString();
        }catch (Exception e){
            Log.i(SessionUtil.LOG, e.getMessage());
        }

        return senha;
    }
}
