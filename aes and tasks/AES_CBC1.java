import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;


class AES_CBC1 {



    static byte[] encrypt_CBC(String pt, String key) throws Exception {
        byte[] initial = pt.getBytes();


        int size_of_iv = 16;
        byte[] IV = new byte[size_of_iv];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
        IvParameterSpec iv_par = new IvParameterSpec(IV);


        MessageDigest digest = MessageDigest.getInstance("SHA-256");  // can find an alternative variant
        digest.update(key.getBytes(StandardCharsets.UTF_8));
        byte[] for_key = new byte[16];
        System.arraycopy(digest.digest(), 0, for_key, 0, for_key.length);
        SecretKeySpec secret = new SecretKeySpec(for_key, "AES");


        Cipher enc = Cipher.getInstance("AES/CBC/PKCS5Padding");
        enc.init(Cipher.ENCRYPT_MODE, secret, iv_par);
        byte[] encrypted = enc.doFinal(initial);


        byte[] combine = new byte[size_of_iv + encrypted.length];
        System.arraycopy(IV, 0, combine, 0, size_of_iv);
        System.arraycopy(encrypted, 0, combine, size_of_iv, encrypted.length);

        return combine;
    }

    static String decrypt_CBC(byte[] encryptedIvTextBytes, String key) throws Exception {
        int size_of_iv = 16;
        int size_of_key = 16;


        byte[] IV = new byte[size_of_iv];
        System.arraycopy(encryptedIvTextBytes, 0, IV, 0, IV.length);
        IvParameterSpec iv_par = new IvParameterSpec(IV);


        int size_of_enc = encryptedIvTextBytes.length - size_of_iv;
        byte[] encr  = new byte[size_of_enc];
        System.arraycopy(encryptedIvTextBytes, size_of_iv, encr , 0, size_of_enc);


        byte[] key_b = new byte[size_of_key];
        MessageDigest my_digest = MessageDigest.getInstance("SHA-256");
        my_digest.update(key.getBytes());
        System.arraycopy(my_digest.digest(), 0, key_b, 0, key_b.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key_b, "AES");


        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, iv_par);
        byte[] decrypted = cipherDecrypt.doFinal(encr );
        return new String(decrypted);
    }
}