package cn.com.open.apptoolservice.app.record;

import java.lang.annotation.*;

/**
 * 记录调用远程接口
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface RecordRemoteCall {
}
