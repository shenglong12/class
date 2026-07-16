package com.kuafu.web.service;


import com.kuafu.MisAppBackendApplication;
import com.kuafu.orc.service.OcrService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {MisAppBackendApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
public class OcrTest {


    @Autowired
    @Qualifier("LocalOcrService")
    private OcrService ocrService;

    @Test
    public void test1() {
        String filePath = "/profile/111.png";
        String result = ocrService.ocr(filePath);

        log.info("{}", result);
    }
}
