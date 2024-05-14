package com.gpersist.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.gpersist.dao.*;
import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SystemService {

    // region Pmt Methods

    public static List<PmtSelect> GetPmtSelects(List<String> selects) {
        List<PmtSelect> lists = new ArrayList<PmtSelect>();

        SqlSession session = DBUtils.getFactory();

        try {
            for (String pmtname : selects) {
                PmtSelect ps = new PmtSelect();
                ps.setPmtname(pmtname);
                ps.getPmtdata().clear();
                ps.getPmtdata().addAll(SystemDao.GetPmtSelect(session, pmtname));

                lists.add(ps);
            }
        } catch (Exception e) {
            // do nothing
        } finally {
            session.close();
        }

        return lists;
    }

    public static void SavePmt(String pmttable, String pmtcode, String listdata, ReturnValue rtv) {
        try {
            List<String> sqls = new ArrayList<String>();

            // 需要做表的判断，待考虑
            sqls.add("delete from " + pmttable);

            switch (pmtcode.toUpperCase()) {
            case "P_GET_IPMT":
                List<IntPmt> ipmts = ToolUtils.GetArrayFromJson(listdata, IntPmt.class);
                for (IntPmt intPmt : ipmts) {
                    sqls.add("insert into " + pmttable + " values (" + Integer.toString(intPmt.getId()) + ", '"
                            + intPmt.getName() + "')");
                }
                break;

            case "P_GET_SPMT":
                List<ShortPmt> spmts = ToolUtils.GetArrayFromJson(listdata, ShortPmt.class);
                for (ShortPmt shortPmt : spmts) {
                    sqls.add("insert into " + pmttable + " values (" + Short.toString(shortPmt.getId()) + ", '"
                            + shortPmt.getName() + "')");
                }
                break;

            case "P_GET_TPMT":
                List<StringPmt> tpmts = ToolUtils.GetArrayFromJson(listdata, StringPmt.class);
                for (StringPmt stringPmt : tpmts) {
                    sqls.add("insert into " + pmttable + " values ('" + stringPmt.getId() + "', '" + stringPmt.getName()
                            + "')");
                }
                break;

            default:
                break;
            }

            if (sqls.size() > 0)
                PublicDao.ExecSql(sqls, rtv);

            rtv.SetValues(true, "参数保存成功!", "", false);

        } catch (Exception e) {
            rtv.SetValues(true, ToolUtils.GetErrorMessage(e, "保存出错:"), "", false);
        }
    }

    // endregion Pmt Methods

}
