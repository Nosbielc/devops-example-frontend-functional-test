package nosbielc.com.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author Cleibson Gomes (https://github.com/Nosbielc) ON 25/05/2020
 * @project devops-example-frontend-functional-test
 */
public class TaskTest {

    public WebDriver acessarDriver() throws MalformedURLException {
//        WebDriver driver = new ChromeDriver();  //Drive basico
        URL remoteAddress;
        Capabilities desiredCapabilities = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(
                new URL("http://192.168.1.106:4444/wd/hub"), desiredCapabilities);
        driver.navigate().to("http://192.168.1.106:7000/tasks/");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarDriver();
        try {

            driver.findElement(By.id("addTodo")).click(); //Clicando no bottão Add

            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.now().plusDays(2);
            driver.findElement(By.id("dueDate")).sendKeys(data.format(formatter));

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Success!", message);

        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
        WebDriver driver = acessarDriver();
        try {

            driver.findElement(By.id("addTodo")).click(); //Clicando no bottão Add

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.now().plusDays(2);
            driver.findElement(By.id("dueDate")).sendKeys(data.format(formatter));

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the task description", message);

        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
        WebDriver driver = acessarDriver();
        try {

            driver.findElement(By.id("addTodo")).click(); //Clicando no bottão Add

            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the due date", message);

        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
        WebDriver driver = acessarDriver();
        try {

            driver.findElement(By.id("addTodo")).click(); //Clicando no bottão Add

            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.now().minusDays(2);
            driver.findElement(By.id("dueDate")).sendKeys(data.format(formatter));

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Due date must not be in past", message);

        } finally {
            driver.quit();
        }
    }

}
