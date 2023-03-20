package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testHomePageAccessWithoutLogin() {
		driver.get("http://localhost:" + port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testSignupAndLogout() {

		signUpAndLogin("Test", "User", "testuser", "password123");
		assertEquals("Home", driver.getTitle());

		driver.findElement(By.id("btnLogout")).click();
		assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	private void signUpAndLogin(String firstName, String lastName, String userName, String password) {

		SignUpPage signUpPage = new SignUpPage(driver, this.port);
		signUpPage.doMockSignUp(firstName, lastName, userName, password);

		LoginPage loginPage = new LoginPage(driver, this.port);
		loginPage.doLogIn(userName, password);

	}

}
