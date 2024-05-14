package com.gpersist.entity.org;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

public class SimpleDept implements BaseBean {

    private String deptid;

    private String deptname;

    private String deptpid;

    public SimpleDept() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SimpleDept) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "机构编号:deptid", "机构名称:deptname", "父机构编号:deptpid" };
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.deptid = "";
        this.deptname = "";
        this.deptpid = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return null;
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

    public String getDeptpid() {
        return deptpid;
    }

    public void setDeptpid(String deptpid) {
        this.deptpid = deptpid;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }
}
