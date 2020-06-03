package cn.infomany.common.service;

import cn.infomany.common.dao.BaseDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 通用Service层
 *
 * @author zjb
 * @date 2020/6/2
 */
public class BaseService<K extends BaseDao, M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    @Autowired
    protected K baseDao;

    public K getBaseDao() {
        return baseDao;
    }
}
