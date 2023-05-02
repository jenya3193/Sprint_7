package client;

import com.github.javafaker.Faker;


public class RandomFirstNameCourier {
    public static String generateFirstName()  {
        Faker faker = new Faker();
        return faker.superhero().prefix()+faker.name().firstName();

    }
}

