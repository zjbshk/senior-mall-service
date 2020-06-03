package cn.infomany.handler;

import cn.infomany.common.anno.TableAuto;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.support.BiIntFunction;
import com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入操作");
        fill(metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新操作");
        fill(metaObject);
    }

    private void fill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        Field[] fields = originalObject.getClass().getDeclaredFields();

        for (Field field : fields) {
            TableAuto annotation = field.getAnnotation(TableAuto.class);
            if (Objects.nonNull(annotation)) {
                Supplier supplier = applicationContext.getBean(annotation.value());
                String name = field.getName();

                this.fillStrategy(metaObject, name, supplier.get());
            }
        }
    }

}