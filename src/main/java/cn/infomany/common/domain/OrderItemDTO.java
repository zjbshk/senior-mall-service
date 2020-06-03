package cn.infomany.common.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义排序类
 *
 * @author: zjb
 */

@Slf4j
@Data
public class OrderItemDTO {
    private String column;
    private Boolean order;
}
