package org.david.rain.web.service.annotations;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.david.rain.common.repository.Idao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-5
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
@Service()
@Qualifier("ssw")
public class TestAnnotationService {

    private static Logger logger = LoggerFactory.getLogger(TestAnnotationService.class);
    private final static String TABLE_USER = "sdxl_dadwhere_user";

    @Autowired
    @Qualifier("writeDaoImp")
    private Idao writeDaoImp;
    /**
     * self definded
     * @param value
     * @return
     */
    public static String htmlEncode(String value) {
        if (StringUtils.isBlank(value)) return value;
        String html;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case '\'':
                    buffer.append("&apos;");
                    break;
                case 10:
                case 13:
                    break;
                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }

//    @TransactionalForExceptionRollback
@Test
    public void testCustomAnnotations() throws Exception {
      /*  String sql = "update " + TABLE_USER + " set status=? where username=?";
        int rtn = writeDaoImp.update(sql, "1", "test");
        if (rtn == 1) {
            throw  new Exception("roll back");
        }else{
            System.out.println("god fail");
        }
    }*/
        String rolename = "<<test>d的的'\"12&>";
        System.out.println(StringEscapeUtils.unescapeHtml3(htmlEncode(rolename)));
        System.out.println(StringEscapeUtils.unescapeEcmaScript(htmlEncode(rolename)));
    }
}
