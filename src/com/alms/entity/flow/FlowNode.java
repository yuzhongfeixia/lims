package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class FlowNode implements BaseBean {

    // 节点编号;
    private String flownode;

    // 业务流程;
    private String busflow;

    // 节点名称;
    private String flownodename;

    // 节点类型;
    private String nodetype;

    // 节点类型名称;
    private String nodetypename;

    // 节点执行序号;
    private int nodeserial;

    // 首次超期天数;
    private int firstdays;

    // 二次超期天数;
    private int seconddays;

    // 数据操作范围;
    private String dataauth;

    // 数据操作范围名称;
    private String dataauthname;

    // 节点操作形式;
    private String nodeoper;

    // 节点操作形式名称;
    private String nodeopername;

    // 是否短信提醒;
    private boolean issms;

    // 是否邮件提醒;
    private boolean isemail;

    // 超期是否短信提醒;
    private boolean isoversms;

    // 是否上传附件;
    private boolean isfile;

    // 业务编号代码;
    private String trancode;

    // 工时（按工时单位）;
    private double worktime;

    // 执行角色
    private String noderole;

    // 执行角色名称
    private String noderolenames;

    // 按钮
    private String flowbuttons;

    // 按钮名称
    private String flowbuttonnames;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FlowNode> item;

    public FlowNode() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getFlownode())) {
            msg.setErrmsg("节点编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getFlownodename())) {
            msg.setErrmsg("节点名称不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.StringIsEmpty(this.getBusflow())) {
            msg.setErrmsg("业务流程编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.CheckComboValue(this.getDataauth())) {
            msg.setErrmsg("请选择数据操作范围！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.CheckComboValue(this.getNodeoper())) {
            msg.setErrmsg("请选择节点操作形式！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.CheckComboValue(this.getNodetype())) {
            msg.setErrmsg("请选择节点类型！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (this.getFirstdays() > 0) {
            if ((this.getSeconddays() > 0) && (this.getSeconddays() < this.getFirstdays())) {
                msg.setErrmsg("当首次超期天数和二次超期天数都大于0时，二次超期天数必须大于首次超期天数！" + ToolUtils.GetNewLines());
                rtn = true;
            }
        } else {
            if (this.getSeconddays() > 0) {
                msg.setErrmsg("当二次超期天数大于0时，首次超期天数必须大于0！" + ToolUtils.GetNewLines());
                rtn = true;
            }
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((FlowNode) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "节点编号:flownode", "业务流程:busflow", "节点名称:flownodename", "节点类型:nodetype",
                "节点类型名称:nodetypename", "节点执行序号:nodeserial", "首次超期天数:firstdays", "二次超期天数:seconddays", "数据操作范围:dataauth",
                "数据操作范围名称:dataauthname", "节点操作形式:nodeoper", "节点操作形式名称:nodeopername", "是否短信提醒:issms", "是否邮件提醒:isemail",
                "超期是否短信提醒:isoversms", "是否上传附件:isfile", "业务编号代码:trancode", "工时（按工时单位）:worktime" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.flownode = "";
        this.busflow = "";
        this.flownodename = "";
        this.nodetype = "";
        this.nodetypename = "";
        this.nodeserial = 0;
        this.firstdays = 0;
        this.seconddays = 0;
        this.dataauth = "";
        this.dataauthname = "";
        this.nodeoper = "";
        this.nodeopername = "";
        this.issms = false;
        this.isemail = false;
        this.isoversms = false;
        this.isfile = false;
        this.trancode = "";
        this.worktime = 0;
    }

    public String getFlownode() {
        return flownode;
    }

    public void setFlownode(String flownode) {
        this.flownode = flownode;
    }

    public String getBusflow() {
        return busflow;
    }

    public void setBusflow(String busflow) {
        this.busflow = busflow;
    }

    public String getFlownodename() {
        return flownodename;
    }

    public void setFlownodename(String flownodename) {
        this.flownodename = flownodename;
    }

    public String getNodetype() {
        return nodetype;
    }

    public void setNodetype(String nodetype) {
        this.nodetype = nodetype;
    }

    public String getNodetypename() {
        return nodetypename;
    }

    public void setNodetypename(String nodetypename) {
        this.nodetypename = nodetypename;
    }

    public int getNodeserial() {
        return nodeserial;
    }

    public void setNodeserial(int nodeserial) {
        this.nodeserial = nodeserial;
    }

    public int getFirstdays() {
        return firstdays;
    }

    public void setFirstdays(int firstdays) {
        this.firstdays = firstdays;
    }

    public int getSeconddays() {
        return seconddays;
    }

    public void setSeconddays(int seconddays) {
        this.seconddays = seconddays;
    }

    public String getDataauth() {
        return dataauth;
    }

    public void setDataauth(String dataauth) {
        this.dataauth = dataauth;
    }

    public String getDataauthname() {
        return dataauthname;
    }

    public void setDataauthname(String dataauthname) {
        this.dataauthname = dataauthname;
    }

    public String getNodeoper() {
        return nodeoper;
    }

    public void setNodeoper(String nodeoper) {
        this.nodeoper = nodeoper;
    }

    public String getNodeopername() {
        return nodeopername;
    }

    public void setNodeopername(String nodeopername) {
        this.nodeopername = nodeopername;
    }

    public boolean getIssms() {
        return issms;
    }

    public void setIssms(boolean issms) {
        this.issms = issms;
    }

    public boolean getIsemail() {
        return isemail;
    }

    public void setIsemail(boolean isemail) {
        this.isemail = isemail;
    }

    public boolean getIsoversms() {
        return isoversms;
    }

    public void setIsoversms(boolean isoversms) {
        this.isoversms = isoversms;
    }

    public boolean getIsfile() {
        return isfile;
    }

    public void setIsfile(boolean isfile) {
        this.isfile = isfile;
    }

    public String getTrancode() {
        return trancode;
    }

    public void setTrancode(String trancode) {
        this.trancode = trancode;
    }

    public double getWorktime() {
        return worktime;
    }

    public void setWorktime(double worktime) {
        this.worktime = worktime;
    }

    public String getNoderole() {
        return noderole;
    }

    public void setNoderole(String noderole) {
        this.noderole = noderole;
    }

    public String getFlowbuttons() {
        return flowbuttons;
    }

    public void setFlowbuttons(String flowbuttons) {
        this.flowbuttons = flowbuttons;
    }

    public String getNoderolenames() {
        return noderolenames;
    }

    public void setNoderolenames(String noderolenames) {
        this.noderolenames = noderolenames;
    }

    public String getFlowbuttonnames() {
        return flowbuttonnames;
    }

    public void setFlowbuttonnames(String flowbuttonnames) {
        this.flowbuttonnames = flowbuttonnames;
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

    public SelectBean<FlowNode> getItem() {
        if (item == null)
            item = new SelectBean<FlowNode>();

        return item;
    }

    public void setItem(SelectBean<FlowNode> item) {
        this.item = item;
    }

}
