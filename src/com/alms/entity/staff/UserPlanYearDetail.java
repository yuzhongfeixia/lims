package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserPlanYearDetail implements BaseBean {

    // 培训计划编号;
    private String tranid;

    // 关联业务编号;
    private String relaid;

    // 计划年度;
    private String tranyear;

    // 拟培训人
    private String trainer;

    // 部门机构;
    private String deptid;

    // 机构名称;
    private String deptname;

    // 培训时间;
    private java.util.Date traindate;

    // 培训目标;
    private String traintarget;

    // 培训对象;
    private String trainobject;

    // 培训项目;
    private String traincontent;

    // 培训形式;
    private String traintype;

    // 培训形式名;
    private String traintypename;

    // 计费预算;
    private double trainfee;

    // 培训要求;
    private String trainrequest;

    // 备注;
    private String trainremark;

    // 培训状态;
    private String trainstatus;

    // 培训状态名称;
    private String trainstatusname;

    // 修改标记;
    private int modifyserial;

    // 计划建立日期;
    private java.util.Date trandate;

    // 计划建立人员;
    private String tranuser;

    // 计划建立人员
    private String tranusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserPlanYearDetail> item;

    public UserPlanYearDetail() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((UserPlanYearDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "培训计划编号:tranid", "关联业务编号:relaid", "计划年度:tranyear", "部门机构:deptid", "机构名称:deptname",
                "培训时间:traindate", "培训目标:traintarget", "培训对象:trainobject", "培训项目:traincontent", "培训形式:traintype",
                "培训形式名:traintypename", "计费预算:trainfee", "培训要求:trainrequest", "备注:trainremark", "培训状态:trainstatus",
                "培训状态名称:trainstatusname", "修改标记:modifyserial", "计划建立日期:trandate", "计划建立人员:tranuser" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.relaid = "";
        this.tranyear = "";
        this.trainer = "";
        this.deptid = "";
        this.deptname = "";
        this.traindate = ToolUtils.GetMinDate();
        this.traintarget = "";
        this.trainobject = "";
        this.traincontent = "";
        this.traintype = "";
        this.traintypename = "";
        this.trainfee = 0;
        this.trainrequest = "";
        this.trainremark = "";
        this.trainstatus = "";
        this.trainstatusname = "";
        this.modifyserial = 0;
        // this.trandate = ToolUtils.GetMinDate();
        this.tranuser = "";
    }

    public String getTranusername() {
        return tranusername;
    }

    public void setTranusername(String tranusername) {
        this.tranusername = tranusername;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getRelaid() {
        return relaid;
    }

    public void setRelaid(String relaid) {
        this.relaid = relaid;
    }

    public String getTranyear() {
        return tranyear;
    }

    public void setTranyear(String tranyear) {
        this.tranyear = tranyear;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public java.util.Date getTraindate() {
        return traindate;
    }

    public void setTraindate(java.util.Date traindate) {
        this.traindate = traindate;
    }

    public String getTraintarget() {
        return traintarget;
    }

    public void setTraintarget(String traintarget) {
        this.traintarget = traintarget;
    }

    public String getTrainobject() {
        return trainobject;
    }

    public void setTrainobject(String trainobject) {
        this.trainobject = trainobject;
    }

    public String getTraincontent() {
        return traincontent;
    }

    public void setTraincontent(String traincontent) {
        this.traincontent = traincontent;
    }

    public String getTraintype() {
        return traintype;
    }

    public void setTraintype(String traintype) {
        this.traintype = traintype;
    }

    public String getTraintypename() {
        return traintypename;
    }

    public void setTraintypename(String traintypename) {
        this.traintypename = traintypename;
    }

    public double getTrainfee() {
        return trainfee;
    }

    public void setTrainfee(double trainfee) {
        this.trainfee = trainfee;
    }

    public String getTrainrequest() {
        return trainrequest;
    }

    public void setTrainrequest(String trainrequest) {
        this.trainrequest = trainrequest;
    }

    public String getTrainremark() {
        return trainremark;
    }

    public void setTrainremark(String trainremark) {
        this.trainremark = trainremark;
    }

    public String getTrainstatus() {
        return trainstatus;
    }

    public void setTrainstatus(String trainstatus) {
        this.trainstatus = trainstatus;
    }

    public String getTrainstatusname() {
        return trainstatusname;
    }

    public void setTrainstatusname(String trainstatusname) {
        this.trainstatusname = trainstatusname;
    }

    public int getModifyserial() {
        return modifyserial;
    }

    public void setModifyserial(int modifyserial) {
        this.modifyserial = modifyserial;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public SearchParams getSearch() {
        if (search == null)
            search = new SearchParams();

        return search;
    }

    public void setSearch(SearchParams search) {
        this.search = search;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<UserPlanYearDetail> getItem() {
        if (item == null)
            item = new SelectBean<UserPlanYearDetail>();

        return item;
    }

    public void setItem(SelectBean<UserPlanYearDetail> item) {
        this.item = item;
    }

}
