package com.hisense.nettyServer.model.dao.TrafficAccident;

import com.hisense.nettyServer.model.entity.TdTrafficevent;

/**
 * 交通事件数据
 * @author qiush
 *
 */
public interface TdTrafficeventMapper {
    int insert(TdTrafficevent record);

    int insertSelective(TdTrafficevent record);
}