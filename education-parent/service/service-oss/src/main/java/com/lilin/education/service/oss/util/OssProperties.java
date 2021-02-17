package com.lilin.education.service.oss.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@ConfigurationProperties(prefix = "aliyun.oss") //从配置文件读取常量
@Component
public class OssProperties {
    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;

}
