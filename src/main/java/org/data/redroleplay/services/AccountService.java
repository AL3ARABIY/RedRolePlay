package org.data.redroleplay.services;

import org.data.redroleplay.entities.game.Account;
import org.data.redroleplay.entities.website.User;

public interface AccountService {

    Account save(User user);
}
