package com.anodtester.Checkbox_Radio_Dropdown;

import com.anodtester.Locators.ExLocators.LocatorsCRM;
import com.anodtester.common.BaseTest;
import org.openqa.selenium.By;

public class DemoAddCustomer extends BaseTest {
    public static void main(String[] args) {
        createBrowser();
        driver.get("https://store.onstove.com/en");
        sleep(1);
        driver.findElement(By.xpath(LocatorsCRM.inputEmail)).sendKeys("admin@example.com");
        driver.findElement(By.xpath(LocatorsCRM.inputPassword)).sendKeys("123456");
        driver.findElement(By.xpath(LocatorsCRM.buttonLogin)).click();
        sleep(1);
        driver.findElement(By.xpath(LocatorsCRM.menuCustomers)).click();
        sleep(1);
        driver.findElement(By.xpath(LocatorsCRM.buttonAddNewCustomer)).click();
        //Chọn Group
        sleep(1);
        driver.findElement(By.xpath(LocatorsCRM.dropdownGroups)).click();
        sleep(1);
        String groupName = "VIP";
        driver.findElement(By.xpath(LocatorsCRM.inputSearchGroups)).sendKeys(groupName);
        //Click chọn item group hoặc nhấn ENTER
        //driver.findElement(By.xpath(LocatorsCRM.inputSearchGroups)).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//span[normalize-space()='" + groupName + "']")).click();
        sleep(1);
        driver.findElement(By.xpath(LocatorsCRM.dropdownGroups)).click();

        closeBrowser();
    }
}
