package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.ClientDto;
import com.unibuc.restaurant_manager.dto.CredentialsDto;
import com.unibuc.restaurant_manager.dto.TokenDto;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.model.Client;
import com.unibuc.restaurant_manager.model.Utilizator;
import com.unibuc.restaurant_manager.repository.ClientRepository;
import com.unibuc.restaurant_manager.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JWTService jwtService;

    public TokenDto login(CredentialsDto credentials) {
        Utilizator user = utilizatorRepository.findByUsername(credentials.getUsername());

        if (user != null && JWTService.isPasswordValid(credentials.getPassword(), user.getPassword())) {
            return new TokenDto(jwtService.getToken(String.valueOf(user.getId())));
        }

        throw new ValidationException("Invalid username or password");
    }

    public Utilizator signupClient(ClientDto client) {
        Client newClient = Client.builder()
                .username(client.getUsername())
                .password(JWTService.encryptPassword(client.getPassword()))
                .email(client.getEmail())
                .nume(client.getNume())
                .prenume(client.getPrenume())
                .nrTelefon(client.getNrTelefon())
                .adresa(client.getAdresa())
                .build();

        clientRepository.save(newClient);
        return newClient;
    }

}
