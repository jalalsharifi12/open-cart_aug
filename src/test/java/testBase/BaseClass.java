package testBase;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

// we must import from apache logging.

public class BaseClass {

	public static WebDriver driver;
	
	public Logger logger;
public ResourceBundle rb; 
	
	@BeforeClass(groups= {"Regression","Master", "Sanity"})
	@Parameters("browser")
	public void setup(String br)
	{
		rb=ResourceBundle.getBundle("config");// load config.properties file
		
		logger=LogManager.getLogger(this.getClass());
		
		
				
		//WebDriverManager.chromedriver().setup(); 
		//driver=new ChromeDriver();
		// the reason we commented the above two line is that we dont need webdrivermanager in selenuim 4.6.0
		// and for the second  line we write if condition for all browsers to perform cross browser of pararell testing
		
		//launch right browser based on parameter
				if (br.equals("chrome")) {
					driver = new ChromeDriver();
				} 
				else if (br.equals("edge")) {
					driver = new EdgeDriver();
				} 
				else {
					driver = new ChromeDriver();
				}
			

		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//driver.get("http://localhost/opencart/upload/index.php");
		driver.get(rb.getString("appURL2"));
		
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Regression","Master", "Sanity"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomeString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return (generatedString);
	}

	public String randomeNumber() {
		String generatedString2 = RandomStringUtils.randomNumeric(10);
		return (generatedString2);
	}
	
	public String randomAlphaNumeric() {
		String st = RandomStringUtils.randomAlphabetic(4);
		String num = RandomStringUtils.randomNumeric(3);
		
		return (st+"@"+num);
	}
	
//***********************************************************
	// method of capturing the screen shot and timeStamp after creating the method we must call it whenever test fails
	//*******************************************************
	
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;

	}

	
	
}
