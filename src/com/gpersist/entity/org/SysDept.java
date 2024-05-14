package com.gpersist.entity.org;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.ToolUtils;

public class SysDept implements BaseBean {

    // 机构编号;
    private String deptid;

    // 机构名称;
    private String deptname;

    // 机构简称;
    private String deptshort;

    // 所属公司;
    private String coid;

    // 公司名称;
    private String coname;

    // 上级机构编号;
    private String deptpid;

    // 城市代码;
    private String citycode;

    // 机构级别;
    private String deptlevel;

    // 部门级别名称;
    private String deptlevelname;

    // 机构类别;
    private String depttype;

    // 部门类型名称;
    private String depttypename;

    // 机构层次深度;
    private int deptdepth;

    // 排序序号;
    private int sortorder;

    // 机构状态;
    private String deptstatus;

    // 部门状态名称;
    private String deptstatusname;

    // 备用;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SysDept> item;

    public SysDept() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getDeptid())) {
            msg.setErrmsg("机构编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getDeptname())) {
            msg.setErrmsg("机构名称不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.StringIsEmpty(this.getDeptpid())) {
            msg.setErrmsg("上级机构编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.getDeptlevel())) {
            msg.setErrmsg("请选择机构级别！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.getDeptstatus())) {
            msg.setErrmsg("请选择机构状态！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.getDepttype())) {
            msg.setErrmsg("请选择机构类型！" + ToolUtils.GetNewLines());
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
        return ToolUtils.CompareProperty((SysDept) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "机构编号:deptid", "机构名称:deptname", "机构简称:deptshort", "所属公司:coid", "公司名称:coname",
                "上级机构编号:deptpid", "城市代码:citycode", "机构级别:deptlevel", "部门级别名称:deptlevelname", "机构类别:depttype",
                "部门类型名称:depttypename", "机构层次深度:deptdepth", "排序序号:sortorder", "机构状态:deptstatus", "部门状态名称:deptstatusname",
                "备用:remark" };
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.deptid = "";
        this.deptname = "";
        this.deptshort = "";
        this.coid = "";
        this.coname = "";
        this.deptpid = "";
        this.citycode = "";
        this.deptlevel = "";
        this.deptlevelname = "";
        this.depttype = "";
        this.depttypename = "";
        this.deptdepth = 0;
        this.sortorder = 0;
        this.deptstatus = "";
        this.deptstatusname = "";
        this.remark = "";
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

    public String getDeptshort() {
        return deptshort;
    }

    public void setDeptshort(String deptshort) {
        this.deptshort = deptshort;
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

    public String getDeptpid() {
        return deptpid;
    }

    public void setDeptpid(String deptpid) {
        this.deptpid = deptpid;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDeptlevel() {
        return deptlevel;
    }

    public void setDeptlevel(String deptlevel) {
        this.deptlevel = deptlevel;
    }

    public String getDeptlevelname() {
        return deptlevelname;
    }

    public void setDeptlevelname(String deptlevelname) {
        this.deptlevelname = deptlevelname;
    }

    public String getDepttype() {
        return depttype;
    }

    public void setDepttype(String depttype) {
        this.depttype = depttype;
    }

    public String getDepttypename() {
        return depttypename;
    }

    public void setDepttypename(String depttypename) {
        this.depttypename = depttypename;
    }

    public int getDeptdepth() {
        return deptdepth;
    }

    public void setDeptdepth(int deptdepth) {
        this.deptdepth = deptdepth;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    public String getDeptstatus() {
        return deptstatus;
    }

    public void setDeptstatus(String deptstatus) {
        this.deptstatus = deptstatus;
    }

    public String getDeptstatusname() {
        return deptstatusname;
    }

    public void setDeptstatusname(String deptstatusname) {
        this.deptstatusname = deptstatusname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<SysDept> getItem() {
        if (item == null)
            item = new SelectBean<SysDept>();

        return item;
    }

    public void setItem(SelectBean<SysDept> item) {
        this.item = item;
    }

}
