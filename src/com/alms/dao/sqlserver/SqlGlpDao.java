package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.glp.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlGlpDao {

    // region GlpFileDestroyRecord Methods

    public static GlpFileDestroyRecord GetGlpFileDestroyRecord(SqlSession session, GlpFileDestroyRecord item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileDestroyRecord(item);
    }

    public static List<GlpFileDestroyRecord> SearchGlpFileDestroyRecord(SqlSession session, GlpFileDestroyRecord item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileDestroyRecord(item);
    }

    public static void SaveGlpFileDestroyRecord(SqlSession session, GlpFileDestroyRecord item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileDestroyRecord(item);
    }

    // endregion GlpFileDestroyRecord Methods

    // region GlpFileRegister Methods

    public static GlpFileRegister GetGlpFileRegister(SqlSession session, GlpFileRegister item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileRegister(item);
    }

    public static List<GlpFileRegister> SearchGlpFileRegister(SqlSession session, GlpFileRegister item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileRegister(item);
    }

    public static void SaveGlpFileRegister(SqlSession session, GlpFileRegister item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileRegister(item);
    }

    public static List<GlpFileLoan> SearchGlpLoanForRegister(SqlSession session, GlpFileLoan item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpLoanForRegister(item);
    }

    // endregion GlpFileRegister Methods

    // region GlpFilePassword Methods

    public static GlpFilePassword GetGlpFilePassword(SqlSession session, GlpFilePassword item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFilePassword(item);
    }

    public static List<GlpFilePassword> GetListGlpFilePassword(SqlSession session, GlpFilePassword item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFilePassword(item);
    }

    public static List<GlpFilePassword> SearchGlpFilePassword(SqlSession session, GlpFilePassword item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFilePassword(item);
    }

    public static void SaveGlpFilePassword(SqlSession session, GlpFilePassword item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFilePassword(item);
    }

    // endregion GlpFilePassword Methods

    // region GlpFileOnline Methods

    public static GlpFile HasAuthToReadGlp(SqlSession session, GlpFileOnline item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        return mapper.HasAuthToReadGlp(item);
    }

    public static GlpFileOnline GetGlpFileOnline(SqlSession session, GlpFileOnline item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileOnline(item);
    }

    public static List<GlpFileOnline> SearchGlpFileOnline(SqlSession session, GlpFileOnline item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileOnline(item);
    }

    public static void SaveGlpFileOnline(SqlSession session, GlpFileOnline item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileOnline(item);
    }

    // endregion GlpFileOnline Methods

    // region GlpFile Methods

    public static GlpFile GetGlpFile(SqlSession session, GlpFile item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFile(item);
    }

    public static List<GlpFile> GetListGlpFile(SqlSession session, GlpFile item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFile(item);
    }

    public static List<GlpFile> GetGlpFileForNeed(SqlSession session, GlpFile item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetGlpFileForNeed(item);
    }

    public static List<GlpFile> SearchGlpFile(SqlSession session, GlpFile item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFile(item);
    }

    public static void SaveGlpFile(SqlSession session, GlpFile item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFile(item);
    }

    // endregion GlpFile Methods

    // region GlpFileChange Methods

    public static GlpFileChange GetGlpFileChange(SqlSession session, GlpFileChange item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileChange(item);
    }

    public static List<GlpFileChange> GetListGlpFileChange(SqlSession session, GlpFileChange item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFileChange(item);
    }

    public static List<GlpFileChange> GetListGlpFileChangeForNotify(SqlSession session, GlpFileChange item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFileChangeForNotify(item);
    }

    public static List<GlpFileChange> SearchGlpFileChange(SqlSession session, GlpFileChange item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileChange(item);
    }

    public static void SaveGlpFileChange(SqlSession session, GlpFileChange item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileChange(item);
    }

    // endregion GlpFileChange Methods

    // region GlpFileDestroyDetail Methods

    public static GlpFileDestroyDetail GetGlpFileDestroyDetail(SqlSession session, GlpFileDestroyDetail item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileDestroyDetail(item);
    }

    public static List<GlpFileDestroyDetail> GetListGlpFileDestroyDetail(SqlSession session,
            GlpFileDestroyDetail item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFileDestroyDetail(item);
    }

    public static List<GlpFileDestroyDetail> SearchGlpFileDestroyDetail(SqlSession session, GlpFileDestroyDetail item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileDestroyDetail(item);
    }

    public static void SaveGlpFileDestroyDetail(SqlSession session, GlpFileDestroyDetail item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileDestroyDetail(item);
    }

    // endregion GlpFileDestroyDetail Methods

    // region destoryapply Methods

    public static GlpFileDestroy GetGlpFileDestroy(SqlSession session, GlpFileDestroy item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileDestroy(item);
    }

    public static List<GlpFileDestroy> GetListGlpFileDestroy(SqlSession session, GlpFileDestroy item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFileDestroy(item);
    }

    public static List<GlpFileDestroy> SearchGlpFileDestroy(SqlSession session, GlpFileDestroy item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileDestroy(item);
    }

    public static List<GlpFileDestroy> SearchGlpFileDestroyForRecord(SqlSession session, GlpFileDestroy item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileDestroyForRecord(item);
    }

    public static void SaveGlpFileDestroy(SqlSession session, GlpFileDestroy item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileDestroy(item);
    }

    // endregion destoryapply Methods

    // region GlpFileLoan Methods

    public static GlpFileLoan GetGlpFileLoan(SqlSession session, GlpFileLoan item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileLoan(item);
    }

    public static List<GlpFileLoan> GetListGlpFileLoan(SqlSession session, GlpFileLoan item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFileLoan(item);
    }

    public static List<GlpFileLoan> SearchGlpFileLoan(SqlSession session, GlpFileLoan item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileLoan(item);
    }

    public static void SaveGlpFileLoan(SqlSession session, GlpFileLoan item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileLoan(item);
    }

    // endregion GlpFileLoan Methods

    // region GlpFileLeak Methods

    public static GlpFileLeak GetGlpFileLeak(SqlSession session, GlpFileLeak item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileLeak(item);
    }

    public static List<GlpFileLeak> GetListGlpFileLeak(SqlSession session, GlpFileLeak item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFileLeak(item);
    }

    public static List<GlpFileLeak> SearchGlpFileLeak(SqlSession session, GlpFileLeak item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileLeak(item);
    }

    public static void SaveGlpFileLeak(SqlSession session, GlpFileLeak item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileLeak(item);
    }

    // endregion GlpFileLeak Methods

    // region GlpFileNotify Methods

    public static GlpFileNotify GetGlpFileNotify(SqlSession session, GlpFileNotify item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileNotify(item);
    }

    public static List<GlpFileNotify> GetListGlpFileNotify(SqlSession session, GlpFileNotify item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFileNotify(item);
    }

    public static List<GlpFileNotify> SearchGlpFileNotify(SqlSession session, GlpFileNotify item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileNotify(item);
    }

    public static void SaveGlpFileNotify(SqlSession session, GlpFileNotify item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileNotify(item);
    }

    // endregion GlpFileNotify Methods

    // region GlpFileRele Methods

    public static GlpFileRele GetGlpFileRele(SqlSession session, GlpFileRele item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetGlpFileRele(item);
    }

    public static List<GlpFileRele> GetListGlpFileRele(SqlSession session, GlpFileRele item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.GetListGlpFileRele(item);
    }

    public static List<GlpFileRele> SearchGlpFileRele(SqlSession session, GlpFileRele item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        return mapper.SearchGlpFileRele(item);
    }

    public static void SaveGlpFileRele(SqlSession session, GlpFileRele item) {
        com.alms.mapper.sqlserver.GlpMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.GlpMapper.class);

        mapper.SaveGlpFileRele(item);
    }

    // endregion GlpFileRele Methods

}