package com.alms.service;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.DatDao;
import com.alms.dao.FormDao;
import com.alms.dao.LabDao;
import com.alms.entity.dat.BusReport;
import com.alms.entity.dat.BusReportDetail;
import com.alms.entity.dat.SetBusGet;
import com.alms.entity.dat.SetBusGetDetail;
import com.alms.entity.dat.SetBusRecord;
import com.alms.entity.dat.SetBusRecordDetail;
import com.alms.entity.dat.SetBusReport;
import com.alms.entity.dat.SetBusReportDetail;
import com.alms.entity.form.FrmGet;
import com.alms.entity.form.FrmGetData;
import com.alms.entity.form.FrmGetDetail;
import com.alms.entity.form.FrmRecord;
import com.alms.entity.form.FrmRecordDetail;
import com.alms.entity.form.FrmRecordParameter;
import com.alms.entity.form.FrmReport;
import com.alms.entity.form.FrmReportDetail;
import com.alms.entity.form.IntField;
import com.alms.entity.form.IntInterface;
import com.alms.entity.lab.BusAccSample;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusTaskSingle;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class FormService {

    // region IntInterface Methods

    public static void SaveIntInterface(IntInterface item, List<IntField> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FormDao.SaveIntInterface(session, item);

            IntField del = new IntField();
            del.getDeal().setAction(DataAction.Delete.getAction());
            del.setIntid(item.getIntid());
            FormDao.SaveIntField(session, del);

            for (IntField intfield : details) {
                intfield.getDeal().setAction(DataAction.Create.getAction());
                intfield.setIntid(item.getIntid());
                FormDao.SaveIntField(session, intfield);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getIntid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"intid\":\"" + item.getIntid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion IntInterface Methods

    // region FrmRecord Methods

    public static SetBusRecord GetSetFrmRecord(FrmRecord item) {
        SetBusRecord rtn = new SetBusRecord();

        SqlSession session = DBUtils.getFactory();

        try {
            if (!ToolUtils.StringIsEmpty(item.getFormid())) {
                rtn.setForm(FormDao.GetFrmRecord(session, item));

                SetBusRecordDetail sd = new SetBusRecordDetail();
                sd.setFormserial(1);
                sd.setDatas(FormDao.GetPreviewRecordDetailByID(session, item));

                rtn.getDetails().add(sd);
            }

            return rtn;
        } catch (Exception e) {
            return new SetBusRecord();
        } finally {
            session.close();
        }
    }

    public static void SaveFrmRecord(FrmRecord item, List<FrmRecordDetail> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setCreateuser(log.getTranuser());
            item.setCreateusername(log.getUsername());
            item.setModifyuser(log.getTranuser());
            item.setModifyusername(log.getUsername());

            FormDao.SaveFrmRecord(session, item);

            FrmRecordDetail ditem = new FrmRecordDetail();
            ditem.getDeal().setAction(DataAction.Delete.getAction());
            ditem.setFormid(item.getFormid());
            FormDao.SaveFrmRecordDetail(session, ditem);

            String insertsql = "insert into t_frm_record_detail(formid, cellserial, beginrow, endrow, begincolumn, endcolumn, cellname, "
                    + "valuesource, valuetype, classsource, fieldcode, groupserial, specserial, celltext, cellformat, isborder, isline, "
                    + "isbold, fontsize, aligntype, prefixtext, postfixtext, ismutil) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = session.getConnection().prepareStatement(insertsql);

            for (FrmRecordDetail detail : details) {
                // detail.getDeal().setAction(DataAction.Create.getAction());
                // detail.setFormid(item.getFormid());

                ps.setString(1, item.getFormid());
                ps.setInt(2, detail.getCellserial());
                ps.setInt(3, detail.getBeginrow());
                ps.setInt(4, detail.getEndrow());
                ps.setInt(5, detail.getBegincolumn());
                ps.setInt(6, detail.getEndcolumn());
                ps.setString(7, detail.getCellname());
                ps.setString(8, detail.getValuesource());
                ps.setString(9, detail.getValuetype());
                ps.setString(10, detail.getClasssource());
                ps.setString(11, detail.getFieldcode());
                ps.setInt(12, detail.getGroupserial());
                ps.setInt(13, detail.getSpecserial());
                ps.setString(14, detail.getCelltext());
                ps.setString(15, detail.getCellformat());
                ps.setBoolean(16, detail.isIsborder());
                ps.setBoolean(17, detail.getIsline());
                ps.setBoolean(18, detail.getIsbold());
                ps.setInt(19, detail.getFontsize());
                ps.setString(20, detail.getAligntype());
                ps.setString(21, detail.getPrefixtext());
                ps.setString(22, detail.getPostfixtext());
                ps.setBoolean(23, detail.isIsmutil());
                ps.addBatch();

                // FormDao.SaveFrmRecordDetail(session, detail);
            }
            ps.executeBatch();

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getFormid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"formid\":\"" + item.getFormid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion FrmRecord Methods

    // region FrmGet Methods

    public static SetBusGet GetSetFrmGet(FrmGet item) {
        SetBusGet rtn = new SetBusGet();

        SqlSession session = DBUtils.getFactory();

        try {
            if (item.getFormid().equals("000010") || item.getFormid().equals("000012")) {
                rtn.setForm(FormDao.GetFrmGet(session, item));

                BusGet bGet = new BusGet();
                bGet.setTranid(item.getTranid());
                bGet = LabDao.GetBusGet(bGet);
                List<FrmGetData> nowdatas = SetGet(bGet);

                BusAccSample bSample = new BusAccSample();
                bSample.setGetid(item.getTranid());
                bSample = LabDao.GetBusAccSampleByGetID(bSample);
                List<FrmGetData> accsamples = SetAccSample(bSample);
                nowdatas.addAll(accsamples);

                BusGetDetail bDetail = new BusGetDetail();
                bDetail.setTranid(item.getTranid());
                bDetail.setSamplecode(item.getSamplecode());

                int i = 1;
                List<BusGetDetail> bDetails = LabDao.GetListBusGetDetail(bDetail);

                // 申报单显示有内容
                if (bDetails.size() > 0) {
                    int pagecouont = (int) Math.ceil(bDetails.size() / 9.0);
                    // for (int j = 0; j < pagecouont; j++) {

                    for (BusGetDetail bGetDetail : bDetails) {

                        List<FrmGetData> getdetails = SetGetDetail(bGetDetail, i);

                        nowdatas.addAll(getdetails);

                        String deletesql = "delete from t_frm_get_data where tranid = ?";

                        PreparedStatement ds = session.getConnection().prepareStatement(deletesql);

                        ds.setString(1, item.getTranid());
                        ds.addBatch();
                        ds.executeBatch();

                        // 将相关数据保存到T_Bus_Record_Data中
                        String insertsql = "insert into t_frm_get_data(formid, samplecode, tranid, fieldcode, celltext) "
                                + "values (?, ?, ?, ?, ?)";

                        PreparedStatement ps = session.getConnection().prepareStatement(insertsql);
                        for (FrmGetData data : nowdatas) {

                            ps.setString(1, item.getFormid());
                            ps.setString(2, data.getSamplecode());
                            ps.setString(3, item.getTranid());
                            ps.setString(4, data.getFieldcode());
                            ps.setString(5, data.getCelltext());
                            ps.addBatch();
                            data.getDeal().setAction(DataAction.Create.getAction());
                        }
                        ps.executeBatch();
                        i++;
                    }
                    SetBusGetDetail sd = new SetBusGetDetail();
                    sd.setFormserial(1);
                    item.setPagecount(1);
                    sd.setDatas(FormDao.GetPreviewGetDetailByID(session, item));

                    rtn.getDetails().add(sd);

                } else {
                    // 申报单是空的
                    SetBusGetDetail sd = new SetBusGetDetail();
                    sd.setFormserial(1);
                    item.setPagecount(i);
                    sd.setDatas(FormDao.GetPreviewGetDetailByID(session, item));

                    rtn.getDetails().add(sd);
                }

            } else if (!ToolUtils.StringIsEmpty(item.getFormid())) {
                rtn.setForm(FormDao.GetFrmGet(session, item));

                BusGet bGet = new BusGet();
                bGet.setTranid(item.getTranid());
                bGet = LabDao.GetBusGet(bGet);
                List<FrmGetData> nowdatas = SetGet(bGet);

                BusAccSample bSample = new BusAccSample();
                bSample.setGetid(item.getTranid());
                bSample = LabDao.GetBusAccSampleByGetID(bSample);
                List<FrmGetData> accsamples = SetAccSample(bSample);
                nowdatas.addAll(accsamples);

                BusGetDetail bDetail = new BusGetDetail();
                bDetail.setTranid(item.getTranid());
                bDetail.setSamplecode(item.getSamplecode());

                int i = 1;
                List<BusGetDetail> bDetails = LabDao.GetListBusGetDetail(bDetail);

                for (int k = 0; k < bDetails.size(); k++) {
                    if (bDetails.get(k).getGetsource().contains(",")) {
                        String[] aa = bDetails.get(k).getGetsource().split(",");

                        String sourcena = "";
                        for (int j = 0; j < aa.length; j++) {
                            BusGetDetail bgds = new BusGetDetail();
                            bgds.setGetsource(aa[j]);
                            BusGetDetail sourcename = LabDao.GetGetSourceName(bgds);
                            sourcena = sourcena + "&nbsp&nbsp&nbsp&nbsp" + sourcename.getGetsourcename();
                        }

                        bDetails.get(k).setGetsourcename(sourcena);

                    }

                    if (bDetails.get(k).getKeepwarm().contains(",")) {
                        String[] kw = bDetails.get(k).getKeepwarm().split(",");

                        String Keepwarm = "";
                        for (int j = 0; j < kw.length; j++) {
                            BusGetDetail bgds = new BusGetDetail();
                            bgds.setKeepwarm(kw[j]);
                            BusGetDetail Keepwarmname = LabDao.GetKeepWarmName(bgds);
                            Keepwarm = Keepwarm + "&nbsp&nbsp&nbsp&nbsp" + Keepwarmname.getKeepwarmname();
                        }
                        bDetails.get(k).setKeepwarmname(Keepwarm);

                    }

                }

                // 申报单显示有内容
                if (bDetails.size() > 0) {

                    for (BusGetDetail bGetDetail : bDetails) {

                        List<FrmGetData> getdetails = SetGetDetail(bGetDetail, i);

                        nowdatas.addAll(getdetails);

                        String deletesql = "delete from t_frm_get_data where tranid = ?";

                        PreparedStatement ds = session.getConnection().prepareStatement(deletesql);

                        ds.setString(1, item.getTranid());
                        ds.addBatch();
                        ds.executeBatch();

                        // 将相关数据保存到t_frm_get_data中
                        String insertsql = "insert into t_frm_get_data(formid, samplecode, tranid, fieldcode, celltext) "
                                + "values (?, ?, ?, ?, ?)";

                        PreparedStatement ps = session.getConnection().prepareStatement(insertsql);
                        for (FrmGetData data : nowdatas) {

                            ps.setString(1, item.getFormid());
                            ps.setString(2, data.getSamplecode());
                            ps.setString(3, item.getTranid());
                            ps.setString(4, data.getFieldcode());
                            ps.setString(5, data.getCelltext());
                            ps.addBatch();
                            data.getDeal().setAction(DataAction.Create.getAction());
                        }
                        ps.executeBatch();

                        SetBusGetDetail sd = new SetBusGetDetail();
                        sd.setFormserial(1);
                        item.setPagecount(i);
                        sd.setDatas(FormDao.GetPreviewGetDetailByID(session, item));

                        rtn.getDetails().add(sd);

                        i++;
                    }
                } else {
                    // 申报单是空的
                    SetBusGetDetail sd = new SetBusGetDetail();
                    sd.setFormserial(1);
                    item.setPagecount(i);
                    sd.setDatas(FormDao.GetPreviewGetDetailByID(session, item));

                    rtn.getDetails().add(sd);
                }

            }

            return rtn;
        } catch (Exception e) {
            return new SetBusGet();
        } finally {
            session.close();
        }
    }

    public static List<FrmGetData> SetGet(BusGet item) throws Exception {
        List<FrmGetData> datas = new ArrayList<FrmGetData>();

        Class demo = Class.forName("com.alms.entity.lab.BusGet");
        Field[] fields = demo.getDeclaredFields();

        if (item != null) {

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

                for (Field f : fields) {
                    f.setAccessible(true);
                    FrmGetData data = new FrmGetData();
                    if (f.getName() == "samplecode") {
                        continue;
                    } else {
                        data.setFieldcode(f.getName());
                    }

                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setCelltext(sp.format(f.get(item)));
                        else {
                            if (f.get(item).toString().equals("") || f.get(item).toString() == null) {
                                data.setCelltext("/");
                            } else {
                                data.setCelltext(f.get(item).toString());
                            }

                        }
                    }

                    datas.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return datas;
    }

    public static List<FrmGetData> SetGetDetail(BusGetDetail item, int i) throws Exception {
        List<FrmGetData> datas = new ArrayList<FrmGetData>();

        Class demo = Class.forName("com.alms.entity.lab.BusGetDetail");
        Field[] fields = demo.getDeclaredFields();

        if (item != null) {

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

                for (Field f : fields) {
                    if (f.getName().equals("parameterids")) {
                        continue;
                    }

                    if (f.getName().equals("packstatus")) {
                        continue;
                    }
                    if (f.getName().equals("markstatus")) {
                      continue;
                    }
                    if (f.getName().equals("certstatus")) {
                        continue;
                    }

                    if (f.getName().equals("getsource")) {
                        continue;
                    }

                    if (f.getName().equals("unitcharacte")) {
                        continue;
                    }

                    if (f.getName().equals("similarvariety")) {
                        continue;
                    }
                    if (f.getName().equals("keepwarm")) {
                        continue;
                    }

                    f.setAccessible(true);
                    FrmGetData data = new FrmGetData();
                    data.setFieldcode(f.getName() + i);
                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setCelltext(sp.format(f.get(item)));
                        else {
                            if (f.get(item).toString().equals("") || f.get(item).toString() == null) {
                                data.setCelltext("/");
                            } else {
                                data.setCelltext(f.get(item).toString());
                            }
                        }
                    }

                    datas.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return datas;
    }

    public static List<FrmGetData> SetAccSample(BusAccSample item) throws Exception {
        List<FrmGetData> datas = new ArrayList<FrmGetData>();

        Class demo = Class.forName("com.alms.entity.lab.BusAccSample");
        Field[] fields = demo.getDeclaredFields();

        if (item != null) {

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

                for (Field f : fields) {
                    f.setAccessible(true);
                    FrmGetData data = new FrmGetData();
                    data.setFieldcode(f.getName());

                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setCelltext(sp.format(f.get(item)));
                        else {
                            if (f.get(item).toString().equals("") || f.get(item).toString() == null) {
                                data.setCelltext("/");
                            } else {
                                data.setCelltext(f.get(item).toString());
                            }

                        }
                    }

                    datas.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return datas;
    }

    public static void SaveFrmGet(FrmGet item, List<FrmGetDetail> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setCreateuser(log.getTranuser());
            item.setCreateusername(log.getUsername());
            item.setModifyuser(log.getTranuser());
            item.setModifyusername(log.getUsername());

            FormDao.SaveFrmGet(session, item);

            FrmGetDetail ditem = new FrmGetDetail();
            ditem.getDeal().setAction(DataAction.Delete.getAction());
            ditem.setFormid(item.getFormid());
            FormDao.SaveFrmGetDetail(session, ditem);

            String insertsql = "insert into t_frm_get_detail(formid, cellserial, beginrow, endrow, begincolumn, endcolumn, cellname, "
                    + "valuesource, valuetype, classsource, fieldcode, groupserial, specserial, celltext, cellformat, isborder, isline, "
                    + "isbold, fontsize, aligntype, prefixtext, postfixtext, ismutil) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = session.getConnection().prepareStatement(insertsql);

            for (FrmGetDetail detail : details) {
                // detail.getDeal().setAction(DataAction.Create.getAction());
                // detail.setFormid(item.getFormid());

                ps.setString(1, item.getFormid());
                ps.setInt(2, detail.getCellserial());
                ps.setInt(3, detail.getBeginrow());
                ps.setInt(4, detail.getEndrow());
                ps.setInt(5, detail.getBegincolumn());
                ps.setInt(6, detail.getEndcolumn());
                ps.setString(7, detail.getCellname());
                ps.setString(8, detail.getValuesource());
                ps.setString(9, detail.getValuetype());
                ps.setString(10, detail.getClasssource());
                ps.setString(11, detail.getFieldcode());
                ps.setInt(12, detail.getGroupserial());
                ps.setInt(13, detail.getSpecserial());
                ps.setString(14, detail.getCelltext());
                ps.setString(15, detail.getCellformat());
                ps.setBoolean(16, detail.isIsborder());
                ps.setBoolean(17, detail.getIsline());
                ps.setBoolean(18, detail.getIsbold());
                ps.setInt(19, detail.getFontsize());
                ps.setString(20, detail.getAligntype());
                ps.setString(21, detail.getPrefixtext());
                ps.setString(22, detail.getPostfixtext());
                ps.setBoolean(23, detail.isIsmutil());
                ps.addBatch();

                // FormDao.SaveFrmGetDetail(session, detail);
            }
            ps.executeBatch();

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getFormid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion FrmRecord Methods

    // region FrmRecordParameter Methods

    public static void SaveFrmRecordParameter(FrmRecordParameter item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FormDao.SaveFrmRecordParameter(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static List<IntInterface> GetIntInterfaceByTask(BusTaskSingle item) {
        List<IntInterface> lists = new ArrayList<IntInterface>();
        // IntInterface baseiif = FormDao.GetIntInterfaceBase(item);
        // if(baseiif != null){
        // lists.add(baseiif);
        // }
        List<IntInterface> listiif = FormDao.GetIntInterfaceByTask(item);
        for (IntInterface intInterface : listiif) {
            lists.add(intInterface);
        }
        return lists;
    }

    // endregion FrmRecordParameter Methods

    // region FrmReport Methods

    public static void SaveFrmReport(FrmReport item, List<FrmReportDetail> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setCreateuser(log.getTranuser());
            item.setCreateusername(log.getUsername());
            item.setModifyuser(log.getTranuser());
            item.setModifyusername(log.getUsername());

            FormDao.SaveFrmReport(session, item);

            FrmReportDetail ditem = new FrmReportDetail();
            ditem.getDeal().setAction(DataAction.Delete.getAction());
            ditem.setFormid(item.getFormid());
            FormDao.SaveFrmReportDetail(session, ditem);

            String insertsql = "insert into t_frm_report_detail(formid, cellserial, beginrow, endrow, begincolumn, endcolumn, cellname, "
                    + "valuesource, valuetype, classsource, fieldcode, groupserial, specserial, celltext, cellformat, isborder, isline, "
                    + "isbold, fontsize, aligntype, prefixtext, postfixtext, ismutil,fonttype) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            PreparedStatement ps = session.getConnection().prepareStatement(insertsql);

            for (FrmReportDetail detail : details) {
                // detail.getDeal().setAction(DataAction.Create.getAction());
                // detail.setFormid(item.getFormid());

                ps.setString(1, item.getFormid());
                ps.setInt(2, detail.getCellserial());
                ps.setInt(3, detail.getBeginrow());
                ps.setInt(4, detail.getEndrow());
                ps.setInt(5, detail.getBegincolumn());
                ps.setInt(6, detail.getEndcolumn());
                ps.setString(7, detail.getCellname());
                ps.setString(8, detail.getValuesource());
                ps.setString(9, detail.getValuetype());
                ps.setString(10, detail.getClasssource());
                ps.setString(11, detail.getFieldcode());
                ps.setInt(12, detail.getGroupserial());
                ps.setInt(13, detail.getSpecserial());
                ps.setString(14, detail.getCelltext());
                ps.setString(15, detail.getCellformat());
                ps.setBoolean(16, detail.getIsborder());
                ps.setBoolean(17, detail.getIsline());
                ps.setBoolean(18, detail.getIsbold());
                ps.setInt(19, detail.getFontsize());
                ps.setString(20, detail.getAligntype());
                ps.setString(21, detail.getPrefixtext());
                ps.setString(22, detail.getPostfixtext());
                ps.setBoolean(23, detail.isIsmutil());
                ps.setString(24, detail.getFonttype());
                ps.addBatch();
            }
            ps.executeBatch();

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static SetBusReport GetSetFrmReport(FrmReport item) {
        SetBusReport rtn = new SetBusReport();

        SqlSession session = DBUtils.getFactory();

        try {
            if (!ToolUtils.StringIsEmpty(item.getFormid())) {
                rtn.setForm(FormDao.GetFrmReport(session, item));

                SetBusReportDetail sd = new SetBusReportDetail();
                sd.setFormserial(1);
                sd.setDatas(FormDao.GetPreviewReportDetailByID(session, item));

                rtn.getDetails().add(sd);
            }

            return rtn;
        } catch (Exception e) {
            return new SetBusReport();
        } finally {
            session.close();
        }
    }

    // endregion FrmReport Methods

    // region FrmReportDetail Methods

    public static void SaveFrmReportDetail(FrmReportDetail item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FormDao.SaveFrmReportDetail(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion FrmReportDetail Methods

    // region BusReport Methods

    public static void SaveBusPrintReport(BusReportDetail item, List<BusReportDetail> details, ReturnValue rtv,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            for (BusReportDetail detail : details) {
                detail.setReportid(item.getReportid());
                detail.getDeal().setAction(DataAction.Modify.getAction());
                FormDao.SaveBusPrintReport(session, detail);
                session.commit();
            }

            item.getDeal().setAction(DataAction.Special01.getAction());
            FormDao.SaveBusPrintReport(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);
            //
            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }
}
