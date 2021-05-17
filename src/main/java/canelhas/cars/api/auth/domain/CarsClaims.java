package canelhas.cars.api.auth.domain;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.common.type.EmailAddress;
import canelhas.cars.common.type.ProperName;
import canelhas.cars.common.utils.TypingHelper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CarsClaims extends HashMap< String, Object > {

    //region keys
    private static final String NAME    = "name";
    private static final String EMAIL   = "email";
    private static final String VERSION = "version";
    private static final String ROLES   = "roles";
    private static final String ID      = "id";
    //endregion

    //region getters
    public Optional< ProperName > getName( ) {

        return Optional.ofNullable( ( String ) this.get( NAME ) )
                       .map( ProperName::of );

    }

    public Optional< Integer > getId( ) {
        return Optional.ofNullable( ( Integer ) this.get( ID ) );
    }

    public Optional< EmailAddress > getEmail( ) {
        return Optional.ofNullable( ( String ) this.get( EMAIL ) )
                       .map( EmailAddress::of );
    }

    public Optional< String > getVersion( ) {

        return Optional.ofNullable( ( String ) this.get( VERSION ) );
    }

    public Set< Authorization.Roles > getRoles( ) {

        //region definitions
        @SuppressWarnings( "unchecked" )
        Collection< String > roles = ( Collection< String > ) this.getOrDefault( ROLES, Collections.emptyList() );
        Function< String, Authorization.Roles > toRoles =
                name -> TypingHelper.maybe( Authorization.Roles.class,
                                            name )
                                    .orElse( null );
        //endregion

        return roles.stream()
                    .map( toRoles )
                    .filter( Objects::nonNull )
                    .collect( Collectors.toSet() );

    }
    //endregion

    //region builder
    public static JwtClaimsBuilder builder( ) {
        return new JwtClaimsBuilder();
    }

    public static class JwtClaimsBuilder {

        private final CarsClaims claims;

        public JwtClaimsBuilder( ) {
            this.claims = new CarsClaims();
        }

        public JwtClaimsBuilder version( String version ) {
            this.claims.put( VERSION, version );
            return this;
        }

        public JwtClaimsBuilder name( ProperName name ) {
            this.claims.put( NAME, name.value() );
            return this;
        }

        public JwtClaimsBuilder name( String name ) {
            this.claims.put( NAME, name );
            return this;
        }

        public JwtClaimsBuilder email( EmailAddress email ) {
            this.claims.put( EMAIL, email.value() );
            return this;
        }

        public JwtClaimsBuilder email( String email ) {
            this.claims.put( EMAIL, email );
            return this;
        }

        public JwtClaimsBuilder roles( Collection< Authorization.Roles > roles ) {
            Set< String > rolesList = roles.stream().map( Authorization.Roles::name ).collect( Collectors.toSet() );
            this.claims.put( ROLES, rolesList );
            return this;
        }

        public CarsClaims build( ) {
            return this.claims;
        }

        public JwtClaimsBuilder id( Integer id ) {
            this.claims.put( ID, id );
            return this;
        }
    }
    //endregion

}
