package cn.infomany.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

/**
 * 该注解用户数据库实体类字段，当操作数据是字段填充字段
 *
 * @author zjb
 * @date 2020/6/2
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableAuto {

    Class<? extends Supplier> value();
}
