package canelhas.cars.common.exception;

public class ExceptionMessages {


    //region monorepo
    private ExceptionMessages( ) {}

    //endregion
    public static final String EMAIL_REQUIRED    = "Informe algum valor de email.";
    public static final String NAME_REQUIRED     = "Informe algum valor de nome.";
    public static final String CPF_REQUIRED      = "Informe algum valor de cpf.";
    public static final String BIRTHDAY_REQUIRED = "Informe alguma data de nascimento";
    public static final String BRAND_REQUIRED    = "Informe algum valor para a marca.";
    public static final String MODEL_REQUIRED    = "Informe algum valor para o modelo.";
    public static final String YEAR_REQUIRED     = "Informe algum valor para o ano.";

    public static final String IS_A_INVALID_EMAIL          = " não é um endereço de email válido.";
    public static final String INVALID_CPF_LENGTH          = " não é um valor válido de cpf. Certifique-se que o valor contenha 11 digitos numéricos.";
    public static final String IS_A_INVALID_CPF            = " não é um valor válido de cpf.";
    public static final String CONTAINS_INVALID_CHARACTERS = " não é um valor válido de nome, pois contém caracteres não permitidos.";

    public static final String IS_TOO_EARLY            = " anos é muito cedo para se cadastrar por aqui.";
    public static final String ARE_YOU_BENJAMIN_BUTTON = " anos de idade? Seria você o Benjamin Button?";
}
