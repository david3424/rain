package org.david.rain.common.webservice.imageservice;

/**
 *上传图片，任意文件和MP3等音频文件至图片服务器接口
 */
public interface HdongImageService {
    //上传图片方法
	public String uploadImage(byte[] bytes, String hdtype, String hdname, String filename) throws Exception;
    //上传任意文件方法
    public void upload(byte[] source, String filepath, String filename) throws Exception;
    //上传图片时候制作缩略图功能
    public void zoomImage(String largeImagePath, String smallImagePath, int width, int height) throws Exception;
}
