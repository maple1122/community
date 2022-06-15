package newBBS;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/15 14:08
 */
public class BBSConfigTest {

//    @Test//(需要手动上传图片，暂不支持)
//    public void testAddForum() throws InterruptedException {
//        BBSConfig.addForum();
//    }

    @Test(priority = 1)//新建子版块
    public void testAddSubForum() throws InterruptedException {
        BBSConfig.addSubForum();
    }

    @Test(priority = 2)//编辑子版块
    public void testEditSubForm() throws InterruptedException {
        BBSConfig.editSubForum();
    }

    @Test(priority = 3)//删除子版块
    public void testDelSubForm() throws InterruptedException {
        BBSConfig.delSubForum();
    }

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