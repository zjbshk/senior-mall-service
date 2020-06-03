package cn.infomany.handler.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class AutoGenerateNo implements Supplier<Object> {

    @Autowired
    private Sequence snowflake;

    @Override
    public Object get() {
        return snowflake.nextId();
    }
}
