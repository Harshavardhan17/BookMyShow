package org.harsha.bookmyshow.services;

import org.harsha.bookmyshow.models.User;

public interface UserService {
    User singUp(String username, String password);
}
