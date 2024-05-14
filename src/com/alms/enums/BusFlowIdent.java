package com.alms.enums;

public enum BusFlowIdent {
    // 无流程
    None("none"),
    // 人员申请
    UserApply("userapply"),
    // 设备申请
    DevApply("devapply"),
    // 人员分配
    UserAlloc("useralloc"),
    // 人员变更
    UserChange("userchange"),
    // 人员培训计划申请
    TrainApply("trainapply"), ChangeTrainApply("changetrainapply"),
    // 人员技能考核
    SkillExam("skillexam"),
    // 人员技能撤销
    UserCancel("usercancel"),
    // 专家提议
    ExpertOffer("expertoffer"),
    // 检测大纲（交竣工）
    Program("program"),
    // 项目成员选定
    Selection("selection"),
    // 设备分配申请
    DevAlloc("devalloc"),
    // 设备调拨申请
    DevChange("devchange"),
    // 检测大纲（项目筹备）
    WorkOut("workout"),
    // 参数申请
    Param("param"),
    // 选址分析
    Site("site"),
    // 授权申请
    Register("register"),
    // 月报提交
    Month("month"),
    // 质量监督计划
    Plan("plan"),
    // 质量监督记录
    SpvRecord("spvrecord"),
    // 结果质量保证计划
    SpvResult("spvresult"),
    // 结果质量保证报告
    SpvReport("spvreport"),
    // 设备总体计划
    DevPlan("devplan"),
    // 设备记录
    DevMain("devmain"),
    // 设备检定校准
    DevCali("devcali"),
    // 设备检定校准
    DevCheck("devcheck"), UserRecord("userrecord"), DevAccept("devaccept"), DevReturn("devreturn");

    private final String busflowident;

    private BusFlowIdent(String busflowident) {
        this.busflowident = busflowident;
    }

    public String getBusflowident() {
        return this.busflowident;
    }

    public static BusFlowIdent parse(String busflowident) {
        if (busflowident != null) {
            for (BusFlowIdent type : BusFlowIdent.values()) {
                if (busflowident.equalsIgnoreCase(type.busflowident))
                    return type;
            }
        }

        return BusFlowIdent.None;
    }
}