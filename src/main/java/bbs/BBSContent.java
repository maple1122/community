package bbs;

import base.CommonMethod;
import base.LoginPortal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * @author wufeng
 * @date 2022/4/7 9:38
 */
public class BBSContent extends LoginPortal {

    static WebDriver driver;

    //添加测试版块
    public static void addTopic() throws InterruptedException {
        int count = (int) (1 + Math.random() * 99);
        driver.findElement(By.className("topic-left-add")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//form[@class='el-form']/div[1]/div/div/input")).sendKeys("autoTest" + count);
        driver.findElement(By.xpath("//form[@class='el-form']/div[2]/div/div/input")).sendKeys(System.currentTimeMillis() + "");
        Thread.sleep(200);
        driver.findElement(By.xpath("//div[@class='el-dialog__wrapper']/div/div[@class='el-dialog__footer']/div/button[1]")).click();
        System.out.println("~~~ addTopic()，新建版块，执行成功 ~~~");
    }

    //编辑版块
    public static void editTopic() throws InterruptedException {
        if (getTestTopic()) {
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.className("topic-left-block-active"))).perform();
            Thread.sleep(200);
            driver.findElement(By.xpath("//li[@class='topic-left-block-active']/span[1]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form']/div[2]/div/div/input")).clear();
            driver.findElement(By.xpath("//form[@class='el-form']/div[2]/div/div/input")).sendKeys(System.currentTimeMillis() + "");
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='el-dialog__wrapper']/div/div[@class='el-dialog__footer']/div/button[1]")).click();
            System.out.println("~~~ editTopic()，编辑版块，执行成功 ~~~");
        } else System.out.println("没有自动化测试版块");
        Thread.sleep(3000);
    }

    //删除版块
    public static void delTopic() throws InterruptedException {
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='topic-left']/ul/li"))) {//校验是否有数据
            boolean del = false;
            delAllTestArtical();
            List<WebElement> topics = driver.findElements(By.xpath("//div[@class='topic-left']/ul/li"));
            Actions actions = new Actions(driver);
            if (isTestBBS()) {
                for (int i = topics.size(); i > 0; i--) {
                    actions.moveToElement(driver.findElement(By.xpath("//div[@class='topic-left']/ul/li[" + i + "]"))).perform();
                    Thread.sleep(500);
                    driver.findElement(By.xpath("//div[@class='topic-left']/ul/li[" + i + "]/span[2]")).click();
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
                    Thread.sleep(1000);
                    del = true;
                }
            } else {
                for (int i = topics.size(); i > 0; i--) {
                    if (driver.findElement(By.xpath("//div[@class='topic-left']/ul/li[" + i + "]")).getText().contains("autoTest")) {
                        driver.findElement(By.xpath("//div[@class='topic-left']/ul/li[" + i + "]")).click();
                        Thread.sleep(1000);
                        int page;
                        if (!driver.findElement(By.className("page")).getAttribute("style").contains("display")) {
                            page = Integer.parseInt(driver.findElement(By.xpath("//ul[@class='el-pager']/li[last()]")).getText());
                            for (int p = 0; p < page; p++) {
                                if (CommonMethod.isJudgingElement(driver, By.className("has-gutter"))) {
                                    driver.findElement(By.xpath("//thead[@class='has-gutter']/tr/th[1]/div/label/span/span")).click();
                                    Thread.sleep(500);
                                    driver.findElement(By.cssSelector("span.topic-btn.topic-btn-delete")).click();
                                    Thread.sleep(500);
                                    driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
                                    Thread.sleep(2000);
                                }
                            }
                        }
                        Thread.sleep(1000);
                        actions.moveToElement(driver.findElement(By.xpath("//div[@class='topic-left']/ul/li[" + i + "]"))).perform();
                        Thread.sleep(500);
                        driver.findElement(By.xpath("//li[@class='topic-left-block-active']/span[2]")).click();
                        Thread.sleep(500);
                        driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
                        Thread.sleep(1000);
                        del = true;
                    }
                }
            }
            if (del) System.out.println("~~~ delTopic()，删除测试版块，执行成功 ~~~");
            else System.out.println("没有可删除的auto测试版块");
        } else System.out.println("没有版块");
        Thread.sleep(3000);
    }

    //发表文章
    public static void addArtical() throws InterruptedException {
        getTestTopic();//获取测试版块
        driver.findElement(By.cssSelector("span.topic-btn.topic-btn-publish")).click();//点击发表
        Thread.sleep(500);
        driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[1]/div/div/div")).click();//点击选择虚拟用户
        Thread.sleep(500);
        //选择虚拟用户，有autotest用户则选择，没有autotest用户则默认选第一个，无虚拟用户则关闭发表
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']/li"))) {//校验是否存在虚拟用户
            chooseUser();
            Thread.sleep(500);
            driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[2]/div/div/input")).sendKeys("autoTest-标题-" + System.currentTimeMillis());//录入标题
            driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[3]/div/div/textarea")).sendKeys("autoTest-内容信息-" + System.currentTimeMillis());//录入内容
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='el-dialog__wrapper topic-pub-dialog']/div/div[@class='el-dialog__footer']/div/button[1]")).click();//点击发表
            System.out.println("~~~ addArtical()，发表文章，执行成功 ~~~");
        } else {
            driver.findElement(By.xpath("//div[@class='el-dialog__wrapper topic-pub-dialog']/div/div[@class='el-dialog__footer']/div/button[2]")).click();//没有虚拟用户则点击关闭
            System.out.println("没有可发表的虚拟用户");
        }
        Thread.sleep(3000);
    }

    //编辑文章
    public static void editArtical() throws InterruptedException {
        WebElement tr = getTestArtical();
        Actions actions = new Actions(driver);
        boolean articalStatus = tr.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-detail']/p[2]/span[3]")).getText().equals("已通过");//数据状态是否已通过
        if (articalStatus) {
            actions.moveToElement(tr).perform();
            Thread.sleep(200);
            tr.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-opt']/span[1]")).click();//点击审核
            Thread.sleep(1000);
//            driver.findElement(By.xpath("//div[@class='el-message-box__btns']/button[1]")).click();
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small")).click();
            Thread.sleep(2000);
        }
        actions.moveToElement(tr).perform();//光标悬浮第一条数据
        Thread.sleep(500);
        tr.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-opt']/span[1]")).click();//点击编辑
        Thread.sleep(500);
        driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[2]/div/div/input")).clear();//清空标题
        driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[2]/div/div/input")).sendKeys("autoTest-标题-编辑-" + System.currentTimeMillis());//录入标题
        driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[3]/div/div/textarea")).clear();//清空内容
        driver.findElement(By.xpath("//form[@class='el-form topic-pub-form']/div[3]/div/div/textarea")).sendKeys("autoTest-编辑内容信息-" + System.currentTimeMillis());//录入编辑后的内容
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@class='el-dialog__wrapper topic-pub-dialog']/div/div[@class='el-dialog__footer']/div/button[1]")).click();//点击发表
        System.out.println("~~~ editArtical()，编辑文章，执行成功 ~~~");
        Thread.sleep(3000);
    }

    //审核文章
    public static void verifyArtical() throws InterruptedException {
        WebElement tr = getTestArtical();
        Actions actions = new Actions(driver);
        boolean articalStatus = tr.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-detail']/p[2]/span[3]")).getText().equals("已通过");//数据状态是否已通过
        actions.moveToElement(tr).perform();//光标悬浮第一条数据
        Thread.sleep(200);
        if (articalStatus) {//已通过状态-审核不通过
            tr.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-opt']/span[1]")).click();//点击审核
            Thread.sleep(500);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small")).click();//点击不通过
            System.out.println("~~~ verifyArtical()，审核不通过，执行成功 ~~~");
        } else {//未通过状态-审核通过
            tr.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-opt']/span[2]")).click();//点击审核
            Thread.sleep(500);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();//点击通过
            System.out.println("~~~ verifyArtical()，审核通过，执行成功 ~~~");
        }
        Thread.sleep(3000);
    }

    //置顶文章
    public static void topArtical() throws InterruptedException {
        WebElement tr = getTestArtical();
        Actions actions = new Actions(driver);
        boolean articalStatus = tr.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-detail']/p[2]/span[3]")).getText().equals("已通过");//数据状态是否已通过
        boolean istop = CommonMethod.isJudgingElement(tr, By.xpath("//table[@class='el-table__body']/tbody/tr[1]/td[2]/div/div/div/p/span"));
        //如果是审核不通过状态，需要先审核通过
        if (!articalStatus) {
            actions.moveToElement(tr).perform();//光标悬浮数据
            Thread.sleep(200);
            driver.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-opt']/span[2]")).click();//点击审核
            Thread.sleep(500);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();//点击通过
            Thread.sleep(500);
        }
        actions.moveToElement(tr).perform();//光标悬浮数据
        Thread.sleep(200);
        tr.findElement(By.xpath("td[2]/div/div/div/div[@class='topic-table-opt']/span[2]")).click();//点击置顶
        istop = !istop;//置顶或取消置顶
        Thread.sleep(200);
        if (istop) System.out.println("~~~ topArtical()，文章置顶，执行成功 ~~~");
        else System.out.println("~~~ topArtical()，文章取消置顶，执行成功 ~~~");
        Thread.sleep(3000);
    }

    //删除所有auto测试版块下的文章
    public static void delAllTestArtical() throws InterruptedException {
        if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='topic-left']/ul/li"))) {//校验是否有数据
            int page;
            boolean del = false;
            //如果是autoTest论坛，则删除该论坛下所有版块的稿件
            if (driver.findElement(By.xpath("//div[@class='float-right']/div[2]/div/span")).getText().contains("autoTest")) {//校验当前是否是autoTest论坛，如果不是
                List<WebElement> topic = driver.findElements(By.xpath("//div[@class='topic-left']/ul/li"));//版块列表
                for (int i = 0; i < topic.size(); i++) {
                    topic.get(i).click();
                    Thread.sleep(500);
                    if (!driver.findElement(By.className("page")).getAttribute("style").contains("display")) {
                        page = Integer.parseInt(driver.findElement(By.xpath("//ul[@class='el-pager']/li[last()]")).getText());
                        for (int p = 0; p < page; p++) {
                            if (CommonMethod.isJudgingElement(driver, By.className("has-gutter"))) {
                                driver.findElement(By.xpath("//thead[@class='has-gutter']/tr/th[1]/div/label/span/span")).click();
                                Thread.sleep(200);
                                driver.findElement(By.cssSelector("span.topic-btn.topic-btn-delete")).click();
                                Thread.sleep(500);
                                driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
                                Thread.sleep(2000);
                                del = true;
                            }
                        }
                    }
                }
            } else {//如果不是autoTest论坛，则删除当前论坛第一个版块中，标题有autoTest的稿件，没有则不删
                driver.findElement(By.xpath("//div[@class='topic-keyword el-input']/input")).clear();
                driver.findElement(By.xpath("//div[@class='topic-keyword el-input']/input")).sendKeys("autoTest");
                driver.findElement(By.className("topic-search-btn")).click();
                Thread.sleep(500);
                if (!driver.findElement(By.className("page")).getAttribute("style").contains("display")) {
                    page = Integer.parseInt(driver.findElement(By.xpath("//ul[@class='el-pager']/li[last()]")).getText());
                    for (int p = 0; p < page; p++) {
                        if (CommonMethod.isJudgingElement(driver, By.className("has-gutter"))) {
                            driver.findElement(By.xpath("//thead[@class='has-gutter']/tr/th[1]/div/label/span/span")).click();
                            Thread.sleep(200);
                            driver.findElement(By.cssSelector("span.topic-btn.topic-btn-delete")).click();
                            Thread.sleep(500);
                            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
                            Thread.sleep(2000);
                            del = true;
                        }
                    }
                }
            }
            if (del) System.out.println("~~~ delArtical()，删除文章，执行成功 ~~~");
            else System.out.println("没有可删除的测试文章");
        } else System.out.println("没有版块");
        Thread.sleep(3000);
    }

    //回复
    public static void reply() throws InterruptedException {
        WebElement tr = getTestArtical();
        tr.findElement(By.xpath("td[2]/div/div/div/p")).click();
        driver.findElement(By.cssSelector("button.el-button.topic-detail-reply-btn.el-button--default")).click();
        boolean reply = detailReply();
        if (reply) System.out.println("~~~ reply()，回复帖子，执行成功 ~~~");
        else System.out.println("没有虚拟用户");
        driver.findElement(By.xpath("//div[@class='topic-detail-header']/span")).click();
        Thread.sleep(3000);
    }

    //审核回复
    public static void verifyReply() throws InterruptedException {
        WebElement tr = getTestArtical();
        tr.findElement(By.xpath("td[2]/div/div/div/p")).click();
        Thread.sleep(1000);
        if (!CommonMethod.isJudgingElement(driver, By.className("topic-detail-comment"))) {
            driver.findElement(By.cssSelector("button.el-button.topic-detail-reply-btn.el-button--default")).click();
            detailReply();
        }
        Thread.sleep(1000);
        List<WebElement> replyList = driver.findElements(By.xpath("//div[@class='topic-detail-comment']/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(replyList.get(0)).perform();
        Thread.sleep(500);
        boolean status = replyList.get(0).findElement(By.xpath("div/div[1]/ul/li[1]/span")).getText().equals("已通过");
        if (status) {
            replyList.get(0).findElement(By.xpath("div/div[2]/span[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small")).click();
            System.out.println("~~~ verifyReply()，回复审核通过，执行成功 ~~~");
        } else {
            replyList.get(0).findElement(By.xpath("div/div[2]/span[1]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
            System.out.println("~~~ verifyReply()，回复审核不通过，执行成功 ~~~");
        }
        driver.findElement(By.xpath("//div[@class='topic-detail-header']/span")).click();
        Thread.sleep(3000);
    }

    //对回复进行回复
    public static void reply2() throws InterruptedException {
        WebElement tr = getTestArtical();
        tr.findElement(By.xpath("td[2]/div/div/div/p")).click();
        Thread.sleep(1000);
        if (!CommonMethod.isJudgingElement(driver, By.className("topic-detail-comment"))) detailReply();
        Thread.sleep(1000);
        List<WebElement> replyList = driver.findElements(By.xpath("//div[@class='topic-detail-comment']/div"));
        Actions actions = new Actions(driver);
        boolean status = replyList.get(0).findElement(By.xpath("div/div[1]/ul/li[1]/span")).getText().equals("已通过");
        if (!status) {
            actions.moveToElement(replyList.get(0)).perform();
            Thread.sleep(200);
            replyList.get(0).findElement(By.xpath("div/div[2]/span[1]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
            Thread.sleep(1000);
        }
        actions.moveToElement(replyList.get(0)).perform();
        Thread.sleep(200);
        replyList.get(0).findElement(By.xpath("div/div[2]/span[1]")).click();
        Thread.sleep(500);
        boolean reply = detailReply();
        if (reply) System.out.println("~~~ reply2()，对回复进行回复，执行成功 ~~~");
        else System.out.println("没有虚拟账号");
        driver.findElement(By.xpath("//div[@class='topic-detail-header']/span")).click();
        Thread.sleep(3000);
    }

    //删除回复
    public static void delReply() throws InterruptedException {
        WebElement tr = getTestArtical();
        tr.findElement(By.xpath("td[2]/div/div/div/p")).click();
        Thread.sleep(1000);
        if (CommonMethod.isJudgingElement(driver, By.className("topic-detail-comment"))) {
            List<WebElement> replyList = driver.findElements(By.xpath("//div[@class='topic-detail-comment']/div"));
            Actions actions = new Actions(driver);
            for (int i = replyList.size(); i > 0; i--) {
                actions.moveToElement(driver.findElement(By.xpath("//div[@class='topic-detail-comment']/div[" + i + "]"))).perform();
                Thread.sleep(200);
                driver.findElement(By.xpath("//div[@class='topic-detail-comment']/div[" + i + "]/div/div[2]/span[last()]")).click();
                Thread.sleep(500);
                driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();
                Thread.sleep(1000);
            }
            System.out.println("~~~ delReply()，删除回复，执行成功 ~~~");
        } else System.out.println("没有回复可删除");
        driver.findElement(By.xpath("//div[@class='topic-detail-header']/span")).click();
        Thread.sleep(3000);
    }

    //详情页进行回复
    private static boolean detailReply() throws InterruptedException {
        boolean reply;
        Thread.sleep(500);
        driver.findElement(By.xpath("//form[@class='el-form']/div[1]/div/div")).click();
        Thread.sleep(200);
        //选择虚拟用户，有autotest用户则选择，没有autotest用户则默认选第一个，无虚拟用户则关闭发表
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']/li"))) {//校验是否存在虚拟用户
            chooseUser();
            driver.findElement(By.xpath("//form[@class='el-form']/div[2]/div/div/textarea")).sendKeys("这是autoTest回复内容" + System.currentTimeMillis());
            Thread.sleep(500);
            driver.findElement(By.xpath("//div[@class='el-dialog__footer']/div/button[1]")).click();
            reply = true;
            System.out.println("~~~ reply()，回复帖子，执行成功 ~~~");
        } else {
            driver.findElement(By.xpath("//div[@class='el-dialog__footer']/div/button[2]")).click();
            reply = false;
        }
        Thread.sleep(1000);
        return reply;
    }

    //获取测试帖子
    private static WebElement getTestArtical() throws InterruptedException {
        getTestTopic();
        if (!CommonMethod.isJudgingElement(driver, By.xpath("//table[@class='el-table__body']/tbody/tr"))) addArtical();
        List<WebElement> trs = driver.findElements(By.xpath("//table[@class='el-table__body']/tbody/tr"));//文章列表
        WebElement artical = trs.get(0);//默认取第一条数据
        if (!isTestBBS()) {//如果不是autoTest论坛，先取标题有auto的文章，无则默认第一条
            for (int i = 0; i < trs.size(); i++) {
                if (trs.get(i).findElement(By.xpath("td[2]/div/div/div/p")).getText().contains("auto")) {
                    artical = trs.get(i);
                    break;
                }
            }
        }
        return artical;
    }

    //是否有测试版块，有则激活
    private static boolean getTestTopic() throws InterruptedException {
        boolean hasTestTopic = false;
        if (!driver.findElement(By.xpath("//div[@class='float-right']/div[2]/div/span")).getText().contains("autoTest")) {//校验当前是否是autoTest论坛，如果不是
            if (CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='topic-left']/ul/li"))) {//校验是否有数据
                List<WebElement> topic = driver.findElements(By.xpath("//div[@class='topic-left']/ul/li"));//版块列表
                for (int i = 0; i < topic.size(); i++) {
                    if (topic.get(i).getText().contains("autoTest")) {//版面名称是否包含autoTest
                        topic.get(i).click();//点击autoTest版块
                        Thread.sleep(1000);
                        hasTestTopic = true;
                        break;
                    }
                }
            }
        } else {
            if (!CommonMethod.isJudgingElement(driver, By.xpath("//div[@class='topic-left']/ul/li")))
                addTopic(); //校验是否有数据
            hasTestTopic = true;
        }
        Thread.sleep(1000);
        return hasTestTopic;
    }

    //当前是否是autoTest论坛
    private static boolean isTestBBS() {
        boolean isTestBBS = driver.findElement(By.xpath("//div[@class='float-right']/div[2]/div/span")).getText().contains("autoTest");
        return isTestBBS;
    }

    //选择虚拟用户
    private static void chooseUser() throws InterruptedException {
        //选择虚拟用户，有autotest用户则选择，没有autotest用户则默认选第一个，无虚拟用户则关闭发表
        if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']/li"))) {//校验是否存在虚拟用户
            boolean testUser = false;
            List<WebElement> users = driver.findElements(By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']/li"));//虚拟用户列表
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getText().contains("autoTest")) {
                    testUser = true;
                    users.get(i).click();
                    break;
                }
            }
            if (!testUser) users.get(0).click();//没有autoTest用户，则默认选第一个
        }
        Thread.sleep(1000);
    }

    //初始化登录
    static {
        try {
            driver = login();
            //校验是否已登录成功
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver, By.className("header-user-pack"))) {
                    if (CommonMethod.isJudgingElement(driver, By.className("loginBtn")))
                        driver = login();
                    driver.get(domain + "/bbs/static/index.html#/home");
                    Thread.sleep(2000);
                } else break;
            }
            //切换到测试站点
            driver.findElement(By.xpath("//div[@class='float-right']/div[1]/div")).click();
            Thread.sleep(200);
            List<WebElement> li = driver.findElements(By.xpath("//ul[@class='listParent']/li"));
            for (int i = 0; i < li.size(); i++) {
                if (li.get(i).getText().contains(siteName)) {
                    li.get(i).click();
                    break;
                }
            }
            Thread.sleep(1000);
            //有autoTest论坛则切换到autoTest论坛，无autoTest论坛则使用默认的第一个
            if (!driver.findElement(By.xpath("//div[@class='float-right']/div[2]/div/span")).getText().contains("autoTest")) {//校验当前是否不是autoTest论坛
                driver.findElement(By.xpath("//div[@class='float-right']/div[2]/div")).click();//点击打开选择论坛图层
                Thread.sleep(500);
                driver.findElement(By.xpath("//div[@class='search-pack-input']/div/input")).sendKeys("autoTest");//录入搜索关键词autoTest论坛
                driver.findElement(By.className("search-pack-button")).click();//点击搜索
                Thread.sleep(1000);
                if (CommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='select-list']/li"))) {//校验是都有搜索结果
                    driver.findElement(By.xpath("//ul[@class='select-list']/li[1]")).click();//有结果则点击选中
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("button.el-button.el-button--danger")).click();//点击确定
                } else driver.findElement(By.xpath("//div[@class='footer-pack']/button[2]")).click();//无搜索结果则点击取消
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
