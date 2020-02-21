package cn.lovehao.backend.controller.exception;

import cn.lovehao.backend.dto.ResponseEntity;
import cn.lovehao.backend.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    /**
     * 处理service 异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public ResponseEntity<String> passwordErrorHandler(ServiceException e){
        return ResponseEntity.error(e.getMessage());
    }


}
