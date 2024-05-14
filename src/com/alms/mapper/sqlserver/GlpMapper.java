package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.glp.*;
import com.gpersist.mapper.BaseMapper;

public interface GlpMapper extends BaseMapper {

    // region GlpFileDestroyRecord Methods

    public GlpFileDestroyRecord GetGlpFileDestroyRecord(GlpFileDestroyRecord item);

    public List<GlpFileDestroyRecord> SearchGlpFileDestroyRecord(GlpFileDestroyRecord item);

    public void SaveGlpFileDestroyRecord(GlpFileDestroyRecord item);

    // endregion GlpFileDestroyRecord Methods

    // region GlpFileRegister Methods

    public GlpFileRegister GetGlpFileRegister(GlpFileRegister item);

    public List<GlpFileRegister> SearchGlpFileRegister(GlpFileRegister item);

    public void SaveGlpFileRegister(GlpFileRegister item);

    public List<GlpFileLoan> SearchGlpLoanForRegister(GlpFileLoan item);

    // endregion GlpFileRegister Methods

    // region GlpFilePassword Methods

    public GlpFilePassword GetGlpFilePassword(GlpFilePassword item);

    public List<GlpFilePassword> GetListGlpFilePassword(GlpFilePassword item);

    public List<GlpFilePassword> SearchGlpFilePassword(GlpFilePassword item);

    public void SaveGlpFilePassword(GlpFilePassword item);

    // endregion GlpFilePassword Methods

    // region GlpFileOnline Methods

    public GlpFile HasAuthToReadGlp(GlpFileOnline item);

    public GlpFileOnline GetGlpFileOnline(GlpFileOnline item);

    public List<GlpFileOnline> SearchGlpFileOnline(GlpFileOnline item);

    public void SaveGlpFileOnline(GlpFileOnline item);

    // endregion GlpFileOnline Methods

    // region GlpFile Methods

    public GlpFile GetGlpFile(GlpFile item);

    public List<GlpFile> GetListGlpFile(GlpFile item);

    public List<GlpFile> GetGlpFileForNeed(GlpFile item);

    public List<GlpFile> SearchGlpFile(GlpFile item);

    public void SaveGlpFile(GlpFile item);

    // endregion GlpFile Methods

    // region GlpFileChange Methods

    public GlpFileChange GetGlpFileChange(GlpFileChange item);

    public List<GlpFileChange> GetListGlpFileChange(GlpFileChange item);

    public List<GlpFileChange> GetListGlpFileChangeForNotify(GlpFileChange item);

    public List<GlpFileChange> SearchGlpFileChange(GlpFileChange item);

    public void SaveGlpFileChange(GlpFileChange item);

    // endregion GlpFileChange Methods

    // region GlpFileDestroyDetail Methods

    public GlpFileDestroyDetail GetGlpFileDestroyDetail(GlpFileDestroyDetail item);

    public List<GlpFileDestroyDetail> GetListGlpFileDestroyDetail(GlpFileDestroyDetail item);

    public List<GlpFileDestroyDetail> SearchGlpFileDestroyDetail(GlpFileDestroyDetail item);

    public void SaveGlpFileDestroyDetail(GlpFileDestroyDetail item);

    // endregion GlpFileDestroyDetail Methods

    // region destoryapply Methods

    public GlpFileDestroy GetGlpFileDestroy(GlpFileDestroy item);

    public List<GlpFileDestroy> GetListGlpFileDestroy(GlpFileDestroy item);

    public List<GlpFileDestroy> SearchGlpFileDestroy(GlpFileDestroy item);

    public List<GlpFileDestroy> SearchGlpFileDestroyForRecord(GlpFileDestroy item);

    public void SaveGlpFileDestroy(GlpFileDestroy item);

    // endregion destoryapply Methods

    // region GlpFileLoan Methods

    public GlpFileLoan GetGlpFileLoan(GlpFileLoan item);

    public List<GlpFileLoan> GetListGlpFileLoan(GlpFileLoan item);

    public List<GlpFileLoan> SearchGlpFileLoan(GlpFileLoan item);

    public void SaveGlpFileLoan(GlpFileLoan item);

    // endregion GlpFileLoan Methods

    // region GlpFileLeak Methods

    public GlpFileLeak GetGlpFileLeak(GlpFileLeak item);

    public List<GlpFileLeak> GetListGlpFileLeak(GlpFileLeak item);

    public List<GlpFileLeak> SearchGlpFileLeak(GlpFileLeak item);

    public void SaveGlpFileLeak(GlpFileLeak item);

    // endregion GlpFileLeak Methods

    // region GlpFileNotify Methods

    public GlpFileNotify GetGlpFileNotify(GlpFileNotify item);

    public List<GlpFileNotify> GetListGlpFileNotify(GlpFileNotify item);

    public List<GlpFileNotify> SearchGlpFileNotify(GlpFileNotify item);

    public void SaveGlpFileNotify(GlpFileNotify item);

    // endregion GlpFileNotify Methods

    // region GlpFileRele Methods

    public GlpFileRele GetGlpFileRele(GlpFileRele item);

    public List<GlpFileRele> GetListGlpFileRele(GlpFileRele item);

    public List<GlpFileRele> SearchGlpFileRele(GlpFileRele item);

    public void SaveGlpFileRele(GlpFileRele item);

    // endregion GlpFileRele Methods

}