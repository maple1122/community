package newBBS;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/16 11:32
 */
public class ForumTest {

    @Test(priority = 1)//发表帖子
    public void testPublish() throws InterruptedException {
        Forum.publish();
    }

    @Test(priority = 2)//帖子置顶或取消置顶
    public void testTop() throws InterruptedException {
        Forum.top();
    }

    @Test(priority = 3)//帖子审核通过或不通过
    public void testCheck() throws InterruptedException {
        Forum.check();
    }

    @Test(priority = 4)//帖子编辑
    public void testEdit() throws InterruptedException {
        Forum.edit();
    }

    @Test(priority = 5)//帖子删除
    public void testDel() throws InterruptedException {
        Forum.del();
    }

    @Test(priority = 6)//帖子批量删除
    public void testALLDel() throws InterruptedException {
        Forum.delAll();
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