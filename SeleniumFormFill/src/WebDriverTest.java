import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class WebDriverTest {

	public static void main(String[] args) {

		
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com");
		driver.manage().window().maximize();	
		List<WebElement> l = new ArrayList<WebElement>(driver.findElements(By.xpath("//div[@class='title']")));
		WebElement x;
		for(int i=0;i<l.size();i++) {
			x = l.get(i);
			System.out.println(x.getText());
			driver.findElement(By.xpath("(//div[@class='title']/following::input)["+(i+1)+"]")).sendKeys("test"+i);
		}
		WebElement divClick = driver.findElement(By.cssSelector("div.truncate.flex-auto"));
		divClick.click();
		WebElement dropdown = driver.findElement(By.xpath("//input[@placeholder='Find an option']"));
		dropdown.sendKeys("Open");
		dropdown.sendKeys(Keys.ENTER);
		//driver.close();
	}

}
