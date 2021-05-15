package com.lilin.education.service.vod.service.impl;


import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lilin.education.common.base.result.ResultCodeEnum;
import com.lilin.education.service.base.exception.EducationException;
import com.lilin.education.service.vod.service.VideoService;
import com.lilin.education.service.vod.util.AliyunVodSDKUtils;
import com.lilin.education.service.vod.util.VodProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;

/**
 * @author helen
 * @since 2020/4/24
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VodProperties vodProperties;

    @Override
    public String uploadVideo(InputStream inputStream, String originalFilename) {

        String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

        UploadStreamRequest request = new UploadStreamRequest(
                vodProperties.getKeyId(),
                vodProperties.getKeySecret(),
                title, originalFilename, inputStream);
        /* 模板组ID(可选) */
//        request.setTemplateGroupId(vodProperties.getTemplateGroupId());
        /* 工作流ID(可选) */
//        request.setWorkflowId(vodProperties.getWorkflowId());

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        String videoId = response.getVideoId();
        if(StringUtils.isEmpty(videoId)){
            log.error("阿里云上传失败：" + response.getCode() + "-" + response.getMessage());
            throw  new EducationException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
        }

        return videoId;
    }

    @Override
    public void removeVideo(String videoId) throws ClientException {
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                vodProperties.getKeyId(),
                vodProperties.getKeySecret());

        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoId);
        client.getAcsResponse(request);
    }

    @Override
    public void removeVideoByIdList(List<String> videoIdList) throws ClientException {
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                vodProperties.getKeyId(),
                vodProperties.getKeySecret());

        DeleteVideoRequest request = new DeleteVideoRequest();
        int size=videoIdList.size();
        StringBuffer idListStr=new StringBuffer();
        for (int i=0;i<size;i++){
            idListStr.append(videoIdList.get(i));
            //支持传入多个视频ID，多个用逗号分隔,id不能超过20个
            if(i==size-1 || i % 20==19){
                //删除
                log.info("idListStr="+idListStr.toString());
                request.setVideoIds(idListStr.toString());
                client.getAcsResponse(request);
                //重置idListStr
                idListStr=new StringBuffer();
            }else if (i % 20 <19){
                idListStr.append(",");
            }

        }


    }

}
