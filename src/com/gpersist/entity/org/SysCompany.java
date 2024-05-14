package com.gpersist.entity.org;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.*;
import com.gpersist.utils.*;

public class SysCompany implements BaseBean {

    // 公司编码;
    private String coid;

    // 公司名称;
    private String coname;

    // 公司简称;
    private String coshort;

    // 打印抬头;
    private String coheader;

    // 排序序号;
    private int sortorder;

    // 公司类型;
    private String cotype;

    // 公司类型名称;
    private String cotypename;

    // 公司状态;
    private String costatus;

    // 公司状态名称;
    private String costatusname;

    // 备用;
    private String remark;

    private DataDeal deal;

    private SelectBean<SysCompany> item;

    public SysCompany() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.coid)) {
            msg.setErrmsg("公司编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.coname)) {
            msg.setErrmsg("公司名称不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.StringIsEmpty(this.coshort)) {
            msg.setErrmsg("公司简称不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.StringIsEmpty(this.coheader)) {
            msg.setErrmsg("打印抬头不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }
        if (ToolUtils.MustComboValue(this.cotype)) {
            msg.setErrmsg("请选择公司类型！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.costatus)) {
            msg.setErrmsg("请选择公司状态！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysCompany) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "公司编码:coid", "公司名称:coname", "公司简称:coshort", "打印抬头:coheader", "排序序号:sortorder",
                "公司类型:cotype", "公司类型名称:cotypename", "公司状态:costatus", "公司状态名称:costatusname", "备用:remark" };
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.coid = "";
        this.coname = "";
        this.coshort = "";
        this.coheader = "";
        this.sortorder = 0;
        this.cotype = "";
        this.cotypename = "";
        this.costatus = "";
        this.costatusname = "";
        this.remark = "";
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    public String getConame() {
        return coname;
    }

    public void setConame(String coname) {
        this.coname = coname;
    }

    public String getCoshort() {
        return coshort;
    }

    public void setCoshort(String coshort) {
        this.coshort = coshort;
    }

    public String getCoheader() {
        return coheader;
    }

    public void setCoheader(String coheader) {
        this.coheader = coheader;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    public String getCotype() {
        return cotype;
    }

    public void setCotype(String cotype) {
        this.cotype = cotype;
    }

    public String getCotypename() {
        return cotypename;
    }

    public void setCotypename(String cotypename) {
        this.cotypename = cotypename;
    }

    public String getCostatus() {
        return costatus;
    }

    public void setCostatus(String costatus) {
        this.costatus = costatus;
    }

    public String getCostatusname() {
        return costatusname;
    }

    public void setCostatusname(String costatusname) {
        this.costatusname = costatusname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<SysCompany> getItem() {
        if (item == null)
            item = new SelectBean<SysCompany>();

        return item;
    }

    public void setItem(SelectBean<SysCompany> item) {
        this.item = item;
    }

}
