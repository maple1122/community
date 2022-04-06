package base;

import org.openqa.selenium.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 公用方法
 * @author wufeng
 * @date 2022/3/25 11:34
 */
public class CommonMethod {

    //图片资源库获取图片文件
    public static void getImg(WebDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.cropSet-button.cropSet-button-primary.online")).click();//上传图片-在线资源库

        Thread.sleep(1000);
        if (!isAlert(driver)) {
            driver.switchTo().frame("material_iframe");//切换到资源库frame进行操作
            Thread.sleep(1000);

            if (!isJudgingElement(driver, By.cssSelector("ul.mtl_nav.clearfix"))) {
                driver.switchTo().defaultContent();
                Thread.sleep(500);
                driver.findElement(By.cssSelector("button.cropSet-button.cropSet-button-primary.online")).click();
                Thread.sleep(1000);
                driver.switchTo().frame("material_iframe");//切换到资源库frame进行操作
                Thread.sleep(1000);
            }
            getPic(driver);
            driver.switchTo().parentFrame();//退出当前iframe
            Thread.sleep(1000);

            driver.findElement(By.cssSelector("button.cropSet-button.ok.save")).click();//保存图片
            Thread.sleep(2000);

            if (isJudgingElement(driver, By.cssSelector("button.cropSet-button.ok.save")))//校验是否还存在保存按钮-未添加图片成功
                driver.findElement(By.cssSelector("button.cropSet-button.cancel")).click();//点击取消关闭图层
        } else {
            closeAlert(driver);//关闭当前alert
            Thread.sleep(500);
            driver.findElement(By.className("cropSet-header__close")).click();//返回到列表页
        }
        Thread.sleep(3000);
    }

