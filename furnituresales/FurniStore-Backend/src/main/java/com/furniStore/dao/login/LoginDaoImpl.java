package com.furniStore.dao.login;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Optional;

public class LoginDaoImpl implements LoginDao {

    @NonNull
    private DynamoDBTableMapper<Login, String, ?> dynamoDBMapper;

    public LoginDaoImpl(final DynamoDBMapper mapper) {
        this.dynamoDBMapper = mapper.newTableMapper(Login.class);
    }

    @Override
    public void saveLogin(@NonNull final Login login) {
        try {
            Validate.notEmpty(login.getEmail(), "Email must not be empty!");
            Validate.notEmpty(login.getPassword(), "Password must not be empty!");
            Validate.notEmpty(login.getUserName(), "UserName must not be empty!");
            dynamoDBMapper.saveIfNotExists(login);
        } catch (final NullPointerException | ConditionalCheckFailedException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Login> getLogin(@NonNull final String email) {
        final Login login = dynamoDBMapper.load(email);
        return Optional.ofNullable(login);
    }

    @Override
    public void updateLogin(@NonNull final Login login) {
        try {
            Validate.notEmpty(login.getEmail(), "Email must not be empty!");
            Validate.notEmpty(login.getPassword(), "Password must not be empty!");
        } catch (final NullPointerException e) {
            throw new IllegalArgumentException(e);
        }

        if (StringUtils.isEmpty(login.getUserName())) {
            final Optional<Login> retrievedLogin = getLogin(login.getEmail());
            if (!retrievedLogin.isPresent()) {
                throw new IllegalArgumentException("The email id does not exists in the system to update the password" +
                        "Please create a new account");
            }
            login.setUserName(retrievedLogin.get().getUserName());
        }
        try {
            dynamoDBMapper.saveIfExists(login);
        } catch (final ConditionalCheckFailedException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
