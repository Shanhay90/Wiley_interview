package com.wiley.testsClasses;

import com.google.common.io.Files;
import com.wiley.rest.RestServices;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;

public class RestTests {

    @Test
    public void checkResponseAndDelayTime(){
        long expectedDelayTime = 10L;
        long actualDelaytime;
        RestServices restServices = new RestServices("https://httpbin.org");
        Assert.assertEquals("Wrong response status", 200 , restServices.getResponseStatus("/delay/"+ expectedDelayTime));
        actualDelaytime = restServices.sendGetRequest("/delay/"+ expectedDelayTime).getTime()/ 1000L;
        Assert.assertEquals("Wrong delay time", expectedDelayTime, actualDelaytime );
    }


    @Test
    public void checkImage() throws IOException {
        String imageFolder = "src/test/resources/";
        RestServices restServices = new RestServices("https://httpbin.org");
        Assert.assertEquals("Wrong response status", 200 , restServices.getResponseStatus("/image/png"));
        byte[] downloadImage = restServices.getImage("/image/png");
        File expectedImage = new File(imageFolder+"expectedImage.png");
        byte[] expectedByte = Files.toByteArray(expectedImage);
        Assert.assertArrayEquals("Wrong image", expectedByte, downloadImage);
        FileOutputStream outputStream = new FileOutputStream(imageFolder+"downloadImage.png");
        outputStream.write(downloadImage);
        outputStream.close();
        System.out.println("You can check images in directory: "+ imageFolder);
    }

}
