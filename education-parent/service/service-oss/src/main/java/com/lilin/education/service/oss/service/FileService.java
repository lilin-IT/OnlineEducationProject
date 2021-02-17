package com.lilin.education.service.oss.service;

import java.io.InputStream;

/**
 * 阿里云oss文件上传
 * module 文件夹名称
 * originalFilename 原始文件名
 */
public interface FileService {
    String upload(InputStream inputStream,String module,String originalFilename);
    /**
     * 阿里云oss文件删除
     * @param url 文件url地址
     */
    void removeFile(String url);

}
