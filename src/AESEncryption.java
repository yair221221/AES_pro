import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class AESEncryption {

    public static String encryption(File encryptionFile, String key) throws Exception {
        // Read all bytes from the file
        byte[] fileBytes = fileToBytes(encryptionFile);

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // Create the AES cipher instance
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Initialize the cipher in encryption mode with the key
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Perform the encryption
        byte[] encryptedBytes = cipher.doFinal(fileBytes);

        // Convert the encrypted bytes to Base64 representation
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

        // Print the encrypted text
        System.out.println("Encrypted Text: " + encryptedText);

        return encryptedText;
    }

    public static String decrypt(File encryptedFile, String key) throws Exception {

        byte[] fileBytes = fileToBytes(encryptedFile);

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = Base64.getDecoder().decode(fileBytes);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
        System.out.println("Decrypted Text: " + decryptedText);

        return decryptedText;
    }
    public static byte[] fileToBytes(File file) throws IOException {
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        return fileBytes;
    }
}