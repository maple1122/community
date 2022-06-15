package newBBS;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/15 17:15
 */
public class TopicManageTest {

//    @Test//需要本地上传封面图，暂不支持
//    public void testAddTopic() throws InterruptedException {
//        TopicManage.addTopic();
//    }

    @Test(priority = 1)
    public void testEditTopic() throws InterruptedException {
        TopicManage.editTopic();
    }

//    @Test(priority = 2)//删除后不支持自动添加，顾先不执行了
//    public void testDelTopic() throws InterruptedException {
//        TopicManage.delTopic();
//    }
}