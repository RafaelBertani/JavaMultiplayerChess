package aes;

import java.io.*;
import java.security.*;  
import java.security.cert.*;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.*;

public class CryptoAES {
   
    private SecretKey secretKey = null;
   
    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public void geraChave() throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");  
        kg.init(128);
        SecretKey sk = kg.generateKey();
        this.secretKey = sk;
    }

    public String geraCifra(String str, SecretKey iSim) throws Exception {
        byte[] chave = iSim.getEncoded();
        Cipher aescf = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);
        aescf.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"), ivspec);
        byte[] cifrado = aescf.doFinal(str.getBytes());
        return Base64.getEncoder().encodeToString(cifrado);
    }

    public String geraDecifra(String str, SecretKey fSim) throws Exception {
        byte[] chave = fSim.getEncoded();
        Cipher aescf = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);
        aescf.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chave, "AES"), ivspec);
        byte[] decifrado = aescf.doFinal(Base64.getDecoder().decode(str));
        return new String(decifrado);
    }

}
