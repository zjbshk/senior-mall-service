package cn.infomany.handler.mybatisplus;

import cn.infomany.common.anno.TableAuto;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入操作");
        fill(metaObject, FieldFill.INSERT);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新操作");
        fill(metaObject, FieldFill.UPDATE);
    }

    private void fill(MetaObject metaObject, FieldFill fieldFill) {
        Object originalObject = metaObject.getOriginalObject();
        Field[] fields = originalObject.getClass().getDeclaredFields();

        for (Field field : fields) {
            TableAuto tableAuto = field.getAnnotation(TableAuto.class);

            if (Objects.nonNull(tableAuto)) {
                TableField tableFieldAnn = field.getAnnotation(TableField.class);

                // 判断是否有 FieldFill.INSERT_UPDATE
                if (!tableFieldAnn.fill().equals(FieldFill.INSERT_UPDATE)
                        && !tableFieldAnn.fill().equals(fieldFill)) {
                    continue;
                }
                Supplier supplier = applicationContext.getBean(tableAuto.value());
                String name = field.getName();
                this.fillStrategy(metaObject, name, supplier.get());
            }
        }
    }
}