package com.unibuc.restaurant_manager.controller;

import com.unibuc.restaurant_manager.dto.ClientDto;
import com.unibuc.restaurant_manager.dto.CredentialsDto;
import com.unibuc.restaurant_manager.dto.TokenDto;
import com.unibuc.restaurant_manager.model.Utilizator;
import com.unibuc.restaurant_manager.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.unibuc.restaurant_manager.utils.ResponseUtils.created;
import static com.unibuc.restaurant_manager.utils.ResponseUtils.ok;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDto> login(@RequestBody @Valid CredentialsDto credentials) {
        return ok(authenticationService.login(credentials));
    }

    @PostMapping("/signup/client")
    @ResponseBody
    public ResponseEntity<Utilizator> signupClient(@RequestBody @Valid ClientDto client) {
        return created(authenticationService.signupClient(client));
    }

//    @PostMapping("/signup/angajat")
//    @ResponseBody
//    public ResponseEntity<Utilizator> signupAngajat(@Valid @RequestBody AngajatDto angajat) {
//        return created(authenticationService.signupClient(angajat));
//    }

}
