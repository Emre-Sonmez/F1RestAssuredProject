import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Logger;

import static org.hamcrest.core.IsEqual.equalTo;


import static io.restassured.RestAssured.given;


public class GetAndPost {

    static String url = "http://ergast.com";

    @BeforeClass
    public static void setBaseUri () {
        RestAssured.baseURI = url;
    }

    @Test
    public void getNonParameter(){

        RequestSpecification requestSpecification = given();
        Response response = requestSpecification.request(Method.GET ,"/api/f1/drivers/alonso/constructors/renault/seasons.json");

        String res = response.getBody().asString();
        System.out.println(res);

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);

        JsonPath jsonPath = response.jsonPath();
        String season = jsonPath.get("MRData.SeasonTable.Seasons[0].season");
        System.out.println("Season: " + season);

    }

    @Test
    public void getWithParameter(){
        Response response = given()
                .param("limit", "30")
                .param("offset", "0")
                .param("total", "6")
                .param("series","f1")
                .param("driverId", "alonso")
                .when()
                .get("/api/f1/drivers/alonso/constructors/renault/seasons.json").then()
                .assertThat().statusCode(200).and().contentType("application/json").extract().response();

        System.out.println(response.asString());
    }

    @Test
    public void Post1(){
        String b = "{ \n" +
                "   \"MRData\":{ \n" +
                "      \"xmlns\":\"http:\\/\\/ergast.com\\/mrd\\/1.4\",\n" +
                "      \"series\":\"f1\",\n" +
                "      \"url\":\"http://ergast.com/api/f1/drivers/alonso/constructors/renault/seasons.json\",\n" +
                "      \"limit\":\"30\",\n" +
                "      \"offset\":\"0\",\n" +
                "      \"total\":\"6\",\n" +
                "      \"SeasonTable\":{ \n" +
                "         \"constructorId\":\"renault\",\n" +
                "         \"driverId\":\"alonso\",\n" +
                "         \"Seasons\":[ \n" +
                "            { \n" +
                "               \"season\":\"2003\",\n" +
                "               \"url\":\"https:\\/\\/en.wikipedia.org\\/wiki\\/2003_Formula_One_season\"\n" +
                "            },\n" +
                "            { \n" +
                "               \"season\":\"2004\",\n" +
                "               \"url\":\"https:\\/\\/en.wikipedia.org\\/wiki\\/2004_Formula_One_season\"\n" +
                "            },\n" +
                "            { \n" +
                "               \"season\":\"2005\",\n" +
                "               \"url\":\"https:\\/\\/en.wikipedia.org\\/wiki\\/2005_Formula_One_season\"\n" +
                "            },\n" +
                "            { \n" +
                "               \"season\":\"2006\",\n" +
                "               \"url\":\"https:\\/\\/en.wikipedia.org\\/wiki\\/2006_Formula_One_season\"\n" +
                "            },\n" +
                "            { \n" +
                "               \"season\":\"2008\",\n" +
                "               \"url\":\"https:\\/\\/en.wikipedia.org\\/wiki\\/2008_Formula_One_season\"\n" +
                "            },\n" +
                "            { \n" +
                "               \"season\":\"2009\",\n" +
                "               \"url\":\"https:\\/\\/en.wikipedia.org\\/wiki\\/2009_Formula_One_season\"\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   }\n" +
                "}";
        Response res = given().body(b).when().post("/api/f1/drivers/alonso/constructors/renault/seasons.json").then().assertThat()
                .statusCode(200).and().contentType("application/json").and()
                .extract().response();
        System.out.println(res.asString());
    }

    @AfterClass
    public static void AfterClass(){
        System.out.println("İşlem başarılı");
    }
}
