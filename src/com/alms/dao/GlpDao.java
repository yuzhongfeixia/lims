package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlGlpDao;
import com.alms.entity.glp.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class GlpDao {

    // region GlpFileDestroyRecord Methods

    public static GlpFileDestroyRecord GetGlpFileDestroyRecord(GlpFileDestroyRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileDestroyRecord(session, item);
        } catch (Exception e) {
            return new GlpFileDestroyRecord();
        } finally {
            session.close();
        }
    }

    public static GlpFileDestroyRecord GetGlpFileDestroyRecord(SqlSession session, GlpFileDestroyRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileDestroyRecord();
        default:
            return SqlGlpDao.GetGlpFileDestroyRecord(session, item);
        }
    }

    public static List<GlpFileDestroyRecord> SearchGlpFileDestroyRecord(GlpFileDestroyRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileDestroyRecord>();
            default:
                return SqlGlpDao.SearchGlpFileDestroyRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileDestroyRecord>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileDestroyRecord(SqlSession session, GlpFileDestroyRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileDestroyRecord(session, item);
            break;
        }
    }

    // endregion GlpFileDestroyRecord Methods

    // region GlpFileRegister Methods

    public static GlpFileRegister GetGlpFileRegister(GlpFileRegister item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileRegister(session, item);
        } catch (Exception e) {
            return new GlpFileRegister();
        } finally {
            session.close();
        }
    }

    public static GlpFileRegister GetGlpFileRegister(SqlSession session, GlpFileRegister item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileRegister();
        default:
            return SqlGlpDao.GetGlpFileRegister(session, item);
        }
    }

    public static List<GlpFileRegister> SearchGlpFileRegister(GlpFileRegister item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileRegister>();
            default:
                return SqlGlpDao.SearchGlpFileRegister(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileRegister>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileRegister(SqlSession session, GlpFileRegister item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileRegister(session, item);
            break;
        }
    }

    public static List<GlpFileLoan> SearchGlpLoanForRegister(GlpFileLoan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileLoan>();
            default:
                return SqlGlpDao.SearchGlpLoanForRegister(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileLoan>();
        } finally {
            session.close();
        }
    }

    // endregion GlpFileRegister Methods

    // region GlpFilePassword Methods

    public static GlpFilePassword GetGlpFilePassword(GlpFilePassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFilePassword(session, item);
        } catch (Exception e) {
            return new GlpFilePassword();
        } finally {
            session.close();
        }
    }

    public static GlpFilePassword GetGlpFilePassword(SqlSession session, GlpFilePassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFilePassword();
        default:
            return SqlGlpDao.GetGlpFilePassword(session, item);
        }
    }

    public static List<GlpFilePassword> GetListGlpFilePassword(GlpFilePassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFilePassword(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFilePassword>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFilePassword> GetListGlpFilePassword(SqlSession session, GlpFilePassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFilePassword>();
        default:
            return SqlGlpDao.GetListGlpFilePassword(session, item);
        }
    }

    public static List<GlpFilePassword> SearchGlpFilePassword(GlpFilePassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFilePassword>();
            default:
                return SqlGlpDao.SearchGlpFilePassword(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFilePassword>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFilePassword(SqlSession session, GlpFilePassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFilePassword(session, item);
            break;
        }
    }

    // endregion GlpFilePassword Methods

    // region GlpFileOnline Methods

    public static GlpFile HasAuthToReadGlp(GlpFileOnline item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new GlpFile();
            default:
                return SqlGlpDao.HasAuthToReadGlp(session, item);

            }
        } catch (Exception e) {
            return new GlpFile();
        } finally {
            session.close();
        }
    }

    public static GlpFileOnline GetGlpFileOnline(GlpFileOnline item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileOnline(session, item);
        } catch (Exception e) {
            return new GlpFileOnline();
        } finally {
            session.close();
        }
    }

    public static GlpFileOnline GetGlpFileOnline(SqlSession session, GlpFileOnline item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileOnline();
        default:
            return SqlGlpDao.GetGlpFileOnline(session, item);
        }
    }

    public static List<GlpFileOnline> SearchGlpFileOnline(GlpFileOnline item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileOnline>();
            default:
                return SqlGlpDao.SearchGlpFileOnline(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileOnline>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileOnline(SqlSession session, GlpFileOnline item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileOnline(session, item);
            break;
        }
    }

    // endregion GlpFileOnline Methods

    // region GlpFile Methods

    public static GlpFile GetGlpFile(GlpFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFile(session, item);
        } catch (Exception e) {
            return new GlpFile();
        } finally {
            session.close();
        }
    }

    public static GlpFile GetGlpFile(SqlSession session, GlpFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFile();
        default:
            return SqlGlpDao.GetGlpFile(session, item);
        }
    }

    public static List<GlpFile> GetListGlpFile(GlpFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFile(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFile>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFile> GetListGlpFile(SqlSession session, GlpFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFile>();
        default:
            return SqlGlpDao.GetListGlpFile(session, item);
        }
    }

    public static List<GlpFile> GetGlpFileForNeed(GlpFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileForNeed(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFile>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFile> GetGlpFileForNeed(SqlSession session, GlpFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFile>();
        default:
            return SqlGlpDao.GetGlpFileForNeed(session, item);
        }
    }

    public static List<GlpFile> SearchGlpFile(GlpFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFile>();
            default:
                return SqlGlpDao.SearchGlpFile(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFile>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFile(SqlSession session, GlpFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFile(session, item);
            break;
        }
    }

    // endregion GlpFile Methods

    // region GlpFileChange Methods

    public static GlpFileChange GetGlpFileChange(GlpFileChange item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileChange(session, item);
        } catch (Exception e) {
            return new GlpFileChange();
        } finally {
            session.close();
        }
    }

    public static GlpFileChange GetGlpFileChange(SqlSession session, GlpFileChange item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileChange();
        default:
            return SqlGlpDao.GetGlpFileChange(session, item);
        }
    }

    public static List<GlpFileChange> GetListGlpFileChange(GlpFileChange item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFileChange(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFileChange>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileChange> GetListGlpFileChange(SqlSession session, GlpFileChange item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFileChange>();
        default:
            return SqlGlpDao.GetListGlpFileChange(session, item);
        }
    }

    public static List<GlpFileChange> GetListGlpFileChangeForNotify(GlpFileChange item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFileChangeForNotify(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFileChange>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileChange> GetListGlpFileChangeForNotify(SqlSession session, GlpFileChange item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFileChange>();
        default:
            return SqlGlpDao.GetListGlpFileChangeForNotify(session, item);
        }
    }

    public static List<GlpFileChange> SearchGlpFileChange(GlpFileChange item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileChange>();
            default:
                return SqlGlpDao.SearchGlpFileChange(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileChange>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileChange(SqlSession session, GlpFileChange item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileChange(session, item);
            break;
        }
    }

    // endregion GlpFileChange Methods

    // region GlpFileDestroyDetail Methods

    public static GlpFileDestroyDetail GetGlpFileDestroyDetail(GlpFileDestroyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileDestroyDetail(session, item);
        } catch (Exception e) {
            return new GlpFileDestroyDetail();
        } finally {
            session.close();
        }
    }

    public static GlpFileDestroyDetail GetGlpFileDestroyDetail(SqlSession session, GlpFileDestroyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileDestroyDetail();
        default:
            return SqlGlpDao.GetGlpFileDestroyDetail(session, item);
        }
    }

    public static List<GlpFileDestroyDetail> GetListGlpFileDestroyDetail(GlpFileDestroyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFileDestroyDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFileDestroyDetail>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileDestroyDetail> GetListGlpFileDestroyDetail(SqlSession session,
            GlpFileDestroyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFileDestroyDetail>();
        default:
            return SqlGlpDao.GetListGlpFileDestroyDetail(session, item);
        }
    }

    public static List<GlpFileDestroyDetail> SearchGlpFileDestroyDetail(GlpFileDestroyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileDestroyDetail>();
            default:
                return SqlGlpDao.SearchGlpFileDestroyDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileDestroyDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileDestroyDetail(SqlSession session, GlpFileDestroyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileDestroyDetail(session, item);
            break;
        }
    }

    // endregion GlpFileDestroyDetail Methods

    // region destoryapply Methods

    public static GlpFileDestroy GetGlpFileDestroy(GlpFileDestroy item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileDestroy(session, item);
        } catch (Exception e) {
            return new GlpFileDestroy();
        } finally {
            session.close();
        }
    }

    public static GlpFileDestroy GetGlpFileDestroy(SqlSession session, GlpFileDestroy item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileDestroy();
        default:
            return SqlGlpDao.GetGlpFileDestroy(session, item);
        }
    }

    public static List<GlpFileDestroy> GetListGlpFileDestroy(GlpFileDestroy item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFileDestroy(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFileDestroy>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileDestroy> GetListGlpFileDestroy(SqlSession session, GlpFileDestroy item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFileDestroy>();
        default:
            return SqlGlpDao.GetListGlpFileDestroy(session, item);
        }
    }

    public static List<GlpFileDestroy> SearchGlpFileDestroy(GlpFileDestroy item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileDestroy>();
            default:
                return SqlGlpDao.SearchGlpFileDestroy(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileDestroy>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileDestroy> SearchGlpFileDestroyForRecord(GlpFileDestroy item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileDestroy>();
            default:
                return SqlGlpDao.SearchGlpFileDestroyForRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileDestroy>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileDestroy(SqlSession session, GlpFileDestroy item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileDestroy(session, item);
            break;
        }
    }

    // endregion destoryapply Methods

    // region GlpFileLoan Methods

    public static GlpFileLoan GetGlpFileLoan(GlpFileLoan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileLoan(session, item);
        } catch (Exception e) {
            return new GlpFileLoan();
        } finally {
            session.close();
        }
    }

    public static GlpFileLoan GetGlpFileLoan(SqlSession session, GlpFileLoan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileLoan();
        default:
            return SqlGlpDao.GetGlpFileLoan(session, item);
        }
    }

    public static List<GlpFileLoan> GetListGlpFileLoan(GlpFileLoan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFileLoan(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFileLoan>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileLoan> GetListGlpFileLoan(SqlSession session, GlpFileLoan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFileLoan>();
        default:
            return SqlGlpDao.GetListGlpFileLoan(session, item);
        }
    }

    public static List<GlpFileLoan> SearchGlpFileLoan(GlpFileLoan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileLoan>();
            default:
                return SqlGlpDao.SearchGlpFileLoan(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileLoan>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileLoan(SqlSession session, GlpFileLoan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileLoan(session, item);
            break;
        }
    }

    // endregion GlpFileLoan Methods

    // region GlpFileLeak Methods

    public static GlpFileLeak GetGlpFileLeak(GlpFileLeak item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileLeak(session, item);
        } catch (Exception e) {
            return new GlpFileLeak();
        } finally {
            session.close();
        }
    }

    public static GlpFileLeak GetGlpFileLeak(SqlSession session, GlpFileLeak item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileLeak();
        default:
            return SqlGlpDao.GetGlpFileLeak(session, item);
        }
    }

    public static List<GlpFileLeak> GetListGlpFileLeak(GlpFileLeak item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFileLeak(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFileLeak>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileLeak> GetListGlpFileLeak(SqlSession session, GlpFileLeak item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFileLeak>();
        default:
            return SqlGlpDao.GetListGlpFileLeak(session, item);
        }
    }

    public static List<GlpFileLeak> SearchGlpFileLeak(GlpFileLeak item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileLeak>();
            default:
                return SqlGlpDao.SearchGlpFileLeak(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileLeak>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileLeak(SqlSession session, GlpFileLeak item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileLeak(session, item);
            break;
        }
    }

    // endregion GlpFileLeak Methods

    // region GlpFileNotify Methods

    public static GlpFileNotify GetGlpFileNotify(GlpFileNotify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileNotify(session, item);
        } catch (Exception e) {
            return new GlpFileNotify();
        } finally {
            session.close();
        }
    }

    public static GlpFileNotify GetGlpFileNotify(SqlSession session, GlpFileNotify item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileNotify();
        default:
            return SqlGlpDao.GetGlpFileNotify(session, item);
        }
    }

    public static List<GlpFileNotify> GetListGlpFileNotify(GlpFileNotify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFileNotify(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFileNotify>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileNotify> GetListGlpFileNotify(SqlSession session, GlpFileNotify item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFileNotify>();
        default:
            return SqlGlpDao.GetListGlpFileNotify(session, item);
        }
    }

    public static List<GlpFileNotify> SearchGlpFileNotify(GlpFileNotify item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileNotify>();
            default:
                return SqlGlpDao.SearchGlpFileNotify(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileNotify>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileNotify(SqlSession session, GlpFileNotify item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileNotify(session, item);
            break;
        }
    }

    // endregion GlpFileNotify Methods

    // region GlpFileRele Methods

    public static GlpFileRele GetGlpFileRele(GlpFileRele item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGlpFileRele(session, item);
        } catch (Exception e) {
            return new GlpFileRele();
        } finally {
            session.close();
        }
    }

    public static GlpFileRele GetGlpFileRele(SqlSession session, GlpFileRele item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new GlpFileRele();
        default:
            return SqlGlpDao.GetGlpFileRele(session, item);
        }
    }

    public static List<GlpFileRele> GetListGlpFileRele(GlpFileRele item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListGlpFileRele(session, item);
        } catch (Exception e) {
            return new ArrayList<GlpFileRele>();
        } finally {
            session.close();
        }
    }

    public static List<GlpFileRele> GetListGlpFileRele(SqlSession session, GlpFileRele item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<GlpFileRele>();
        default:
            return SqlGlpDao.GetListGlpFileRele(session, item);
        }
    }

    public static List<GlpFileRele> SearchGlpFileRele(GlpFileRele item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<GlpFileRele>();
            default:
                return SqlGlpDao.SearchGlpFileRele(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<GlpFileRele>();
        } finally {
            session.close();
        }
    }

    public static void SaveGlpFileRele(SqlSession session, GlpFileRele item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlGlpDao.SaveGlpFileRele(session, item);
            break;
        }
    }

    // endregion GlpFileRele Methods

}