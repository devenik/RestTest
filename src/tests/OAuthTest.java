package tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojoClasses.Api;
import pojoClasses.GetCourse;
import pojoClasses.WebAutomation;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//creating the request
		
		String response = given()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().post("/oauthapi/oauth2/resourceOwner/token")
		.asString();
		
		//System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		
		String accessToken = js.getString("access_token");
		
		GetCourse response2 = given()
		.queryParam("access_token", accessToken)
		.when().get("/oauthapi/getCourseDetails")
		.as(GetCourse.class);
		
		System.out.println(response2.getLinkedIn());
		System.out.println(response2.getInstructor());
		
		List<Api> api = response2.getCourses().getApi();
		List<WebAutomation> wa = response2.getCourses().getWebAutomation();
		ArrayList<String> a= new ArrayList<String>();

		for(int i=0;i<api.size();i++) {
			if(api.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println("The course \""+api.get(i).getCourseTitle()+"\" has a price of "+ api.get(i).getPrice());
			}
		}
		
		for(int i=0;i<wa.size();i++) {
			a.add(wa.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitles);
		Assert.assertTrue(a.equals(expectedList));
	}

}
