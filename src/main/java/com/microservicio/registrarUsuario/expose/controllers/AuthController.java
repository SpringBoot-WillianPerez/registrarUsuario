package com.microservicio.registrarUsuario.expose.controllers;

import com.microservicio.registrarUsuario.exceptions.RefreshTokenException;
import com.microservicio.registrarUsuario.expose.dto.*;
import com.microservicio.registrarUsuario.persistence.entities.RefreshToken;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.security.access.JwtTokenProvider;
import com.microservicio.registrarUsuario.services.impl.RefreshTokenService;
import com.microservicio.registrarUsuario.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService userService;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenService refreshTokenService;

    /**
     * REGISTER
     */

    @PostMapping("/register/master")
    public ResponseEntity<GetUserDTO> save_master(@RequestBody CreateUserDTO newuser){
            return new ResponseEntity<GetUserDTO>(userService.saveMaster(newuser), HttpStatus.CREATED);

    }

    @PostMapping("/register/admin-user")
    public ResponseEntity<GetUserDTO> save_admin_user(@RequestBody CreateUserDTO newuser){
        return new ResponseEntity<GetUserDTO>(userService.saveAdminUser(newuser), HttpStatus.CREATED);

    }

    @PostMapping("/register/admin-app")
    public ResponseEntity<GetUserDTO> save_admin_app(@RequestBody CreateUserDTO newuser){
        return new ResponseEntity<GetUserDTO>(userService.saveAdminApp(newuser), HttpStatus.CREATED);

    }

    @PostMapping("/register/client")
    public ResponseEntity<GetUserDTO> save_client(@RequestBody CreateUserDTO newuser){
        return new ResponseEntity<GetUserDTO>(userService.saveClient(newuser), HttpStatus.CREATED);
    }


    /**
     LOGIN
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
    Authentication authenticationDTO = null;
        try {

            authenticationDTO = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        }catch (BadCredentialsException ex){

            throw ex;

        }

        /**
         * Invoca a UserDetailsService para sacar de BBDD
         *  1. Nombre de Usuario
         */
        Authentication authentication = authManager.authenticate(authenticationDTO);
        User user = (User) authentication.getPrincipal();

        //1. aquí GENERAMOS el token desde la authentication
        String token = jwtTokenProvider.generateToken(authentication);

        refreshTokenService.delete(user); //Si tiene algun token de refresco lo borramo
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user); //Creamos token de refresco

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setUsername(user.getUsername());
//        loginResponse.setRoles(user.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .toList());   ESTO ME DABA LAS AUTHORITIES


        loginResponse.setRoles(user.getRoles().stream().map(String::valueOf)
                .toList());

        loginResponse.setToken(token);
        loginResponse.setRefreshToken(refreshToken.getToken());


        


        return ResponseEntity.status(HttpStatus.OK).body(loginResponse); //Se puede cambiar por CREATED
//        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        String refreshToken = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verify) //verificamos token refresh. verify es un método creado por nosotros
                .map(RefreshToken::getUser)//Si verify OK: obtenemos usuario
                .map(user -> {
                    //2. aquí GENERAMOS el token desde user
                    String token = jwtTokenProvider.generateToken(user);

                    refreshTokenService.delete(user); //refresh token antiguo lo borramos
                    RefreshToken refreshToken2 = refreshTokenService.createRefreshToken(user);


                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(LoginResponse.builder()
                                    .token(token)
                                    .refreshToken(refreshToken2.getToken())
                                    .build()
                                    );

                })
                .orElseThrow(() -> new RefreshTokenException("Token de refresco No Encontrado"));
    }
}
