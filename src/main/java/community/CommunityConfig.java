package community;

import base.CommonMethod;
import base.LoginPortal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * @author wufeng
 * @date 2022/4/2 17:03
 */
public class CommunityConfig extends LoginPortal {
    static WebDriver driver;

    //添加区县
    public static void addDistrict() throws InterruptedException {
        Boolean hasAutoTest = hasTestDistrict();
        if (!hasAutoTest) {//测试区不存在
            driver.findElement(By.xpath("//p[@class='title']/span")).click();//点击区县
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form']/div[1]/div/div/input")).sendKeys("autoTest区");//录入名称
            driver.findElement(By.xpath("//div[@class='basic-message']/div[2]/p[1]")).click();//点击保存
            System.out.println("~~~ addDistrict()，新建区县，执行成功 ~~~");
        } else System.out.println("自动化测试区县已经存在");
        Thread.sleep(3000);
    }

    //删除测试区
    public static void delDistrict() throws InterruptedException {
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='select-list-pack']/div"))) {//校验区县是否有数据
            Boolean hasDel = false;
            List<WebElement> dis = driver.findElements(By.xpath("//div[@class='select-list-pack']/div"));//区县数据列表
            if (getCommunity()) deleteCommunity();//测试区有社区则先删除社区
            for (int i = dis.size(); i > 0; i--) {
                if (driver.findElement(By.xpath("//div[@class='select-list-pack']/div[" + i + "]/p")).getText().contains("autoTest")) {//获取autoTest区
                    driver.findElement(By.xpath("//div[@class='select-list-pack']/div[" + i + "]/p")).click();//点击自动化测试区
                    Thread.sleep(500);
                    driver.findElement(By.xpath("//div[@class='basic-message']/div[2]/p[4]")).click();//点击删除
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();//点击确定
                    hasDel = true;
                    Thread.sleep(1000);
                }
            }
            if (hasDel) System.out.println("~~~ delDistrict()，删除测试区，执行成功 ~~~");
            else System.out.println("没有自动化测试的区县");
        } else System.out.println("没有区县数据");
        Thread.sleep(3000);
    }

    //创建社区
    public static void addCommunity() throws InterruptedException {
        if (!hasTestDistrict()) addDistrict();
        Actions actions = new Actions(driver);
        List<WebElement> dis = driver.findElements(By.xpath("//div[@class='select-list-pack']/div"));//区县数据列表
        for (int i = 0; i < dis.size(); i++) {
            if (dis.get(i).findElement(By.xpath("p")).getText().contains("autoTest")) {//有社区的自动化autoTest区县
                int num = dis.get(i).findElements(By.xpath("div/p")).size();//测试区下有几个社区
                actions.moveToElement(dis.get(i)).perform();//光标悬浮测试区
                Thread.sleep(500);
                dis.get(i).findElement(By.xpath("p/span[@class='icon icon-add-community']")).click();//点击添加
                //录入基本信息
                driver.findElement(By.xpath("//form[@class='el-form']/div/div/div/input")).sendKeys("autoTest社区" + (num + 1));//录入社区名称
                driver.findElement(By.xpath("//form[@class='el-form']/div[2]/div/div/div/button")).click();//点击选择地址
                Thread.sleep(500);
                driver.findElement(By.id("mapSearchInput")).sendKeys("人民日报");//录入搜索地址
                driver.findElement(By.cssSelector("span.map-search.activated")).click();//点击搜索
                Thread.sleep(1000);
                driver.findElement(By.cssSelector("div.map-confirm.activated")).click();//点击确定
                Thread.sleep(1000);
                driver.findElement(By.xpath("//form[@class='el-form']/div[last()]/div/div/textarea")).sendKeys("autoTest自动化测试社区" + System.currentTimeMillis());//录入简介
                Thread.sleep(500);
                driver.findElement(By.xpath("//div[@class='button-pack']/p[1]")).click();//点击保存
                System.out.println("~~~ addCommunity()，添加社区，执行成功 ~~~");
                Thread.sleep(3000);
                break;
            }
        }
    }

    //编辑社区
    public static void editCommunity() throws InterruptedException {
        if (getCommunity()) {
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='basic-message']/div[2]/p[3]")).click();//点击编辑
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form']/div[last()]/div/div/textarea")).clear();//清空简介
            driver.findElement(By.xpath("//form[@class='el-form']/div[last()]/div/div/textarea")).sendKeys("autoTest社区-编辑-" + System.currentTimeMillis());//录入编辑后的内容
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='basic-message']/div[2]/p[1]")).click();//点击保存
            System.out.println("~~~ editCommunity()，编辑社区，执行成功 ~~~");
        } else System.out.println("没有可编辑的自动化测试社区");
        Thread.sleep(3000);
    }

    //删除社区
    public static void deleteCommunity() throws InterruptedException {
        Boolean hasdel = false;
        if (hasTestDistrict()) {//校验区县是否有数据
            List<WebElement> dis = driver.findElements(By.xpath("//div[@class='select-list-pack']/div"));//区县数据列表
            int numCom;//区县下社区数量
            for (int i = 0; i < dis.size(); i++) {
                if (dis.get(i).findElement(By.xpath("p")).getText().contains("autoTest") && dis.get(i).getAttribute("class").contains("have-son")) {//有社区的自动化autoTest区县
                    if (!dis.get(i).getAttribute("class").contains("is-show-son"))//如果还未展开
                        dis.get(i).findElement(By.xpath("p/span")).click();//点击展开社区
                    Thread.sleep(500);
                    numCom = dis.get(i).findElements(By.xpath("div/p")).size();//测试区下有几个社区
                    if (numCom > 0) {
                        for (int j = numCom; j > 0; j--) {//遍历该区县的删除所有社区
                            dis.get(i).findElement(By.xpath("div/p[" + j + "]")).click();//点击第j个社区
                            Thread.sleep(500);
                            if (!driver.findElement(By.xpath("//div[@class='tabs']/p[1]")).getAttribute("class").contains("activated")) {
                                driver.findElement(By.xpath("//div[@class='tabs']/p[1]")).click();
                                Thread.sleep(500);
                            }
                            driver.findElement(By.xpath("//div[@class='basic-message']/div[2]/p[4]")).click();//点击删除
                            Thread.sleep(500);
                            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();//确定删除
                            Thread.sleep(1000);
                        }
                        hasdel = true;//删除已执行
                    }
                }
            }
            if (hasdel) System.out.println("~~~ deleteCommunity()，删除社区，执行成功 ~~~");
            else System.out.println("没有可删除的测试社区");
        } else System.out.println("没有测试区县");
        Thread.sleep(3000);
    }

    //新加版块
    public static void addChannel(int type) throws InterruptedException {
        getChannel(driver);
        int num = 0;
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='reporter-pack']/li")))//判断是否已有测试版块
            num = driver.findElements(By.xpath("//ul[@class='reporter-pack']/li")).size();//已有版块数量
        driver.findElement(By.xpath("//div[@class='channel-box']/div[2]/p[1]")).click();//点击新建版块
        Thread.sleep(200);
        driver.findElement(By.xpath("//form[@class='el-form channel-form']/div[1]/div/div/input")).sendKeys("测试版块" + (num + 1));
        if (type == 2) {
            driver.findElement(By.xpath("//form[@class='el-form channel-form']/div[2]/div/div/label[2]/span")).click();//选择论坛
            Thread.sleep(200);
            driver.findElement(By.cssSelector("button.el-button.channel-select.el-button--info.is-plain")).click();//点击选择
            Thread.sleep(500);
            if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='select-list']/li[2]"))) {//如果存在论坛
                driver.findElement(By.xpath("//ul[@class='select-list']/li[last()-1]")).click();//选择最后一个论坛
                Thread.sleep(200);
                driver.findElement(By.xpath("//div[@class='footer-pack']/button[1]")).click();//选择论坛图层点击确定
                Thread.sleep(500);
                driver.findElement(By.xpath("//div[@class='channel-box']/div[@class='el-dialog__wrapper']/div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击确定保存版块
                System.out.println("~~~ addChannel()，新增版块（论坛），执行成功 ~~~");
            } else {
                driver.findElement(By.xpath("//div[@class='footer-pack']/button[2]")).click();//没有论坛数据，则点击取消关闭选择论坛
                Thread.sleep(500);
                driver.findElement(By.xpath("//div[@class='channel-box']/div[@class='el-dialog__wrapper']/div/div[@class='el-dialog__footer']/span/button[2]")).click();//点击取消
                System.out.println("论坛数据源无数据");
            }
        } else {
            driver.findElement(By.cssSelector("button.el-button.channel-select.el-button--info.is-plain")).click();//点击选择数据源
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@class='search-pack-input']/div[1]/input")).sendKeys("测试test");//录入频道搜索关键词
            driver.findElement(By.xpath("//div[@class='search-pack-input']/div[2]")).click();//点击搜索
            Thread.sleep(1000);
            driver.findElement(By.className("el-tree-node__label")).click();//展开测试频道
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='el-tree-node is-focusable']/div/span[2]")).click();//选择测试频道
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='footer-pack']/button[1]")).click();//点击确定
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='channel-box']/div[@class='el-dialog__wrapper']/div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击确定，保存版块
            System.out.println("~~~ addChannel()，新增版块（资讯），执行成功 ~~~");
        }
        Thread.sleep(3000);
    }

    //编辑版块
    public static void editChannel() throws InterruptedException {
        getChannel(driver);
        if (!CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='reporter-pack']/li"))) {//判断是否已有测试版块
            addChannel(1);
            getChannel(driver);
        }
        driver.findElement(By.xpath("//div[@class='channel-box']/div[2]/p[3]")).click();//点击编辑
        Thread.sleep(200);
        driver.findElement(By.xpath("//form[@class='el-form channel-form']/div[1]/div/div/input")).clear();
        driver.findElement(By.xpath("//form[@class='el-form channel-form']/div[1]/div/div/input")).sendKeys("测试版块-编辑");
        driver.findElement(By.xpath("//div[@class='channel-box']/div[@class='el-dialog__wrapper']/div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击确定，保存版块
        System.out.println("~~~ editChannel()，编辑版块，执行成功 ~~~");
        Thread.sleep(3000);
    }

    //删除版块
    public static void deleteChannel() throws InterruptedException {
        if (getCommunity()) {//如果有测试社区打开测试社区
            driver.findElement(By.xpath("//div[@class='tabs']/p[2]")).click();//切换到版块
            Thread.sleep(500);
            if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='reporter-pack']/li"))) {//判断是否已有测试版块
                List<WebElement> channels = driver.findElements(By.xpath("//ul[@class='reporter-pack']/li"));//版块数据list
                int num = channels.size();
                for (int i = 0; i < num; i++) {
                    driver.findElement(By.xpath("//div[@class='channel-box']/div[2]/p[4]")).click();//点击删除
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();//确定删除
                    Thread.sleep(1500);
                }
                System.out.println("~~~ deleteChannel()，删除版块，执行成功 ~~");
            } else System.out.println("没有版块可删除");
        } else System.out.println("没有测试社区");
    }

    //添加运营人员
    public static void addOperational() throws InterruptedException {
        getOperational(driver);//切换到运营人员设置tab
        driver.findElement(By.xpath("//div[@class='operational-box']/div[2]/p[1]")).click();//点击添加
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@class='el-table__body-wrapper is-scrolling-none']/table/tbody/tr[1]/td[1]/div/label/span/span")).click();//选择第一个用户
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@class='operational-box']/div[@class='el-dialog__wrapper']/div/div[@class='el-dialog__footer']/span/button[1]")).click();//确定添加
        System.out.println("~~~ addOperational()，添加运营人员，执行成功 ~~~");
        Thread.sleep(3000);
    }

    //删除运营人员
    public static void delOperational() throws InterruptedException {
        if (getCommunity()) {//如果有测试社区则切换到测试社区
            driver.findElement(By.xpath("//div[@class='tabs']/p[3]")).click();//点击切换到运营人员设置
            Thread.sleep(500);
            if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='el-table__body-wrapper is-scrolling-none']/table/tbody/tr"))) {//判断是否已有测试运营人员
                driver.findElement(By.xpath("//thead[@class='has-gutter']/tr/th[1]/div/label/span/span")).click();//点击全选
                Thread.sleep(200);
                driver.findElement(By.xpath("//div[@class='operational-box']/div[2]/p[2]")).click();//点击删除
                Thread.sleep(500);
                driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();//确定删除
                System.out.println("~~~ delOperational()，删除运营人员，执行成功 ~~~");
            } else System.out.println("没有可删除的运营人员");
        } else System.out.println("没有测试社区");
        Thread.sleep(3000);
    }

    //是否存在测试区
    private static boolean hasTestDistrict() throws InterruptedException {
        Boolean hasTestDistrict = false;
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='select-list-pack']/div"))) {//校验是否有数据
            List<WebElement> dis = driver.findElements(By.xpath("//div[@class='select-list-pack']/div"));//区县数据列表
            for (int i = 0; i < dis.size(); i++) {
                if (dis.get(i).findElement(By.xpath("p")).getText().contains("autoTest")) {//有自动化autoTest区县
                    hasTestDistrict = true;
                    break;
                }
            }
        }
        return hasTestDistrict;
    }

