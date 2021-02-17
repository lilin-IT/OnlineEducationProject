package com.lilin.education.service.edu.feign;

import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.feign.fallback.OssFileServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 若service-oss中的方法不可以，调用OssFileServiceFallback中的方法
 */
@Service
@FeignClient(value = "service-oss",fallback = OssFileServiceFallback.class)
public interface OssFileService {
    @DeleteMapping("/admin/oss/file/remove")
    R removeFile(@RequestBody String url);

}
