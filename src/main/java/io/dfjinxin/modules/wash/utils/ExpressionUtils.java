package io.dfjinxin.modules.wash.utils;

/**
 * 生成JAVA SCRIPT表达式工具类
 */
public class ExpressionUtils {

    /**
     * 清洗判断表达式
     * 数据清洗运算ID: dataWashCmpuid
     */
    public static String washJudge(int dataWashCmpuid){
        return "ExpressionWashJudge" + dataWashCmpuid;
    }

    /**
     * 清洗转换表达式
     * 数据清洗运算ID: dataWashCmpuid
     */
    public static String washTransition(int dataWashCmpuid){
        return "ExpressionWashTrans" + dataWashCmpuid;
    }

    /**
     * 探查判断表达式
     * 检查项目ID: chkProjid
     */
    public static String chkJudge(int chkProjid){
        return "ExpressionChkJudge" + chkProjid;
    }
}
