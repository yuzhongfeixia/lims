package com.gpersist.entity.user;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.ToolUtils;

public class SysWorkGroup implements BaseBean {

    private String workgroup;

    private String workgroupname;

    private DataDeal deal;

    private SearchParams search;

    private SelectBean<SysWorkGroup> item;

    public SysWorkGroup() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getWorkgroup())) {
            msg.setErrmsg("工作组编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getWorkgroupname())) {
            msg.setErrmsg("工作组姓名不能为空！" + ToolUtils.GetNewLines());
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
        return ToolUtils.CompareProperty((SysUserWorkGroup) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "工作组编号:workgroup", "工作组名称:workgroupname" };
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.workgroup = "";
        this.workgroupname = "";
    }

    public String getWorkgroup() {
        return workgroup;
    }

    public void setWorkgroup(String workgroup) {
        this.workgroup = workgroup;
    }

    public String getWorkgroupname() {
        return workgroupname;
    }

    public void setWorkgroupname(String workgroupname) {
        this.workgroupname = workgroupname;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<SysWorkGroup> getItem() {
        if (item == null)
            item = new SelectBean<SysWorkGroup>();

        return item;
    }

    public void setItem(SelectBean<SysWorkGroup> item) {
        this.item = item;
    }

    public SearchParams getSearch() {
        if (search == null)
            search = new SearchParams();

        return search;
    }

    public void setSearch(SearchParams search) {
        this.search = search;
    }

}
