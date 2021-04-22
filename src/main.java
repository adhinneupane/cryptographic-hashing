import java.security.MessageDigest;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;


public class Main {

    public static void main(String[] args) {
        // Variables and Hash constants have been initialized in the lines below
        Integer c = 12;
        String algorithmused = "SHA-256 encryption";
        Random x = new Random();


        // First Step of SHA 256 : We need to generate the random bits of number n
        String HexadecimalvalueofStringUsed = RandomBitsgenerator(c, x);
        boolean Decrypted = false;
        int attempts = 0;

        // Loop for implementing Brute force  attack
        while (!Decrypted) {

            attempts++;

            // Following code sequence Generates a binary random message that can be upto 101 bits
            int lengthOfRandomMessage = ThreadLocalRandom.current().nextInt(1, 101);
            String randomMessage = RandomStringUtils.randomAlphanumeric(lengthOfRandomMessage);

            // Following code will hash the random message generated in above step
            String hexStringOf256Hash = Hasher.hash(randomMessage, algorithmused);
            
            // Count the number of strings to be compared
            int countofstringstobecompared = c / 4;
            // Compare the last n number of bits of the hashed string for 4,8,12, 16 bits of message P
            String lastnnumberofbitsofhash = hexStringOf256Hash.substring(hexStringOf256Hash.length() - countofstringstobecompared);
            if (HexadecimalvalueofStringUsed.equals(lastnnumberofbitsofhash)) {
                //Set Boolean as true after successful decryption
                Decrypted = true;
                // print the output to console
                System.out.println("******-----********--******-********-********-********-********-*****");
                System.out.println("The message used for input is: " + randomMessage);
                System.out.println("Hash of the plaintext message is:" + hexStringOf256Hash);
                System.out.println("The message has been decrypted after this many no of attempts " + attempts + "!!");
                System.out.println("******-----********--******-********-********-********-********-*****");
            }
        }
    }

    //method used to generate random bits 
    public static String RandomBitsgenerator(Integer numberOfBits, Random r) {
        BigInteger randomBits = new BigInteger(numberOfBits, r);
        String randomBitsInHexString = Hex.encodeHexString(randomBits.toByteArray());
        System.out.println("Random " + numberOfBits + " bits are: " + randomBits.toString(2) + " i.e. " + randomBitsInHexString.substring(randomBitsInHexString.length() - numberOfBits / 4));
        return randomBitsInHexString.substring(randomBitsInHexString.length() - numberOfBits / 4);
    }

    // method used to genrate a hexadecimal hash string
    public static String hash(String message, String hashAlgo) {
        String algorithm = hashAlgo;
        String encodedHashString = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashValue = digest.digest(message.getBytes());
            encodedHashString = Hex.encodeHexString(hashValue);
            return encodedHashString;
        } catch (Exception e) {
            System.out.println(e);
        }
        return encodedHashString;
    }

}


