package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.AuthToken;

public interface AuthTokenService {

    AuthToken saveIfNotExists(AuthToken authToken);
}
