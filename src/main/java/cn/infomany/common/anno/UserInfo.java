package cn.infomany.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于controller层的参数上，会自动将Long类型的参数赋予UserNo
 *
 * @author zjb
 * @date 2020/6/2
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfo {

}
