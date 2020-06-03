package cn.infomany.common.domain;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 分页基础参数
 *
 * @author zjb
 */
@Data
public class PageParamDTO {

    @NotNull(message = "分页参数不能为空")
    protected Integer pageIndex;

    @NotNull(message = "每页数量不能为空")
    @Max(value = 200, message = "每页最大为200")
    protected Integer pageSize;

    protected List<OrderItemDTO> orders;
}
