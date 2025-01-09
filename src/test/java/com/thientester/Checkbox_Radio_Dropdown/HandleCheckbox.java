package com.thientester.Checkbox_Radio_Dropdown;

import com.thientester.common.BaseTest;
import com.thientester.Config.LoggerUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class HandleCheckbox extends BaseTest {

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

        // Xpath của feedback message
        String feedbackXPath = "//div[@class='modal-body']//span";

        boolean isCorrectAnswerFound = false;
        int n = 5; // Giới hạn số lần thử (n)

        // Vòng lặp for để thử n radio buttons
        for (int i = 0; i < n && i < radioButtons.size(); i++) {
            WebElement radioButton = radioButtons.get(i);

            LoggerUtil.log("Selecting radio button #" + (i + 1));

            // Click radio button
            radioButton.click();

            // Chờ feedback message cập nhật
            WebElement feedbackMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(feedbackXPath)));
            String feedbackText = feedbackMessage.getText();
            LoggerUtil.log("Feedback message text: " + feedbackText);

            // Kiểm tra nội dung của feedback message
            if (feedbackText.contains("Your answer is wrong, please select other")) {

            } else if (feedbackText.contains("Congrats, your answer is right")) {
                LoggerUtil.log("Correct answer found.");
                isCorrectAnswerFound = true;
                break; // Thoát vòng lặp khi tìm thấy câu trả lời đúng

            } else {
                LoggerUtil.log("Unexpected feedback: " + feedbackText);
            }
        }


        // Xác thực nếu không tìm thấy câu trả lời đúng
        if (!isCorrectAnswerFound) {
            LoggerUtil.log("Test Failed: No correct answer found within " + n + " attempts.");
            captureScreenshot();
            return;
        }
        Thread.sleep(3000);
        // Đảm bảo rằng phần tử OK Button có thể click được
        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='modal-footer']//button[@type='button']")));
        okButton.click();
        LoggerUtil.log("Clicked the OK button.");

        LoggerUtil.log("Test Completed.");
    }

    // Method to capture screenshots in case of failure
    private void captureScreenshot() {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
        // Attach screenshot bytes to the report or save locally as needed
    }
}
