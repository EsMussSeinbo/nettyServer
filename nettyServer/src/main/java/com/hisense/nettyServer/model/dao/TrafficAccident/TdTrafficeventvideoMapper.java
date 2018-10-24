package com.hisense.nettyServer.model.dao.TrafficAccident;

import com.hisense.nettyServer.model.entity.TdTrafficeventvideo;

/**
 * 交通事件视频
 * @author qiush
 *
 */
public interface TdTrafficeventvideoMapper {
    int insert(TdTrafficeventvideo record);

    int insertSelective(TdTrafficeventvideo record);
}