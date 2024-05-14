package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.ToolUtils;

public class BasCity implements BaseBean {

    // 城市编号;
    private String cityid;

    // 城市名称;
    private String cityname;

    // 所属省份编号;
    private String provinceid;

    // 所属省份名称;
    private String provincename;

    private DataDeal deal;

    private SelectBean<BasCity> item;

    private SearchParams search;

    public BasCity() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getCityname())) {
            msg.setErrmsg("请输入城市名称！" + ToolUtils.GetNewLines());
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
        this.cityid = "";
        this.cityname = "";
        this.provinceid = "";
        this.provincename = "";
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
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

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BasCity> getItem() {
        if (item == null)
            item = new SelectBean<BasCity>();

        return item;
    }

    public void setItem(SelectBean<BasCity> item) {
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
        return ToolUtils.CompareProperty((BasCity) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "城市编号:cityid", "城市名称:cityname", "所属省份编号:provinceid", "所属省份名称:provincename" };
    }

}
