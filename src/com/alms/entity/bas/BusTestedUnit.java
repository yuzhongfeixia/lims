package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTestedUnit implements BaseBean {

    // 受检单位编号;
    private String testedid;

    // 受检单位名称;
    private String testedname;

    // 企业法人;
    private String enterlegal;

    // 通讯地址;
    private String enteraddr;

    // 邮编;
    private String enterpost;

    // 电话;
    private String entertele;

    // 传真
    private String enterfax;

    // 受检单位性质;
    private String entertype;

    // 受检单位性质
    private String entertypename;

    // 受检单位规模;
    private String enterscale;

    // 受检单位规模
    private String enterscalename;

    // 检测类别;
    private String comtype;

    // 检测类别名称;
    private String comtypename;

    // 种植产品;
    private String plantcorps;

    // 种植面积;
    private String plantarea;

    // 所在省;
    private String provinceid;

    // 省份名称;
    private String provincename;

    // 所在市;
    private String cityid;

    // 城市名称;
    private String cityname;

    // 所在区/县;
    private String areaid;

    // 地区名称;
    private String areaname;

    // 所在区/县;
    private String testtype;

    // 地区名称;
    private String testtypename;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTestedUnit> item;

    public BusTestedUnit() {
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
        return ToolUtils.CompareProperty((BusTestedUnit) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "受检单位编号:testedid", "受检单位名称:testedname", "企业法人:enterlegal", "通讯地址:enteraddr",
                "邮编:enterpost", "电话:entertele", "受检单位性质:entertype", "受检单位规模:enterscale", "检测类别:comtype",
                "检测类别名称:comtypename", "种植产品:plantcorps", "种植面积:plantarea", "所在省:provinceid", "省份名称:provincename",
                "所在市:cityid", "城市名称:cityname", "所在区/县:areaid", "地区名称:areaname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.testedid = "";
        this.testedname = "";
        this.enterlegal = "";
        this.enteraddr = "";
        this.enterpost = "";
        this.enterfax = "";
        this.entertele = "";
        this.entertype = "";
        this.enterscale = "";
        this.comtype = "";
        this.comtypename = "";
        this.plantcorps = "";
        this.plantarea = "";
        this.provinceid = "";
        this.provincename = "";
        this.cityid = "";
        this.cityname = "";
        this.areaid = "";
        this.areaname = "";
        this.testtype = "";
        this.testtypename = "";
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getTesttypename() {
        return testtypename;
    }

    public void setTesttypename(String testtypename) {
        this.testtypename = testtypename;
    }

    public String getTestedid() {
        return testedid;
    }

    public void setTestedid(String testedid) {
        this.testedid = testedid;
    }

    public String getTestedname() {
        return testedname;
    }

    public void setTestedname(String testedname) {
        this.testedname = testedname;
    }

    public String getEnterlegal() {
        return enterlegal;
    }

    public void setEnterlegal(String enterlegal) {
        this.enterlegal = enterlegal;
    }

    public String getEnteraddr() {
        return enteraddr;
    }

    public void setEnteraddr(String enteraddr) {
        this.enteraddr = enteraddr;
    }

    public String getEnterpost() {
        return enterpost;
    }

    public void setEnterpost(String enterpost) {
        this.enterpost = enterpost;
    }

    public String getEntertele() {
        return entertele;
    }

    public String getEnterfax() {
        return enterfax;
    }

    public void setEnterfax(String enterfax) {
        this.enterfax = enterfax;
    }

    public void setEntertele(String entertele) {
        this.entertele = entertele;
    }

    public String getEntertype() {
        return entertype;
    }

    public void setEntertype(String entertype) {
        this.entertype = entertype;
    }

    public String getEnterscale() {
        return enterscale;
    }

    public void setEnterscale(String enterscale) {
        this.enterscale = enterscale;
    }

    public String getEntertypename() {
        return entertypename;
    }

    public void setEntertypename(String entertypename) {
        this.entertypename = entertypename;
    }

    public String getEnterscalename() {
        return enterscalename;
    }

    public void setEnterscalename(String enterscalename) {
        this.enterscalename = enterscalename;
    }

    public String getComtype() {
        return comtype;
    }

    public void setComtype(String comtype) {
        this.comtype = comtype;
    }

    public String getComtypename() {
        return comtypename;
    }

    public void setComtypename(String comtypename) {
        this.comtypename = comtypename;
    }

    public String getPlantcorps() {
        return plantcorps;
    }

    public void setPlantcorps(String plantcorps) {
        this.plantcorps = plantcorps;
    }

    public String getPlantarea() {
        return plantarea;
    }

    public void setPlantarea(String plantarea) {
        this.plantarea = plantarea;
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

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
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

    public SelectBean<BusTestedUnit> getItem() {
        if (item == null)
            item = new SelectBean<BusTestedUnit>();

        return item;
    }

    public void setItem(SelectBean<BusTestedUnit> item) {
        this.item = item;
    }

}
