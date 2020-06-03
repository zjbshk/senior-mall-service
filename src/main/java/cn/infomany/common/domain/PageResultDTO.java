package cn.infomany.common.domain;

import lombok.Data;

import java.util.List;

/**
 * Page返回对象
 *
 * @Author zjb
 */
@Data
public class PageResultDTO<T> {

    /**
     * 当前页
     */
    private Long pageIndex;

    /**
     * 每页的数量
     */
    private Long pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 结果集
     */
    private List<T> list;

}
