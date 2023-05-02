package client;

import com.github.javafaker.Faker;

public class RandomLoginCourier {
    public static String generateLogin() {
        Faker faker = new Faker();
        return faker.superhero().prefix()+faker.name().firstName()+faker.address().buildingNumber();

    }
    }


