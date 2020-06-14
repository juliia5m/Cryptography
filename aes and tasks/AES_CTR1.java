import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES_CTR1{


    public static void CTR(String[] args) throws Exception {

        byte[] your_string = "time is almost up".getBytes();

        final byte[] for_IV = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x01 };

        final byte[] for_key = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
                0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };



        SecretKeySpec key = new SecretKeySpec(for_key, "AES");
        IvParameterSpec IVs = new IvParameterSpec(for_IV);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");


        cipher.init(Cipher.ENCRYPT_MODE, key, IVs);
        ByteArrayInputStream for_input = new ByteArrayInputStream(your_string);
        CipherInputStream cIn = new CipherInputStream(for_input, cipher);
        ByteArrayOutputStream for_output = new ByteArrayOutputStream();

        int charr;
        while ((charr = cIn.read()) >= 0) {
            for_output.write(charr);
        }

        byte[] cipherText = for_output.toByteArray();

        System.out.println("cipher: " + new String(cipherText));


        cipher.init(Cipher.DECRYPT_MODE, key, IVs);
        for_output = new ByteArrayOutputStream();
        CipherOutputStream res = new CipherOutputStream(for_output, cipher);
        res.write(cipherText);
        res.close();

    }
}
