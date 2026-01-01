package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.dto.CustomerDto;
import com.unibuc.restaurant_manager.dto.CredentialsDto;
import com.unibuc.restaurant_manager.dto.TokenDto;
import com.unibuc.restaurant_manager.exception.ValidationException;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.repository.CustomerRepository;
import com.unibuc.restaurant_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository utilizatorRepository;

    @Autowired
    private CustomerRepository clientRepository;

    @Autowired
    private JWTService jwtService;

    public TokenDto login(CredentialsDto credentials) {
        User user = utilizatorRepository.findByUsername(credentials.getUsername());

        if (user != null && JWTService.isPasswordValid(credentials.getPassword(), user.getPassword())) {
            return new TokenDto(jwtService.getToken(String.valueOf(user.getId())));
        }

        throw new ValidationException("Invalid username or password");
    }

    public User signupClient(CustomerDto client) {
        Customer newClient = Customer.builder()
                .username(client.getUsername())
                .password(JWTService.encryptPassword(client.getPassword()))
                .email(client.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .phoneNumber(client.getPhoneNumber())
                .address(client.getAddress())
                .build();

        clientRepository.save(newClient);
        return newClient;
    }

//    public User signup(CustomerDto angajat) {
//
//
//        return newAngajat;
//    }

}
