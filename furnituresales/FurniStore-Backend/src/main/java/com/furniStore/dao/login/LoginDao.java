package com.furniStore.dao.login;

import java.util.Optional;

public interface LoginDao {
    void saveLogin(final Login login);
    Optional<Login> getLogin(final String email);
    void updateLogin(final Login login);
}
