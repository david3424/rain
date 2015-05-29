package org.david.rain.web.util.shileUtil;

import org.david.rain.common.repository.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * create date: 2009-12-4
 * <p/>
 * 2013-05-09 修改static 方法为 成员方法，防止 多个关键字Util的使用导致混乱
 * desc: 屏蔽关键字
 */
@Component
public class CheckWordUtil {


    @Autowired
    @Qualifier("daoImp")
    private Idao readDao;

    @Autowired
    @Qualifier("writeDaoImp")
    private Idao writeDao;

    public static final String flag = "_event_shilekey";

    /**
     * 缓存中的标示
     */
    private String tableName;

    /**
     * 检查字符串中是否存在特殊字
     *
     * @param str
     * @return true 存在 false 不存在
     * @throws Exception
     */
    public boolean existForbiddenWord(String game,String str) throws Exception {
        return forbiddenWordList(game,str).size() > 0;
    }

    /**
     * 替换字符串中是否存在特殊字
     *
     * @param str
     * @return true 存在 false 不存在
     * @throws Exception
     */
    public String replaceForbiddenWord(String game,String str) throws Exception {
        List<String> slist = getForbiddenWord(game);
        //特殊字集合
//      List<String> forbidden = getForbiddenWord();
        List<String> forbidden = new ArrayList<String>();
        //匹配特殊字,放入集合

        for (String s : slist) {
            if (str.toLowerCase().contains(s)) {
                forbidden.add(s);
                str = str.toLowerCase().replaceAll(s, "**");
            }
        }
        return str;

    }


    /**
     * 挑选出字符串str中所有存在的特殊字
     *
     * @param str
     * @return 返回存在的特殊字的集合 forbidden
     * @throws Exception
     */
    public List<String> forbiddenWordList(String game,String str) throws Exception {
        List<String> slist = getForbiddenWord(game);
        //特殊字集合
        List<String> forbidden = new ArrayList<String>();
        //匹配特殊字,放入集合
        for (String s : slist) {
            if (str.toLowerCase().contains(s)) {
                forbidden.add(s);
                str = str.toLowerCase().replaceAll(s, "**");
            }
        }
        return forbidden;
    }

    /**
     * 替换特殊文字
     *
     * @param str 需要检查的文字
     * @return result 替换后的文字
     * @throws Exception
     */
    public String replaceWord(String str) throws Exception {
        //特殊字集合
        List<String> forbidden = getForbiddenWord(str);
        //替换特殊字
        for (String s : forbidden) {

            str = str.toLowerCase().replaceAll(s, CheckWordUtil.buildSign(s.length()));
        }
        return str;
    }

    /**
     * 替换*生成方法
     *
     * @param length
     * @return *
     */
    private static String buildSign(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append("*");
        }
        return sb.toString();
    }

    /**
     * 查询特殊字符表
     *
     * @return list
     * @throws Exception
     */
    @Cacheable(value = "cache30min", key = "'#game+'_event_shilekey'")
    public List<String> getForbiddenWord(String game) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select word from  " + (game+flag);
        List<String> list=readDao.queryOneColumnList(sql);
        return list;
    }
}
