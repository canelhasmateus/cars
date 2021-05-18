package canelhas.cars.schema;

public class DatabaseColumns {

    //region monorepo

    private DatabaseColumns( ) {}

    //endregion

    //region user
    public static final String USER_BIRTHDAY = "birthday";
    public static final String USER_CPF      = "cpf";
    public static final String USER_ID       = "user_id";
    public static final String USER_EMAIL    = "email";
    public static final String USER_NAME     = "name";
    //endregion

    //region vehicle
    public static final String VEHICLE_ID  = "vehicle_id";
    public static final String MODEL_ID    = "model_id";
    public static final String MODEL_NAME  = "name";
    public static final String MODEL_YEAR  = "year";
    public static final String MODEL_BRAND = "brand";
    //endregion

}
