package cn.infomany.handler.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 自动生成编号
 * 配合{@link cn.infomany.common.anno.TableAuto}使用
 *
 * @author zjb
 */
@Component
public class AutoGenerateNo implements Supplier<Object> {

    @Autowired
    private Sequence snowflake;

    @Override
    public Object get() {
        return snowflake.nextId();
    }
}
