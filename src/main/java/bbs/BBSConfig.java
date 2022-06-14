package bbs;

import base.CommonMethod;
import base.LoginPortal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * 论坛设置
 *
 * @author wufeng
 * @date 2022/4/7 9:38
 */
public class BBSConfig extends LoginPortal {

    static WebDriver driver;

    //添加论坛
    public static void addBBS() throws InterruptedException {
        Boolean hasTestBBS = hasTestBBS();
        if (!hasTestBBS) {//不存在autoTest论坛
            driver.findElement(By.xpath("//div[@class='leftTitle']/img[2]")).click();//点击新建
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[1]/div/div/input")).sendKeys("autoTest论坛");//录入标题
            driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[2]/div/div/input")).sendKeys("autotest");//录入编码
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='top']/div[1]")).click();//点击保存
            System.out.println("~~~ addBBS()，新建测试论坛，执行成功 ~~~");
        } else System.out.println("已经存在autoTest论坛");
        Thread.sleep(3000);
    }

    //编辑论坛
    public static void editBBS() throws InterruptedException {
        Boolean hasTestBBS = hasTestBBS();
        if (hasTestBBS) {//不存在autoTest论坛
            driver.findElement(By.xpath("//div[@class='top']/div[2]")).click();//点击编辑
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[2]/div/div/input")).clear();//清空编码编辑框
            driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[2]/div/div/input")).sendKeys("" + System.currentTimeMillis());//录入编码
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='top']/div[1]")).click();//点击保存
            System.out.println("~~~ editBBS()，编辑论坛，执行成功 ~~~");
        } else System.out.println("不存在autoTest论坛");
        Thread.sleep(3000);
    }

    //删除论坛
    public static void delBBS() throws InterruptedException {
        Boolean hasTestBBS = hasTestBBS();
        if (hasTestBBS) {//不存在autoTest论坛
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='top']/div[3]")).click();//点击删除
            Thread.sleep(1000);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
            System.out.println("~~~ delBBS()，删除论坛，执行成功 ~~~");
        } else System.out.println("不存在autoTest论坛");
        Thread.sleep(3000);
    }

    //是否存在auto论坛
    private static boolean hasTestBBS() throws InterruptedException {
        Boolean hasTestBBS = false;
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='left']/ul/li"))) {//判断是都有论坛数据
            List<WebElement> bbs = driver.findElements(By.xpath("//div[@class='left']/ul/li"));//论坛数据列表
            for (int i = 0; i < bbs.size(); i++) {
                if (bbs.get(i).getText().contains("autoTest")) {//遍历判断是否有autoTest论坛
                    bbs.get(i).click();
                    hasTestBBS = true;
                    break;
                }
            }
        }
        Thread.sleep(1000);
        return hasTestBBS;
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver, By.className("header-user-pack"))) {
                    if (CommonMethod.isJudgingElement(driver, By.className("loginBtn")))
                        driver = login();
                    driver.get(domain + "/bbs/static/index.html#/config");
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
