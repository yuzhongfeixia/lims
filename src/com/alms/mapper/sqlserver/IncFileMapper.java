package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.file.BasFile;
import com.alms.entity.file.BasLeak;
import com.alms.entity.file.ChangeApply;
import com.alms.entity.file.ChangeNotify;
import com.alms.entity.file.DestoryApply;
import com.alms.entity.file.DestoryApplyDetail;
import com.alms.entity.file.IncFileDestroyRecord;
import com.alms.entity.file.IncFileOnline;
import com.alms.entity.file.IncFilePassword;
import com.alms.entity.file.IncFileRegister;
import com.alms.entity.file.LoanApply;
import com.alms.entity.file.ReleFile;
import com.alms.entity.file.SecretApply;
import com.gpersist.mapper.BaseMapper;

public interface IncFileMapper extends BaseMapper {

    // region IncFileDestroyRecord Methods

    public IncFileDestroyRecord GetIncFileDestroyRecord(IncFileDestroyRecord item);

    public List<IncFileDestroyRecord> SearchIncFileDestroyRecord(IncFileDestroyRecord item);

    public void SaveIncFileDestroyRecord(IncFileDestroyRecord item);

    // endregion IncFileDestroyRecord Methods

    // region IncFilePassword Methods

    public IncFilePassword GetIncFilePassword(IncFilePassword item);

    public List<IncFilePassword> GetListIncFilePassword(IncFilePassword item);

    public List<IncFilePassword> SearchIncFilePassword(IncFilePassword item);

    public void SaveIncFilePassword(IncFilePassword item);

    // endregion IncFilePassword Methods

    // region IncFileOnline Methods

    public IncFileOnline GetIncFileOnline(IncFileOnline item);

    public List<IncFileOnline> SearchIncFileOnline(IncFileOnline item);

    public void SaveIncFileOnline(IncFileOnline item);

    // endregion IncFileOnline Methods

    // region IncFileRegister Methods

    public IncFileRegister GetIncFileRegister(IncFileRegister item);

    public List<IncFileRegister> SearchIncFileRegister(IncFileRegister item);

    public void SaveIncFileRegister(IncFileRegister item);

    // endregion IncFileRegister Methods

    // region BasFile Methods

    public BasFile HasAuthToRead(IncFileOnline item);

    public BasFile GetBasFile(BasFile item);

    public List<BasFile> GetListBasFile(BasFile item);

    public List<BasFile> GetBasFileForNeed(BasFile item);

    public List<BasFile> SearchBasFile(BasFile item);

    public void SaveBasFile(BasFile item);

    // endregion BasFile Methods

    // region ChangeApply Methods

    public ChangeApply GetChangeApply(ChangeApply item);

    public List<ChangeApply> GetListChangeApply(ChangeApply item);

    public List<ChangeApply> GetListChangeApplyForNotify(ChangeApply item);

    public List<ChangeApply> SearchChangeApply(ChangeApply item);

    public void SaveChangeApply(ChangeApply item);

    // endregion ChangeApply Methods

    // region DestoryApplyDetail Methods

    public DestoryApplyDetail GetDestoryApplyDetail(DestoryApplyDetail item);

    public List<DestoryApplyDetail> GetListDestoryApplyDetail(DestoryApplyDetail item);

    public List<DestoryApplyDetail> SearchDestoryApplyDetail(DestoryApplyDetail item);

    public void SaveDestoryApplyDetail(DestoryApplyDetail item);

    // endregion DestoryApplyDetail Methods

    // region destoryapply Methods

    public DestoryApply GetDestoryApply(DestoryApply item);

    public List<DestoryApply> GetListDestoryApply(DestoryApply item);

    public List<DestoryApply> SearchDestoryApply(DestoryApply item);

    public List<DestoryApply> SearchIncFileDestroyForRecord(DestoryApply item);

    public void SaveDestoryApply(DestoryApply item);

    // endregion destoryapply Methods

    // region LoanApply Methods

    public LoanApply GetLoanApply(LoanApply item);

    public List<LoanApply> GetListLoanApply(LoanApply item);

    public List<LoanApply> SearchLoanApply(LoanApply item);

    public List<LoanApply> SearchLoanApplyForRegister(LoanApply item);

    public void SaveLoanApply(LoanApply item);

    // endregion LoanApply Methods

    // region SecretApply Methods

    public SecretApply GetSecretApply(SecretApply item);

    public List<SecretApply> GetListSecretApply(SecretApply item);

    public List<SecretApply> SearchSecretApply(SecretApply item);

    public void SaveSecretApply(SecretApply item);

    // endregion SecretApply Methods

    // region BasLeak Methods

    public BasLeak GetBasLeak(BasLeak item);

    public List<BasLeak> GetListBasLeak(BasLeak item);

    public List<BasLeak> SearchBasLeak(BasLeak item);

    public void SaveBasLeak(BasLeak item);

    // endregion BasLeak Methods

    // region ChangeNotify Methods

    public ChangeNotify GetChangeNotify(ChangeNotify item);

    public List<ChangeNotify> GetListChangeNotify(ChangeNotify item);

    public List<ChangeNotify> SearchChangeNotify(ChangeNotify item);

    public void SaveChangeNotify(ChangeNotify item);

    // endregion ChangeNotify Methods

    // region ReleFile Methods

    public ReleFile GetReleFile(ReleFile item);

    public List<ReleFile> GetListReleFile(ReleFile item);

    public List<ReleFile> SearchReleFile(ReleFile item);

    public void SaveReleFile(ReleFile item);

    // endregion ReleFile Methods

}