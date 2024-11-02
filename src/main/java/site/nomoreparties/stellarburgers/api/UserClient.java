package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.html5.LocalStorage;
import site.nomoreparties.stellarburgers.Constants;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class UserClient {
    public String getResponseDataByKey(ValidatableResponse response, String key) {
        return response.extract().path(key).toString();
    }

    @Step("Проверка статуса HTTP ответа")
    public void checkResponseHTTPStatus(ValidatableResponse response, int statusCode) {
        response.assertThat().statusCode(statusCode);
    }

    private RequestSpecification specificationWithoutAuth() {
        return given().log().all()
                .baseUri(Constants.BASE_URI_API);
    }

    private RequestSpecification specificationWithAuth(String token) {
        return given().log().all()
                .baseUri(Constants.BASE_URI_API)
                .auth().oauth2(token);
    }

    @Step("Создание нового пользователя через API")
    public ValidatableResponse createNewUser(NewUser newUser) {
        return specificationWithoutAuth()
                .contentType(ContentType.JSON)
                .body(newUser)
                .and()
                .when()
                .post(Constants.REGISTRATION_PATH)
                .then().log().all();
    }

    @Step("Получение информации о созданном новом пользователе через API")
    public NewUserRegistrationInfo getResponseAboutNewUser(ValidatableResponse response) {
        return  response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(NewUserRegistrationInfo.class);
    }

    @Step("Удаление пользователя через API")
    public ValidatableResponse deleteUser(LocalStorage localStorage) {
        String token = StringUtils.substringAfter(localStorage.getItem("accessToken"), " ");
        return  specificationWithAuth(token)
                .when()
                .delete(Constants.DELETE_UPDATE_PATH)
                .then().log().all();
    }

    @Step("Удаление пользователя через API")
    public ValidatableResponse deleteUser(NewUserRegistrationInfo info) {
        String token = StringUtils.substringAfter(info.getAccessToken(), " ");
        return  specificationWithAuth(token)
                .when()
                .delete(Constants.DELETE_UPDATE_PATH)
                .then().log().all();
    }
}