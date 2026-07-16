package com.kuafu.common.storage.impl;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kuafu.common.file.FileUploadUtils;
import com.kuafu.common.file.MimeTypeUtils;
import com.kuafu.common.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;


@Slf4j
@Service("S3StorageServiceImpl")
@ConditionalOnProperty(name = "storage.type", havingValue = "s3")
public class S3StorageServiceImpl implements StorageService {

    @Value("${message.appId:1}")
    private Integer appId;


    @Value("${storage.s3.access_key:1}")
    private String accessKey;

    @Value("${storage.s3.secret_key:1}")
    private String secretKey;

    @Value("${storage.s3.endpoint:1}")
    private String endpoint;

    @Value("${storage.s3.domain:1}")
    private String domain;

    @Value("${storage.s3.region:1}")
    private String region;

    @Value("${storage.s3.bucket_name:1}")
    private String bucketName;

    private AmazonS3 s3Client;

    @PostConstruct
    public void init() {
        System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");
        log.info("===========================init s3 : {}, {} ======================", accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region)) // 华为区域
                .withClientConfiguration(new ClientConfiguration()
                        .withSignerOverride("S3SignerType")) // 重要！华为 OBS 使用 V2 签名
                .build();
    }


    @Override
    public String upload(MultipartFile file) {
        try {
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

            String fileName = "profile/" + appId + "/" + FileUploadUtils.extractFilename(file);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            try (InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(bucketName, fileName, inputStream, objectMetadata);
            }

            return domain + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
