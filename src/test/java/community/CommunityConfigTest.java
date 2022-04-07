package community;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 *
 * @author wufeng
 * @date 2022/4/6 10:00
 */
public class CommunityConfigTest {

    @Test(priority = 1)//添加测试区
    public void testAddDistrict() throws InterruptedException {
        CommunityConfig.addDistrict();
    }

    @Test(priority = 10)//删除测试区
    public void testDelDistrict() throws InterruptedException {
        CommunityConfig.delDistrict();
    }

    @Test(priority = 2)//添加社区
    public void testAddCommunity() throws InterruptedException {
        CommunityConfig.addCommunity();
    }

    @Test(priority = 3)//编辑社区
    public void testEditCommunity() throws InterruptedException {
        CommunityConfig.editCommunity();
    }

    @Test(priority = 9)//删除社区
    public void testDeleteCommunity() throws InterruptedException {
        CommunityConfig.deleteCommunity();
    }

    @Test(priority = 4)//添加版块
    public void testAddChannel() throws InterruptedException {
        CommunityConfig.addChannel(1);
        CommunityConfig.addChannel(2);
    }

    @Test(priority = 5)//编辑版块
    public void testEditChannel() throws InterruptedException {
        CommunityConfig.editChannel();
    }

    @Test(priority = 6)//删除版块
    public void testDeleteChannel() throws InterruptedException {
        CommunityConfig.deleteChannel();
    }

    @Test(priority = 7)//添加运营人员
    public void testAddOperational() throws InterruptedException {
        CommunityConfig.addOperational();
    }

    @Test(priority = 8)//删除运营人员
    public void testDelOperational() throws InterruptedException {
        CommunityConfig.delOperational();
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