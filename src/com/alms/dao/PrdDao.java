package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlPrdDao;
import com.alms.entity.prd.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class PrdDao {

    // region PrdPoisonFile Methods

    public static List<PrdPoisonFile> GetListPrdPoisonFile(PrdPoisonFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdPoisonFile>();
            default:
                return SqlPrdDao.GetListPrdPoisonFile(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdPoisonFile>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdPoisonFile(SqlSession session, PrdPoisonFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdPoisonFile(session, item);
            break;
        }
    }

    // endregion PrdPoisonFile Methods

    // region PrdPoisonDetail Methods

    public static List<PrdPoisonDetail> GetListPrdPoisonDetail(PrdPoisonDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdPoisonDetail>();
            default:
                return SqlPrdDao.GetListPrdPoisonDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdPoisonDetail>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdPoisonDetail(SqlSession session, PrdPoisonDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdPoisonDetail(session, item);
            break;
        }
    }

    // endregion PrdPoisonDetail Methods

    // region PrdPoison Methods

    public static PrdPoison GetPrdPoison(PrdPoison item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdPoison(session, item);
        } catch (Exception e) {
            return new PrdPoison();
        } finally {
            session.close();
        }
    }

    public static PrdPoison GetPrdPoison(SqlSession session, PrdPoison item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new PrdPoison();
        default:
            return SqlPrdDao.GetPrdPoison(session, item);
        }
    }

    public static List<PrdPoison> GetListPrdPoison(PrdPoison item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListPrdPoison(session, item);
        } catch (Exception e) {
            return new ArrayList<PrdPoison>();
        } finally {
            session.close();
        }
    }

    public static List<PrdPoison> GetListPrdPoison(SqlSession session, PrdPoison item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdPoison>();
        default:
            return SqlPrdDao.GetListPrdPoison(session, item);
        }
    }

    public static List<PrdPoison> SearchPrdPoison(PrdPoison item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdPoison>();
            default:
                return SqlPrdDao.SearchPrdPoison(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdPoison>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdPoison(SqlSession session, PrdPoison item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdPoison(session, item);
            break;
        }
    }

    // endregion PrdPoison Methods

    // region PrdCodeDetail Methods

    public static PrdCodeDetail GetPrdCodeDetail(PrdCodeDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdCodeDetail(session, item);
        } catch (Exception e) {
            return new PrdCodeDetail();
        } finally {
            session.close();
        }
    }

    public static PrdCodeDetail GetPrdCodeDetail(SqlSession session, PrdCodeDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new PrdCodeDetail();
        default:
            return SqlPrdDao.GetPrdCodeDetail(session, item);
        }
    }

    public static List<PrdCodeDetail> GetListPrdCodeDetail(PrdCodeDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListPrdCodeDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<PrdCodeDetail>();
        } finally {
            session.close();
        }
    }

    public static List<PrdCodeDetail> GetListPrdCodeDetail(SqlSession session, PrdCodeDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdCodeDetail>();
        default:
            return SqlPrdDao.GetListPrdCodeDetail(session, item);
        }
    }

    public static List<PrdCodeDetail> SearchPrdCodeDetail(PrdCodeDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdCodeDetail>();
            default:
                return SqlPrdDao.SearchPrdCodeDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdCodeDetail>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdCodeDetail(SqlSession session, PrdCodeDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdCodeDetail(session, item);
            break;
        }
    }

    // endregion PrdCodeDetail Methods

    // region PrdCode Methods

    public static PrdCode GetPrdCodeByPrd(PrdCode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new PrdCode();
            default:
                return SqlPrdDao.GetPrdCodeByPrd(session, item);
            }
        } catch (Exception e) {
            return new PrdCode();
        } finally {
            session.close();
        }
    }

    public static PrdCode GetPrdCode(PrdCode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdCode(session, item);
        } catch (Exception e) {
            return new PrdCode();
        } finally {
            session.close();
        }
    }

    public static PrdCode GetPrdCode(SqlSession session, PrdCode item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new PrdCode();
        default:
            return SqlPrdDao.GetPrdCode(session, item);
        }
    }

    public static List<PrdCode> GetListPrdCode(PrdCode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListPrdCode(session, item);
        } catch (Exception e) {
            return new ArrayList<PrdCode>();
        } finally {
            session.close();
        }
    }

    public static List<PrdCode> GetListPrdCode(SqlSession session, PrdCode item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdCode>();
        default:
            return SqlPrdDao.GetListPrdCode(session, item);
        }
    }

    public static List<PrdCode> SearchPrdCode(PrdCode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdCode>();
            default:
                return SqlPrdDao.SearchPrdCode(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdCode>();
        } finally {
            session.close();
        }
    }

    public static List<PrdCode> SearchPrdCodeForLack(PrdCode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdCode>();
            default:
                return SqlPrdDao.SearchPrdCodeForLack(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdCode>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdCode(SqlSession session, PrdCode item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdCode(session, item);
            break;
        }
    }

    // endregion PrdCode Methods

    // region StkCheckDetail Methods

    public static List<StkCheckDetail> GetListStkCheckDetail(StkCheckDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkCheckDetail>();
            default:
                return SqlPrdDao.GetListStkCheckDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkCheckDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveStkCheckDetail(SqlSession session, StkCheckDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SaveStkCheckDetail(session, item);
            break;
        }
    }

    // endregion StkCheckDetail Methods

    // region StkCheck Methods

    public static StkCheck GetStkCheck(StkCheck item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStkCheck(session, item);
        } catch (Exception e) {
            return new StkCheck();
        } finally {
            session.close();
        }
    }

    public static StkCheck GetStkCheck(SqlSession session, StkCheck item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StkCheck();
        default:
            return SqlPrdDao.GetStkCheck(session, item);
        }
    }

    public static List<StkCheck> GetListStkCheck() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStkCheck(session);
        } catch (Exception e) {
            return new ArrayList<StkCheck>();
        } finally {
            session.close();
        }
    }

    public static List<StkCheck> GetListStkCheck(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StkCheck>();
        default:
            return SqlPrdDao.GetListStkCheck(session);
        }
    }

    public static List<StkCheck> SearchStkCheck(StkCheck item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkCheck>();
            default:
                return SqlPrdDao.SearchStkCheck(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkCheck>();
        } finally {
            session.close();
        }
    }

    public static void SaveStkCheck(SqlSession session, StkCheck item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SaveStkCheck(session, item);
            break;
        }
    }

    // endregion StkCheck Methods

    // region StkOutDetail Methods

    public static List<StkOutDetail> GetListStkOutDetail(StkOutDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkOutDetail>();
            default:
                return SqlPrdDao.GetListStkOutDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkOutDetail>();
        } finally {
            session.close();
        }
    }

    public static List<StkOutDetail> GetListStkOutDetailByPrdID(StkOutDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkOutDetail>();
            default:
                return SqlPrdDao.GetListStkOutDetailByPrdID(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkOutDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveStkOutDetail(SqlSession session, StkOutDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SaveStkOutDetail(session, item);
            break;
        }
    }

    // endregion StkOutDetail Methods

    // region StkOut Methods

    public static StkOut GetStkOut(StkOut item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStkOut(session, item);
        } catch (Exception e) {
            return new StkOut();
        } finally {
            session.close();
        }
    }

    public static StkOut GetStkOut(SqlSession session, StkOut item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StkOut();
        default:
            return SqlPrdDao.GetStkOut(session, item);
        }
    }

    public static List<StkOut> GetListStkOut() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStkOut(session);
        } catch (Exception e) {
            return new ArrayList<StkOut>();
        } finally {
            session.close();
        }
    }

    public static List<StkOut> GetListStkOut(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StkOut>();
        default:
            return SqlPrdDao.GetListStkOut(session);
        }
    }

    public static List<StkOut> SearchStkOut(StkOut item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkOut>();
            default:
                return SqlPrdDao.SearchStkOut(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkOut>();
        } finally {
            session.close();
        }
    }

    public static void SaveStkOut(SqlSession session, StkOut item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SaveStkOut(session, item);
            break;
        }
    }

    public static List<StkOut> SearchStkOutCount(StkOut item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkOut>();
            default:
                return SqlPrdDao.SearchStkOutCount(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkOut>();
        } finally {
            session.close();
        }
    }

    // endregion StkOut Methods

    // region StkInDetail Methods

    public static List<StkInDetail> GetListStkInDetail(StkInDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkInDetail>();
            default:
                return SqlPrdDao.GetListStkInDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkInDetail>();
        } finally {
            session.close();
        }
    }

    public static List<StkInDetail> GetListStkInDetailByPrdID(StkInDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkInDetail>();
            default:
                return SqlPrdDao.GetListStkInDetailByPrdID(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkInDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveStkInDetail(SqlSession session, StkInDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SaveStkInDetail(session, item);
            break;
        }
    }

    // endregion StkInDetail Methods

    // region StkIn Methods

    public static StkIn GetStkIn(StkIn item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStkIn(session, item);
        } catch (Exception e) {
            return new StkIn();
        } finally {
            session.close();
        }
    }

    public static StkIn GetStkIn(SqlSession session, StkIn item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StkIn();
        default:
            return SqlPrdDao.GetStkIn(session, item);
        }
    }

    public static List<StkIn> GetListStkIn() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStkIn(session);
        } catch (Exception e) {
            return new ArrayList<StkIn>();
        } finally {
            session.close();
        }
    }

    public static List<StkIn> GetListStkIn(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StkIn>();
        default:
            return SqlPrdDao.GetListStkIn(session);
        }
    }

    public static List<StkIn> SearchStkIn(StkIn item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkIn>();
            default:
                return SqlPrdDao.SearchStkIn(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkIn>();
        } finally {
            session.close();
        }
    }

    public static void SaveStkIn(SqlSession session, StkIn item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SaveStkIn(session, item);
            break;
        }
    }

    // endregion StkIn Methods

    // region StkStore Methods

    public static StkStore GetStkStore(StkStore item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStkStore(session, item);
        } catch (Exception e) {
            return new StkStore();
        } finally {
            session.close();
        }
    }

    public static StkStore GetStkStore(SqlSession session, StkStore item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StkStore();
        default:
            return SqlPrdDao.GetStkStore(session, item);
        }
    }

    public static List<StkStore> GetListStkStore(StkStore item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStkStore(session, item);
        } catch (Exception e) {
            return new ArrayList<StkStore>();
        } finally {
            session.close();
        }
    }

    public static List<StkStore> GetListStkStore(SqlSession session, StkStore item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StkStore>();
        default:
            return SqlPrdDao.GetListStkStore(session, item);
        }
    }

    public static List<StkStore> SearchStkStore(StkStore item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StkStore>();
            default:
                return SqlPrdDao.SearchStkStore(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StkStore>();
        } finally {
            session.close();
        }
    }

    public static void SaveStkStore(SqlSession session, StkStore item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SaveStkStore(session, item);
            break;
        }
    }

    // endregion StkStore Methods

    // region PrdWasteDetail Methods

    public static List<PrdWasteDetail> GetListPrdWasteDetail(PrdWasteDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListPrdWasteDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<PrdWasteDetail>();
        } finally {
            session.close();
        }
    }

    public static List<PrdWasteDetail> GetListPrdWasteDetail(SqlSession session, PrdWasteDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdWasteDetail>();
        default:
            return SqlPrdDao.GetListPrdWasteDetail(session, item);
        }
    }

    public static void SavePrdWasteDetail(SqlSession session, PrdWasteDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdWasteDetail(session, item);
            break;
        }
    }

    // endregion PrdWasteDetail Methods

    // region PrdWaste Methods

    public static PrdWaste GetPrdWaste(PrdWaste item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdWaste(session, item);
        } catch (Exception e) {
            return new PrdWaste();
        } finally {
            session.close();
        }
    }

    public static PrdWaste GetPrdWaste(SqlSession session, PrdWaste item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new PrdWaste();
        default:
            return SqlPrdDao.GetPrdWaste(session, item);
        }
    }

    public static List<PrdWaste> GetListPrdWaste() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListPrdWaste(session);
        } catch (Exception e) {
            return new ArrayList<PrdWaste>();
        } finally {
            session.close();
        }
    }

    public static List<PrdWaste> GetListPrdWaste(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdWaste>();
        default:
            return SqlPrdDao.GetListPrdWaste(session);
        }
    }

    public static List<PrdWaste> SearchPrdWaste(PrdWaste item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdWaste>();
            default:
                return SqlPrdDao.SearchPrdWaste(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdWaste>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdWaste(SqlSession session, PrdWaste item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdWaste(session, item);
            break;
        }
    }

    // endregion PrdWaste Methods

    // region PrdVerify Methods

    public static PrdVerify GetPrdVerify(PrdVerify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdVerify(session, item);
        } catch (Exception e) {
            return new PrdVerify();
        } finally {
            session.close();
        }
    }

    public static PrdVerify GetPrdVerify(SqlSession session, PrdVerify item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new PrdVerify();
        default:
            return SqlPrdDao.GetPrdVerify(session, item);
        }
    }

    public static List<PrdVerify> GetListPrdVerify() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListPrdVerify(session);
        } catch (Exception e) {
            return new ArrayList<PrdVerify>();
        } finally {
            session.close();
        }
    }

    public static List<PrdVerify> GetListPrdVerify(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdVerify>();
        default:
            return SqlPrdDao.GetListPrdVerify(session);
        }
    }

    public static List<PrdVerify> SearchPrdVerify(PrdVerify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdVerify>();
            default:
                return SqlPrdDao.SearchPrdVerify(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdVerify>();
        } finally {
            session.close();
        }
    }

    public static List<PrdVerify> SearchPrdVerifyForIn(PrdVerify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdVerify>();
            default:
                return SqlPrdDao.SearchPrdVerifyForIn(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdVerify>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdVerify(SqlSession session, PrdVerify item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdVerify(session, item);
            break;
        }
    }

    // endregion PrdVerify Methods

    // region PrdApplyDetail Methods

    public static List<PrdApplyDetail> SearchPrdApplyDetailForVerify(PrdApplyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdApplyDetail>();
            default:
                return SqlPrdDao.SearchPrdApplyDetailForVerify(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdApplyDetail>();
        } finally {
            session.close();
        }
    }

    public static List<PrdApplyDetail> GetListPrdApplyDetail(PrdApplyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListPrdApplyDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<PrdApplyDetail>();
        } finally {
            session.close();
        }
    }

    public static List<PrdApplyDetail> GetListPrdApplyDetail(SqlSession session, PrdApplyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdApplyDetail>();
        default:
            return SqlPrdDao.GetListPrdApplyDetail(session, item);
        }
    }

    // 申请验证的耗材查询
    public static List<PrdApplyDetail> GetListPrdApplyDetailForVerify(PrdApplyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdApplyDetail>();
            default:
                return SqlPrdDao.GetListPrdApplyDetailForVerify(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdApplyDetail>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdApplyDetail(SqlSession session, PrdApplyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdApplyDetail(session, item);
            break;
        }
    }

    // endregion PrdApplyDetail Methods

    // region PrdApply Methods

    public static PrdApply GetPrdApply(PrdApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdApply(session, item);
        } catch (Exception e) {
            return new PrdApply();
        } finally {
            session.close();
        }
    }

    public static PrdApply GetPrdApply(SqlSession session, PrdApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new PrdApply();
        default:
            return SqlPrdDao.GetPrdApply(session, item);
        }
    }

    public static List<PrdApply> GetListPrdApply() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListPrdApply(session);
        } catch (Exception e) {
            return new ArrayList<PrdApply>();
        } finally {
            session.close();
        }
    }

    public static List<PrdApply> GetListPrdApply(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdApply>();
        default:
            return SqlPrdDao.GetListPrdApply(session);
        }
    }

    public static List<PrdApply> SearchPrdApply(PrdApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdApply>();
            default:
                return SqlPrdDao.SearchPrdApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdApply>();
        } finally {
            session.close();
        }
    }

    // 耗材验证的耗材申请
    public static List<PrdApply> SearchPrdApplyForVerify(PrdApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<PrdApply>();
            default:
                return SqlPrdDao.SearchPrdApplyForVerify(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<PrdApply>();
        } finally {
            session.close();
        }
    }

    public static void SavePrdApply(SqlSession session, PrdApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SavePrdApply(session, item);
            break;
        }
    }

    // endregion PrdApply Methods

    // region BasPrd Methods

    public static BasPrd GetBasPrd(BasPrd item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasPrd(session, item);
        } catch (Exception e) {
            return new BasPrd();
        } finally {
            session.close();
        }
    }

    public static BasPrd GetBasPrd(SqlSession session, BasPrd item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasPrd();
        default:
            return SqlPrdDao.GetBasPrd(session, item);
        }
    }

    public static List<BasPrd> GetListBasPrd() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasPrd(session);
        } catch (Exception e) {
            return new ArrayList<BasPrd>();
        } finally {
            session.close();
        }
    }

    public static List<BasPrd> GetListBasPrd(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasPrd>();
        default:
            return SqlPrdDao.GetListBasPrd(session);
        }
    }

    public static List<BasPrd> SearchBasPrd(BasPrd item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasPrd>();
            default:
                return SqlPrdDao.SearchBasPrd(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasPrd>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasPrd(SqlSession session, BasPrd item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlPrdDao.SaveBasPrd(session, item);
            break;
        }
    }

    // endregion BasPrd Methods

}
