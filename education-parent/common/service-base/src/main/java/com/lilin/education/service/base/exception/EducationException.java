package com.lilin.education.service.base.exception;

import com.lilin.education.common.base.result.ResultCodeEnum;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Data
public class EducationException extends RuntimeException{
    private Integer code;

    public EducationException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    public EducationException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code=resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "EducationException{" +
                "code=" + code +
                ",message="+this.getMessage()+
                '}';
    }
}
