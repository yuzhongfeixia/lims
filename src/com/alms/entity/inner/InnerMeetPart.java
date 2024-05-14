package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerMeetPart implements BaseBean {

    // 会议编号;
    private String meetid;

    // 参与人员;
    private String partuser;
    private String partusername;
    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerMeetPart> item;

    public InnerMeetPart() {
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
        return ToolUtils.CompareProperty((InnerMeetPart) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "会议编号:meetid", "参与人员:partuser" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.meetid = "";
        this.partuser = "";
    }

    public String getMeetid() {
        return meetid;
    }

    public void setMeetid(String meetid) {
        this.meetid = meetid;
    }

    public String getPartuser() {
        return partuser;
    }

    public void setPartuser(String partuser) {
        this.partuser = partuser;
    }

    public String getPartusername() {
        return partusername;
    }

    public void setPartusername(String partusername) {
        this.partusername = partusername;
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

    public SelectBean<InnerMeetPart> getItem() {
        if (item == null)
            item = new SelectBean<InnerMeetPart>();

        return item;
    }

    public void setItem(SelectBean<InnerMeetPart> item) {
        this.item = item;
    }

}
