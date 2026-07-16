package com.kuafu.oss;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class OssTest {
    //
    @Test
    public void test1() {
        String accessKey = "xxx"; // 华为云 Access Key
        String secretKey = "xxx"; // 华为云 Secret Key
        String endpoint = "https://obs.cn-east-3.myhuaweicloud.com"; // 华为 OBS Endpoint
        String region = "cn-east-3"; // 华为 OBS 区域


        // 3. 初始化 AWS S3 客户端（兼容华为 OBS）
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region)) // 华为区域
                .withClientConfiguration(new ClientConfiguration()
                        .withSignerOverride("S3SignerType")) // 重要！华为 OBS 使用 V2 签名
                .build();

        // 4. 测试：列出 Bucket
        System.out.println("Buckets: " + s3Client.listBuckets());

        //s3Client.putObject("kuafuai", "upload/test.txt", "test111");
    }
}
