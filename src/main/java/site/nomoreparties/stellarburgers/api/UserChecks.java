package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class UserChecks {
    private UserClient userClient = new UserClient();

    @Step("Проверка, что пользователь успешно создан через API")
    public void checkCreatedUser(NewUserRegistrationInfo userInfo,
                                 NewUser newUser) {
        assertTrue(userInfo.isSuccess());
        assertEquals("Пользователь создан под другим email",
                newUser.getEmail().toLowerCase(), userInfo.getUser().getEmail());
        assertEquals("Пользователь создан под другим name", newUser.getName(), userInfo.getUser().getName());
    }

    @Step("Проверка, что пользователь успешно удален через API")
    public void checkDeletedUser(ValidatableResponse response) {
        userClient.checkResponseHTTPStatus(response, HTTP_ACCEPTED);
        boolean isSuccess = Boolean.valueOf(userClient.getResponseDataByKey(response, "success"));
        assertTrue(isSuccess);
    }
}