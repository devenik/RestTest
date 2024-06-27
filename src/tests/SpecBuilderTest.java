package tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoClasses.AddPlace;
import pojoClasses.Location;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON)
		.build();
		
		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200)
		.expectContentType(ContentType.JSON).build();
		
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
		
		//RestAssured.baseURI="https://rahulshettyacademy.com";
		
		 RequestSpecification reqspec=given()
		.spec(req)
		.body(ap);
		 
		String res = reqspec.when().log().all().post("/maps/api/place/add/json")
		.then().log().all().spec(resspec)
		.extract().response().asString();
		 
		 
		 System.out.println(res);
	}

}
