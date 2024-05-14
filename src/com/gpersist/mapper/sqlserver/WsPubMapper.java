package com.gpersist.mapper.sqlserver;

import java.util.List;

import com.gpersist.webservice.entity.*;

import com.gpersist.mapper.BaseMapper;

public interface WsPubMapper extends BaseMapper {

    // region WsApp Methods

    public WsApp GetWsApp(WsApp item);

    public List<WsApp> GetListWsApp();

    public List<WsApp> SearchWsApp(WsApp item);

    public void SaveWsApp(WsApp item);

    // endregion WsApp Methods

    // region WsVisit Methods

    public WsVisit GetWsVisit(WsVisit item);

    public List<WsVisit> SearchWsVisit(WsVisit item);

    public void SaveWsVisit(WsVisit item);

    // endregion WsVisit Methods
}
