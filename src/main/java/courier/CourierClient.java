package courier;

import io.restassured.response.ValidatableResponse;



import static io.restassured.RestAssured.given;

public class CourierClient extends Client{
    private static final String POST_COURIER_CREATE_PATH = "/api/v1/courier"; // создание курьера методом POST
    public static final String POST_COURIER_LOGIN_PATH = "/api/v1/courier/login"; //логин курьера в системе методом POST
    public static final String DELETE_COURIER_PATH = "/api/v1/courier/"; //для удаления курьера нужно его id (появляется после логина)


    public ValidatableResponse createCourier(Courier courier) {
        return given().log().all()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(POST_COURIER_CREATE_PATH)
                .then();
    }

    public ValidatableResponse loginCourier(Credentials credentials) {
        return given().log().all()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(POST_COURIER_LOGIN_PATH)
                .then();
    };

    public ValidatableResponse deleteCourier(int idCourier) {
        return given().log().all()
                .spec(getSpec())
                .when()
                .delete(DELETE_COURIER_PATH + idCourier)
                .then();
    }
}
