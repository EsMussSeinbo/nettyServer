package com.hisense.nettyServer.model.dao.TsDevice;

import java.util.List;

import com.hisense.nettyServer.model.entity.TsDevice;

/**
 * 设备基础信息
 * @author qiush
 *
 */

public interface TsDeviceMapper {
    int deleteByPrimaryKey(String deviceid);

    int insert(TsDevice record);

    int insertSelective(TsDevice record);

    List<TsDevice> selectByPrimaryKey();

    int updateByPrimaryKeySelective(TsDevice record);

    int updateByPrimaryKey(TsDevice record);

}