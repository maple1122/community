package newBBS;

import base.CommonMethod;
import base.LoginPortal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author wufeng
 * @date 2022/6/15 15:49
 */
public class TopicManage extends LoginPortal {

    static WebDriver driver;

    //新建话题，需要本地上传封面图片
    public static void addTopic() throws InterruptedException {
        driver.findElement(By.cssSelector("span.btn.btn-red")).click();
        Thread.sleep(500);
        int count = (int) (1 + Math.random() * 9999);
        driver.findElement(By.xpath("//form[@class='el-form']/div[1]/div/div/input")).sendKeys("autoTest" + count);
        //本地上传封面，省略
        driver.findElement(By.xpath("//form[@class='el-form']/div[3]/div/div/textarea")).sendKeys("这是话题autoTest+" + count + "+的简介信息。");
        driver.findElement(By.xpath("//div[@aria-label='新增话题']/div[@class='el-dialog__footer']/span/button[1]")).click();
        System.out.println("~~~ addTopic()，新建话题，执行成功 ~~~");
    }

    //编辑话题
    public static void editTopic() throws InterruptedException {
        if (searchTestTopic()){
            driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr/td[5]/div/button[1]")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("el-textarea__inner")).clear();
            driver.findElement(By.className("el-textarea__inner")).sendKeys("这是自动化测试的话题简介"+System.currentTimeMillis());
            driver.findElement(By.xpath("//div[@class='manage-paper']/div[4]/div/div[@class='el-dialog__footer']/span/button")).click();
            System.out.println("~~~ editTopic()，编辑话题，执行成功 ~~~");
        }else System.out.println("没有autoTest自动化测试话题");
        Thread.sleep(3000);
    }

    //删除话题
    public static void delTopic() throws InterruptedException {
        if (searchTestTopic()){
            driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr/td[5]/div/button[1]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='manage-paper']/div[3]/div/div[@class='el-dialog__footer']/span/button")).click();
            System.out.println("~~~ delTopic()，删除话题，执行成功 ~~~");
        }
    }

    //搜索autoTest话题
    public static boolean searchTestTopic() throws InterruptedException {
        boolean hasTestTopic = false;
        driver.findElement(By.xpath("//div[@class='search-content']/div/input")).clear();
        driver.findElement(By.xpath("//div[@class='search-content']/div/input")).sendKeys("autotest");
        driver.findElement(By.xpath("//div[@class='search-content']/span[1]")).click();
        Thread.sleep(2000);
        if (CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr")))
            hasTestTopic = true;
        return hasTestTopic;
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver, By.className("header-user-pack"))) {
                    if (CommonMethod.isJudgingElement(driver, By.className("loginBtn")))
                        driver = login();
                    driver.get(domain + "/forum/static/index.html#/management");
                    Thread.sleep(2000);
                } else break;
            }
            driver.findElement(By.className("communit-toggle")).click();
            Thread.sleep(200);
            List<WebElement> li = driver.findElements(By.xpath("//ul[@class='listParent']/li"));
            for (int i = 0; i < li.size(); i++) {
                if (li.get(i).getText().contains(siteName)) {
                    li.get(i).click();
                    break;
                }
            }
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
