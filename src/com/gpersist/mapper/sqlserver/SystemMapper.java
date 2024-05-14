package com.gpersist.mapper.sqlserver;

import java.util.List;

import com.gpersist.entity.publics.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.log.*;
import com.gpersist.mapper.BaseMapper;

public interface SystemMapper extends BaseMapper {

    public List<SysSet> GetListSet();

    public List<PmtBean> GetPmtSelect(PmtBean item);

    public SysPmt GetPmt(SysPmt item);

    public List<SysPmt> GetListPmt(SysPmt item);

    public List<IntPmt> IntPmtByTable(short pmtid);

    public List<ShortPmt> ShortPmtByTable(short pmtid);

    public List<StringPmt> StringPmtByTable(short pmtid);

    public void ExecSQL(String execsql);

    public void SaveTranLog(TranLog log);

    public List<TranLog> SearchTranLog(TranLog item);

    public List<LoginLog> SearchLoginLog(LoginLog item);

    public List<JsonSqlColumn> SqlColumn(JsonSqlColumn item);

    public String GetStringValue(String sql);

    public int GetIntValue(String sql);

    public short GetShortValue(String sql);
}
