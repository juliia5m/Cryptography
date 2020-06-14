import java.util.Arrays;
public class test_and_second_task {

    public static void main(String[] args) throws Exception {


        String plain_text = "00112233445566778899aabbccddeeff";
        String key = "000102030405060708090a0b0c0d0e0f";

        System.out.println("our message:          " + plain_text);
        System.out.println("our key:              " + key);


        //--------------------------------encryption testing

        Encryption aes = new Encryption(plain_text,key);
        aes.encrypt();

        System.out.println();
        System.out.println("Your encryption result above");

        System.out.println("-----------------------------------------------------");

        //--------------------------------decryption testing


        String cipherText = "69c4e0d86a7b0430d8cdb78070b4c55a";

        Decryprion aes_d = new Decryprion(key, cipherText);
        aes_d.decrypt();
        System.out.println("------------------------------------------------------");
        System.out.println("You can compare that plain text and decryption result are equal");
        System.out.println();

        System.out.println("----------------------Second task: ");
        System.out.println("Let`s see the difference between encrypted text we used above\\" +
                "and encrypted text after we changed one bit in plain text  ");
        System.out.println("Our encrypted text: " + cipherText);
        //byte[] byteArray = plainText.getBytes();
        //System.out.println("Str into byte array : " + Arrays.toString(byteArray)); - just for checking myself
        // I changed one bit in the last f so we got e as a result
        String changed_pt = "00112233445566778899aabbccddeefe";
        Encryption d = new Encryption(changed_pt,key);
        d.encrypt();
        System.out.println("Our encrypted text after changing one bit in pt above"  );

        System.out.println("-------------------------------------------------------");
        String key_cbc = "dontgiveup";
        String clean = "It is never too late.";
        System.out.println("Initial message:" + clean);

        byte[] encrypted = AES_CBC1.encrypt_CBC(clean, key_cbc);
        String decrypted = AES_CBC1.decrypt_CBC(encrypted, key_cbc);
        System.out.println(Arrays.toString(encrypted));
        System.out.println("Decrypted message:" + decrypted);
        System.out.println(clean.equals(decrypted));


    }

}