package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.ToolUtils;

public class BasProvince implements BaseBean {

    // 城市编号;
    private String provinceid;

    // 城市名称;
    private String provincename;

    // 所属省份编号;
    private String countryid;

    // 所属省份名称;
    private String countryname;

    private DataDeal deal;

    private SelectBean<BasProvince> item;

    private SearchParams search;

    public BasProvince() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getProvincename())) {
            msg.setErrmsg("请输入省份名称！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.provinceid = "";
        this.provincename = "";
        this.countryid = "";
        this.countryname = "";
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BasProvince> getItem() {
        if (item == null)
            item = new SelectBean<BasProvince>();

        return item;
    }

    public void setItem(SelectBean<BasProvince> item) {
        this.item = item;
    }

    public SearchParams getSearch() {
        if (search == null) {
            search = new SearchParams();
        }
        return search;
    }

    public void setSearch(SearchParams search) {
        this.search = search;
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((BasProvince) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "省份编号:provinceid", "省份名称:provincename", "所属国家编号:countryid", "所属国家名称:countryname" };
    }

}
