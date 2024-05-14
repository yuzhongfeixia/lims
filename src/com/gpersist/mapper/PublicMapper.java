package com.gpersist.mapper;

import java.util.List;

import com.gpersist.entity.publics.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.log.*;

public interface PublicMapper extends BaseMapper {

    public List<PmtBean> GetPmtSelect(SelectByString params);

    public List<SysPmt> GetListSysPmt(SysPmt params);

    public List<IntPmt> IntPmtByTable(short pmtid);

    public List<ShortPmt> ShortPmtByTable(short pmtid);

    public List<StringPmt> StringPmtByTable(short pmtid);

    public void ExecSQL(String execsql);

    public void SaveTranLog(TranLog log);

    public List<TranLog> SearchTranLog(SearchParams search);

    public List<LoginLog> SearchLoginLog(SearchParams search);
}
