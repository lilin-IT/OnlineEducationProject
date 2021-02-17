package com.lilin.education.service.base.handler;

import com.lilin.education.common.base.result.R;
import com.lilin.education.common.base.result.ResultCodeEnum;
import com.lilin.education.common.base.util.ExceptionUtils;
import com.lilin.education.service.base.exception.EducationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice //切面，专门处理错误信息
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class) //异常处理器
    @ResponseBody
    public R error(Exception e){
  //      e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return R.error();
    }
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){
    //    e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(HttpMessageNotReadableException e){
     //   e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }
    @ExceptionHandler(EducationException.class)
    @ResponseBody
    public R error(EducationException e){
        //   e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }

}
