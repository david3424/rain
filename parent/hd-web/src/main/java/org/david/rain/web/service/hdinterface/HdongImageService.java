package org.david.rain.web.service.hdinterface;

import org.springframework.stereotype.Component;

/**
 * 上传图片，任意文件和MP3等音频文件至图片服务器接口
 */
@Component
public interface HdongImageService {
    //上传图片方法
    public String uploadImage(byte[] bytes, String hdtype, String hdname, String filename) throws Exception;

    //上传任意文件方法
    public void upload(byte[] source, String filepath, String filename) throws Exception;

    //上传图片时候制作缩略图功能
   //  public void zoomImage(String largeImagePath, String smallImagePath, int width, int height) throws Exception;


    /**
     * copy图片
     *
     * @param sourcePath 源
     * @param descPath   目的
     */
    public boolean copyImage(String sourcePath, String descPath);

    /**
     * 调整图片尺寸，一般用于缩放，放大图片会失真，不推荐放大。
     * 只支持常见图片格式：jpg,gif,png,bmp
     * @param sourcePath  原始图片存在的路径（包含文件名）
     * @param savePath  调整尺寸后图片存放的路径(不包含文件名)，根据自己的活动，日期输入合适的路径不能为空
     * @param reFileName 调整尺寸过后文件名，加入流水号不要重复，不要为中文
     * @param fileType  调整图片后的图片类型(后缀)，最好与调整之前保持一致
     * @param width 调整后图片的宽度
     * @param height 调整后图片的高度
     * ***/
    public boolean reSizeImage(String sourcePath, String savePath, String reFileName, String fileType, int width, int height) throws Exception ;

}
