package canelhas.cars.api.admin;

import canelhas.cars.api.auth.domain.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import static canelhas.cars.api.auth.domain.Authorization.Roles.ADMIN;

@RestController
@RequiredArgsConstructor
public class AdminController {


    private final AdminRepository repository;


    @PostMapping( "api/admin/database" )
    @Authorization( ADMIN )
    public ResponseEntity< Map< String, String > > resetDatabase( ) {

        final var body = Collections.singletonMap( "message", "Finge que deu certo :)" );

        return new ResponseEntity<>( body, HttpStatus.OK );
    }

}
