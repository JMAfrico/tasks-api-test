package br.JMAfrico.tasks.apitest;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI="http://localhost:8001/tasks-backend";
	}

	@Test
	public void getAllTarefasComLog() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.log().all()
		;
	}
	
	@Test
	public void getAllTarefasSemLog() {
		RestAssured
		.given()
		.when()
			.get("http://localhost:8001/tasks-backend/todo")
		.then()
			.statusCode(200)
		;
	}
	
	@Test
	public void postNewTarefa() {
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body("{\"task\":\"teste via api\",\"dueDate\":\"2022-05-21\"}")
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void notPostNewTarefaInvalida() {
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body("{\"task\":\"teste via api\",\"dueDate\":\"2020-05-21\"}")
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", Matchers.is("Due date must not be in past"))
		;
	}
}

//{"task":"teste via api","dueDate":"2022-05-21"} //variáveis que está na classe model/task
