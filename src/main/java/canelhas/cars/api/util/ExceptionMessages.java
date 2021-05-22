package canelhas.cars.api.util;

public class ExceptionMessages {

    //region search
    public static final String AMBIGUOUS_SEARCH = "%s é um pouco ambiguo... Os registros mais proximos são %s";
    public static final String NONE_FOUND       = "Não foram encontrados registros para a pesquisa %s";
    public static final String INEXACT_SEARCH   = "%s? você quis dizer %s?";

    //endregion

    //region typing

    //region vehicle
    public static final String COULD_NOT_PARSE_YEAR = "Erro ao tentar interpretar %s como numericamente.";
    public static final String YEAR_REQUIRED        = "Informe algum valor para o ano.";
    public static final String BRAND_REQUIRED       = "Informe algum valor para a marca.";
    public static final String MODEL_REQUIRED       = "Informe algum valor para o modelo.";
    //endregion

    //region birthday
    public static final String BIRTHDAY_REQUIRED       = "Informe alguma data de nascimento";
    public static final String TOO_BABY                = "%s anos é muito cedo para se cadastrar por aqui.";
    public static final String ARE_YOU_BENJAMIN_BUTTON = "%s anos de idade? Seria você o Benjamin Button?";
    public static final String ARE_YOU_IMMORTAL        = "Você tem %s anos? Sério mesmo?";
    //endregion

    //region email
    public static final String IS_A_INVALID_EMAIL = "%s não é um endereço de email válido.";
    public static final String EMAIL_REQUIRED     = "Informe algum valor de email.";
    //endregion

    //region name
    public static final String NAME_REQUIRED               = "Informe algum valor de nome.";
    public static final String CONTAINS_INVALID_CHARACTERS = "%s não é um valor válido de nome, pois contém caracteres não permitidos.";
    //endregion

    //region cpf
    public static final String CPF_REQUIRED       = "Informe algum valor de cpf.";
    public static final String INVALID_CPF_LENGTH = "%s não é um valor válido de cpf. Certifique-se que o valor contenha 11 digitos numéricos.";
    public static final String INVALID_CPF_VALUE  = "%s não é um valor válido de cpf.";
    //endregion

    //endregion

    //region http
    public static final String NO_CURRENT_SESSION    = "Não existe sessão válida dentro do contexto atual";
    public static final String NOT_ENOUGH_PRIVILEGES = "Você não possui os privilégios necessários de acesso.";
    public static final String UNSUCCESSFUL_REQUEST  = "Um recurso dependente falhou. Tente novamente mais tarde";

    //endregion

    public static final String EMAIL_IS_USED          = "Já existe um usuário usando o email %s.";
    public static final String CPF_IS_USED            = "Pare aí! Eu já vi o cpf %s por aqui...";
    public static final String USER_NOT_FOUND_WITH_ID = "Não foi encontrado usuário com o id %s";

    //region monorepo
    private ExceptionMessages( ) {}
    //endregion
}
