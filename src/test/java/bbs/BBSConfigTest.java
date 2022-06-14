package bbs;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/4/7 9:58
 */
public class BBSConfigTest {

    @Test(priority = 1)//添加测试论坛
    public void testAddBBS() throws InterruptedException {
        BBSConfig.addBBS();
    }

    @Test(priority = 2)//编辑测试论坛
    public void testEditBBS() throws InterruptedException {
        BBSConfig.editBBS();
    }

//    @Test(priority = 3)//删除测试论坛
//    public void testDelBBS() throws InterruptedException {
//        BBSConfig.delBBS();
//    }

    @BeforeMethod
    public void testStart(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Test case: "
                + method.getName());
    }

    @AfterMethod
    public void testEnd(Method method) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<< Test End!\n");
    }
}