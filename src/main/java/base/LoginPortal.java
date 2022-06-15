package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 登录
 *
 * @author wufeng
 * @date 2022/3/25 11:33
 */
public class LoginPortal {

    public static String domain = env().get(0);//获取域名
    public static String siteName = env().get(1);//获取站点名

    static WebDriver driver = initDriver();

    //浏览器初始化
    public static WebDriver initDriver() {
        System.setProperty("webdriver.chrome.driver", "D:\\tools\\other\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("ignore-certificate-errors");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        return driver;
    }

    //登录portal后台
    public static WebDriver login(String username, String password) throws InterruptedException {
        driver.get(domain + "/portal/login");
        //校验是否需要登录
        if (CommonMethod.isJudgingElement(driver, By.className("loginBtn"))) {//是否是登录页
            driver.findElement(By.name("username")).sendKeys(username);//录入用户名
            driver.findElement(By.name("password")).sendKeys(password);//录入密码

            //手动拖动滑块
            Actions action = new Actions(driver);
            WebElement moveButton = driver.findElement(By.className("slide"));
            //移到滑块元素并悬停
            action.moveToElement(moveButton).clickAndHold(moveButton);
            action.dragAndDropBy(moveButton, 305, 0).perform();
            action.release();

            Thread.sleep(2000);
            driver.findElement(By.className("loginBtn")).click();//点击登录
            Thread.sleep(3000);
        }
        return driver;
    }


    //默认wf账号登录
    public static WebDriver login() throws InterruptedException {
        String username = env().get(2);
        String password = env().get(3);
        login(username, password);
        return driver;
    }

    //获取环境配置
    public static List<String> env() {

        String envString ="envtest";//测试环境
//        String envString = "envyanshi";//演示环境

        Properties pro = new Properties();
        InputStream prois;

        List<String> envlist = new ArrayList<>();
        try {
            prois = new FileInputStream("application.properties");
            pro.load(new InputStreamReader(prois,"UTF-8"));

            String domain = (String) pro.getProperty(envString + ".domain");
            String siteName = (String) pro.getProperty(envString + ".siteName");
            String username = (String) pro.getProperty(envString + ".username");
            String password = (String) pro.getProperty(envString + ".password");

            envlist.add(domain);
            envlist.add(siteName);
            envlist.add(username);
            envlist.add(password);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return envlist;
    }
}
