package nosbielc.com.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author Cleibson Gomes (https://github.com/Nosbielc) ON 25/05/2020
 * @project devops-example-frontend-functional-test
 */
public class TaskTest {

    public WebDriver acessarDriver() {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:7000/tasks/");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() {
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
    public void naoDeveSalvarTarefaSemDescricao() {
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
    public void naoDeveSalvarTarefaSemData() {
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
    public void naoDeveSalvarTarefaComDataPassada() {
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
