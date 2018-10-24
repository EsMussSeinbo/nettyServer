package com.hisense.nettyServer.model.dao.DeviceLog;

import com.hisense.nettyServer.model.entity.TlDevice;

/**
 * 设备日志
 * @author qiush
 *
 */
public interface TlDeviceMapper {
    int insert(TlDevice record);

    int insertSelective(TlDevice record);
}