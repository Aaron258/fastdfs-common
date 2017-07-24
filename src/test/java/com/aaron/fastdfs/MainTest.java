package com.aaron.fastdfs;

import com.aaron.fastdfs.exception.FastDFSException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 
* @Description: 测试连接 
* @author: Aaron
* @date: 2017年7月12日 下午7:08:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-fastdfs.xml")
public class MainTest {

    @Resource
    private FastDFSTemplate dfsTemplate;

    @Test
    public void testUploadAndDel() throws FastDFSException {
        FastDfsInfo rv = dfsTemplate.upload("".getBytes(), "txt");
        System.out.println(rv);
        dfsTemplate.deleteFile(rv);
    }

    @Test
    public void testPool() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                File file = new File("/Users/duhuifan/Downloads/edu-demo-fdfs/TestFile/aaron.jpg");
                FileInputStream fis = new FileInputStream(file);
                byte[] b = new byte[fis.available()];
                fis.read(b);
                Map<String, String> map = new HashMap<>();
                FastDfsInfo rv = dfsTemplate.upload(b, "jpg", map);
                System.out.println(rv.getFileAbsolutePath());
                //dfsTemplate.deleteFile(rv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 1; i++) {
            new Thread(runnable).start();
        }
        Thread.currentThread().join();
    }

}
