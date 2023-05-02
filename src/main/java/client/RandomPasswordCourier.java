package client;

import java.util.Random;

public class RandomPasswordCourier {
    public static String generatePassword () {
        Random rnd = new Random();
        Integer number = rnd.nextInt(65656) + 1;
        return number.toString();
    }

}
