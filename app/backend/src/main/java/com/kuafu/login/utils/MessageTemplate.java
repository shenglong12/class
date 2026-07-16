package com.kuafu.login.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.kuafu.web.config.SmsConfig;
import org.springframework.stereotype.Component;


@Component
public class MessageTemplate {


    /**
     * 发送短信
     *
     * @param phone
     * @param code
     * @throws Exception
     */
    public void sendMessage(String phone, String code) throws Exception {
        String accessKeyId = SmsConfig.accessKeyId;
        String accessKeySecret = SmsConfig.accessKeySecret;
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        String endpoint = SmsConfig.endpoint;
        config.endpoint = endpoint;
        Client client = new Client(config);
        String signName = SmsConfig.signName;
        String templateCode = SmsConfig.templateCode;
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setTemplateCode(templateCode)
                .setSignName(signName)
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        SendSmsResponseBody body = sendSmsResponse.getBody();
        System.out.println("短信发送结果：" + body.code);//打印结果
        System.out.println("短信发送结果：" + body.message);//打印结果
    }
}

