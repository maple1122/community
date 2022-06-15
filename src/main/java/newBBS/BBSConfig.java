package newBBS;

import base.CommonMethod;
import base.LoginPortal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * @author wufeng
 * @date 2022/6/15 9:09
 */
public class BBSConfig extends LoginPortal {

    static WebDriver driver;

    //创建新版块
    public static void addForum() throws InterruptedException {
        Boolean hasTestBBS = hasTestBBS();//获取时候有自动化测试版块
        if (!hasTestBBS) {//如果没有则新增
            driver.findElement(By.xpath("//div[@class='leftTitle']/div[2]/img[last()]")).click();//点击新建icon
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[1]/div/div/input")).sendKeys("autoTest");//录入标题
            driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[2]/div/div/input")).sendKeys("autoTest");//录入编码
            //上传版块logo需要本地上传，不支持
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@class='top-btns']/div[1]")).click();//点击保存
        } else System.out.println("已经存在auto版块");
        Thread.sleep(3000);
    }

    //创建子版块
    public static void addSubForum() throws InterruptedException {
        Boolean hasTestBBS = hasTestBBS();//获取时候有自动化测试版块，如果有则激活
        if (hasTestBBS) {//有测试版块
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.cssSelector("li.listItem.active"))).perform();//光标悬浮在自动化测试版块
            Thread.sleep(100);
            driver.findElement(By.xpath("//li[@class='listItem active']/div/div[2]/img")).click();//点击新建子版块
            Thread.sleep(500);
            int count = (int) (1 + Math.random() * 9999);
            String name = "auto" + count;
            driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[1]/div/div/input")).sendKeys(name);//录入标题
            driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[2]/div/div/input")).sendKeys(name);//录入编码
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='top-btns']/div[1]")).click();//点击保存
            System.out.println("~~~ addSubForum()，添加子版块，执行成功 ~~~");
        } else System.out.println("没有自动化测试版块");
        Thread.sleep(3000);
    }

    //编辑子版块
    public static void editSubForum() throws InterruptedException {
        Boolean hasTestBBS = hasTestBBS();//获取时候有自动化测试版块，如果有则激活
        if (hasTestBBS) {//有测试版块
            if (CommonMethod.isJudgingElement(driver, By.xpath("//li[@class='listItem active']/ul[@class='child-node']"))) {//校验是否有子版块
                driver.findElement(By.xpath("//ul[@class='child-node']/li[1]")).click();//点击激活子版块
                Thread.sleep(500);
                driver.findElement(By.xpath("//div[@class='top-btns']/div[2]")).click();//点击编辑
                Thread.sleep(200);
                int count = (int) (1 + Math.random() * 9999);
                String name = "auto" + count;
                driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[1]/div/div/input")).clear();//清空子版块名称
                driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[1]/div/div/input")).sendKeys(name);//录入子版块名称
                driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[2]/div/div/input")).clear();//清空子版块编码
                driver.findElement(By.xpath("//form[@class='el-form demo-ruleForm']/div[2]/div/div/input")).sendKeys(name);//录入子版块编码
                Thread.sleep(500);
                driver.findElement(By.xpath("//div[@class='top-btns']/div[1]")).click();//点击保存
                System.out.println("~~~ editSubForum()，编辑子版块，执行成功 ~~~");
            } else System.out.println("没有子版块");
        } else System.out.println("没有自动化测试版块");
        Thread.sleep(3000);
    }

    //删除子版块
    public static void delSubForum() throws InterruptedException {
        Boolean hasTestBBS = hasTestBBS();
        if (hasTestBBS) {//有测试版块
            if (CommonMethod.isJudgingElement(driver, By.xpath("//li[@class='listItem active']/ul[@class='child-node']"))) {//校验是否有子版块
                driver.findElement(By.xpath("//ul[@class='child-node']/li[1]")).click();//点击激活子版块
                Thread.sleep(500);
                driver.findElement(By.xpath("//div[@class='top-btns']/div[3]")).click();//点击删除
                Thread.sleep(1000);
                driver.findElement(By.xpath("//div[@class='parent']/div[7]/div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击弹窗的确定
                System.out.println("~~~ delSubForum()，删除子版块，执行成功 ~~~");
            } else System.out.println("没有子版块");
        } else System.out.println("没有自动化测试版块");
    }

    //是否存在auto论坛
    private static boolean hasTestBBS() throws InterruptedException {
        Boolean hasTestBBS = false;
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='left']/ul/li"))) {//判断是都有论坛数据
            List<WebElement> bbs = driver.findElements(By.xpath("//div[@class='left']/ul/li"));//论坛数据列表
            for (int i = 0; i < bbs.size(); i++) {
                if (bbs.get(i).getAttribute("title").contains("autoTest")) {//遍历判断是否有autoTest论坛
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
                    driver.get(domain + "/forum/static/index.html#/config");
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
