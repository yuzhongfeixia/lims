package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.BasDao;
import com.alms.entity.bas.*;
import com.alms.service.BasService;
import com.gpersist.action.BaseAction;
import com.gpersist.dao.UserDao;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.entity.user.SysUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class BasAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region BasSampleTest Methods

    private BasSampleTest bst;

    public BasSampleTest getBst() {
        if (bst == null)
            bst = new BasSampleTest();

        return bst;
    }

    public void setBst(BasSampleTest bst) {
        this.bst = bst;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleTest> lists = BasDao.GetListBasSampleTest(this.getBst());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0140-基础-样品检测依据关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleTestBySample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleTest> lists = BasDao.GetListBasSampleTestBySample(this.getBst());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0140-基础-样品检测依据关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleTestByParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleTest> lists = BasDao.GetListBasSampleTestByParam(this.getBst());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0140-基础-样品检测依据关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    private BasTestCollect btc;

    public BasTestCollect getBtc() {
        if (btc == null) {
            btc = new BasTestCollect();
        }
        return btc;
    }

    public void setBtc(BasTestCollect btc) {
        this.btc = btc;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasTestCollect() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasTestCollect> lists = BasDao.GetListBasTestCollect(this.getBtc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0131-基础-样品大类", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasSampleTest Methods

    // region BasSampleJudge Methods

    private BasSampleJudge bsj;

    public BasSampleJudge getBsj() {
        if (bsj == null)
            bsj = new BasSampleJudge();

        return bsj;
    }

    public void setBsj(BasSampleJudge bsj) {
        this.bsj = bsj;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleJudge> lists = BasDao.GetListBasSampleJudge(this.getBsj());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0139-基础-样品判定依据关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleJudgeByParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleJudge> lists = BasDao.GetListBasSampleJudgeByParam(this.getBsj());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0139-基础-样品判定依据关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasSampleJudge Methods

    // region BasSampleParameter Methods

    private BasSampleParameter bsampara;

    public BasSampleParameter getBsampara() {
        if (bsampara == null)
            bsampara = new BasSampleParameter();

        return bsampara;
    }

    public void setBsampara(BasSampleParameter bsampara) {
        this.bsampara = bsampara;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleParameter> lists = BasDao.GetListBasSampleParameter(this.getBsampara());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0136-基础-样品与检测关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleParameterSure() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleParameter> lists = BasDao.GetListBasSampleParameterSure(this.getBsampara());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0136-基础-样品与检测关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleParameter> lists = BasDao.GetListBasSampleDetail(this.getBsampara());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0136-基础-样品与检测关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String GetListBasSampleParameterDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String sampleid = ServletActionContext.getRequest().getParameter("sampleid");
            // if ("".equals(sampleid) || sampleid == null) {
            // ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
            // return null;
            // }

            List<BasSampleParameter> lists = BasDao.GetListBasSampleParameterDetail(sampleid);

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasSampleParameter Methods

    // region BasMainParameter Methods

    private BasMainParameter basmp;

    public BasMainParameter getBasmp() {
        if (basmp == null)
            basmp = new BasMainParameter();

        return basmp;
    }

    public void setBasmp(BasMainParameter basmp) {
        this.basmp = basmp;
    }

    public String GetBasMainParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasMainParameter rtn = new BasMainParameter();
        if (ou != null) {
            rtn = BasDao.GetBasMainParameter(this.getBasmp());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasMainParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasMainParameter> lists = BasDao.GetListBasMainParameter(this.getBasmp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0136-基础-样品与检测关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasMainParameter Methods

    // region BasCatalogParameter Methods

    private BasCatalogParameter bcatapara;

    public BasCatalogParameter getBcatapara() {
        if (bcatapara == null)
            bcatapara = new BasCatalogParameter();

        return bcatapara;
    }

    public void setBcatapara(BasCatalogParameter bcatapara) {
        this.bcatapara = bcatapara;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasCatalogParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasCatalogParameter> lists = BasDao.GetListBasCatalogParameter(this.getBcatapara());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0135-基础-样品大类与检测关系", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasCatalogParameter Methods

    // region BasTest Methods

    private BasTest btest;

    public BasTest getBtest() {
        if (btest == null)
            btest = new BasTest();

        return btest;
    }

    public void setBtest(BasTest btest) {
        this.btest = btest;
    }

    @AuthMethod(Menus = "1325", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasTest rtn = new BasTest();
        if (ou != null) {
            rtn = BasDao.GetBasTest(this.getBtest());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1325", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasTest> lists = BasDao.GetListBasTest();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0138-基础-检测依据", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1325", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String teststandard = ToolUtils.Decode(this.getBtest().getTeststandard()).trim();
            String teststandardname = ToolUtils.Decode(this.getBtest().getTeststandardname()).trim();

            if (!ToolUtils.StringIsEmpty(teststandard))
                search += ToolUtils.GetAndSearch(search) + " a.teststandard like '%" + teststandard + "%' ";

            if (!ToolUtils.StringIsEmpty(teststandardname))
                search += ToolUtils.GetAndSearch(search) + " a.teststandardname like '%" + teststandardname + "%' ";

            this.SetSearch(this.getBtest().getSearch(), this.getBtest().getItem(), ou, search);

            List<BasTest> lists = BasDao.SearchBasTest(this.getBtest());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtest().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0138-基础-检测依据", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1325", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1325");
            BasService.SaveBasTest(this.getBtest(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasTest Methods

    private AbilityForm aform;

    public AbilityForm getAform() {
        if (aform == null)
            aform = new AbilityForm();

        return aform;
    }

    public void setAform(AbilityForm aform) {
        this.aform = aform;
    }

    @AuthMethod(Menus = "1301", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetAbilityForm() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        AbilityForm rtn = new AbilityForm();
        if (ou != null) {
            rtn = BasDao.GetAbilityForm(this.getAform());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1301", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListAbilityForm() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<AbilityForm> lists = BasDao.GetListAbilityForm();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1301-中心能力表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1301", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchAbilityForm() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String abilityformnum = ToolUtils.Decode(this.getAform().getAbilityformnum()).trim();

            if (!ToolUtils.StringIsEmpty(abilityformnum))
                search += ToolUtils.GetAndSearch(search) + " a.abilityformnum like '%" + abilityformnum + "%' ";

            this.SetSearch(this.getAform().getSearch(), this.getAform().getItem(), ou, search);

            List<AbilityForm> lists = BasDao.SearchAbilityForm(this.getAform());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getAform().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0138-中心能力表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1301", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveAbilityForm() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1301");
            BasService.SaveAbilityForm(this.getAform(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasTest Methods

    // region BasJudge Methods

    private BasJudge bjudge;

    public BasJudge getBjudge() {
        if (bjudge == null)
            bjudge = new BasJudge();

        return bjudge;
    }

    public void setBjudge(BasJudge bjudge) {
        this.bjudge = bjudge;
    }

    @AuthMethod(Menus = "1323", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasJudge rtn = new BasJudge();
        if (ou != null) {
            rtn = BasDao.GetBasJudge(this.getBjudge());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1323", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasJudge> lists = BasDao.GetListBasJudge();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0137-基础-判定依据", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1323", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String judgestandard = ToolUtils.Decode(this.getBjudge().getJudgestandard());
            String judgestandardname = ToolUtils.Decode(this.getBjudge().getJudgestandardname());

            if (!ToolUtils.StringIsEmpty(judgestandard))
                search += ToolUtils.GetAndSearch(search) + " a.judgestandard like '%" + judgestandard + "%' ";

            if (!ToolUtils.StringIsEmpty(judgestandardname))
                search += ToolUtils.GetAndSearch(search) + " a.judgestandardname like '%" + judgestandardname + "%' ";

            this.SetSearch(this.getBjudge().getSearch(), this.getBjudge().getItem(), ou, search);

            List<BasJudge> lists = BasDao.SearchBasJudge(this.getBjudge());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBjudge().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0137-基础-判定依据", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1323", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1323");
            BasService.SaveBasJudge(this.getBjudge(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasJudge Methods

    // region BasParameter Methods

    private BasParameter bpara;

    public BasParameter getBpara() {
        if (bpara == null)
            bpara = new BasParameter();

        return bpara;
    }

    public void setBpara(BasParameter bpara) {
        this.bpara = bpara;
    }

    @AuthMethod(Menus = "1321", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasParameter rtn = new BasParameter();
        if (ou != null) {
            rtn = BasDao.GetBasParameter(this.getBpara());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1321", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasParameter> lists = BasDao.GetListBasParameter();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0134-基础-检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasParameterByLab() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasParameter> lists = BasDao.GetListBasParameterByLab(this.getBpara());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1321", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String parameterid = ToolUtils.Decode(this.getBpara().getParameterid());
            String parametername = ToolUtils.Decode(this.getBpara().getParametername()).trim();
            String labid = ToolUtils.Decode(this.getBpara().getLabid());
            String parameterstatus = ToolUtils.Decode(this.getBpara().getParameterstatus());
            String connectparameter = ToolUtils.Decode(this.getBpara().getConnectparameter());
            if (!ToolUtils.StringIsEmpty(parameterid))
                search += ToolUtils.GetAndSearch(search) + " a.parameterid like '%" + parameterid + "%' ";

            if (!ToolUtils.StringIsEmpty(parametername))
                search += ToolUtils.GetAndSearch(search) + " a.parametername like '%" + parametername + "%' ";

            if (!ToolUtils.StringIsEmpty(labid))
                search += ToolUtils.GetAndSearch(search) + " a.labid like '%" + labid + "%' ";

            if (!ToolUtils.StringIsEmpty(parameterstatus))
                search += ToolUtils.GetAndSearch(search) + " a.parameterstatus like '%" + parameterstatus + "%' ";

            if (!ToolUtils.StringIsEmpty(connectparameter))
                search += ToolUtils.GetAndSearch(search) + " a.parametername like '%" + connectparameter + "%' ";

            this.SetSearch(this.getBpara().getSearch(), this.getBpara().getItem(), ou, search);

            List<BasParameter> lists = BasDao.SearchBasParameter(this.getBpara());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBpara().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0134-基础-检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1321", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1321");
            BasService.SaveBasParameter(this.getBpara(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasParameter Methods

    // region BasSample Methods

    private BasSample bsample;

    public BasSample getBsample() {
        if (bsample == null)
            bsample = new BasSample();

        return bsample;
    }

    public void setBsample(BasSample bsample) {
        this.bsample = bsample;
    }

    // @AuthMethod(Menus="1315", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetBasSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasSample rtn = new BasSample();
        if (ou != null) {
            rtn = BasDao.GetBasSample(this.getBsample());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1315", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSample> lists = BasDao.GetListBasSample();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0133-基础-样品", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // @AuthMethod(Menus = "1315", Auth = MenuAuth.BrowseExport, OutType =
    // ActionOutType.Array)
    public String SearchBasSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String sampleid = ToolUtils.Decode(this.getBsample().getSampleid()).trim();
            String samplename = ToolUtils.Decode(this.getBsample().getSamplename()).trim();
            String samplemainname = ToolUtils.Decode(this.getBsample().getSamplemainname()).trim();
            String samplemain = ToolUtils.Decode(this.getBsample().getSamplemain()).trim();
            String samplecatalog = ToolUtils.Decode(this.getBsample().getSamplecatalog());
            if (!ToolUtils.StringIsEmpty(sampleid))
                search += ToolUtils.GetAndSearch(search) + " a.sampleid like '%" + sampleid + "%' ";

            if (!ToolUtils.StringIsEmpty(samplename))
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";

            if (!ToolUtils.StringIsEmpty(samplemain))
                search += ToolUtils.GetAndSearch(search) + " a.samplemain like '%" + samplemain + "%' ";

            if (!ToolUtils.StringIsEmpty(samplemainname))
                search += ToolUtils.GetAndSearch(search) + " b.samplemainname like '%" + samplemainname + "%' ";

            if (!ToolUtils.StringIsEmpty(samplecatalog))
                search += ToolUtils.GetAndSearch(search) + " c.samplecatalog = " + samplecatalog + " ";

            this.SetSearch(this.getBsample().getSearch(), this.getBsample().getItem(), ou, search);

            List<BasSample> lists = BasDao.SearchBasSample(this.getBsample());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBsample().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0133-基础-样品", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1315");
            List<BasSampleParameter> param = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), BasSampleParameter.class);

            List<BasSampleStand> sampleStands = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("partsdetails"), BasSampleStand.class);
            // List<BasSampleTest> tests = ToolUtils.GetArrayFromJson(
            // ServletActionContext.getRequest().getParameter("tests"),
            // BasSampleTest.class);
            // List<BasSampleJudge> judges = ToolUtils.GetArrayFromJson(
            // ServletActionContext.getRequest().getParameter("judges"),
            // BasSampleJudge.class);
            // BasService.SaveBasSample(this.getBsample(), param, tests, judges,
            // this.getRtv(), log);
            BasService.SaveBasSample(this.getBsample(), param, sampleStands, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasSample Methods

    // region BasSampleMain Methods

    private BasSampleMain bsmain;

    public BasSampleMain getBsmain() {
        if (bsmain == null)
            bsmain = new BasSampleMain();

        return bsmain;
    }

    public void setBsmain(BasSampleMain bsmain) {
        this.bsmain = bsmain;
    }

    @AuthMethod(Menus = "1313", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasSampleMain() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasSampleMain rtn = new BasSampleMain();
        if (ou != null) {
            rtn = BasDao.GetBasSampleMain(this.getBsmain());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1313", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleMain() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleMain> lists = BasDao.GetListBasSampleMain();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0132-基础-样品主类", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1313", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasSampleMain() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String samplecatalog = ToolUtils.Decode(this.getBsmain().getSamplecatalog()).trim();
            String samplecatalogname = ToolUtils.Decode(this.getBsmain().getSamplecatalogname()).trim();
            String samplemain = ToolUtils.Decode(this.getBsmain().getSamplemain()).trim();
            String samplemainname = ToolUtils.Decode(this.getBsmain().getSamplemainname()).trim();

            if (!ToolUtils.StringIsEmpty(samplecatalog))
                search += ToolUtils.GetAndSearch(search) + " a.samplecatalog like '%" + samplecatalog + "%' ";

            if (!ToolUtils.StringIsEmpty(samplecatalogname))
                search += ToolUtils.GetAndSearch(search) + " b.samplecatalogname like '%" + samplecatalogname + "%' ";

            if (!ToolUtils.StringIsEmpty(samplemain))
                // search += ToolUtils.GetAndSearch(search) + " a.samplemain like '%"
                // + samplemain + "%' ";
                search += ToolUtils.GetAndSearch(search) + " b.samplecatalogname like '%" + samplemain + "%' ";

            if (!ToolUtils.StringIsEmpty(samplemainname))

                search += ToolUtils.GetAndSearch(search) + " a.samplemainname like '%" + samplemainname + "%' ";

            this.SetSearch(this.getBsmain().getSearch(), this.getBsmain().getItem(), ou, search);
            List<BasSampleMain> lists = BasDao.SearchBasSampleMain(this.getBsmain());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBsmain().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0132-基础-样品主类", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1313", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasSampleMain() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1313");

            List<BasMainParameter> param = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("params"), BasMainParameter.class);
            List<BasMainParameter> tests = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("tests"), BasMainParameter.class);
            List<BasMainParameter> judges = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("judges"), BasMainParameter.class);

            BasService.SaveBasSampleMain(this.getBsmain(), param, tests, judges, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasSampleMain Methods

    // region BasSampleCatalog Methods

    private BasSampleCatalog bscatalog;

    public BasSampleCatalog getBscatalog() {
        if (bscatalog == null)
            bscatalog = new BasSampleCatalog();

        return bscatalog;
    }

    public void setBscatalog(BasSampleCatalog bscatalog) {
        this.bscatalog = bscatalog;
    }

    @AuthMethod(Menus = "1311", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasSampleCatalog() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasSampleCatalog rtn = new BasSampleCatalog();
        if (ou != null) {
            rtn = BasDao.GetBasSampleCatalog(this.getBscatalog());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1311", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasSampleCatalogBySampleID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasSampleCatalog rtn = new BasSampleCatalog();
        if (ou != null) {
            rtn = BasDao.GetBasSampleCatalogBySampleID(this.getBscatalog());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1311", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleCatalog() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleCatalog> lists = BasDao.GetListBasSampleCatalog();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0131-基础-样品大类", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1311", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasSampleCatalog() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String samplecatalog = ToolUtils.Decode(this.getBscatalog().getSamplecatalog());
            String samplecatalogname = ToolUtils.Decode(this.getBscatalog().getSamplecatalogname());

            if (!ToolUtils.StringIsEmpty(samplecatalog))
                search += ToolUtils.GetAndSearch(search) + " a.samplecatalog like '%" + samplecatalog + "%' ";

            if (!ToolUtils.StringIsEmpty(samplecatalogname))
                search += ToolUtils.GetAndSearch(search) + " a.samplecatalogname like '%" + samplecatalogname + "%' ";

            this.SetSearch(this.getBscatalog().getSearch(), this.getBscatalog().getItem(), ou, search);

            List<BasSampleCatalog> lists = BasDao.SearchBasSampleCatalog(this.getBscatalog());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBscatalog().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0131-基础-样品大类", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1311", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasSampleCatalog() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1311");
            // List<BasCatalogParameter> details = ToolUtils.GetArrayFromJson(
            // ServletActionContext.getRequest().getParameter("listdetails"),
            // BasCatalogParameter.class);
            BasService.SaveBasSampleCatalog(this.getBscatalog(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasSampleCatalog Methods

    // region BusTestedUnit Methods

    private BusTestedUnit btu;

    public BusTestedUnit getBtu() {
        if (btu == null)
            btu = new BusTestedUnit();

        return btu;
    }

    public void setBtu(BusTestedUnit btu) {
        this.btu = btu;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTestedUnit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTestedUnit rtn = new BusTestedUnit();
        if (ou != null) {
            rtn = BasDao.GetBusTestedUnit(this.getBtu());
            if (rtn == null) {
                rtn = new BusTestedUnit();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTestedUnit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTestedUnit> lists = BasDao.GetListBusTestedUnit(this.getBtu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-受检单位", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTestedUnit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String testedname = ToolUtils.Decode(this.getBtu().getTestedname());

            if (!ToolUtils.StringIsEmpty(testedname))
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";

            this.SetSearch(this.getBtu().getSearch(), this.getBtu().getItem(), ou, search);

            List<BusTestedUnit> lists = BasDao.SearchBusTestedUnit(this.getBtu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtu().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-受检单位", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTestedUnit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0801");
            BasService.SaveBusTestedUnit(this.getBtu(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else {
            ToolUtils.OutString(this.NotLoginRtv());
        }

        return null;
    }

    // endregion BusTestedUnit Methods

    // region SysUser Methods

    private SysUser user;

    public SysUser getUser() {
        if (user == null)
            user = new SysUser();

        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String SearchUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String deptid = this.getUser().getDeptid();
            String username = ToolUtils.Decode(this.getUser().getUsername());
            String search = "";

            if (!ToolUtils.StringIsEmpty(deptid) && !deptid.equals(Consts.DEFAULT_ALL_DEPT))
                search += ToolUtils.GetAndSearch(search) + " a.deptid = '" + deptid + "' ";

            if (!ToolUtils.StringIsEmpty(username))
                search += ToolUtils.GetAndSearch(search) + " a.username like '%" + username + "%' ";

            search += ToolUtils.GetAndSearch(search) + " a.deptid <> '9999' ";

            this.getUser().getSearch().setSearch(search);
            this.getUser().getSearch().setStart(start + 1);
            this.getUser().getSearch().setEnd(this.GetEndCnt());
            this.getUser().getSearch().setUserid(ou.getUser().getUserid());

            List<SysUser> lists = UserDao.SearchUser(this.getUser());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getUser().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("操作员信息", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    public String GetUserByUserid() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SysUser rtn = new SysUser();
        if (ou != null) {
            String userid = ServletActionContext.getRequest().getParameter("userid");
            rtn = UserDao.GetUser(userid);
        }
        ToolUtils.OutString(this.OutBean(rtn, false));
        return null;
    }

    public String GetUserByDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String deptid = ServletActionContext.getRequest().getParameter("deptid");
            List<SysUser> lists = BasDao.GetUserByDept(deptid);

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion SysUser Methods

    // region BasLabParameter Methods

    private BasLabParameter blp;

    public BasLabParameter getBlp() {
        if (blp == null)
            blp = new BasLabParameter();

        return blp;
    }

    public void setBlp(BasLabParameter blp) {
        this.blp = blp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasLabParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasLabParameter rtn = new BasLabParameter();
        if (ou != null) {
            rtn = BasDao.GetBasLabParameter(this.getBlp());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasLabParameterForInfo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasLabParameter rtn = new BasLabParameter();
        if (ou != null) {
            rtn = BasDao.GetBasLabParameterForInfo(this.getBlp());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasLabParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasLabParameter> lists = BasDao.GetListBasLabParameter(this.getBlp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0144-基础-试验组可检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasLabBySample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasLabParameter> lists = BasDao.GetListBasLabBySample(this.getBlp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasLabParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            this.SetSearch(this.getBlp().getSearch(), this.getBlp().getItem(), ou, search);

            List<BasLabParameter> lists = BasDao.SearchBasLabParameter(this.getBlp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBlp().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0144-基础-试验组可检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasLabParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0210");
            BasService.SaveBasLabParameter(this.getBlp(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasLabParameter Methods

    // region DeptTree Methods

    public String DeptTree() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            try {
                BasService.DeptTree(this.getRtv());

                ToolUtils.JsonOutString(this.getRtv().getData().toString());
                return null;
            } catch (Exception e) {
            }
        }

        ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        return null;
    }

    // endregion DeptTree Methods

    // region BasArea Methods
    private BasArea ba;

    public BasArea getBa() {
        if (ba == null)
            ba = new BasArea();

        return ba;
    }

    public void setBa(BasArea ba) {
        this.ba = ba;
    }

    public String GetBasArea() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasArea rtn = new BasArea();
        if (ou != null) {
            rtn = BasDao.GetBasArea(this.getBa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    public String SearchBasArea() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String areaname = ToolUtils.Decode(this.getBa().getAreaname()).trim();

            if (!ToolUtils.StringIsEmpty(areaname))
                search += ToolUtils.GetAndSearch(search) + " a.areaname like '%" + areaname + "%' ";

            this.SetSearch(this.getBa().getSearch(), this.getBa().getItem(), ou, search);

            List<BasArea> lists = BasDao.SearchBasArea(this.getBa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("地区", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SaveBasArea() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0857");
            BasService.SaveBasArea(this.getBa(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String DeleteBasArea() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0857");

            List<BasArea> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), BasArea.class);

            BasService.DeleteBasArea(this.getBa(), deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBasAreaByCity() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            List<BasArea> lists = BasDao.GetBasAreaByCity(this.getBa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("地区", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasArea Methods

    // region BasCity Methods
    private BasCity bct;

    public BasCity getBct() {
        if (bct == null)
            bct = new BasCity();

        return bct;
    }

    public void setBct(BasCity bct) {
        this.bct = bct;
    }

    public String GetBasCity() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasCity rtn = new BasCity();
        if (ou != null) {
            rtn = BasDao.GetBasCity(this.getBct());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    public String SearchBasCity() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String cityname = ToolUtils.Decode(this.bct.getCityname()).trim();

            if (!ToolUtils.StringIsEmpty(cityname))
                search += ToolUtils.GetAndSearch(search) + " a.cityname like '%" + cityname + "%' ";

            this.SetSearch(this.getBct().getSearch(), this.getBct().getItem(), ou, search);

            List<BasCity> lists = BasDao.SearchBasCity(this.getBct());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBct().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("城市", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SaveBasCity() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0855");
            BasService.SaveBasCity(this.getBct(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String DeleteBasCity() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0855");

            List<BasCity> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), BasCity.class);

            BasService.DeleteBasCity(this.getBct(), deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String GetBasCityByProv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            List<BasCity> lists = BasDao.GetBasCityByProv(this.getBct());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("城市", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasCity Methods

    // region BasProvince Methods
    private BasProvince bp;

    public BasProvince getBp() {
        if (bp == null)
            bp = new BasProvince();

        return bp;
    }

    public void setBp(BasProvince bp) {
        this.bp = bp;
    }

    public String GetBasProvince() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasProvince rtn = new BasProvince();
        if (ou != null) {
            rtn = BasDao.GetBasProvince(this.getBp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    public String SearchBasProvince() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String provincename = ToolUtils.Decode(this.getBp().getProvincename()).trim();

            if (!ToolUtils.StringIsEmpty(provincename))
                search += ToolUtils.GetAndSearch(search) + " a.provincename like '%" + provincename + "%' ";

            this.SetSearch(this.getBp().getSearch(), this.getBp().getItem(), ou, search);

            List<BasProvince> lists = BasDao.SearchBasProvince(this.getBp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("省份", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SaveBasProvince() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0853");
            BasService.SaveBasProvince(this.getBp(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String DeleteBasProvince() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0853");

            List<BasProvince> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), BasProvince.class);

            BasService.DeleteBasProvince(this.getBp(), deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasProvince Methods

    // region BasCountry Methods
    private BasCountry bc;

    public BasCountry getBc() {
        if (bc == null)
            bc = new BasCountry();

        return bc;
    }

    public void setBc(BasCountry bc) {
        this.bc = bc;
    }

    public String GetBasCountry() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasCountry rtn = new BasCountry();
        if (ou != null) {
            rtn = BasDao.GetBasCountry(this.getBc());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    public String SearchBasCountry() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String countryname = ToolUtils.Decode(this.getBc().getCountryname()).trim();
            if (!ToolUtils.StringIsEmpty(countryname))
                search += ToolUtils.GetAndSearch(search) + " a.countryname like '%" + countryname + "%' ";

            this.SetSearch(this.getBc().getSearch(), this.getBc().getItem(), ou, search);

            List<BasCountry> lists = BasDao.SearchBasCountry(this.getBc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBc().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("国家", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SaveBasCountry() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0851");
            BasService.SaveBasCountry(this.getBc(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String DeleteBasCountry() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<BasCountry> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), BasCountry.class);

            BasService.DeleteBasCountry(this.getBc(), deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasCountry Methods

    // region BasLocation Methods

    private BasLocation bl;

    public BasLocation getBl() {
        if (bl == null)
            bl = new BasLocation();

        return bl;
    }

    public void setBl(BasLocation bl) {
        this.bl = bl;
    }

    public String GetLocation() throws Exception {

        String property = ServletActionContext.getRequest().getParameter("property");
        BasService.LocationTree(this.getBl(), this.getRtv(), property);
        ToolUtils.OutString(this.getRtv().getData().toString());

        return null;
    }

    // endregion BasLocation Methods

    // region BasSampleStand Methods

    private BasSampleStand bss;

    public BasSampleStand getBss() {
        if (bss == null) {
            bss = new BasSampleStand();
        }
        return bss;
    }

    public void setBss(BasSampleStand bss) {
        this.bss = bss;
    }

    public String GetSampleStandCount() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasSampleStand rtn = new BasSampleStand();
        if (ou != null) {
            rtn = BasDao.GetSampleStandCount(this.getBss());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    public String GetSampleStandByLevel() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleStand> lists = BasDao.GetSampleStandByLevel(this.getBss());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String GetListBasSampleStand() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleStand> lists = BasDao.GetListBasSampleStand(this.getBss());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasSampleStand Methods

    // region BasSampleReplace Methods

    private BasSampleReplace bsr;

    public BasSampleReplace getBsr() {
        if (bsr == null)
            bsr = new BasSampleReplace();

        return bsr;
    }

    public void setBsr(BasSampleReplace bsr) {
        this.bsr = bsr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasSampleReplace() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasSampleReplace rtn = new BasSampleReplace();
        if (ou != null) {
            rtn = BasDao.GetBasSampleReplace(this.getBsr());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleReplace() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleReplace> lists = BasDao.GetListBasSampleReplace();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasSampleReplaceBySampleID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasSampleReplace> lists = BasDao.GetListBasSampleReplaceBySampleID(this.getBsr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasSampleReplace() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String samplename = ToolUtils.Decode(this.getBsr().getSamplename());
            String samplemainname = ToolUtils.Decode(this.getBsr().getSamplemainname());
            String parametername = ToolUtils.Decode(this.getBsr().getParametername());

            if (!ToolUtils.StringIsEmpty(samplename))
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";

            if (!ToolUtils.StringIsEmpty(samplemainname))
                search += ToolUtils.GetAndSearch(search) + " a.samplemainname like '%" + samplemainname + "%' ";

            if (!ToolUtils.StringIsEmpty(parametername))
                search += ToolUtils.GetAndSearch(search) + " a.parametername like '%" + parametername + "%' ";

            this.SetSearch(this.getBsr().getSearch(), this.getBsr().getItem(), ou, search);

            List<BasSampleReplace> lists = BasDao.SearchBasSampleReplace(this.getBsr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBsr().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasSampleReplace() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1317");
            BasService.SaveBasSampleReplace(this.getBsr(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String DeleteBasSampleReplace() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<BasSampleReplace> deletes = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("deletes"), BasSampleReplace.class);

            BasService.DeleteBasSampleReplace(this.getBsr(), deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasSampleReplace Methods
}
