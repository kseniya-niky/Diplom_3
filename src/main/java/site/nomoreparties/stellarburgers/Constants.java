package site.nomoreparties.stellarburgers;

import java.time.Duration;
import java.util.Random;

public class Constants {
    public static final String BASE_URI_API = "https://stellarburgers.nomoreparties.site/api/";
    public static final String REGISTRATION_PATH = "auth/register";
    public static final String DELETE_UPDATE_PATH = "auth/user";

    public static final String NEW_EMAIL = "tst" + new Random().nextInt() + "@tst.fjk.com";
    public static final String NEW_NAME = "tst" + new Random().nextInt() + "user";
    public static final String PASSWORD_THREE_SYMBOLS = "D5$";
    public static final String PASSWORD_FIVE_SYMBOLS = "D754@";
    public static final String PASSWORD_SIX_SYMBOLS = "754@#$";
    public static final String PASSWORD_SEVEN_SYMBOLS = "D754@#$";
    public static final String PASSWORD_FIFTEEN_SYMBOLS = "JGD754@#$hr46)_";

    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    public static final String ACCOUNT_URI = "https://stellarburgers.nomoreparties.site/account";
    public static final Duration WAIT_SECONDS_DURATION = Duration.ofSeconds(10);
}