    //素材库获取图片及视频
    public static void getPic(WebDriver driver) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread.sleep(3000);
            if (isJudgingElement(driver, By.xpath("//*[@id='cont_1']/div[3]/div/ul/li")))
                break;
        }
        //获取素材数据列表
        List<WebElement> pics = driver.findElements(By.xpath("//*[@id='cont_1']/div[3]/div/ul/li"));

        //判断是否有图片素材，无素材则结束；有素材>则选择第1个图片
        if (pics.size() > 0) {
            driver.findElement(By.xpath("//*[@id='cont_1']/div[3]/div/ul/li[1]/div")).click();//选择第一张图片
            driver.findElement(By.cssSelector("button.mtl_btn.yes")).click();//融媒页确认添加图片返回
        } else {
            System.out.println("没有可用素材！");
            driver.findElement(By.cssSelector("button.mtl_btn.cancel")).click();//融媒页关闭返回
        }
    }

    //素材库获取音频
    public static void getAudio(WebDriver driver) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            Thread.sleep(3000);
            if (isJudgingElement(driver, By.xpath("//ul[@class='mtl_audioList']/li")))
                break;
        }

        //获取素材数据列表
        List<WebElement> pics = driver.findElements(By.xpath("//*[@id='cont_1']/div[3]/div/ul/li"));

        //判断是否有图片素材，无素材则结束；有素材>则选择第1个图片
        if (pics.size() > 0) {
            driver.findElement(By.xpath("//*[@id='cont_1']/div[3]/div/ul/li[1]/input")).click();//选择第一张音频
            driver.findElement(By.cssSelector("button.mtl_btn.yes")).click();//融媒页确认添加音频返回
        } else {
            System.out.println("没有可用素材！");
            driver.findElement(By.cssSelector("button.mtl_btn.cancel")).click();//融媒页关闭返回
        }
    }

    //获取指定测试频道
    public static void getTestTree(WebDriver driver, String id, String channelName) throws InterruptedException {
        List<WebElement> lis1, lis2;
        Boolean isTest = false;
        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            if (isJudgingElement(driver, By.xpath("//ul[@id='" + id + "']/li"))) break;
        }
        lis1 = driver.findElements(By.xpath("//ul[@id='" + id + "']/li"));//一级list
        for (int i = 0; i < lis1.size(); i++) {
            lis2 = lis1.get(i).findElements(By.xpath("ul/li"));//二级list
            for (int j = 0; j < lis2.size(); j++) {
                if (lis2.get(j).findElement(By.xpath("a/span[2]")).getText().equals(channelName)) {//二级中是否有其他频道名称
                    if (!lis2.get(j).findElement(By.xpath("a")).getAttribute("class").contains("curSelectedNode")) {
                        lis2.get(j).findElement(By.xpath("a/span[2]")).click();//点击该频道
                        if (isJudgingElement(lis2.get(j), By.xpath("span[@class='button chk radio_false_full']")))//如果是选择框方式
                            lis2.get(j).findElement(By.xpath("span[@class='button chk radio_false_full']")).click();//点击选择框
                    }
                    isTest = true;
                    break;
                }
            }
            if (isTest) break;
        }
        Thread.sleep(3000);
    }

    //获取默认测试频道“测试test”
    public static void getTestTree(WebDriver driver) throws InterruptedException {
        getTestTree(driver, "columnTree_1_ul", "测试test");
    }

    //签发到测试频道
    public static Boolean getPublishChannel(WebDriver driver, String channelName) throws InterruptedException {
        Boolean selected = false;
        driver.findElement(By.cssSelector("input.layui-input.myKeyword2")).sendKeys(channelName);//录入搜索目标签发频道
        Thread.sleep(200);
        driver.findElement(By.cssSelector("button.layui-btn.layui-btn-primary.search2")).click();//搜索
        Thread.sleep(500);
        List<WebElement> list1, list2;
//        list1 = driver.findElements(By.xpath("//div[@id='xtree']/div/div"));//一级目录
        list1=driver.findElements(By.xpath("//div[@id='SendPushChannel']/div/div"));
        for (int i = 0; i < list1.size(); i++) {
            list1.get(i).getAttribute("style").contains("block");
            list2 = list1.get(i).findElements(By.xpath("div"));//二级目录
            for (int j = 0; j < list2.size(); j++) {
                if (list2.get(j).getAttribute("style").contains("block")) {
                    list2.get(j).findElement(By.xpath("div/i")).click();
                    selected = true;
                    break;
                }
            }
            if (selected) break;
        }
        Thread.sleep(1000);
        return selected;
    }

    //获取自动化测试数据
    public static WebElement getTestArtical(WebDriver driver, int type) throws InterruptedException {

        if (type == 1 || type == 2) {
            if (type == 1 && !isJudgingElement(driver, By.cssSelector("div.accordion.accordionlist.accordion1.opened"))) {//待编区，且未打开状态
                driver.findElement(By.cssSelector("div.accordion.accordionlist.accordion1")).click();//打开待编区
                Thread.sleep(2000);
            }
            if (isJudgingElement(driver, By.xpath("//ul[@id='ulListArea1']/li/div"))) {
                String articalName;
                List<WebElement> articals = driver.findElements(By.xpath("//ul[@id='ulListArea" + type + "']/li"));
                for (int i = 0; i < articals.size(); i++) {
                    articalName = articals.get(i).findElement(By.xpath("div/div/div[@class='article-content']/div/section/a")).getText();
                    if (articalName.contains("autoTest")) {
                        return articals.get(i);
                    }
                }
            }
        } else System.out.println("type传值错误，1为待编区；2为成稿区；其他值无效");
        return null;
    }

    //获取自动化测试数据
    public static WebElement getTestArtical(WebDriver driver, int type, String exclude) throws InterruptedException {

        if (type == 1 || type == 2) {
            if (type == 1 && !isJudgingElement(driver, By.cssSelector("div.accordion.accordionlist.accordion1.opened"))) {//待编区，且未打开状态
                driver.findElement(By.cssSelector("div.accordion.accordionlist.accordion1")).click();//打开待编区
                Thread.sleep(2000);
            }
            if (isJudgingElement(driver, By.xpath("//ul[@id='ulListArea1']/li/div"))) {
                String articalName;
                List<WebElement> articals = driver.findElements(By.xpath("//ul[@id='ulListArea" + type + "']/li"));
                for (int i = 0; i < articals.size(); i++) {
                    articalName = articals.get(i).findElement(By.xpath("div/div/div[@class='article-content']/div/section/a")).getText();
                    if (articalName.contains("autoTest") && !articalName.contains(exclude)) {
                        return articals.get(i);
                    }
                }
            }
        } else System.out.println("type传值错误，1为待编区；2为成稿区；其他值无效");
        return null;
    }

    //切换到功能页面
    public static void changeMenu(WebDriver driver, String type) throws InterruptedException {
        if (!driver.findElement(By.xpath("//ul[@id='headNavMenu']/li[@class='layui-nav-item layui-this']")).getText().equals(type)) {//校验当前是否在目标tab页
            List<WebElement> menu = driver.findElements(By.xpath("//ul[@id='headNavMenu']/li"));//菜单列表
            for (int i = 0; i < menu.size(); i++) {
                if (menu.get(i).getText().equals(type)) {//菜单标题等于其他tab
                    menu.get(i).click();//点击进入该tab页
                    Thread.sleep(2000);
                    break;
                }
            }
        }
    }

    //获取自动化测试数据
    public static void searchTestArticals(WebDriver driver, int type) throws InterruptedException {
        if (type == 1 && !isJudgingElement(driver, By.cssSelector("div.accordion.accordionlist.accordion1.opened"))) {//待编区，且未打开状态
            driver.findElement(By.cssSelector("div.accordion.accordionlist.accordion1")).click();//打开待编区
            Thread.sleep(2000);
        }
        driver.findElement(By.xpath("//form[@id='formDemo']/ul/li[@class='item search']/input")).sendKeys("autoTest");//录入搜索关键词
        Thread.sleep(200);
        driver.findElement(By.xpath("//form[@id='formDemo']/ul/li[@class='item search']/a")).click();//点击搜索
        Thread.sleep(1500);
    }

    //校验元素是否存在
    public static boolean isJudgingElement(WebElement webElement, By by) {
        try {
            webElement.findElement(by);
            return true;//有登录按钮，登录界面
        } catch (Exception e) {
            return false;//无登录按钮，非登录界面
        }
    }

    //校验元素是否能找到
    public static boolean isJudgingElement(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;//有登录按钮，登录界面
        } catch (Exception e) {
            return false;//无登录按钮，非登录界面
        }
    }

    //校验元素有某个属性
    public static boolean isAttribtuePresent(WebElement element, String attribute) {
        try {
            element.getAttribute(attribute);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //判断是否有alert
    public static boolean isAlert(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    //获取alert文案、关闭alert
    public static String closeAlert(WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        System.out.println(text);
        alert.accept();
        return text;
    }

    //拖动元素到指定元素
    public static void moveWebElement(WebDriver driver, WebElement draggable, WebElement target) throws InterruptedException {
        final String java_script =//执行元素拖拽的js。不懂，网上扒下来的~
//                "var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe" +
//                        "ct:'',effectAllowed:'all',files:[],items:{},types:[],setData:fun" +
//                        "ction(format,data){this.items[format]=data;this.types.append(for" +
//                        "mat);},getData:function(format){return this.items[format];},clea" +
//                        "rData:function(format){}};var emit=function(event,target){var ev" +
//                        "t=document.createEvent('Event');evt.initEvent(event,true,false);" +
//                        "evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit('" +
//                        "mousedown',src);emit('dragstart',src);emit('dragenter',tgt);emi" +
//                        "t('dragover',tgt);emit('drop',tgt);emit('dragend',src);emit('mouseup',src);";
        "var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe" +
                "ct:'',effectAllowed:'all',files:[],items:{},types:[],setData:fun" +
                "ction(format,data){this.items[format]=data;this.types.append(for" +
                "mat);},getData:function(format){return this.items[format];},clea" +
                "rData:function(format){}};var emit=function(event,target){var ev" +
                "t=document.createEvent('Event');evt.initEvent(event,true,false);" +
                "evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit('" +
                "dragstart',src);emit('dragenter',tgt);emit('dragover',tgt);emit(" +
                "'drop',tgt);emit('dragend',src);";
//        Actions actions1 = new Actions(driver);
//        actions1.clickAndHold(draggable).perform();
//        System.out.println("draggable:"+draggable.getAttribute("draggable"));
        ((JavascriptExecutor) driver).executeScript(java_script, draggable, target);//执行拖拽js
        Thread.sleep(1000);
    }

    //切换到新标签页
    public static void swichWindow(WebDriver driver){
        Set<String> windowHandles = driver.getWindowHandles();
        WebDriver.TargetLocator targetLocator = driver.switchTo();
        String handle = "";
        for (String windowHandle : windowHandles) {
            handle = windowHandle;
        }
        targetLocator.window(handle);// 切换到对应标签页
    }

//关闭标签页
    public static void closeWindow(WebDriver driver) {

        try {
            String winHandleBefore = driver.getWindowHandle();//关闭当前窗口前，获取当前窗口句柄
            Set<String> winHandles = driver.getWindowHandles();//使用set集合获取所有窗口句柄

            driver.close();//关闭窗口

            Iterator<String> it = winHandles.iterator();//创建迭代器，迭代winHandles里的句柄
            while (it.hasNext()) {//用it.hasNext()判断时候有下一个窗口,如果有就切换到下一个窗口
                String win = it.next();//获取集合中的元素
                if (!win.equals(winHandleBefore)) { //如果此窗口不是关闭前的窗口
                    driver.switchTo().window(win);//切换到新窗口
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
