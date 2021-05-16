package canelhas.cars.api.context;

public class SecurityHolder {


    //region monorepo

    private SecurityHolder( ) {}
    //endregion


    public static String getJWTKey( ) {
        return "carscarscarscars";
    }

    public static String getVersion( ) {
        return "0.0.1";
    }
}
