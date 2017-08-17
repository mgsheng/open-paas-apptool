package cn.com.open.apptoolservice.app.utils;

import java.util.List;

public class ParamUtil {

    /**
     *
     * 检验参数是否为空
     * @param params
     * @return
     */
    public static boolean paramMandatoryCheck(List<String> params){
        for(String param:params){
            if(nullEmptyBlankJudge(param)){
                return false;
            }
        }
        return true;
    }
    /**
     * 检验字符串是否为空
     * @param str
     * @return
     */
    public static boolean nullEmptyBlankJudge(String str){
        return null == str || str.isEmpty() || "".equals(str.trim());
    }

}