//    //搜索测试区
//    private static void search() throws InterruptedException {
//        driver.findElement(By.xpath("//div[@class='topic-keyword el-input']/input")).clear();//清空搜索关键词
//        driver.findElement(By.xpath("//div[@class='topic-keyword el-input']/input")).sendKeys("autoTest");//录入搜索关键词
//        Thread.sleep(200);
//        driver.findElement(By.xpath("//p[@class='community-search-pack']/i")).click();//点击确定
//        Thread.sleep(500);
//    }

    //打开测试区下的第一个社区
    private static Boolean getCommunity() throws InterruptedException {
        Boolean hasTestCommunity = false;
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='select-list-pack']/div"))) {//校验区县是否有数据
            List<WebElement> dis = driver.findElements(By.xpath("//div[@class='select-list-pack']/div"));//区县数据列表
            for (int i = 0; i < dis.size(); i++) {
                if (dis.get(i).findElement(By.xpath("p")).getText().contains("autoTest") && dis.get(i).getAttribute("class").contains("have-son")) {//有社区的自动化autoTest区县
                    if (!dis.get(i).getAttribute("class").contains("is-show-son"))
                        dis.get(i).findElement(By.xpath("p/span")).click();//点击展开社区
                    Thread.sleep(200);
                    dis.get(i).findElement(By.xpath("div[1]")).click();//点击第一个社区
                    Thread.sleep(1000);
                    hasTestCommunity = true;
                    break;
                }
            }
        }
        return hasTestCommunity;
    }

    //打开测试区第一个社区的版块
    private static void getOperational(WebDriver driver) throws InterruptedException {
        if (!getCommunity()) {
            addCommunity();
            getCommunity();
        }
        driver.findElement(By.xpath("//div[@class='tabs']/p[3]")).click();
        Thread.sleep(500);
    }

    //打开运营人员设置
    private static void getChannel(WebDriver driver) throws InterruptedException {
        if (!getCommunity()) {
            addCommunity();
            getCommunity();
        }
        driver.findElement(By.xpath("//div[@class='tabs']/p[2]")).click();
        Thread.sleep(500);
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver, By.className("header-user-pack"))) {
                    if (CommonMethod.isJudgingElement(driver, By.className("loginBtn"))) driver = login();
                    driver.get(domain + "/community/static/index.html");
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
