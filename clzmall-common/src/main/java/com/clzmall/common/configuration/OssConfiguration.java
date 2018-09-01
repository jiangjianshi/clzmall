package com.clzmall.common.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by jiangjianshi on 18/9/1.
 */
@Component
@Data
public class OssConfiguration {

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.upload-endpoint}")
    private String uploadEndpoint;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.download-endpoint}")
    private String downloadEndpoint;
}
