
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumCode {

	@SuppressWarnings({ "deprecation", "static-access" })
	public SeleniumCode(String xlPath, String url) throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		FileInputStream fis = new FileInputStream(new File(xlPath));

		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet spreadsheet = workbook.getSheetAt(0);
		int rowCount = spreadsheet.getLastRowNum() + 1; // returns last value counted from 0
		int colCount = spreadsheet.getRow(0).getLastCellNum(); // returns total value counted from 1

		System.out.println("rowcount : " + rowCount);
		System.out.println("colcount : " + colCount);

		SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");

		List<WebElement> l = new ArrayList<WebElement>(driver.findElements(By.xpath("//div[@class='title']")));
		WebElement x;
		for (int i = 0; i < l.size(); i++) {
			x = l.get(i);
			System.out.println(x.getText());
		}
		String data = new String();
		for (int row = 1; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				XSSFCell cell = spreadsheet.getRow(row).getCell(col);
				if (cell == null) {
					data = "";
					continue;
				}
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					// Dates count as numeric and must be handled separately
					if (DateUtil.isCellDateFormatted(cell)) {
						data = timeFormat.format(cell.getDateCellValue());
					} else
						data = cell.getRawValue();
				} else
					data = cell.getStringCellValue();
				System.out.println(data);
				driver.findElement(By.xpath("(//div[@class='title']/following::input)[" + (col + 1) + "]"))
						.sendKeys(data);
			}
			WebElement divClick = driver.findElement(By.cssSelector("div.truncate.flex-auto"));
			divClick.click();
			WebElement dropdown = driver.findElement(By.xpath("//input[@placeholder='Find an option']"));
			dropdown.sendKeys(data);
			dropdown.sendKeys(Keys.ENTER);
			Thread.sleep(500);
			driver.findElement(By.tagName("input").className("submitButton")).click();
			Thread.sleep(5000);
			driver.get(url);
			Thread.sleep(2000);
		}
		driver.close();
		workbook.close();
	}
}