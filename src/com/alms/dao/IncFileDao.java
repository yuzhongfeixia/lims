package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.*;
import com.alms.entity.file.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class IncFileDao {

    // region IncFileDestroyRecord Methods

    public static IncFileDestroyRecord GetIncFileDestroyRecord(IncFileDestroyRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIncFileDestroyRecord(session, item);
        } catch (Exception e) {
            return new IncFileDestroyRecord();
        } finally {
            session.close();
        }
    }

    public static IncFileDestroyRecord GetIncFileDestroyRecord(SqlSession session, IncFileDestroyRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IncFileDestroyRecord();
        default:
            return SqlIncFileDao.GetIncFileDestroyRecord(session, item);
        }
    }

    public static List<IncFileDestroyRecord> SearchIncFileDestroyRecord(IncFileDestroyRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<IncFileDestroyRecord>();
            default:
                return SqlIncFileDao.SearchIncFileDestroyRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<IncFileDestroyRecord>();
        } finally {
            session.close();
        }
    }

    public static void SaveIncFileDestroyRecord(SqlSession session, IncFileDestroyRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveIncFileDestroyRecord(session, item);
            break;
        }
    }

    // endregion IncFileDestroyRecord Methods

    // region IncFilePassword Methods

    public static IncFilePassword GetIncFilePassword(IncFilePassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIncFilePassword(session, item);
        } catch (Exception e) {
            return new IncFilePassword();
        } finally {
            session.close();
        }
    }

    public static IncFilePassword GetIncFilePassword(SqlSession session, IncFilePassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IncFilePassword();
        default:
            return SqlIncFileDao.GetIncFilePassword(session, item);
        }
    }

    public static List<IncFilePassword> GetListIncFilePassword(IncFilePassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListIncFilePassword(session, item);
        } catch (Exception e) {
            return new ArrayList<IncFilePassword>();
        } finally {
            session.close();
        }
    }

    public static List<IncFilePassword> GetListIncFilePassword(SqlSession session, IncFilePassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<IncFilePassword>();
        default:
            return SqlIncFileDao.GetListIncFilePassword(session, item);
        }
    }

    public static List<IncFilePassword> SearchIncFilePassword(IncFilePassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<IncFilePassword>();
            default:
                return SqlIncFileDao.SearchIncFilePassword(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<IncFilePassword>();
        } finally {
            session.close();
        }
    }

    public static void SaveIncFilePassword(SqlSession session, IncFilePassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveIncFilePassword(session, item);
            break;
        }
    }

    // endregion IncFilePassword Methods

    // region IncFileOnline Methods

    public static IncFileOnline GetIncFileOnline(IncFileOnline item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIncFileOnline(session, item);
        } catch (Exception e) {
            return new IncFileOnline();
        } finally {
            session.close();
        }
    }

    public static IncFileOnline GetIncFileOnline(SqlSession session, IncFileOnline item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IncFileOnline();
        default:
            return SqlIncFileDao.GetIncFileOnline(session, item);
        }
    }

    public static List<IncFileOnline> SearchIncFileOnline(IncFileOnline item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<IncFileOnline>();
            default:
                return SqlIncFileDao.SearchIncFileOnline(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<IncFileOnline>();
        } finally {
            session.close();
        }
    }

    public static void SaveIncFileOnline(SqlSession session, IncFileOnline item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveIncFileOnline(session, item);
            break;
        }
    }

    // endregion IncFileOnline Methods

    // region IncFileRegister Methods

    public static IncFileRegister GetIncFileRegister(IncFileRegister item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIncFileRegister(session, item);
        } catch (Exception e) {
            return new IncFileRegister();
        } finally {
            session.close();
        }
    }

    public static IncFileRegister GetIncFileRegister(SqlSession session, IncFileRegister item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IncFileRegister();
        default:
            return SqlIncFileDao.GetIncFileRegister(session, item);
        }
    }

    public static List<IncFileRegister> SearchIncFileRegister(IncFileRegister item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<IncFileRegister>();
            default:
                return SqlIncFileDao.SearchIncFileRegister(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<IncFileRegister>();
        } finally {
            session.close();
        }
    }

    public static void SaveIncFileRegister(SqlSession session, IncFileRegister item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveIncFileRegister(session, item);
            break;
        }
    }

    // endregion IncFileRegister Methods

    // region BasFile Methods

    public static BasFile HasAuthToRead(IncFileOnline item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new BasFile();
            default:
                return SqlIncFileDao.HasAuthToRead(session, item);

            }
        } catch (Exception e) {
            return new BasFile();
        } finally {
            session.close();
        }
    }

    public static BasFile GetBasFile(BasFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasFile(session, item);
        } catch (Exception e) {
            return new BasFile();
        } finally {
            session.close();
        }
    }

    public static BasFile GetBasFile(SqlSession session, BasFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasFile();
        default:
            return SqlIncFileDao.GetBasFile(session, item);
        }
    }

    public static List<BasFile> GetListBasFile(BasFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasFile(session, item);
        } catch (Exception e) {
            return new ArrayList<BasFile>();
        } finally {
            session.close();
        }
    }

    public static List<BasFile> GetListBasFile(SqlSession session, BasFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasFile>();
        default:
            return SqlIncFileDao.GetListBasFile(session, item);
        }
    }

    public static List<BasFile> GetBasFileForNeed(BasFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasFileForNeed(session, item);
        } catch (Exception e) {
            return new ArrayList<BasFile>();
        } finally {
            session.close();
        }
    }

    public static List<BasFile> GetBasFileForNeed(SqlSession session, BasFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasFile>();
        default:
            return SqlIncFileDao.GetBasFileForNeed(session, item);
        }
    }

    public static List<BasFile> SearchBasFile(BasFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasFile>();
            default:
                return SqlIncFileDao.SearchBasFile(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasFile>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasFile(SqlSession session, BasFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveBasFile(session, item);
            break;
        }
    }

    // endregion BasFile Methods

    // region ChangeApply Methods

    public static ChangeApply GetChangeApply(ChangeApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetChangeApply(session, item);
        } catch (Exception e) {
            return new ChangeApply();
        } finally {
            session.close();
        }
    }

    public static ChangeApply GetChangeApply(SqlSession session, ChangeApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ChangeApply();
        default:
            return SqlIncFileDao.GetChangeApply(session, item);
        }
    }

    public static List<ChangeApply> GetListChangeApply(ChangeApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListChangeApply(session, item);
        } catch (Exception e) {
            return new ArrayList<ChangeApply>();
        } finally {
            session.close();
        }
    }

    public static List<ChangeApply> GetListChangeApply(SqlSession session, ChangeApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ChangeApply>();
        default:
            return SqlIncFileDao.GetListChangeApply(session, item);
        }
    }

    public static List<ChangeApply> GetListChangeApplyForNotify(ChangeApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListChangeApplyForNotify(session, item);
        } catch (Exception e) {
            return new ArrayList<ChangeApply>();
        } finally {
            session.close();
        }
    }

    public static List<ChangeApply> GetListChangeApplyForNotify(SqlSession session, ChangeApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ChangeApply>();
        default:
            return SqlIncFileDao.GetListChangeApplyForNotify(session, item);
        }
    }

    public static List<ChangeApply> SearchChangeApply(ChangeApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ChangeApply>();
            default:
                return SqlIncFileDao.SearchChangeApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ChangeApply>();
        } finally {
            session.close();
        }
    }

    public static void SaveChangeApply(SqlSession session, ChangeApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveChangeApply(session, item);
            break;
        }
    }

    // endregion ChangeApply Methods

    // region DestoryApplyDetail Methods

    public static DestoryApplyDetail GetDestoryApplyDetail(DestoryApplyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDestoryApplyDetail(session, item);
        } catch (Exception e) {
            return new DestoryApplyDetail();
        } finally {
            session.close();
        }
    }

    public static DestoryApplyDetail GetDestoryApplyDetail(SqlSession session, DestoryApplyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DestoryApplyDetail();
        default:
            return SqlIncFileDao.GetDestoryApplyDetail(session, item);
        }
    }

    public static List<DestoryApplyDetail> GetListDestoryApplyDetail(DestoryApplyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDestoryApplyDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<DestoryApplyDetail>();
        } finally {
            session.close();
        }
    }

    public static List<DestoryApplyDetail> GetListDestoryApplyDetail(SqlSession session, DestoryApplyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DestoryApplyDetail>();
        default:
            return SqlIncFileDao.GetListDestoryApplyDetail(session, item);
        }
    }

    public static List<DestoryApplyDetail> SearchDestoryApplyDetail(DestoryApplyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DestoryApplyDetail>();
            default:
                return SqlIncFileDao.SearchDestoryApplyDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DestoryApplyDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveDestoryApplyDetail(SqlSession session, DestoryApplyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveDestoryApplyDetail(session, item);
            break;
        }
    }

    // endregion DestoryApplyDetail Methods

    // region destoryapply Methods

    public static DestoryApply GetDestoryApply(DestoryApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDestoryApply(session, item);
        } catch (Exception e) {
            return new DestoryApply();
        } finally {
            session.close();
        }
    }

    public static DestoryApply GetDestoryApply(SqlSession session, DestoryApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DestoryApply();
        default:
            return SqlIncFileDao.GetDestoryApply(session, item);
        }
    }

    public static List<DestoryApply> GetListDestoryApply(DestoryApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDestoryApply(session, item);
        } catch (Exception e) {
            return new ArrayList<DestoryApply>();
        } finally {
            session.close();
        }
    }

    public static List<DestoryApply> GetListDestoryApply(SqlSession session, DestoryApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DestoryApply>();
        default:
            return SqlIncFileDao.GetListDestoryApply(session, item);
        }
    }

    public static List<DestoryApply> SearchDestoryApply(DestoryApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DestoryApply>();
            default:
                return SqlIncFileDao.SearchDestoryApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DestoryApply>();
        } finally {
            session.close();
        }
    }

    public static List<DestoryApply> SearchIncFileDestroyForRecord(DestoryApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DestoryApply>();
            default:
                return SqlIncFileDao.SearchIncFileDestroyForRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DestoryApply>();
        } finally {
            session.close();
        }
    }

    public static void SaveDestoryApply(SqlSession session, DestoryApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveDestoryApply(session, item);
            break;
        }
    }

    // endregion destoryapply Methods

    // region LoanApply Methods

    public static LoanApply GetLoanApply(LoanApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetLoanApply(session, item);
        } catch (Exception e) {
            return new LoanApply();
        } finally {
            session.close();
        }
    }

    public static LoanApply GetLoanApply(SqlSession session, LoanApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new LoanApply();
        default:
            return SqlIncFileDao.GetLoanApply(session, item);
        }
    }

    public static List<LoanApply> GetListLoanApply(LoanApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListLoanApply(session, item);
        } catch (Exception e) {
            return new ArrayList<LoanApply>();
        } finally {
            session.close();
        }
    }

    public static List<LoanApply> GetListLoanApply(SqlSession session, LoanApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<LoanApply>();
        default:
            return SqlIncFileDao.GetListLoanApply(session, item);
        }
    }

    public static List<LoanApply> SearchLoanApply(LoanApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<LoanApply>();
            default:
                return SqlIncFileDao.SearchLoanApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<LoanApply>();
        } finally {
            session.close();
        }
    }

    public static List<LoanApply> SearchLoanApplyForRegister(LoanApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<LoanApply>();
            default:
                return SqlIncFileDao.SearchLoanApplyForRegister(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<LoanApply>();
        } finally {
            session.close();
        }
    }

    public static void SaveLoanApply(SqlSession session, LoanApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveLoanApply(session, item);
            break;
        }
    }

    // endregion LoanApply Methods

    // region SecretApply Methods

    public static SecretApply GetSecretApply(SecretApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSecretApply(session, item);
        } catch (Exception e) {
            return new SecretApply();
        } finally {
            session.close();
        }
    }

    public static SecretApply GetSecretApply(SqlSession session, SecretApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SecretApply();
        default:
            return SqlIncFileDao.GetSecretApply(session, item);
        }
    }

    public static List<SecretApply> GetListSecretApply(SecretApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListSecretApply(session, item);
        } catch (Exception e) {
            return new ArrayList<SecretApply>();
        } finally {
            session.close();
        }
    }

    public static List<SecretApply> GetListSecretApply(SqlSession session, SecretApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SecretApply>();
        default:
            return SqlIncFileDao.GetListSecretApply(session, item);
        }
    }

    public static List<SecretApply> SearchSecretApply(SecretApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SecretApply>();
            default:
                return SqlIncFileDao.SearchSecretApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SecretApply>();
        } finally {
            session.close();
        }
    }

    public static void SaveSecretApply(SqlSession session, SecretApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveSecretApply(session, item);
            break;
        }
    }

    // endregion SecretApply Methods

    // region BasLeak Methods

    public static BasLeak GetBasLeak(BasLeak item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasLeak(session, item);
        } catch (Exception e) {
            return new BasLeak();
        } finally {
            session.close();
        }
    }

    public static BasLeak GetBasLeak(SqlSession session, BasLeak item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasLeak();
        default:
            return SqlIncFileDao.GetBasLeak(session, item);
        }
    }

    public static List<BasLeak> GetListBasLeak(BasLeak item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasLeak(session, item);
        } catch (Exception e) {
            return new ArrayList<BasLeak>();
        } finally {
            session.close();
        }
    }

    public static List<BasLeak> GetListBasLeak(SqlSession session, BasLeak item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasLeak>();
        default:
            return SqlIncFileDao.GetListBasLeak(session, item);
        }
    }

    public static List<BasLeak> SearchBasLeak(BasLeak item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasLeak>();
            default:
                return SqlIncFileDao.SearchBasLeak(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasLeak>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasLeak(SqlSession session, BasLeak item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveBasLeak(session, item);
            break;
        }
    }

    // endregion BasLeak Methods

    // region ChangeNotify Methods

    public static ChangeNotify GetChangeNotify(ChangeNotify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetChangeNotify(session, item);
        } catch (Exception e) {
            return new ChangeNotify();
        } finally {
            session.close();
        }
    }

    public static ChangeNotify GetChangeNotify(SqlSession session, ChangeNotify item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ChangeNotify();
        default:
            return SqlIncFileDao.GetChangeNotify(session, item);
        }
    }

    public static List<ChangeNotify> GetListChangeNotify(ChangeNotify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListChangeNotify(session, item);
        } catch (Exception e) {
            return new ArrayList<ChangeNotify>();
        } finally {
            session.close();
        }
    }

    public static List<ChangeNotify> GetListChangeNotify(SqlSession session, ChangeNotify item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ChangeNotify>();
        default:
            return SqlIncFileDao.GetListChangeNotify(session, item);
        }
    }

    public static List<ChangeNotify> SearchChangeNotify(ChangeNotify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ChangeNotify>();
            default:
                return SqlIncFileDao.SearchChangeNotify(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ChangeNotify>();
        } finally {
            session.close();
        }
    }

    public static void SaveChangeNotify(SqlSession session, ChangeNotify item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveChangeNotify(session, item);
            break;
        }
    }

    // endregion ChangeNotify Methods

    // region ReleFile Methods

    public static ReleFile GetReleFile(ReleFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetReleFile(session, item);
        } catch (Exception e) {
            return new ReleFile();
        } finally {
            session.close();
        }
    }

    public static ReleFile GetReleFile(SqlSession session, ReleFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ReleFile();
        default:
            return SqlIncFileDao.GetReleFile(session, item);
        }
    }

    public static List<ReleFile> GetListReleFile(ReleFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListReleFile(session, item);
        } catch (Exception e) {
            return new ArrayList<ReleFile>();
        } finally {
            session.close();
        }
    }

    public static List<ReleFile> GetListReleFile(SqlSession session, ReleFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ReleFile>();
        default:
            return SqlIncFileDao.GetListReleFile(session, item);
        }
    }

    public static List<ReleFile> SearchReleFile(ReleFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReleFile>();
            default:
                return SqlIncFileDao.SearchReleFile(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReleFile>();
        } finally {
            session.close();
        }
    }

    public static void SaveReleFile(SqlSession session, ReleFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncFileDao.SaveReleFile(session, item);
            break;
        }
    }

    // endregion ReleFile Methods

}