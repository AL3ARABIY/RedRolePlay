package org.data.redroleplay.services;

import org.data.redroleplay.entities.website.User;

import java.util.Optional;

public interface AuthenticationService {

    Optional<User> getAuthenticatedUser();
}
