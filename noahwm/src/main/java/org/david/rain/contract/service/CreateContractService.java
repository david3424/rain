package org.david.rain.contract.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;
import org.david.rain.contract.pojo.ContractData;
import org.david.rain.contract.service.abstractor.IServiceProcessor;
import org.david.rain.common.exception.ErrorCode;
import org.david.rain.common.exception.ServiceException;
import org.david.rain.common.logback.LoggerUtil;
import org.david.rain.common.mapper.JsonMapper;
import org.david.rain.common.text.StringUtil;
import org.david.rain.common.time.ClockUtil;
import org.david.rain.common.time.DateFormatUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

@Service("createContractService")
public class CreateContractService implements IServiceProcessor {

    @Value("${contract.dir.local}")
    String contractDirLocal;
    @Value("${wartermark.img}")
    String warterMarkImg;
    @Value("${template.dir.local}")
    private String templateDirLocal;
    @Value("${resource.url}")
    private String resourceUrl;


    public String doHandler(String datas) {
        if (StringUtils.isEmpty(datas)) {
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, "empty data");
        }
        String result;
        try {
            result = beginProduce(datas);

        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.SERVER_ERROR, e);
        }

        return result;
    }

    private String beginProduce(String datas) throws Exception {
        long start = ClockUtil.currentTimeMillis();
        LoggerUtil.info("create pdf 请求begin =====请求时间:{}========", ClockUtil.currentDate());
        LoggerUtil.info("接收到的参数：{}", datas);
        ContractData contractData;
        try {
            contractData = JsonMapper.INSTANCE.fromJson(datas, ContractData.class);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, "parse to json error");
        }

        if (StringUtil.isEmpty(contractData.getSysTemplateName(), contractData.getSysContractName(), contractData.getSysTemplateUrl(), contractData.getTx_ContractNumber())) {
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, "important params are empty");
        }
        String result = createContract(contractData);

        LoggerUtil.info("create pdf 请求end =============,time elapsed(ms):{}", ClockUtil.elapsedTime(start));
        return result;
    }

    /**
     * 制作合同
     *
     */
    public String createContract(ContractData contractData) {
        String templateName = contractData.getSysTemplateName();
        if (!templateName.toLowerCase().endsWith(".pdf")) {
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, " error suffix of template");
        }
        // 如果template存在则直接使用
        String templatePath = templateDirLocal + templateName;
        File tempFile = new File(templatePath);
        if (!tempFile.exists()) { // 调用接口下载
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, "error template url");
        }
        // 对template进行处理,处理成功返回合同本地地址
        String localContractPath_watermark = fillTagInPdf(templatePath, contractData, true);
        String localContractPath = fillTagInPdf(templatePath, contractData, false);

        LoggerUtil.info("create pdf result :{},watermarker:{}", localContractPath, localContractPath_watermark);
        return "success";
    }

    /**
     * 将数据填入模板标签
     *
     * @param templatePath 模板路径
     * @param contractData 填充的数据
     */
    private String fillTagInPdf(String templatePath, ContractData contractData, boolean isWarterMark) { //UniCNS-UCS2-H

        String testFile = resourceUrl;
        LoggerUtil.info("font name:" + testFile);
        BaseFont bf;
        try {
            bf = BaseFont.createFont(testFile, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            LoggerUtil.error("create font exception :{}", e);
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, "create font exception: " + e.getMessage());
        }
        bf.setCompressionLevel(9);
        bf.setSubset(true);

        String contractName = isWarterMark ? contractData.getSysContractName() + "-wm" : contractData.getSysContractName();// 生成后的合同名称
        if (!contractName.toLowerCase().endsWith(".pdf")) {
            contractName += ".pdf";
        } else {
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, " already have  suffix of contractName ");
        }
        String date = DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_ON_DATE, new Date());
        String contractPath = contractDirLocal + date + "/";
        File file = new File(contractPath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        String localContract = contractPath + contractName;
        LoggerUtil.info("===templatePath:" + templatePath);
        PdfReader reader = null;
        try {
            reader = new PdfReader(templatePath);
        } catch (IOException e) {
            LoggerUtil.error("create PdfReader exception :{}", e);
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, "create PdfReader exception: " + e.getMessage());
        }

        PdfStamper stamper = null;
        try {
            stamper = new PdfStamper(reader, new FileOutputStream(localContract), PdfWriter.VERSION_1_5);
            stamper.getWriter().setCompressionLevel(9);
            stamper.setFullCompression();
            AcroFields s = stamper.getAcroFields();
            Field[] contractFields = contractData.getClass().getDeclaredFields();
            for (Field cfe : contractFields) {
                cfe.setAccessible(true);
                String fieldName = cfe.getName();
                Object fieldValue = cfe.get(contractData);
                if (StringUtil.isEmpty(fieldName) || fieldValue == null || fieldName.startsWith("sys")) { // 非文件数据，不需要填入
                    continue;
                }

                if (fieldName.startsWith("Tx_") || fieldName.startsWith("JG_")) {
                    s.setFieldProperty(fieldName, "textfont", bf, null);
                    s.setFieldProperty(fieldName, "textsize", 10f, null);
                    s.setField(fieldName, fieldValue.toString());
                } else if (fieldName.startsWith("Cb_")) {
                    s.setField(fieldName, "on", true); // 保留原来设定的符合
                }
            }
            if (s == null) {
                LoggerUtil.info(" AcroFields is null ");
            } else {
                LoggerUtil.info(" AcroFields is not null ");
                if (s.getFields().keySet().size() > 0) {
                    LoggerUtil.info("!!!warning:template has AcroFields");
                    s.setGenerateAppearances(true);
                } else {
                    LoggerUtil.info("!!!warning:template has no AcroFields");
                }
            }
            stamper.setRotateContents(false);
            //给每一页页眉加条形码
            int n = reader.getNumberOfPages();
            //            add wartermark
            Image image_warter = null;
            boolean isLoan = StringUtils.isNotEmpty(contractData.getTx_LoanAmount());
            if (isWarterMark) {
                if (isLoan) {
                    image_warter = Image.getInstance(warterMarkImg);
                    image_warter.setAbsolutePosition(0, 100);
                    image_warter.scalePercent(90);//缩放
                    image_warter.setRotationDegrees(37f);
                } else {
                    image_warter = Image.getInstance(warterMarkImg);
                    image_warter.setAbsolutePosition(-100, 20);
                    image_warter.scalePercent(70);//缩放
                    image_warter.setRotationDegrees(37f);
                }
            }
            Barcode39 code39 = new Barcode39();
            if (!StringUtil.isEmpty(contractData.getTx_ContractNumber())) {
                code39.setCode(contractData.getTx_ContractNumber());
                for (int i = 1; i <= n; i++) {
                    Rectangle rect = reader.getPageSizeWithRotation(i);
                    PdfContentByte canvas = stamper.getOverContent(i);
                    canvas.setFontAndSize(bf, 10f);
                    Image image = code39.createImageWithBarcode(canvas, null, null);
                    image.setAbsolutePosition(rect.getRight() - image.getWidth() - 5, rect.getTop() - image.getHeight() - 5);
                    canvas.addImage(image);
                    if (isWarterMark) {
                        // transparency
                        PdfGState gs1 = new PdfGState();
                        if (!isLoan) {//排除小贷的
                            gs1.setFillOpacity(0.6f);
                        }
                        canvas.setGState(gs1);
                        canvas.addImage(image_warter);
                    }
                }
            }
            stamper.setFormFlattening(true);
            return localContract;
        } catch (IOException | IllegalAccessException | DocumentException e) {
            LoggerUtil.error("create PDF exception :{}", e);
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL, "create PDF exception: " + e.getMessage());
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (DocumentException | IOException e) {
                    LoggerUtil.warn("stamper close exception ", e);
                }
            }
            reader.close();
        }
    }
}

