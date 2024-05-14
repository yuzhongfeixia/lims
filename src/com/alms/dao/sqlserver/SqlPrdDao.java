package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.prd.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlPrdDao {

    // region PrdPoisonFile Methods

    public static List<PrdPoisonFile> GetListPrdPoisonFile(SqlSession session, PrdPoisonFile item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdPoisonFile(item);
    }

    public static void SavePrdPoisonFile(SqlSession session, PrdPoisonFile item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdPoisonFile(item);
    }

    // endregion PrdPoisonFile Methods

    // region PrdPoisonDetail Methods

    public static List<PrdPoisonDetail> GetListPrdPoisonDetail(SqlSession session, PrdPoisonDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdPoisonDetail(item);
    }

    public static void SavePrdPoisonDetail(SqlSession session, PrdPoisonDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdPoisonDetail(item);
    }

    // endregion PrdPoisonDetail Methods

    // region PrdPoison Methods

    public static PrdPoison GetPrdPoison(SqlSession session, PrdPoison item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPrdPoison(item);
    }

    public static List<PrdPoison> GetListPrdPoison(SqlSession session, PrdPoison item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdPoison(item);
    }

    public static List<PrdPoison> SearchPrdPoison(SqlSession session, PrdPoison item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdPoison(item);
    }

    public static void SavePrdPoison(SqlSession session, PrdPoison item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdPoison(item);
    }

    // endregion PrdPoison Methods

    // region PrdCodeDetail Methods

    public static PrdCodeDetail GetPrdCodeDetail(SqlSession session, PrdCodeDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPrdCodeDetail(item);
    }

    public static List<PrdCodeDetail> GetListPrdCodeDetail(SqlSession session, PrdCodeDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdCodeDetail(item);
    }

    public static List<PrdCodeDetail> SearchPrdCodeDetail(SqlSession session, PrdCodeDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdCodeDetail(item);
    }

    public static void SavePrdCodeDetail(SqlSession session, PrdCodeDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdCodeDetail(item);
    }

    // endregion PrdCodeDetail Methods

    // region PrdCode Methods

    public static PrdCode GetPrdCodeByPrd(SqlSession session, PrdCode item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPrdCodeByPrd(item);
    }

    public static PrdCode GetPrdCode(SqlSession session, PrdCode item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPrdCode(item);
    }

    public static List<PrdCode> GetListPrdCode(SqlSession session, PrdCode item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdCode(item);
    }

    public static List<PrdCode> SearchPrdCode(SqlSession session, PrdCode item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdCode(item);
    }

    public static List<PrdCode> SearchPrdCodeForLack(SqlSession session, PrdCode item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdCodeForLack(item);
    }

    public static void SavePrdCode(SqlSession session, PrdCode item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdCode(item);
    }

    // endregion PrdCode Methods

    // region StkCheckDetail Methods

    public static List<StkCheckDetail> GetListStkCheckDetail(SqlSession session, StkCheckDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkCheckDetail(item);
    }

    public static void SaveStkCheckDetail(SqlSession session, StkCheckDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SaveStkCheckDetail(item);
    }

    // endregion StkCheckDetail Methods

    // region StkCheck Methods

    public static StkCheck GetStkCheck(SqlSession session, StkCheck item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStkCheck(item);
    }

    public static List<StkCheck> GetListStkCheck(SqlSession session) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkCheck();
    }

    public static List<StkCheck> SearchStkCheck(SqlSession session, StkCheck item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchStkCheck(item);
    }

    public static void SaveStkCheck(SqlSession session, StkCheck item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SaveStkCheck(item);
    }

    // endregion StkCheck Methods

    // region StkOutDetail Methods

    public static List<StkOutDetail> GetListStkOutDetail(SqlSession session, StkOutDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkOutDetail(item);
    }

    public static void SaveStkOutDetail(SqlSession session, StkOutDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SaveStkOutDetail(item);
    }

    public static List<StkOutDetail> GetListStkOutDetailByPrdID(SqlSession session, StkOutDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkOutDetailByPrdID(item);
    }

    // endregion StkOutDetail Methods

    // region StkOut Methods

    public static StkOut GetStkOut(SqlSession session, StkOut item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStkOut(item);
    }

    public static List<StkOut> GetListStkOut(SqlSession session) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkOut();
    }

    public static List<StkOut> SearchStkOut(SqlSession session, StkOut item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchStkOut(item);
    }

    public static List<StkOut> SearchStkOutCount(SqlSession session, StkOut item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchStkOutCount(item);
    }

    public static void SaveStkOut(SqlSession session, StkOut item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SaveStkOut(item);
    }

    // endregion StkOut Methods

    // region StkInDetail Methods

    public static List<StkInDetail> GetListStkInDetail(SqlSession session, StkInDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkInDetail(item);
    }

    public static List<StkInDetail> GetListStkInDetailByPrdID(SqlSession session, StkInDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkInDetailByPrdID(item);
    }

    public static void SaveStkInDetail(SqlSession session, StkInDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SaveStkInDetail(item);
    }

    // endregion StkInDetail Methods

    // region StkIn Methods

    public static StkIn GetStkIn(SqlSession session, StkIn item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStkIn(item);
    }

    public static List<StkIn> GetListStkIn(SqlSession session) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkIn();
    }

    public static List<StkIn> SearchStkIn(SqlSession session, StkIn item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchStkIn(item);
    }

    public static void SaveStkIn(SqlSession session, StkIn item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SaveStkIn(item);
    }

    // endregion StkIn Methods

    // region StkStore Methods

    public static StkStore GetStkStore(SqlSession session, StkStore item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStkStore(item);
    }

    public static List<StkStore> GetListStkStore(SqlSession session, StkStore item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListStkStore(item);
    }

    public static List<StkStore> SearchStkStore(SqlSession session, StkStore item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchStkStore(item);
    }

    public static void SaveStkStore(SqlSession session, StkStore item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SaveStkStore(item);
    }

    // endregion StkStore Methods

    // region PrdWasteDetail Methods

    public static List<PrdWasteDetail> GetListPrdWasteDetail(SqlSession session, PrdWasteDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdWasteDetail(item);
    }

    public static void SavePrdWasteDetail(SqlSession session, PrdWasteDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdWasteDetail(item);
    }

    // endregion PrdWasteDetail Methods

    // region PrdWaste Methods

    public static PrdWaste GetPrdWaste(SqlSession session, PrdWaste item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPrdWaste(item);
    }

    public static List<PrdWaste> GetListPrdWaste(SqlSession session) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdWaste();
    }

    public static List<PrdWaste> SearchPrdWaste(SqlSession session, PrdWaste item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdWaste(item);
    }

    public static void SavePrdWaste(SqlSession session, PrdWaste item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdWaste(item);
    }

    // endregion PrdWaste Methods

    // region PrdVerify Methods

    public static PrdVerify GetPrdVerify(SqlSession session, PrdVerify item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPrdVerify(item);
    }

    public static List<PrdVerify> GetListPrdVerify(SqlSession session) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdVerify();
    }

    public static List<PrdVerify> SearchPrdVerify(SqlSession session, PrdVerify item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdVerify(item);
    }

    public static List<PrdVerify> SearchPrdVerifyForIn(SqlSession session, PrdVerify item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdVerifyForIn(item);
    }

    public static void SavePrdVerify(SqlSession session, PrdVerify item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdVerify(item);
    }

    // endregion PrdVerify Methods

    // region PrdApplyDetail Methods

    public static List<PrdApplyDetail> SearchPrdApplyDetailForVerify(SqlSession session, PrdApplyDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdApplyDetailForVerify(item);
    }

    public static PrdApplyDetail GetPrdApplyDetail(SqlSession session, PrdApplyDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPrdApplyDetail(item);
    }

    public static List<PrdApplyDetail> GetListPrdApplyDetail(SqlSession session, PrdApplyDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdApplyDetail(item);
    }

    // 申请验证的耗材查询
    public static List<PrdApplyDetail> GetListPrdApplyDetailForVerify(SqlSession session, PrdApplyDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdApplyDetailForVerify(item);
    }

    public static void SavePrdApplyDetail(SqlSession session, PrdApplyDetail item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdApplyDetail(item);
    }

    // endregion PrdApplyDetail Methods

    // region BasPrd Methods

    public static BasPrd GetBasPrd(SqlSession session, BasPrd item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasPrd(item);
    }

    public static List<BasPrd> GetListBasPrd(SqlSession session) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListBasPrd();
    }

    public static List<BasPrd> SearchBasPrd(SqlSession session, BasPrd item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchBasPrd(item);
    }

    public static void SaveBasPrd(SqlSession session, BasPrd item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SaveBasPrd(item);
    }

    // endregion BasPrd Methods

    // region PrdApply Methods

    public static PrdApply GetPrdApply(SqlSession session, PrdApply item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPrdApply(item);
    }

    public static List<PrdApply> GetListPrdApply(SqlSession session) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.GetListPrdApply();
    }

    public static List<PrdApply> SearchPrdApply(SqlSession session, PrdApply item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdApply(item);
    }

    // 耗材验证 的耗材申请查询
    public static List<PrdApply> SearchPrdApplyForVerify(SqlSession session, PrdApply item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        return mapper.SearchPrdApplyForVerify(item);
    }

    public static void SavePrdApply(SqlSession session, PrdApply item) {
        com.alms.mapper.sqlserver.PrdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.PrdMapper.class);

        mapper.SavePrdApply(item);
    }

    // endregion PrdApply Methods

}
