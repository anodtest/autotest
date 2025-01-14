package com.anodtester.Checkbox_Radio_Dropdown;

import com.anodtester.common.BaseTest;
import com.anodtester.Config.LoggerUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static com.anodtester.common.BaseTest.createBrowser;

public class HandleLogin extends BaseTest {

    @Test
    public void handleRadioButtonsWithClick() throws InterruptedException {

        // 1: Navigate to the website
        createBrowser();
        LoggerUtil.log("Browser created successfully.");
        driver.get("https://ticket.ramanaz.net/login");

        // Login process
        driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("anod");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(6000);

        // Wait for the Security question popup to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-body']")));

        LoggerUtil.log("Security question popup displayed.");
        Thread.sleep(6000);

        // Lấy danh sách các radio buttons trong modal-body
        List<WebElement> radioButtons = driver.findElements(By.xpath("//div[@class='modal-body']//input[@type='radio']"));

        // Lặp qua từng radio button và click
        for (int i = 0; i < radioButtons.size(); i++) {
            // Lấy lại danh sách radio buttons để đảm bảo phần tử không bị stale
            radioButtons = driver.findElements(By.xpath("//div[@class='modal-body']//input[@type='radio']"));
            WebElement radioButton = radioButtons.get(i);

            LoggerUtil.log("Selecting radio button #" + (i + 1));

            // Click radio button
            radioButton.click();
            Thread.sleep(2000); // Chờ một chút sau khi click
        }

        LoggerUtil.log("All radio buttons have been clicked.");

        // Click nút OK trong modal-footer
        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='modal-footer']//button[@type='button']")));
        okButton.click();
        LoggerUtil.log("Clicked the OK button.");

        Thread.sleep(3000);

        // Tiếp tục các thao tác khác sau khi hoàn thành việc xử lý radio buttons
        WebElement buyTicketsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Buy tickets']")));
        buyTicketsButton.click();
        Thread.sleep(3000);

        WebElement vipPurchaseTicketsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='VIP purchase tickets']")));
        vipPurchaseTicketsButton.click();
        Thread.sleep(3000);

        WebElement sellTicketsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='dashboard-tab-withdraw']")));
        sellTicketsButton.click();
        Thread.sleep(3000);

        WebElement vipSaleTicketsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='dashboard-tab-vip-withdraw']")));
        vipSaleTicketsButton.click();
        Thread.sleep(3000);

        WebElement vipSuspendedTicketsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='dashboard-tab-waiting']")));
        vipSuspendedTicketsButton.click();
        Thread.sleep(3000);

        LoggerUtil.log("Test Completed.");
    }

    // Method to capture screenshots in case of failure
    private void captureScreenshot() {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
        // Attach screenshot bytes to the report or save locally as needed
    }
}
