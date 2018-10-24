package com.hisense.nettyServer.model.dao.GeneralIllegal;

import com.hisense.nettyServer.model.entity.TdIllegalevent;

/**
 * 一般违法
 * @author qiush
 *
 */
public interface TdIllegaleventMapper {
    int insert(TdIllegalevent record);

    int insertSelective(TdIllegalevent record);
}