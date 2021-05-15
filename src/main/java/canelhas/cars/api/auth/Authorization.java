package canelhas.cars.api.auth;

public @interface Authorization {

    Roles[] value( );

    enum Roles {
        USER,
        ADMIN
    }
}
