package bbs;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/4/7 11:57
 */
public class BBSContentTest {

    @Test(priority = 1)//添加版块
    public void testAddTopic() throws InterruptedException {
        BBSContent.addTopic();
    }

    @Test(priority = 2)//编辑版块
    public void testEditTopic() throws InterruptedException {
        BBSContent.editTopic();
    }

    @Test(priority = 12)//删除版块
    public void testDelTopic() throws InterruptedException {
        BBSContent.delTopic();
    }

    @Test(priority = 3)//新增文章
    public void testAddArtical() throws InterruptedException {
        for (int i = 0; i < 3; i++)
            BBSContent.addArtical();
    }

    @Test(priority = 4)//编辑文章
    public void testEditArtical() throws InterruptedException {
        BBSContent.editArtical();
    }

    @Test(priority = 5)//审核文章
    public void testVerifyArtical() throws InterruptedException {
        BBSContent.verifyArtical();
    }

    @Test(priority = 6)//置顶文章
    public void testTopArtical() throws InterruptedException {
        BBSContent.topArtical();
    }

    @Test(priority = 11)//删除所有测试文章
    public void testDelAllTestArtical() throws InterruptedException {
        BBSContent.delAllTestArtical();
    }

    @Test(priority = 7)//回复帖子
    public void testReply() throws InterruptedException {
        BBSContent.reply();
    }

    @Test(priority = 8)//
    public void testVerifyReply() throws InterruptedException {
        BBSContent.verifyReply();
    }

    @Test(priority = 9)//
    public void testReply2() throws InterruptedException {
        BBSContent.reply2();
    }

    @Test(priority = 10)//
    public void testDelReply() throws InterruptedException {
        BBSContent.delReply();
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