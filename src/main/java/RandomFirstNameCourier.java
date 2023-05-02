import com.github.javafaker.Faker;

public class RandomNameCourier {
    public static void main(String[] args) {
        Faker faker = new Faker();
        System.out.println(faker.superhero().prefix()+faker.name().firstName());

    }
}

