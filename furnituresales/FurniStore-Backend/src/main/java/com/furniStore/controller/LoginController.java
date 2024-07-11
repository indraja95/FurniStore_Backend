package com.furniStore.controller;

import com.furniStore.dao.login.Login;
import com.furniStore.dao.login.LoginDao;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping("login-page")
@RequiredArgsConstructor
public class LoginController {

    @NonNull
    private LoginDao loginDao;

    @PostMapping("/login")
    @ResponseBody
    public void createAccount(@RequestBody final Login login) {
        try {
            loginDao.saveLogin(login);
        } catch (final IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("login")
    @ResponseBody
    public String validateLogin(@RequestParam(value = "email") final String email, @RequestParam(value = "password") final String password) {
        try {
            final Optional<Login> loginOptional = loginDao.getLogin(email);
            if (!loginOptional.isPresent()) {
                throw new IllegalArgumentException("Email Id is not associated with any account");
            } else {
                if (password.equals(loginOptional.get().getPassword())) {
                    return loginOptional.get().getUserName();
                } else {
                    throw new IllegalArgumentException("Invalid Password");
                }
        }
        } catch (final IllegalArgumentException e) {
            System.out.println("I', here");
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/login")
    @ResponseBody
    public void updateAccount(@RequestBody final Login login) {
        try {
            loginDao.updateLogin(login);
        } catch (final IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
