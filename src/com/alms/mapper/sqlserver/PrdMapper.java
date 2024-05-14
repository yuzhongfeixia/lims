package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.prd.*;
import com.gpersist.mapper.BaseMapper;

public interface PrdMapper extends BaseMapper {

    // region PrdPoisonFile Methods

    public List<PrdPoisonFile> GetListPrdPoisonFile(PrdPoisonFile item);

    public void SavePrdPoisonFile(PrdPoisonFile item);

    // endregion PrdPoisonFile Methods

    // region PrdPoisonDetail Methods

    public List<PrdPoisonDetail> GetListPrdPoisonDetail(PrdPoisonDetail item);

    public void SavePrdPoisonDetail(PrdPoisonDetail item);

    // endregion PrdPoisonDetail Methods

    // region PrdPoison Methods

    public PrdPoison GetPrdPoison(PrdPoison item);

    public List<PrdPoison> GetListPrdPoison(PrdPoison item);

    public List<PrdPoison> SearchPrdPoison(PrdPoison item);

    public void SavePrdPoison(PrdPoison item);

    // endregion PrdPoison Methods

    // region PrdCodeDetail Methods

    public PrdCodeDetail GetPrdCodeDetail(PrdCodeDetail item);

    public List<PrdCodeDetail> GetListPrdCodeDetail(PrdCodeDetail item);

    public List<PrdCodeDetail> SearchPrdCodeDetail(PrdCodeDetail item);

    public void SavePrdCodeDetail(PrdCodeDetail item);

    // endregion PrdCodeDetail Methods

    // region PrdCode Methods

    public PrdCode GetPrdCode(PrdCode item);

    public PrdCode GetPrdCodeByPrd(PrdCode item);

    public List<PrdCode> GetListPrdCode(PrdCode item);

    public List<PrdCode> SearchPrdCode(PrdCode item);

    public List<PrdCode> SearchPrdCodeForLack(PrdCode item);

    public void SavePrdCode(PrdCode item);

    // endregion PrdCode Methods

    // region StkCheckDetail Methods

    public List<StkCheckDetail> GetListStkCheckDetail(StkCheckDetail item);

    public void SaveStkCheckDetail(StkCheckDetail item);

    // endregion StkCheckDetail Methods

    // region StkCheck Methods

    public StkCheck GetStkCheck(StkCheck item);

    public List<StkCheck> GetListStkCheck();

    public List<StkCheck> SearchStkCheck(StkCheck item);

    public void SaveStkCheck(StkCheck item);

    // endregion StkCheck Methods

    // region StkOutDetail Methods

    public List<StkOutDetail> GetListStkOutDetail(StkOutDetail item);

    public List<StkOutDetail> GetListStkOutDetailByPrdID(StkOutDetail item);

    public void SaveStkOutDetail(StkOutDetail item);

    // endregion StkOutDetail Methods

    // region StkOut Methods

    public StkOut GetStkOut(StkOut item);

    public List<StkOut> GetListStkOut();

    public List<StkOut> SearchStkOut(StkOut item);

    public List<StkOut> SearchStkOutCount(StkOut item);

    public void SaveStkOut(StkOut item);

    // endregion StkOut Methods

    // region StkInDetail Methods

    public List<StkInDetail> GetListStkInDetail(StkInDetail item);

    public List<StkInDetail> GetListStkInDetailByPrdID(StkInDetail item);

    public void SaveStkInDetail(StkInDetail item);

    // endregion StkInDetail Methods

    // region StkIn Methods

    public StkIn GetStkIn(StkIn item);

    public List<StkIn> GetListStkIn();

    public List<StkIn> SearchStkIn(StkIn item);

    public void SaveStkIn(StkIn item);

    // endregion StkIn Methods

    // region StkStore Methods

    public StkStore GetStkStore(StkStore item);

    public List<StkStore> GetListStkStore(StkStore item);

    public List<StkStore> SearchStkStore(StkStore item);

    public void SaveStkStore(StkStore item);

    // endregion StkStore Methods

    // region PrdWasteDetail Methods

    public PrdWasteDetail GetPrdWasteDetail(PrdWasteDetail item);

    public List<PrdWasteDetail> GetListPrdWasteDetail(PrdWasteDetail item);

    public void SavePrdWasteDetail(PrdWasteDetail item);

    // endregion PrdWasteDetail Methods

    // region PrdWaste Methods

    public PrdWaste GetPrdWaste(PrdWaste item);

    public List<PrdWaste> GetListPrdWaste();

    public List<PrdWaste> SearchPrdWaste(PrdWaste item);

    public void SavePrdWaste(PrdWaste item);

    // endregion PrdWaste Methods

    // region PrdVerify Methods

    public PrdVerify GetPrdVerify(PrdVerify item);

    public List<PrdVerify> GetListPrdVerify();

    public List<PrdVerify> SearchPrdVerify(PrdVerify item);

    public List<PrdVerify> SearchPrdVerifyForIn(PrdVerify item);

    public void SavePrdVerify(PrdVerify item);

    // endregion PrdVerify Methods

    // region PrdApplyDetail Methods

    public PrdApplyDetail GetPrdApplyDetail(PrdApplyDetail item);

    public List<PrdApplyDetail> GetListPrdApplyDetail(PrdApplyDetail item);

    public List<PrdApplyDetail> SearchPrdApplyDetailForVerify(PrdApplyDetail item);

    // 申请验证的耗材查询
    public List<PrdApplyDetail> GetListPrdApplyDetailForVerify(PrdApplyDetail item);

    public void SavePrdApplyDetail(PrdApplyDetail item);

    // endregion PrdApplyDetail Methods

    // region PrdApply Methods

    public PrdApply GetPrdApply(PrdApply item);

    public List<PrdApply> GetListPrdApply();

    public List<PrdApply> SearchPrdApply(PrdApply item);

    // 耗材验证 的 耗材申请
    public List<PrdApply> SearchPrdApplyForVerify(PrdApply item);

    public void SavePrdApply(PrdApply item);

    // endregion PrdApply Methods

    // region BasPrd Methods

    public BasPrd GetBasPrd(BasPrd item);

    public List<BasPrd> GetListBasPrd();

    public List<BasPrd> SearchBasPrd(BasPrd item);

    public void SaveBasPrd(BasPrd item);

    // endregion BasPrd Methods

}
