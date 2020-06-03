package cn.infomany.listener;

import cn.infomany.common.constant.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 应用启动以后检测code码
 *
 * @author zhuo
 * @version 1.0
 * @since JDK1.8
 */

@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        ErrorCodeEnum[] values = ErrorCodeEnum.values();
        Map<Integer, ErrorCodeEnum> errorCodeEnumMap = new HashMap<>(100);
        Arrays.stream(values)
                .forEach(codeEnum -> {
                    ErrorCodeEnum codeEnumByMap = errorCodeEnumMap.get(codeEnum.getCode());
                    if (Objects.nonNull(codeEnumByMap)) {
                        log.error("错误码重复：[{}]和[{}]的code都为:{}", codeEnum.name(), codeEnumByMap.name(), codeEnum.getCode());
                        return;
                    }
                    errorCodeEnumMap.put(codeEnum.getCode(), codeEnum);
                });
        if (errorCodeEnumMap.size() != values.length) {
            throw new RuntimeException("错误码里的值允许重复");
        }
    }
}