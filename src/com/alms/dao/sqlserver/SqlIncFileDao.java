package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.file.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlIncFileDao {

    // region IncFileDestroyRecord Methods

    public static IncFileDestroyRecord GetIncFileDestroyRecord(SqlSession session, IncFileDestroyRecord item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetIncFileDestroyRecord(item);
    }

    public static List<IncFileDestroyRecord> SearchIncFileDestroyRecord(SqlSession session, IncFileDestroyRecord item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchIncFileDestroyRecord(item);
    }

    public static void SaveIncFileDestroyRecord(SqlSession session, IncFileDestroyRecord item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveIncFileDestroyRecord(item);
    }

    // endregion IncFileDestroyRecord Methods

    // region IncFilePassword Methods

    public static IncFilePassword GetIncFilePassword(SqlSession session, IncFilePassword item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetIncFilePassword(item);
    }

    public static List<IncFilePassword> GetListIncFilePassword(SqlSession session, IncFilePassword item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListIncFilePassword(item);
    }

    public static List<IncFilePassword> SearchIncFilePassword(SqlSession session, IncFilePassword item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchIncFilePassword(item);
    }

    public static void SaveIncFilePassword(SqlSession session, IncFilePassword item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveIncFilePassword(item);
    }

    // endregion IncFilePassword Methods

    // region IncFileOnline Methods

    public static IncFileOnline GetIncFileOnline(SqlSession session, IncFileOnline item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetIncFileOnline(item);
    }

    public static List<IncFileOnline> SearchIncFileOnline(SqlSession session, IncFileOnline item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchIncFileOnline(item);
    }

    public static void SaveIncFileOnline(SqlSession session, IncFileOnline item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveIncFileOnline(item);
    }

    // endregion IncFileOnline Methods

    // region IncFileRegister Methods

    public static IncFileRegister GetIncFileRegister(SqlSession session, IncFileRegister item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetIncFileRegister(item);
    }

    public static List<IncFileRegister> SearchIncFileRegister(SqlSession session, IncFileRegister item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchIncFileRegister(item);
    }

    public static void SaveIncFileRegister(SqlSession session, IncFileRegister item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveIncFileRegister(item);
    }

    // endregion IncFileRegister Methods

    // region BasFile Methods

    public static BasFile HasAuthToRead(SqlSession session, IncFileOnline item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        return mapper.HasAuthToRead(item);
    }

    public static BasFile GetBasFile(SqlSession session, BasFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasFile(item);
    }

    public static List<BasFile> GetListBasFile(SqlSession session, BasFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListBasFile(item);
    }

    public static List<BasFile> GetBasFileForNeed(SqlSession session, BasFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetBasFileForNeed(item);
    }

    public static List<BasFile> SearchBasFile(SqlSession session, BasFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchBasFile(item);
    }

    public static void SaveBasFile(SqlSession session, BasFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveBasFile(item);
    }

    // endregion BasFile Methods

    // region ChangeApply Methods

    public static ChangeApply GetChangeApply(SqlSession session, ChangeApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetChangeApply(item);
    }

    public static List<ChangeApply> GetListChangeApply(SqlSession session, ChangeApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListChangeApply(item);
    }

    public static List<ChangeApply> GetListChangeApplyForNotify(SqlSession session, ChangeApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListChangeApplyForNotify(item);
    }

    public static List<ChangeApply> SearchChangeApply(SqlSession session, ChangeApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchChangeApply(item);
    }

    public static void SaveChangeApply(SqlSession session, ChangeApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveChangeApply(item);
    }

    // endregion ChangeApply Methods

    // region DestoryApplyDetail Methods

    public static DestoryApplyDetail GetDestoryApplyDetail(SqlSession session, DestoryApplyDetail item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDestoryApplyDetail(item);
    }

    public static List<DestoryApplyDetail> GetListDestoryApplyDetail(SqlSession session, DestoryApplyDetail item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListDestoryApplyDetail(item);
    }

    public static List<DestoryApplyDetail> SearchDestoryApplyDetail(SqlSession session, DestoryApplyDetail item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchDestoryApplyDetail(item);
    }

    public static void SaveDestoryApplyDetail(SqlSession session, DestoryApplyDetail item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveDestoryApplyDetail(item);
    }

    // endregion DestoryApplyDetail Methods

    // region destoryapply Methods

    public static DestoryApply GetDestoryApply(SqlSession session, DestoryApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDestoryApply(item);
    }

    public static List<DestoryApply> GetListDestoryApply(SqlSession session, DestoryApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListDestoryApply(item);
    }

    public static List<DestoryApply> SearchDestoryApply(SqlSession session, DestoryApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchDestoryApply(item);
    }

    public static List<DestoryApply> SearchIncFileDestroyForRecord(SqlSession session, DestoryApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchIncFileDestroyForRecord(item);
    }

    public static void SaveDestoryApply(SqlSession session, DestoryApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveDestoryApply(item);
    }

    // endregion destoryapply Methods

    // region LoanApply Methods

    public static LoanApply GetLoanApply(SqlSession session, LoanApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetLoanApply(item);
    }

    public static List<LoanApply> GetListLoanApply(SqlSession session, LoanApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListLoanApply(item);
    }

    public static List<LoanApply> SearchLoanApply(SqlSession session, LoanApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchLoanApply(item);
    }

    public static List<LoanApply> SearchLoanApplyForRegister(SqlSession session, LoanApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchLoanApplyForRegister(item);
    }

    public static void SaveLoanApply(SqlSession session, LoanApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveLoanApply(item);
    }

    // endregion LoanApply Methods

    // region SecretApply Methods

    public static SecretApply GetSecretApply(SqlSession session, SecretApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetSecretApply(item);
    }

    public static List<SecretApply> GetListSecretApply(SqlSession session, SecretApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListSecretApply(item);
    }

    public static List<SecretApply> SearchSecretApply(SqlSession session, SecretApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchSecretApply(item);
    }

    public static void SaveSecretApply(SqlSession session, SecretApply item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveSecretApply(item);
    }

    // endregion SecretApply Methods

    // region BasLeak Methods

    public static BasLeak GetBasLeak(SqlSession session, BasLeak item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasLeak(item);
    }

    public static List<BasLeak> GetListBasLeak(SqlSession session, BasLeak item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListBasLeak(item);
    }

    public static List<BasLeak> SearchBasLeak(SqlSession session, BasLeak item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchBasLeak(item);
    }

    public static void SaveBasLeak(SqlSession session, BasLeak item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveBasLeak(item);
    }

    // endregion BasLeak Methods

    // region ChangeNotify Methods

    public static ChangeNotify GetChangeNotify(SqlSession session, ChangeNotify item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetChangeNotify(item);
    }

    public static List<ChangeNotify> GetListChangeNotify(SqlSession session, ChangeNotify item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListChangeNotify(item);
    }

    public static List<ChangeNotify> SearchChangeNotify(SqlSession session, ChangeNotify item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchChangeNotify(item);
    }

    public static void SaveChangeNotify(SqlSession session, ChangeNotify item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveChangeNotify(item);
    }

    // endregion ChangeNotify Methods

    // region ReleFile Methods

    public static ReleFile GetReleFile(SqlSession session, ReleFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetReleFile(item);
    }

    public static List<ReleFile> GetListReleFile(SqlSession session, ReleFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.GetListReleFile(item);
    }

    public static List<ReleFile> SearchReleFile(SqlSession session, ReleFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        return mapper.SearchReleFile(item);
    }

    public static void SaveReleFile(SqlSession session, ReleFile item) {
        com.alms.mapper.sqlserver.IncFileMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncFileMapper.class);

        mapper.SaveReleFile(item);
    }

    // endregion ReleFile Methods

}