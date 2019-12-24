package com.packagename.appfrontend.client;

import com.packagename.appfrontend.domain.UserDto;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserBackend {

    public UserDto login(String login, String password) {
        try {
            HttpResponse<UserDto> httpResponse = Unirest.post("http://localhost:8080/v1/user/loginUser")
                    .header("Content-Type","application/json")
                    .body(new UserDto(login, password))
                    .asObject(UserDto.class);
            return httpResponse.getBody();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public String createUser(UserDto userDto) {
        try {
            HttpResponse<String> httpResponse = Unirest.post("http://localhost:8080/v1/user/createUser")
                    .header("Content-Type","application/json")
                    .body(userDto)
                    .asString();
            return httpResponse.getBody();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }
}
