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
 * @date 2022/6/15 17:24
 */
public class Forum extends LoginPortal {

    static WebDriver driver;

    //发表帖子
    public static void publish() throws InterruptedException {

        if (getTestBBS()) {
            driver.findElement(By.cssSelector("span.topic-btn.topic-btn-publish")).click();//点击发表
            Thread.sleep(500);
            WebElement form = driver.findElement(By.cssSelector("form.el-form.topic-pub-form"));//发表帖子图层元素集
            form.findElement(By.xpath("./div[1]/div/div/div/input")).click();//点击用户选择框
            Thread.sleep(200);
            List<WebElement> users = driver.findElements(By.xpath("//div[last()]/div[@class='el-scrollbar']/div[1]/ul/li"));//用户列表
            boolean hasTestUser = false;//是否有测试用户
            for (int i = 0; i < users.size(); i++) {//循环查找
                if (users.get(i).getText().contains("auto")) {//校验用户名称是否有auto
                    users.get(i).click();//有auto则点击
                    hasTestUser = true;//标识有auto用户
                    break;//退出循环
                }
                Thread.sleep(500);
            }
            Thread.sleep(500);
            if (hasTestUser) {
                form.findElement(By.xpath("./div[2]/div/div/div/input")).click();//点击子版块选择框
                Thread.sleep(500);
                if (CommonMethod.isJudgingElement(driver, By.xpath("//div[last()]/div[@class='el-scrollbar']/div/div[1]/ul/li")))//校验是否有子版块
                    driver.findElement(By.xpath("//div[last()]/div[@class='el-scrollbar']/div/div[1]/ul/li[1]")).click();//有子版块则点击
                else form.findElement(By.xpath("./div[2]/div/div/div/input")).click();//无子版块则点击子版块选择框退出选择
                Thread.sleep(500);
                driver.findElement(By.className("grey-button")).click();//点击选择话题
                Thread.sleep(1000);
                driver.findElement(By.xpath("//div[@id='lz-topic']/div[last()]/div/div[2]/div[1]/div/input")).sendKeys("auto");//录入搜索关键词
                driver.findElement(By.xpath("//div[@id='lz-topic']/div[last()]/div/div[2]/div[1]/span[1]")).click();//点击搜索
                Thread.sleep(1000);
                if (CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr"))) {//校验是否有测试话题
                    driver.findElement(By.xpath("//div[@id='lz-topic']/div[last()]/div/div[2]/div[2]/div[3]/table/tbody/tr/td[1]/div/span")).click();//点击勾选
                    Thread.sleep(500);
                    driver.findElement(By.xpath("//div[@id='lz-topic']/div[last()]/div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击确定
                } else
                    driver.findElement(By.xpath("//div[@id='lz-topic']/div[last()]/div/div[@class='el-dialog__footer']/span/button[2]")).click();//点击取消
                Thread.sleep(500);
                driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[4]/div/div/input")).sendKeys("这是自动化测试的帖子标题" + System.currentTimeMillis());//录入标题
                driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[5]/div/div/textarea")).sendKeys("这是自动化测试的帖子内容" + System.currentTimeMillis());//录入内容
                driver.findElement(By.xpath("//div[@class='el-dialog__wrapper topic-pub-dialog']/div/div[@class='el-dialog__footer']/div/button[1]")).click();//点击确定
                System.out.println("~~~ publish()，发表帖子，执行成功 ~~~");
            } else {
                driver.findElement(By.xpath("//div[@class='el-dialog__wrapper topic-pub-dialog']/div/div[@class='el-dialog__footer']/div/button[2]")).click();//点击关闭
                System.out.println("没有自动化测试用户");
            }
        } else System.out.println("没有自动化测试论坛");
    }

    //置顶
    public static void top() throws InterruptedException {
        if (getTestBBS()) {//校验是否有测试论坛
            if (!CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr"))) {//校验测试论坛是否有帖子
                publish();//没有帖子则先发布
                Thread.sleep(1000);
            }
            if (!status()) check();//校验第一条数据是否是未通过状态，未通过则先审核通过
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]"))).perform();//光标悬浮第一条数据
            Thread.sleep(500);
            String type = driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]/td[2]/div/div/div/div[@class='topic-table-opt']/span[1]")).getText();//获取帖子是置顶还是取消置顶操作
            driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]/td[2]/div/div/div/div[@class='topic-table-opt']/span[1]")).click();//点击置顶或取消置顶
            System.out.println("~~~ top()，帖子" + type + "，执行成功 ~~~");
        } else System.out.println("没有自动化测试论坛");
        Thread.sleep(3000);
    }

    //审核
    public static void check() throws InterruptedException {
        if (getTestBBS()) {//校验是否有测试论坛
            if (!CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr"))) {//校验测试论坛是否有帖子
                publish();//没有帖子则先发布
                Thread.sleep(1000);
            }
            String check;
            if (status()) check = "2";//已通过状态则“审核”是操作列表的第2个
            else check = "1";//未通过状态则“审核”是操作列表的第1个
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]"))).perform();//光标悬浮第一条数据
            Thread.sleep(500);
            driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]/td[2]/div/div/div/div[@class='topic-table-opt']/span[" + check + "]")).click();//点击审核操作
            Thread.sleep(2000);
            if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='checkInput el-textarea']/textarea")))//校验是否有未通过原因输入框
                driver.findElement(By.xpath("//div[@class='checkInput el-textarea']/textarea")).sendKeys("这是自动化测试的审核意见" + System.currentTimeMillis());//审核未通过则录入原因
            driver.findElement(By.xpath("//div[@id='lz-topic']/div[5]/div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击确定
            System.out.println("~~~ check()，帖子审核，执行成功 ~~~");
        } else System.out.println("没有自动化测试论坛");
        Thread.sleep(3000);
    }

    //编辑
    public static void edit() throws InterruptedException {
        if (getTestBBS()) {//校验是否有测试论坛
            if (!CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr"))) {//校验测试论坛是否有帖子
                publish();//没有帖子则先发布
                Thread.sleep(1000);
            }
            String edit;
            if (status()) edit = "3";//已通过状态则“编辑”是操作列表的第3个
            else edit = "2";//未通过状态则“编辑”是操作列表的第2个
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]"))).perform();//光标悬浮第一条数据
            Thread.sleep(500);
            driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]/td[2]/div/div/div/div[@class='topic-table-opt']/span[" + edit + "]")).click();//点击编辑操作
            Thread.sleep(1000);
            driver.findElement(By.className("el-textarea__inner")).sendKeys("——update" + System.currentTimeMillis());
            driver.findElement(By.xpath("//div[@class='el-dialog__wrapper topic-pub-dialog']/div/div[@class='el-dialog__footer']/div/button[1]")).click();//点击确定
            System.out.println("~~~ edit()，编辑帖子，执行成功 ~~~");
        } else System.out.println("没有自动化测试论坛");
        Thread.sleep(3000);
    }

    //删除
    public static void del() throws InterruptedException {
        if (getTestBBS()) {//校验是否有测试论坛
            if (!CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr"))) {//校验测试论坛是否有帖子
                publish();//没有帖子则先发布
                Thread.sleep(1000);
            }
            String del;
            if (status()) del = "4";//已通过状态则“删除”是操作列表的第4个
            else del = "3";//未通过状态则“删除”是操作列表的第3个
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]"))).perform();//光标悬浮第一条数据
            Thread.sleep(500);
            driver.findElement(By.xpath("//table[@class='el-table__body']/tbody/tr[1]/td[2]/div/div/div/div[@class='topic-table-opt']/span[" + del + "]")).click();//点击删除操作
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@id='lz-topic']/div[6]/div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击确定
            System.out.println("~~~ del()，删除帖子，执行成功 ~~~");
        } else System.out.println("没有自动化测试论坛");
        Thread.sleep(3000);
    }

    //批量删除
    public static void delAll() throws InterruptedException {
        if (getTestBBS()) {//校验是否有测试论坛
            if (!CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr"))) {//校验测试论坛是否有帖子
                publish();//没有帖子则先发布
                Thread.sleep(1000);
            }
            driver.findElement(By.xpath("//div[@class='topic-right-opt']/span[1]")).click();//点击全选
            driver.findElement(By.xpath("//div[@class='topic-right-opt']/span[2]")).click();//点击删除
            driver.findElement(By.xpath("//div[@id='lz-topic']/div[6]/div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击确定
            System.out.println("~~~ delAll()，批量删除帖子，执行成功 ~~~");
        } else System.out.println("没有自动化测试论坛");
        Thread.sleep(3000);
    }

    //切换到测试模块
    private static boolean getTestBBS() throws InterruptedException {
        boolean getTestBBS = false;

        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='topic-left']/ul/li"))) {//校验左侧是否有论坛
            List<WebElement> elements = driver.findElements(By.xpath("//div[@class='topic-left']/ul/li"));//获取数据列表
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getText().contains("auto")) {//校验是否有auto论坛版块
                    elements.get(i).click();//有auto测试版块则点击激活
                    getTestBBS = true;//返回标识有测试版块
                    break;
                }
                Thread.sleep(500);
            }
        }
        Thread.sleep(3000);
        return getTestBBS;
    }

    //校验第一条帖子状态
    private static boolean status() throws InterruptedException {
        boolean status = CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr[1]/td[2]/div/div/div//div/p[2]/span[@class='pass-check']"));//校验第一条帖子状态是否是已通过
        return status;//返回帖子状态
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver, By.className("header-user-pack"))) {
                    if (CommonMethod.isJudgingElement(driver, By.className("loginBtn")))
                        driver = login();
                    driver.get(domain + "/forum/static/index.html#/home");
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
