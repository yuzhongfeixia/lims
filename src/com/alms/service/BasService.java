package com.alms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.BasDao;
import com.alms.entity.bas.*;
import com.gpersist.dao.OrgDao;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.org.SysDept;
import com.gpersist.entity.publics.TreeItem;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class BasService {

    // region BasTest Methods

    public static void SaveBasTest(BasTest item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getUser().getUserid());
            item.setTrandate(new Date());

            BasDao.SaveBasTest(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTeststandard());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"teststandard\":\"" + item.getTeststandard() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            System.out.println(e);
            session.rollback();
            rtv.setMsg("当前编号已存在");
        } finally {
            session.close();
        }
    }

    // endregion BasTest Methods

    // region AbilityForm Methods

    public static void SaveAbilityForm(AbilityForm item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getUser().getUserid());
            item.setTrandate(new Date());

            BasDao.SaveAbilityForm(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getAbilityformid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"teststandard\":\"" + item.getAbilityformid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            System.out.println(e);
            session.rollback();
            rtv.setMsg("当前编号已存在");
        } finally {
            session.close();
        }
    }

    // endregion AbilityForm Methods

    // region BasJudge Methods

    public static void SaveBasJudge(BasJudge item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());

            BasDao.SaveBasJudge(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getJudgestandard());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"judgestandard\":\"" + item.getJudgestandard() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasJudge Methods

    // region BasParameter Methods

    public static void SaveBasParameter(BasParameter item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasParameter(session, item);

            BasLabParameter bParameter = new BasLabParameter();
            bParameter.setParameterid(item.getParameterid());
            bParameter.setLabid(item.getLabid());
            bParameter.getDeal().setAction(item.getDeal().getAction());
            BasDao.SaveBasLabParameter(session, bParameter);

            log.ActionToTran(item.getDeal().getAction());

            log.setTrandesc(item.getParameterid());

            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"parameterid\":\"" + item.getParameterid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasParameter Methods

    // region BasSample Methods

    public static void SaveBasSample(BasSample item, List<BasSampleParameter> params, List<BasSampleStand> sampleStands,
            ReturnValue rtv, TranLog log) {

        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasSample(session, item);

            // 删除参数明细
            BasSampleParameter bsp = new BasSampleParameter();
            bsp.setSampleid(item.getSampleid());
            bsp.getDeal().setAction(DataAction.Delete.getAction());
            BasDao.SaveBasSampleParameter(session, bsp);

            BasSampleStand bss = new BasSampleStand();
            bss.setSampleid(item.getSampleid());
            bss.getDeal().setAction(DataAction.Delete.getAction());
            BasDao.SaveBasSampleStand(session, bss);
            // BasSampleTest bst = new BasSampleTest();
            // bst.setSampleid(item.getSampleid());
            // bst.getDeal().setAction(DataAction.Delete.getAction());
            // BasDao.SaveBasSampleTest(session, bst);
            // BasSampleJudge bsj = new BasSampleJudge();
            // bsj.setSampleid(item.getSampleid());
            // bsj.getDeal().setAction(DataAction.Delete.getAction());
            // BasDao.SaveBasSampleJudge(session, bsj);
            //
            // BasSampleReplace bsr = new BasSampleReplace();
            // bsr.setSampleid(item.getSampleid());
            // bsr.getDeal().setAction(DataAction.Delete.getAction());
            // BasDao.SaveBasSampleReplace(session, bsr);
            //
            // 保存参数明细
            for (BasSampleParameter param : params) {
                param.setSampleid(item.getSampleid());
                param.getDeal().setAction(DataAction.Create.getAction());
                BasDao.SaveBasSampleParameter(session, param);
            }

            for (BasSampleStand bStand : sampleStands) {
                bStand.setSampleid(item.getSampleid());
                bStand.getDeal().setAction(DataAction.Create.getAction());
                BasDao.SaveBasSampleStand(session, bStand);
            }
            //
            // BasSampleReplace basSampleReplace = new BasSampleReplace();
            // //保存检测依据明细
            // for(BasSampleTest test:tests){
            // if(param.getParameterid().equals(test.getParameterid())){
            // test.setSampleid(item.getSampleid());
            // test.getDeal().setAction(DataAction.Create.getAction());
            // BasDao.SaveBasSampleTest(session, test);
            // //样品替代表新增
            // basSampleReplace.setTeststandard(test.getTeststandard());
            // BasTest basTest = new BasTest();
            // basTest.setTeststandard(test.getTeststandard());
            // basTest = BasDao.GetBasTest(basTest);
            // basSampleReplace.setTeststandardcode(basTest.getTeststandardcode());
            // basSampleReplace.setTeststandardname(basTest.getTeststandardname());
            // }
            // }
            //
            // for(BasSampleJudge judge:judges){
            // if(param.getParameterid().equals(judge.getParameterid())){
            // judge.setSampleid(item.getSampleid());
            // judge.getDeal().setAction(DataAction.Create.getAction());
            // BasDao.SaveBasSampleJudge(session, judge);
            //
            // //样品替代表新增
            // basSampleReplace.setJudgestandard(judge.getJudgestandard());
            // BasJudge basJudge = new BasJudge();
            // basJudge.setJudgestandard(judge.getJudgestandard());
            // basJudge = BasDao.GetBasJudge(basJudge);
            // basSampleReplace.setJudgestandardcode(basJudge.getJudgestandardcode());
            // basSampleReplace.setJudgestandardname(basJudge.getJudgestandardname());
            // }
            // }

            // 样品替代表新增
            // basSampleReplace.setSamplemain(item.getSamplemain());
            // basSampleReplace.setSamplemainname(item.getSamplemainname());
            // basSampleReplace.setSampleid(item.getSampleid());
            // basSampleReplace.setSamplename(item.getSamplename());
            // basSampleReplace.setParameterid(param.getParameterid());
            // basSampleReplace.setParametername(param.getParametername());
            // basSampleReplace.setTestjudge(param.getTestjudge());
            // basSampleReplace.setStandvalue(param.getStandvalue());
            // basSampleReplace.getDeal().setAction(DataAction.Create.getAction());
            // BasDao.SaveBasSampleReplace(session, basSampleReplace);

            // }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getSampleid() + "-" + item.getSamplename());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"sampleid\":\"" + item.getSampleid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasSample Methods

    // region BasSampleMain Methods

    public static void SaveBasSampleMain(BasSampleMain item, List<BasMainParameter> params,
            List<BasMainParameter> tests, List<BasMainParameter> judges, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasSampleMain(session, item);

            // 删除参数明细
            BasMainParameter bsj = new BasMainParameter();
            bsj.setSamplemain(item.getSamplemain());
            bsj.getDeal().setAction(DataAction.Delete.getAction());
            BasDao.SaveBasMainParameter(session, bsj);

            // 保存参数明细
            for (BasMainParameter param : params) {
                BasMainParameter basMainParameter = new BasMainParameter();
                // 保存检测依据明细
                for (BasMainParameter test : tests) {
                    if (param.getParameterid().equals(test.getParameterid())) {
                        BasTest basTest = new BasTest();
                        basTest.setTeststandard(test.getTeststandard());
                        basTest = BasDao.GetBasTest(basTest);

                        if (basTest != null) {
                            basMainParameter.setTeststandard(test.getTeststandard());
                            basMainParameter.setTeststandardcode(basTest.getTeststandardcode());
                            basMainParameter.setTeststandardname(basTest.getTeststandardname());
                        }

                    }
                }

                for (BasMainParameter judge : judges) {
                    if (param.getParameterid().equals(judge.getParameterid())) {
                        param.setJudgestandard(judge.getJudgestandard());
                        BasJudge basJudge = new BasJudge();
                        basJudge.setJudgestandard(judge.getJudgestandard());
                        basJudge = BasDao.GetBasJudge(basJudge);

                        if (basJudge != null) {
                            basMainParameter.setJudgestandard(judge.getJudgestandard());
                            basMainParameter.setJudgestandardcode(basJudge.getJudgestandardcode());
                            basMainParameter.setJudgestandardname(basJudge.getJudgestandardname());
                        }
                    }
                }

                basMainParameter.setSamplemain(item.getSamplemain());
                basMainParameter.setParameterid(param.getParameterid());
                basMainParameter.setSamplemainname(item.getSamplemainname());
                basMainParameter.setParametername(param.getParametername());
                basMainParameter.setParamunit(param.getParamunit());
                basMainParameter.setTestjudge(param.getTestjudge());
                basMainParameter.setStandvalue(param.getStandvalue());
                basMainParameter.setBelongtype(param.getBelongtype());
                basMainParameter.getDeal().setAction(DataAction.Create.getAction());
                BasDao.SaveBasMainParameter(session, basMainParameter);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getSamplemain());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"samplemain\":\"" + item.getSamplemain() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasSampleMain Methods

    // region BasSampleCatalog Methods

    public static void SaveBasSampleCatalog(BasSampleCatalog item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasSampleCatalog(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getSamplecatalog());
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

    // endregion BasSampleCatalog Methods

    // region BusTestedUnit Methods

    public static void SaveBusTestedUnit(BusTestedUnit item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBusTestedUnit(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTestedid());
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

    // endregion BusTestedUnit Methods

    // region BasLabParameter Methods

    public static void SaveBasLabParameter(BasLabParameter item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            String[] parameterids = item.getParameterid().split(",");
            item.getDeal().setAction(DataAction.Delete.getAction());
            BasDao.SaveBasLabParameter(session, item);

            if (parameterids.length > 0) {
                item.getDeal().setAction(DataAction.Create.getAction());

                for (String parameterid : parameterids) {
                    item.setParameterid(parameterid);
                    BasDao.SaveBasLabParameter(session, item);
                }
            }

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

    // endregion BasLabParameter Methods

    // region DeptTree Methods

    public static void DeptTree(ReturnValue rtv) {
        List<SysDept> depts = OrgDao.GetListDept();

        List<TreeItem> lists = new ArrayList<TreeItem>();

        TreeItem root = new TreeItem();
        root.setText("所有机构");
        root.setId("0000");
        root.setLeaf(false);
        root.setExpanded(true);
        lists.add(root);

        if (depts.size() > 0) {
            for (SysDept dept : depts) {
                if (!dept.getDeptid().equals("9999")) {
                    TreeItem ti = new TreeItem();
                    ti.setText(dept.getDeptname());
                    ti.setId(dept.getDeptid());

                    if (dept.getDeptid().equals(dept.getDeptpid())) {
                        lists.add(ti);
                    } else
                        GetSubDept(lists, dept);
                }
            }
        }

        for (TreeItem item : lists) {
            item.setExpanded(true);
        }

        rtv.SetValues(true, "", ToolUtils.GetJsonFromArray(lists), true);
    }

    public static boolean GetSubDept(List<TreeItem> items, SysDept dept) {
        for (TreeItem item : items) {
            if (item.getId().equals(dept.getDeptpid())) {
                TreeItem ti = new TreeItem();
                ti.setText(dept.getDeptname());
                ti.setId(dept.getDeptid());
                item.getChildren().add(ti);
                item.setLeaf(false);
                return true;
            }

            if (item.getChildren().size() > 0)
                GetSubDept(item.getChildren(), dept);
        }

        return false;
    }

    // region DeptTree Methods

    // region BasArea Methods

    public static void SaveBasArea(BasArea item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasArea(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getAreaid() + "-" + item.getAreaname());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"areaid\":\"" + item.getAreaid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            // rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
            rtv.setMsg(Consts.STR_SAVE_F + "该地区编号已被使用");
        } finally {
            session.close();
        }
    }

    public static void DeleteBasArea(BasArea main, List<BasArea> deletes, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            for (BasArea item : deletes) {
                item.getDeal().setAction(DataAction.Delete.getAction());

                if (item.OnBeforeSave(errmsg)) {
                    rtv.setMsg(errmsg.getErrmsg());
                    return;
                }
            }

            String logs = "";

            for (BasArea item : deletes) {
                BasDao.SaveBasArea(session, item);

                logs += "[" + item.getAreaid() + "-" + item.getAreaname() + "]";
            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc(logs);
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_DELETE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasArea Methods

    // region BasCity Methods

    public static void SaveBasCity(BasCity item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasCity(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getCityid() + "-" + item.getCityname());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"cityid\":\"" + item.getCityid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
            rtv.setMsg(Consts.STR_SAVE_F + "该城市编号已被使用");
        } finally {
            session.close();
        }
    }

    public static void DeleteBasCity(BasCity main, List<BasCity> deletes, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            for (BasCity item : deletes) {
                item.getDeal().setAction(DataAction.Delete.getAction());

                if (item.OnBeforeSave(errmsg)) {
                    rtv.setMsg(errmsg.getErrmsg());
                    return;
                }
            }

            String logs = "";

            for (BasCity item : deletes) {
                BasDao.SaveBasCity(session, item);

                logs += "[" + item.getCityid() + "-" + item.getCityname() + "]";
            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc(logs);
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_DELETE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasCity Methods

    // region BasProvince Methods

    public static void SaveBasProvince(BasProvince item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasProvince(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getProvinceid() + "-" + item.getProvincename());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"provinceid\":\"" + item.getProvinceid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            // rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
            rtv.setMsg(Consts.STR_SAVE_F + "该省份编号已被使用");
        } finally {
            session.close();
        }
    }

    public static void DeleteBasProvince(BasProvince main, List<BasProvince> deletes, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            for (BasProvince item : deletes) {
                item.getDeal().setAction(DataAction.Delete.getAction());

                if (item.OnBeforeSave(errmsg)) {
                    rtv.setMsg(errmsg.getErrmsg());
                    return;
                }
            }

            String logs = "";

            for (BasProvince item : deletes) {
                BasDao.SaveBasProvince(session, item);

                logs += "[" + item.getProvinceid() + "-" + item.getProvincename() + "]";
            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc(logs);
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_DELETE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasProvince Methods

    // region BasCountry Methods

    public static void SaveBasCountry(BasCountry item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasCountry(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getCountryid() + "-" + item.getCountryname());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"countryid\":\"" + item.getCountryid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            // rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
            rtv.setMsg(Consts.STR_SAVE_F + "该国家编号已被使用");
        } finally {
            session.close();
        }
    }

    public static void DeleteBasCountry(BasCountry main, List<BasCountry> deletes, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            for (BasCountry item : deletes) {
                item.getDeal().setAction(DataAction.Delete.getAction());

                if (item.OnBeforeSave(errmsg)) {
                    rtv.setMsg(errmsg.getErrmsg());
                    return;
                }
            }

            String logs = "";

            for (BasCountry item : deletes) {
                BasDao.SaveBasCountry(session, item);

                logs += "[" + item.getCountryid() + "-" + item.getCountryname() + "]";
            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc(logs);
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_DELETE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasCountry Methods

    // region BasLocation Methods

    public static void LocationTree(BasLocation blab, ReturnValue rtv, String property) {

        int level = 0;

        switch (property) {
        case "country":
            level = 1;
            break;
        case "province":
            level = 2;
            break;
        case "city":
            level = 3;
            break;
        case "area":
            level = 4;
            break;
        }

        List<BasLocation> labs = BasDao.GetBasLocation(blab);

        List<TreeItem> lists = new ArrayList<TreeItem>();

        if (labs.size() > 0) {
            for (BasLocation lab : labs) {
                TreeItem ti = new TreeItem();
                ti.setText(lab.getLocationname());
                ti.setId(lab.getLocationid());

                if (lab.getLocationid().equals(lab.getLocationpid())) {
                    lists.add(ti);
                } else if (Integer.parseInt(lab.getLocationlevel()) < level)
                    GetSubLoc(lists, lab);

            }
        }

        // for (TreeItem item : lists) {
        // item.setExpanded(true);
        // }

        rtv.SetValues(true, "", ToolUtils.GetJsonFromArray(lists), true);
    }

    public static boolean GetSubLoc(List<TreeItem> items, BasLocation lab) {
        for (TreeItem item : items) {
            if (item.getId().equals(lab.getLocationpid())) {
                TreeItem ti = new TreeItem();
                ti.setText(lab.getLocationname());
                ti.setId(lab.getLocationid());
                item.getChildren().add(ti);
                // item.setExpanded(true);
                item.setLeaf(false);
                return true;
            }

            if (item.getChildren().size() > 0)
                GetSubLoc(item.getChildren(), lab);
        }

        return false;
    }

    // endregion BasLocation Methods

    // region BasSampleReplace Methods

    public static void SaveBasSampleReplace(BasSampleReplace item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BasDao.SaveBasSampleReplace(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getSamplemain());
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

    public static void DeleteBasSampleReplace(BasSampleReplace basSampleReplace, List<BasSampleReplace> deletes,
            ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            for (BasSampleReplace item : deletes) {
                item.getDeal().setAction(DataAction.Delete.getAction());

                if (item.OnBeforeSave(errmsg)) {
                    rtv.setMsg(errmsg.getErrmsg());
                    return;
                }
            }

            String logs = "";

            for (BasSampleReplace item : deletes) {
                BasDao.SaveBasSampleReplace(session, item);

                // logs += "[" + item.getSamplemainname() + "-" + item.getSamplename()+
                // "]";
            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc("检测信息删除！");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_DELETE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasSampleReplace Methods
}
