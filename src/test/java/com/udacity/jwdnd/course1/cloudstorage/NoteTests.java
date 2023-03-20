package com.udacity.jwdnd.course1.cloudstorage;

import java.util.List;

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

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private WebDriverWait wait;

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
	public void createNote() {

		signUpAndLogin("George", "Doe", "gdoe", "password");

		addNewNote("Test note", "This is a test note.");

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertEquals("Test note", getFirstNote().getNoteTitle());
	}

	@Test
	public void editNote() {

		signUpAndLogin("Mary", "Doe", "mdoe", "password");

		addNewNote("Test note", "This is a test note.");

		driver.findElement(By.id("aResultSuccess")).click();

		editNoteTitle("Updated test note");

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertEquals("Updated test note", getFirstNote().getNoteTitle());
	}

	@Test
	public void deleteNote() {

		signUpAndLogin("Susan", "Doe", "sdoe", "password");

		addNewNote("Test note", "This is a test note.");

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-notes-tab")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNoteLink")));
		driver.findElement(By.id("deleteNoteLink")).click();

		driver.findElement(By.id("aResultSuccess")).click();

		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertTrue(isNoteListEmpty());
	}

	private void signUpAndLogin(String firstName, String lastName, String userName, String password) {
		SignUpPage signUpPage = new SignUpPage(driver, this.port);
		signUpPage.doMockSignUp(firstName, lastName, userName, password);

		LoginPage loginPage = new LoginPage(driver, this.port);
		loginPage.doLogIn(userName, password);

	}

	private void addNewNote(String noteTitle, String noteDescription) {

		driver.findElement(By.id("nav-notes-tab")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));
		driver.findElement(By.id("btnAddNewNote")).click();

		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		driver.findElement(By.id("note-title")).sendKeys("Test note");
		driver.findElement(By.id("note-description")).sendKeys("This is a test note.");

		driver.findElement(By.id("btnSaveNote")).click();
	}

	private void editNoteTitle(String newNoteTitle) {

		driver.findElement(By.id("nav-notes-tab")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnEditNote")));
		driver.findElement(By.id("btnEditNote")).click();

		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleField = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		noteTitleField.clear();
		noteTitleField.sendKeys(newNoteTitle);

		driver.findElement(By.id("btnSaveNote")).click();
	}

	private boolean isNoteListEmpty() {
		List<WebElement> noteTitles = driver.findElements(By.id("rowNoteTitle"));
		List<WebElement> noteDescriptions = driver.findElements(By.id("rowNoteDescription"));
		return noteTitles.isEmpty() && noteDescriptions.isEmpty();
	}

	private Note getFirstNote() {

		WebElement titleElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("rowNoteTitle")));
		WebElement descriptionElement = driver.findElement(By.id("rowNoteDescription"));

		String title = titleElement.getText();
		String description = descriptionElement.getText();

		return new Note(title, description);
	}

}
