package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private WebDriverWait wait;
	private String credentialUrl = "https://www.google.com";
	private String credentialUsername = "newtester";
	private String credentialPassword = "password123";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(driver, 500);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testAddCredential() {

		signUpAndLogin("John", "Doe", "johndoe", "password");

		addNewCredential(credentialUrl, credentialUsername, credentialPassword);

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-credentials-tab")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rowCredentialUrl")));

		WebElement credentialUrlElement = driver.findElement(By.id("rowCredentialUrl"));
		WebElement credentialUsernameElement = driver.findElement(By.id("rowCredentialUsername"));
		WebElement credentialPasswordElement = driver.findElement(By.id("rowCredentialPassword"));

		assertEquals(credentialUrl, credentialUrlElement.getText());
		assertEquals(credentialUsername, credentialUsernameElement.getText());
		assertTrue(!credentialPasswordElement.getText().isEmpty());
	}

	@Test
	public void testEditCredential() {

		signUpAndLogin("Jane", "Doe", "janedoe", "password");

		addNewCredential(credentialUrl, credentialUsername, credentialPassword);

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-credentials-tab")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		WebElement passField = webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("rowCredentialPassword")));
		String oldPasswordEnc = passField.getText();

		editCredentialPassword("updated-password123");

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-credentials-tab")).click();

		webDriverWait = new WebDriverWait(driver, 5);
		passField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rowCredentialPassword")));
		String newPasswordEnc = passField.getText();

		Assertions.assertNotEquals(oldPasswordEnc, newPasswordEnc);
	}

	@Test
	public void testDeleteCredential() {
		signUpAndLogin("Jane", "Doe Jr", "janedoejr", "password");

		driver.findElement(By.id("nav-credentials-tab")).click();

		Integer initialCredentialListSize = getCredentialList().size();

		addNewCredential(credentialUrl, credentialUsername, credentialPassword);

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-credentials-tab")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentialLink")));

		deleteCredential();

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-credentials-tab")).click();

		assertEquals(initialCredentialListSize, getCredentialList().size());
	}

	private void signUpAndLogin(String firstName, String lastName, String userName, String password) {
		SignUpPage signUpPage = new SignUpPage(driver, this.port);
		signUpPage.doMockSignUp(firstName, lastName, userName, password);

		LoginPage loginPage = new LoginPage(driver, this.port);
		loginPage.doLogIn(userName, password);

	}

	private void addNewCredential(String credentialUrl, String username, String password) {

		driver.findElement(By.id("nav-credentials-tab")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));
		driver.findElement(By.id("btnAddNewCredential")).click();

		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

		driver.findElement(By.id("credential-url")).sendKeys(credentialUrl);
		driver.findElement(By.id("credential-username")).sendKeys(username);
		driver.findElement(By.id("credential-password")).sendKeys(password);

		driver.findElement(By.id("btnSaveCredential")).click();
	}

	private void deleteCredential() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentialLink")));
		driver.findElement(By.id("deleteCredentialLink")).click();
	}

	private void editCredentialPassword(String newPassword) {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnEditCredential")));

		driver.findElement(By.id("btnEditCredential")).click();

		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));

		WebElement passField = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		passField.clear();
		passField.sendKeys(newPassword);

		driver.findElement(By.id("btnSaveCredential")).click();
	}

	private List<Map<String, String>> getCredentialList() {
		List<Map<String, String>> credentialList = new ArrayList<>();
		WebElement table = driver.findElement(By.id("credentialTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.tagName("td"));
			if (cols.size() == 3) {
				Map<String, String> credential = new HashMap<>();
				credential.put("url", cols.get(0).getText());
				credential.put("username", cols.get(1).getText());
				credential.put("password", cols.get(2).getText());
				credentialList.add(credential);
			}
		}
		return credentialList;
	}

}
