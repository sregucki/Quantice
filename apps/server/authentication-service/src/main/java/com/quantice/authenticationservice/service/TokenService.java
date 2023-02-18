package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.Token;

public interface TokenService {

    Token saveIfNotExists(Token token);
}
