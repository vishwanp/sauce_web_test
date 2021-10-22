package Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestWeb {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String URL ="jdbc:mysql://localhost:3306/ecommerce";
		String UserName ="root";
		String Password ="root";
		
		DBConnection Obj= new DBConnection(URL, UserName, Password);
		
		Statement statement = Obj.getConnection().createStatement();
		
		ResultSet result_login = statement.executeQuery("select * from Login_details");
		result_login.next();
		
		System.setProperty("webdriver.chrome.driver","chromedriver");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.saucedemo.com/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MILLISECONDS);
		
		
		WebElement Username = driver.findElement(By.xpath("//input[@id='user-name']"));
		Username.sendKeys(result_login.getString("Username"));
		
		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		password.sendKeys(result_login.getString("password"));
		
		WebElement LoginButton = driver.findElement(By.xpath("//input[@name='login-button']"));
		LoginButton.click();
		
		WebElement Add = driver.findElement(By.xpath("//*[@class='btn btn_primary btn_small btn_inventory']"));
		Add.click();
		
		WebElement cart = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
		cart.click();
		
		WebElement product = driver.findElement(By.xpath("//div[@class = 'inventory_item_name']"));
		WebElement price = driver.findElement(By.xpath("//div[@class = 'inventory_item_price']"));
		
		Float cart_price = Float.parseFloat(price.getText().substring(1));
		
		ResultSet result_product = statement.executeQuery("select * from eproduct");
		
		result_product.next();
		
		if(product.getText().equals(result_product.getString("name")) &&  cart_price.equals(result_product.getFloat("price")))
		{
			System.out.println("Your product is verified from DataBase");
			
			WebElement checkout = driver.findElement(By.xpath("//button[@id='checkout']"));
			checkout.click();
			
			WebElement FirstName = driver.findElement(By.xpath("//input[@id='first-name']"));
			FirstName.sendKeys("abc");
			
			WebElement LastName = driver.findElement(By.xpath("//input[@id='last-name']"));
			LastName.sendKeys("xyz");
			
			WebElement PostelCode = driver.findElement(By.xpath("//input[@name='postalCode']"));
			PostelCode.sendKeys("231456");
			
			WebElement Continue = driver.findElement(By.xpath("//input[@id='continue']"));
			Continue.click();
			
			WebElement finish = driver.findElement(By.xpath("//button[@id='finish']"));
			finish.click();
			
			WebElement order = driver.findElement(By.xpath("//div[@class='complete-text']"));
			System.out.println(order.getText());
			
		}else
		{
			System.out.println("Product is not verified from DataBase");
		}
	
//		driver.close();

	}

}
