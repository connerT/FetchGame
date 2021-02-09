package com.Local.Tests;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FetchExerciseTests {

    WebDriver driver;
    ArrayList<Integer> myInts;
    HashMap<Integer, Integer> fakeMap = new HashMap<>();

    @BeforeMethod
    public void numList() {
        myInts = new ArrayList<>();
        myInts.add(0);
        myInts.add(1);
        myInts.add(2);
        myInts.add(3);
        myInts.add(4);
        myInts.add(5);
        myInts.add(6);
        myInts.add(7);
        myInts.add(8);
    }

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        ChromeOptions chrome_options = new ChromeOptions();
        chrome_options.addArguments("--headless");
        driver = new ChromeDriver(chrome_options);
        driver.get("http://ec2-54-208-152-154.compute-1.amazonaws.com/");
    }

    @AfterMethod
    public void tearDown() {
        myInts.clear();
        fakeMap.clear();
        driver.quit();
    }

    @Test(invocationCount = 10)
    public void testScales() {

        mainLoop:
        {
            for (int i : myInts) {

                // If we have two numbers and one is fake
                if (!fakeMap.isEmpty()) {
                    fakeMap.forEach((k, v) -> myInts.remove(k));
                    fakeMap.forEach((k, v) -> myInts.remove(v));
                    fakeMap.forEach((k, v) -> enterNums(k, myInts.get(0)));
                    if (isFake()) {
                        fakeMap.forEach((k, v) -> clickOnCoin(k));
                    } else {
                        fakeMap.forEach((k, v) -> clickOnCoin(v));
                    }
                    break mainLoop;
                }

                ArrayList<Integer> newInts = (ArrayList<Integer>) myInts.clone();
                newInts.remove(i);

                search:
                {
                    for (int e : newInts) {
                        enterNums(i, e);
                        boolean fake = isFake();
                        if (!fake) {
                            //it's not a fake so no need to test them all
                            break search;
                        } else {
                            //gather the fakes
                            fakeMap.put(i, e);
                            break search;
                        }
                    }
                }
            }
        }
    }

    private void enterNums(int i, int e) {
        getResetButton().click();
        getLeftSquare().sendKeys(String.valueOf(i));
        getRightSquare().sendKeys(String.valueOf(e));
        getWeighButton().click();
    }

    private void clickOnCoin(Integer ii) {
        System.out.println("Found it!");
        System.out.println("The fake bar is '" + ii + "'.");
        WebElement coinButton = driver.findElement(By.id("coin_" + ii));
        coinButton.click();
        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(StringUtils.contains(alert.getText(), "Yay! You find it!"), "The message is: " + alert.getText() + ".");
        alert.accept();
    }

    private boolean isFake() {
        boolean fake = false;

        WebElement gameInfo = driver.findElement(By.className("game-info"));
        List<WebElement> resultList = gameInfo.findElements(By.cssSelector("ol > li"));
        String text = resultList.get(resultList.size() - 1).getText();
        System.out.println(text);

        if (text.contains(">") || text.contains("<")) {
            fake = true;
        }

        return fake;
    }

    private WebElement getRightSquare() {
        return driver.findElement(By.id("right_0"));
    }

    private WebElement getLeftSquare() {
        return driver.findElement(By.id("left_0"));
    }

    private WebElement getWeighButton() {
        return driver.findElement(By.id("weigh"));
    }

    private WebElement getResetButton() {
        return driver.findElement(By.xpath("//button[contains(text(), 'Reset')]"));
    }

}
