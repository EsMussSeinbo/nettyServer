package com.hisense.nettyServer.model.dao.TrafficAccident;

import com.hisense.nettyServer.model.entity.TdTrafficeventpic;

/**
 * 交通事件图片
 * @author qiush
 *
 */
public interface TdTrafficeventpicMapper {
    int insert(TdTrafficeventpic record);

    int insertSelective(TdTrafficeventpic record);
}