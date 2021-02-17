package com.lilin.education.service.edu.feign.fallback;

import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OssFileServiceFallback implements OssFileService {
    @Override
    public R removeFile(String url) {
        log.info("熔断保护！");
        return R.error();
    }
}
