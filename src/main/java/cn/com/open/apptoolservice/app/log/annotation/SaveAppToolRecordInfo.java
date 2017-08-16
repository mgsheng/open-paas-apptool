package cn.com.open.apptoolservice.app.log.annotation;

import java.lang.annotation.*;

/**
 * 记录调用远程接口
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface SaveAppToolRecordInfo {
}
