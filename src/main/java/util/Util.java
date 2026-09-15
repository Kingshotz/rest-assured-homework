package util;

import com.github.javafaker.Faker;
import pet.store.v3.model.Order;

import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;
import auth.api.model.RegisterRequest;

public class Util {
    private static final Faker faker = new Faker();
    public static Order generateOrder(){
        return new Order().
                petId(faker.number().randomNumber(4,false)).
                quantity(faker.number().numberBetween(1,100)).
                shipDate(faker.date().past(10, TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC)).
                status(faker.options().option(Order.StatusEnum.values())).
                complete(true);
    }

    /**
     {
     "firstname": "string",
     "lastname": "string",
     "email": "user@example.com",
     "password": "string",
     "role": "ADMIN"
     }
     */
    public static RegisterRequest generateAdmin(){
        return new RegisterRequest()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password()+"Aa1!")
                .role(RegisterRequest.RoleEnum.ADMIN);
    }
}
