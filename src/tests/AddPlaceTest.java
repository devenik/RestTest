package tests;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import pojoClasses.AddPlace;
import pojoClasses.Location;

public class AddPlaceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		Location l1 = new Location();
		l1.setLat("-38.383495");
		l1.setLng("33.427363");
		List<String> types = new ArrayList<String>();
		types.add("Travel");
		types.add("Vacation");
		
		
		AddPlace ap = new AddPlace();
		
		ap.setAccuracy(5);
		ap.setAddress("Del Valle, CDMX");
		ap.setLanguage("Spanish-MX");
		ap.setName("Prueba de ubicacion");
		ap.setPhone_number("+52 55 1234 5678");
		ap.setWebsite("https://www.test-location.com");
		ap.setLocation(l1);
		ap.setTypes(types);
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		 given()
		.header("Content-Type", "application/json")
		.queryParam("key", "qaclick123")
		.body(ap)
		.when().log().all().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);
		
	}

}
