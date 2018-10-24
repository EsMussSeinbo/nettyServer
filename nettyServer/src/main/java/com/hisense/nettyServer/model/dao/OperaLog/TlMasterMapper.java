package com.hisense.nettyServer.model.dao.OperaLog;

import com.hisense.nettyServer.model.entity.TlMaster;

/**
 * 操作日志
 * @author qiush
 *
 */
public interface TlMasterMapper {
    int insert(TlMaster record);

	int insertSelective(TlMaster record);

}