package com.alms.entity.dat;

import java.util.ArrayList;
import java.util.List;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SetBusGetDetail implements BaseBean {

    private int formserial;

    private List<BusRecordDetail> datas;

    private DataDeal deal;

    private SelectBean<SetBusGetDetail> item;

    public SetBusGetDetail() {
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
        this.formserial = 0;
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SetBusGetDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<SetBusGetDetail> getItem() {
        if (item == null)
            item = new SelectBean<SetBusGetDetail>();

        return item;
    }

    public void setItem(SelectBean<SetBusGetDetail> item) {
        this.item = item;
    }

    public int getFormserial() {
        return formserial;
    }

    public void setFormserial(int formserial) {
        this.formserial = formserial;
    }

    public List<BusRecordDetail> getDatas() {
        if (datas == null)
            datas = new ArrayList<BusRecordDetail>();

        return datas;
    }

    public void setDatas(List<BusRecordDetail> datas) {
        this.datas = datas;
    }

}
