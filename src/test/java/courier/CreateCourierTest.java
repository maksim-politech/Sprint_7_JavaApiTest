package courier;


import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;



import org.junit.Test;

public class CreateCourierTest {

    private CourierGenerator courierGenerator = new CourierGenerator();
    private CourierClient courierClient;
    private Courier courier;
    private CourierAssertions courierAssertions;
    int idCourier;



    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = courierGenerator.getCourierRandom();
        courierAssertions = new CourierAssertions();
    }

    @Test
    public void courierCanBeCreatedWith201CodeMessageOk() {
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        courierAssertions.creatingCourierSuccessfully(responseCreateCourier);
        Credentials credentials = Credentials.from(courier);
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(credentials);
        idCourier = responseLoginCourier.extract().path("id");
    }

   @Test
    public void courierCanNotBeCreatedWithoutLoginField() {
        courier.setLogin(null);
        ValidatableResponse responseNullLogin = courierClient.createCourier(courier);
        courierAssertions.creationCourierFailedField(responseNullLogin);
    }

    @Test
    public void courierCanNotBeCreatedWithoutPasswordField() {
        courier.setPassword(null);
        ValidatableResponse responseNullPassword = courierClient.createCourier(courier);
        courierAssertions.creationCourierFailedField(responseNullPassword);
    }

    @Test
    public void courierCanNotBeCreatedWithoutLoginAndPasswordFields() {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse responseNullFields = courierClient.createCourier(courier);
        courierAssertions.creationCourierFailedField(responseNullFields);
    }

    @Test
    public void courierCanNotBeCreatedWithTheSameData() {
        courierClient.createCourier(courier);
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        courierAssertions.creationCourierTheSameData(responseCreateCourier);
    }

    @After
    public void deleteCourier() {
        if (idCourier != 0) {
            courierClient.deleteCourier(idCourier);
        }
    }
}