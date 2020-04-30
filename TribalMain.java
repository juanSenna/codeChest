package seleniumTest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TribalMain {
	private static boolean found = false;
	private static InputStream ins = null; // raw byte-stream
	private static Reader r = null; // cooked reader
	private static BufferedReader br = null;
	private static int wantedLine = 1;
	private static String x;
	private static String y;
	private static int troopCount = 1;
	private static int lanc;
	private static int espa;
	private static int barb;
	private static int arch;
	private static int leve;
	private static int mont;
	private static int pesa;
	private static int arie;
	private static int cata;
	private static int bers;
	private static int trab;
	private static int nobl;
	private static int pala;

	public static void main(String[] args) throws InterruptedException, IOException {

		// setting the driver executable
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Roam - PC\\Dropbox\\TI\\WebDrivers\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		//options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
		options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
		
		options.addArguments("--headless"); // only if you are running headless
		options.addArguments("--window-size=1600x1000");
		
		options.addArguments("--no-sandbox"); // https://stackoverflow.com/a/50725918/1689770
		options.addArguments("--disable-infobars"); // https://stackoverflow.com/a/43840128/1689770
		options.addArguments("--disable-dev-shm-usage"); // https://stackoverflow.com/a/50725918/1689770
		options.addArguments("--disable-browser-side-navigation"); // https://stackoverflow.com/a/49123152/1689770
		options.addArguments("--disable-gpu"); // https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc

		ChromeDriver driver = new ChromeDriver(options);
		// Initiating your chromedriver
		//driver.manage().window().setPosition(new Point(-1000, 0));
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		driver.get("https://br.tribalwars2.com/game.php?world=br47&character_id=752860");

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[2]/header/div/div[1]")));

		WebElement logoAtStartScreen = driver.findElement(By.xpath("/html/body/div[3]/div[2]/header/div/div[1]"));
		if (logoAtStartScreen == null) {
			// nao apareceu logo
		} else {
			WebElement username = driver.findElement(
					By.xpath("/html/body/div[3]/div[2]/header/div/div[3]/div[2]/div[1]/div[1]/form/div[1]/input"));
			username.click();
			username.sendKeys("noreplyv5");

			WebElement password = driver.findElement(
					By.xpath("/html/body/div[3]/div[2]/header/div/div[3]/div[2]/div[1]/div[1]/form/div[2]/input"));
			password.click();
			password.sendKeys("darknight5");

			WebElement login = driver.findElement(
					By.xpath("/html/body/div[3]/div[2]/header/div/div[3]/div[2]/div[1]/div[1]/form/button/span"));
			login.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//*[@id=\"wrapper\"]/div[2]/div[2]/div/div/div[1]/div[1]/div/div[1]/div[2]/div/ul/li/a/span[2]")));

			// select world
			driver.findElement(
					By.xpath("//*[@id=\"wrapper\"]/div[2]/div[2]/div/div/div[1]/div[1]/div/div[1]/div[2]/div/ul/li/a"))
					.click();

			// wait till the game loads
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"unit-bar\"]/ul/li[1]/div/div")));

			int seconds = 15;
			int minutes = 0;
			int hours = 0;
			int rrCounter = 0;
			int delay = 4000;
			int refreshTimeout = delay * 20;
			// gameLoop
			while (true) {

				if (seconds >= 60) {
					minutes += seconds / 60;
					seconds = seconds % 60;
				}
				if (minutes >= 60) {
					hours += minutes / 60;
					minutes = minutes % 60;
				}
				System.out.println("");
				System.out.println("Running for: " + hours + "h : " + minutes + "m : " + seconds + "s");
				System.out.println("-");

				Thread.sleep(delay);
				seconds += delay / 1000;
				System.out.println("Ticking");

				String army = idleCheck(driver);
				if (army != "") {
					rrCounter = 0;
					printTroops();
					barbKill(army, driver, wait);
					seconds += 10;
				} else {

					rrCounter++;

					try {
						if (driver.findElement(By.xpath("//*[@id=\"screen-loading\"]/div/div")).isDisplayed()
								&& rrCounter >= refreshTimeout) {
							driver.navigate().refresh();
							System.out.println("Login freeze identified in gameLoop - Refreshed");
							troopCount--;
						}

					} catch (Exception e) {
						System.out.println("Should be seaching fine");
					}

				}

				// upgradar?
				// fazer tropas?

			}

		}
	}

	private static void printTroops() {

		System.out.println("Idle Troops Identified:");
		System.out.println("");
		System.out.println("Lanceiros: " + lanc);
		System.out.println("Espadas: " + espa);
		System.out.println("Barbaro: " + barb);
		System.out.println("Archers: " + arch);
		System.out.println("Leves: " + leve);
		System.out.println("Montas: " + mont);
		System.out.println("Pesados: " + pesa);
		System.out.println("Arietes: " + arie);
		System.out.println("Catapas: " + cata);
		System.out.println("Berserkers: " + bers);
		System.out.println("Trabucos: " + trab);
		System.out.println("Nobres: " + nobl);
		System.out.println("Palas: " + pala);
		System.out.println("");

	}

	public static String idleCheck(ChromeDriver driver) {

		try {
			System.out.println("Idle checking");
			lanc = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[1]/div/div")).getText());

			espa = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[2]/div/div")).getText());

			barb = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[3]/div/div")).getText());

			arch = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[4]/div/div")).getText());

			leve = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[5]/div/div")).getText());

			mont = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[6]/div/div")).getText());

			pesa = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[7]/div/div")).getText());

			arie = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[8]/div/div")).getText());

			cata = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[9]/div/div")).getText());

			bers = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[10]/div/div")).getText());

			trab = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[11]/div/div")).getText());

			nobl = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[12]/div/div")).getText());

			pala = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"unit-bar\"]/ul/li[13]/div/div")).getText());

			// if(pala > 0) return "pala";
			if (nobl > 0)
				return "nobl";
			if (lanc >= 20)
				return "lanc";
			if (espa >= 20)
				return "espa";
			if (arch >= 20)
				return "arch";
			if (barb >= 20)
				return "barb";
			if (leve >= 20)
				return "leve";
			if (mont >= 20)
				return "mont";
			if (pesa >= 20)
				return "pesa";
			if (arie >= 20)
				return "arie";
			if (cata >= 20)
				return "cata";
			if (bers >= 20)
				return "bers";
			if (trab >= 20)
				return "trab";
			System.out.println("No Idle Group identified");
			return "";

		} catch (Exception e) {
			System.out.println("Major idleCheck exception");
			driver.navigate().refresh();
			troopCount = 1;
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				System.out.println("Thread Idle Exception");
			}
			return "";
		}
	}

	private static void barbKill(String army, ChromeDriver driver, WebDriverWait wait)
			throws IOException, NoSuchElementException {
		System.out.println("");
		System.out.println("Barbfarming");
		System.out.println("");

		try {

			int actualLine = 1;
			int linesInDoc = countLinesOld("res/barblocations.txt");

			System.out.println("Total aldeias barb: " + linesInDoc);

			if (wantedLine > linesInDoc) {
				wantedLine = 0;
			}

			try {
				String s;
				ins = new FileInputStream("res/barblocations.txt");
				r = new InputStreamReader(ins, "UTF-8");
				br = new BufferedReader(r);

				while ((s = br.readLine()) != null) {

					if (!found) {
						if (actualLine == wantedLine) {
							System.out.println("Coordenadas encontradas");

							String[] arrOfStr = s.split(",", 2);
							x = arrOfStr[0];
							y = arrOfStr[1];

							found = true;

							continue;

						}
					}
					actualLine++;
				}

			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			System.out.println("ATACAR aldeia " + wantedLine + " | x: " + x + ", y: " + y);
			found = false;
			wantedLine++;

			// click world
			driver.findElement(By.xpath("//*[@id=\"world-map\"]")).click();

			// wait inputs to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"minimap\"]/canvas")));
			System.out.println("Waited map options");

			WebElement xInput = null;
			WebElement xInput2 = null;
			// Check xB and xC
			try {
				xInput = driver
						.findElement(By.xpath("//*[@id=\"world-map-search-0xc\"]/div/table[2]/tbody/tr/td[1]/input"));
			} catch (Exception e) {
				System.out.println("Input exception xC");
			}

			try {
				xInput2 = driver
						.findElement(By.xpath("//*[@id=\"world-map-search-0xb\"]/div/table[2]/tbody/tr/td[1]/input"));

			} catch (Exception e) {
				System.out.println("Input exception xB");
			}

			if (xInput != null) { // xc
				System.out.println("Running version xC");
				xInput.click();
				xInput.clear();
				xInput.sendKeys(x);

				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"world-map-search-0xc\"]/div/table[2]/tbody/tr/td[2]/input")));
				System.out.println("WaitedY");

				WebElement yInput = driver
						.findElement(By.xpath("//*[@id=\"world-map-search-0xc\"]/div/table[2]/tbody/tr/td[2]/input"));

				if (yInput != null) {
					yInput.click();
					yInput.clear();
					yInput.sendKeys(y);

					System.out.println("Clicking go");
					driver.findElement(By.xpath("//*[@id=\"world-map-search-0xc\"]/div/table[2]/tbody/tr/td[3]/div"))
							.click();

				}

			} else if (xInput2 != null) { // xb

				System.out.println("Running version xB");
				xInput2.clear();
				xInput2.click();
				xInput2.sendKeys(x);

				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"world-map-search-0xb\"]/div/table[2]/tbody/tr/td[2]/input")));
				System.out.println("WaitedY");

				WebElement yInput2 = driver
						.findElement(By.xpath("//*[@id=\"world-map-search-0xb\"]/div/table[2]/tbody/tr/td[2]/input"));

				if (yInput2 != null) {

					yInput2.click();
					yInput2.clear();
					yInput2.sendKeys(y);

					System.out.println("Clicking go");
					driver.findElement(By.xpath("//*[@id=\"world-map-search-0xb\"]/div/table[2]/tbody/tr/td[3]/div"))
							.click();

				}

			}

			// Click this input

			// driver.findElement(By.xpath("//*[@id=\"world-map-search-0xc\"]/div/table[2]/tbody/tr/td[3]/div")).click();

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"context-menu\"]/div/ul/li[2]/div/div")));
			System.out.println("Waited village");

			driver.findElement(By.xpath("//*[@id=\"context-menu\"]/div/ul/li[2]/div/div")).click();

			// Check troop

			WebElement troopMenu = null;

			// *[@id="twx-w2"]/div/div/div/div/div/div[2]/div/div/div/ul/li[1]/div/div[3]/input

			try {
				troopMenu = driver.findElement(By.xpath("//*[@id=\"twx-w" + troopCount
						+ "\"]/div/div/div/div/div/div[2]/div/div/div/ul/li[1]/div/div[3]/input"));
			} catch (Exception e) {
				System.out.println("Excepcion at troop id1");
			}

			if (troopMenu != null) {
				troopMenu.click();
				troopMenu.sendKeys("20");
				driver.findElement(By.xpath("//*[@id=\"twx-w" + troopCount + "\"]/div/div/div/div/footer/ul/li[2]/a"))
						.click();

			}

			troopCount++;

			System.out.println("Troops sent!");

			/*
			 * System.out.println("Switching army: (" + army + ")"); switch (army) {
			 * 
			 * case "lanc": break;
			 * 
			 * case "espa": break;
			 * 
			 * case "arch": break;
			 * 
			 * case "barb": break;
			 * 
			 * case "leve": break;
			 * 
			 * case "mont": break;
			 * 
			 * case "pesa": break;
			 * 
			 * case "arie": break;
			 * 
			 * case "cata": break;
			 * 
			 * case "bers": break;
			 * 
			 * case "nobl": break;
			 * 
			 * case "pala": break;
			 * 
			 * }
			 */

		} catch (Exception e) {
			System.out.println("Major barbKill exception");
			driver.navigate().refresh();
			troopCount = 1;
			wantedLine--;
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				System.out.println("Thread barbKill Exception");
			}
		}

	}

	public static int countLinesOld(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}

}