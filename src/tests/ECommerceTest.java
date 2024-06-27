package tests;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojoClasses.LoginReq;
import pojoClasses.LoginRes;
import pojoClasses.OrderDetail;
import pojoClasses.Orders;

public class ECommerceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		String token;
		String userId;
		RequestSpecification reqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		LoginReq loginreq = new LoginReq();
		loginreq.setUserEmail("devenik@youremail.com");
		loginreq.setUserPassword("Password123");
		
		//Setting common request configs
		RequestSpecification LoginRequest = given().log().all().spec(reqspec).body(loginreq);
		
		//Beginning Login request and getting response****************************
		LoginRes loginres= LoginRequest.when().post("/api/ecom/auth/login")
		.then().extract().response().as(LoginRes.class);
		
		System.out.println(loginres.getToken());
		System.out.println(loginres.getUserId());
		
		token = loginres.getToken();
		userId = loginres.getUserId();
		
		//Creating a product*****************************
		RequestSpecification addProductReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("authorization", token)
		.build();
		
		RequestSpecification addProdreq = given().log().all().spec(addProductReq)
		.param("productName", "new Laptop")
		.param("productAddedBy", userId)
		.param("productCategory", "Tech")
		.param("productSubCategory", "laptop")
		.param("productPrice", "11500")
		.param("productDescription", "Original Laptop")
		.param("productFor", "women")
		.multiPart("productImage", new File("D:\\RestDocs\\4k-uhd-background-blur-close-up-sneakers-shoes-legs-asphalt.jpg"));
		
		String addProdRes = addProdreq.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addProdRes);
		String productId = js.get("productId");
		
		//Create an order****************************
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("Mexico");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail> orderDetailsList= new ArrayList<OrderDetail>();
		orderDetailsList.add(orderDetail);
		Orders orders = new Orders();
		orders.setOrders(orderDetailsList);
		
		RequestSpecification addOrderReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON)
				.build();
		
		RequestSpecification createOrderReq=given().log().all().spec(addOrderReq).body(orders);
		String response = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().asString();
		
		System.out.println(response);
		
		//Deleting the product**********************
		
		RequestSpecification DelOrderReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON)
				.build();
		
		RequestSpecification deleteProdReq = given().log().all().spec(DelOrderReq).pathParam("productId", productId);
		
		String DelProdRes = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().asString();
		
		JsonPath js1 = new JsonPath(DelProdRes);
		
		Assert.assertEquals("Product Deleted Successfully", js1.getString("message"));
		
		
		
	}

}
