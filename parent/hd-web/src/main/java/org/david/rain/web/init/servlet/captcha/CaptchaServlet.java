package org.david.rain.web.init.servlet.captcha;

import org.david.rain.common.lang.Tuple;
import org.david.rain.common.repository.Idao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-13
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
public class CaptchaServlet extends HttpServlet {

    //设置字母的大小,大小
//    private Font mFont = new Font("Times New Roman", Font.PLAIN, 17);
    private Font mFont = new Font(null, Font.PLAIN, 17);
    private static final Integer TYPE = 3;

    private static Logger logger = LoggerFactory.getLogger(CaptchaServlet.class);

    private static Idao dao;
//    private static final MemcachedManager memcachedManager = MemcachedManager.getInstance();
    public void init() throws ServletException {
        super.init();
    }

    Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        initDao(request);
        request.setCharacterEncoding("utf-8");
      	response.setContentType("text/html;charset=utf-8");


        int width = 150, height = 18;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(1, 1, width - 1, height - 1);
        g.setColor(new Color(102, 102, 102));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(mFont);


        g.setColor(getRandColor(160, 200));

        //画随机线
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x, y, x + xl, y + yl);
        }

        //从另一方向画随机线
        for (int i = 0; i < 70; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            g.drawLine(x, y, x - xl, y - yl);
        }

        //生成随机数,并将随机数字转换为字母
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            int itmp = random.nextInt(26) + 65;
            char ctmp = (char) itmp;
            sRand += String.valueOf(ctmp);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(String.valueOf(ctmp), 15 * i + 10, 16);
        }

        List<Characters> charactersList=getCharacters(dao);
        Tuple<String,String> write=getRandomCharacter(charactersList);
        logger.warn("=========write="+write.l);
        for(int i=0;i<write.l.length();i++){
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(String.valueOf(write.l.substring(i,i+1)), 15 * i + 10, 16);
        }


        HttpSession session = request.getSession(true);
        session.setAttribute("rand", write.r);
        g.dispose();

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //表明生成的响应是图片
        response.setContentType("image/jpeg;charset=utf-8");
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    public static void initDao(HttpServletRequest request) {
        if (dao == null) {
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
            dao = (Idao) wac.getBean("writeDaoImp");
        }
    }

    private List<Characters> getCharacters(Idao dao) {
        try {
            String sql = "select * from hd_captcha_character";
            return dao.queryObjects(Characters.class, sql);
        } catch (Exception e){
             logger.error(e.getMessage());
        }
        Characters c1=new Characters("完","w","n","wan");
        Characters c2=new Characters("美","m","i","mei");
         ArrayList<Characters> defaultCharacters=new ArrayList<>();
        defaultCharacters.add(c1);
        defaultCharacters.add(c2);
        return defaultCharacters;


    }

    private Tuple<String,String> getRandomCharacter(List<Characters> list){
        Random random = new Random();
       int index=  random.nextInt(list.size());
        Characters c=list.get(index);
        StringBuffer sb=new StringBuffer();
        sb.append("请输入").append(c.getCharacter());
        String typeStr="";
        String result="";
        switch (random.nextInt(TYPE)){
            case 0:
                typeStr="的拼音首字母";
                result=c.getFirstAlphabet();
                break;
            case 1:
                typeStr="的拼音全拼";
                result=c.getQuanpin();
                break;
            case 2:
                typeStr="的拼音尾字母";
                result=c.getLastAlphabet();
                break;
        }
        sb.append(typeStr);
        return new Tuple<>(sb.toString(),result);
    }


}
