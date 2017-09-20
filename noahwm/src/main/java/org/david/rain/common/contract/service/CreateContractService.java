package org.david.rain.common.contract.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.david.rain.common.contract.pojo.GlobalConfig;
import org.david.rain.common.contract.service.abstractor.IServiceProcessor;
import org.apache.commons.lang3.StringUtils;
import org.david.rain.common.exception.ErrorCode;
import org.david.rain.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

@Service("createContractService")
public class CreateContractService implements IServiceProcessor {

    @Autowired
    GlobalConfig globalConfig;

    public String doHandler(String datas) {
        if (StringUtils.isEmpty(datas)) {
            throw new ServiceException(ErrorCode.PARAM_ILLEGAL);
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
        long start = System.currentTimeMillis();

        LoggerUtil.info("create pdf 请求begin =====请求时间:" + DateUtils.getNowDateTime() + "========");
        LoggerUtil.info("接收到的参数：" + datas);
        ContractData contractData = new ContractData();
        try {
            contractData = JSON.parseObject(datas, ContractData.class);
        } catch (Exception e) {
            throw new ContractException(CommonErrorCode.PARSING_PARAMS_ERROR);
        }

        if (StringUtil.isEmpty(contractData.getSysTemplateName(), contractData.getSysContractName(), contractData.getSysTemplateUrl(), contractData.getTx_ContractNumber())) {
            throw new ContractException(CommonErrorCode.PARAM_ILLEGAL);
        }
        String result = createContract(contractData);

        stf.setLength(0);
        stf.append("create pdf 请求end =============" + "time elapsed(ms):" + (System.currentTimeMillis() - start));
        LoggerUtil.info(stf.toString());
        return result;
    }

    /**
     * 制作合同
     *
     * @param contractData
     * @return
     * @throws Exception
     */
    public String createContract(ContractData contractData)
            throws Exception {
        String templateName = contractData.getSysTemplateName();
        if (!templateName.toLowerCase().endsWith(".pdf")) {
            templateName += ".pdf";
        }
        // 如果template存在则直接使用，否则调用接口下载; 如果指定更新模板，则重新下载
        String templatePath = globalConfig.getTemplateDirLocal() + templateName;
        File tempFile = new File(templatePath);
        if (!tempFile.exists() || (contractData.getSysReloadTemplate() != null && contractData.getSysReloadTemplate())) { // 调用接口下载
            LoggerUtil.info(" begin download,url:" + contractData.getSysTemplateUrl());
            DownloadFileUtil.getFile(contractData.getSysTemplateUrl(), globalConfig.getTemplateDirLocal(), templateName);
        } else {
            LoggerUtil.info("template file exist,url:" + contractData.getSysTemplateUrl());
        }
        if (!tempFile.exists()) {
            throw new ContractException(CommonErrorCode.TEMPLATE_NOT_FOUND);
        }
        // 对template进行处理,处理成功返回合同本地地址
        String localContractPath_watermark = fillTagInPdf(templatePath, contractData, true);
        String localContractPath = fillTagInPdf(templatePath, contractData, false);
        File localContract = new File(localContractPath);
        String remoteUrl = "";
        String remoteUrl_wartermark = "";
        if (localContract.exists()) {
            try {
                remoteUrl = uploadService.sshSftp(new File(localContractPath));
                remoteUrl_wartermark = uploadService.sshSftp(new File(localContractPath_watermark));
            } catch (Exception e) {
                LoggerUtil.error("upload sshsftp error :{}", e.getMessage());
                return "failed";
            }
        }
        // 将文件url填入对应的订单表中
        KoContractAttachmentHandle contractAtt = new KoContractAttachmentHandle();
        KoContractAttachmentHandleExample contractAttExample = new KoContractAttachmentHandleExample();
        contractAtt.setPdfUrl(remoteUrl);
        contractAtt.setPdfWatermarkUrl(remoteUrl_wartermark);
        contractAttExample.createCriteria().andOrderIdEqualTo(contractData.getTx_ContractNumber());
        contractAttMapper.updateByExampleSelective(contractAtt, contractAttExample);
        return "success";
    }

    /**
     * 将数据填入模板标签
     *
     * @param templatePath 模板路径
     * @param contractData 填充的数据
     * @return
     * @throws DocumentException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private String fillTagInPdf(String templatePath, ContractData contractData, boolean isWarterMark)
            throws DocumentException, IOException, IllegalArgumentException, IllegalAccessException { //UniCNS-UCS2-H
        //SIMHEI  5909
        //BaseFont bf = BaseFont.createFont(Flt.PATH+"simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); 5686
        //SIMKAI 7500
        //		BaseFont bf = BaseFont.createFont(AsianFontMapper.ChineseTraditionalFont_MSung, AsianFontMapper.ChineseTraditionalEncoding_H, BaseFont.EMBEDDED);
        //		BaseFont bf = BaseFont.createFont(Flt.PATH+"SIMHEI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        String testFile = globalConfig.getResourceUrl() + "fangsong_GBK.TTF";
        LoggerUtil.info("testFile:" + testFile);
        BaseFont bf = BaseFont.createFont(testFile, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        bf.setCompressionLevel(9);
        bf.setSubset(true);

        String contractName = isWarterMark ? contractData.getSysContractName() + "-wm" : contractData.getSysContractName();// 生成后的合同名称
        if (!contractName.toLowerCase().endsWith(".pdf")) {
            contractName += ".pdf";
        } else {
            throw new RuntimeException("文件名不能带后缀");
        }
        String date = DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_DATE_FORMAT);
        String contractPath = globalConfig.getContractDirLocal() + date + "/";
        File file = new File(contractPath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        String localContract = contractPath + contractName;
        LoggerUtil.info("===templatePath:" + templatePath);
        PdfReader reader = new PdfReader(templatePath);

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

                if ("ID_Type".equals(fieldName)) {  //根据传入的ID_TYPE计算
                    String tagName = IdTypeConstant.ID_FLAG.get(fieldValue.toString()); //证件号对应的标签名 1身份证 Cb_IDCard
                    if (s.getFields().keySet().contains(tagName)) { //如果已经有该类型的证件，就不要显示其他
                        LoggerUtil.info(" id type exist," + tagName);
                        //设置其他
                        s.setFieldProperty("Tx_OtherName", "textfont", bf, null);
                        s.setFieldProperty("Tx_OtherName", "textsize", 10f, null);
                        s.setField("Tx_OtherName", "其它");

                        //勾选证件类型
                        s.setField(tagName, "on", true); // 保留原来设定的符合

                        //设置页面idType
                        s.setFieldProperty("Tx_IDType", "textfont", bf, null);
                        s.setFieldProperty("Tx_IDType", "textsize", 10f, null);
                        if (contractData.getID_TypeName() != null) {
                            s.setField("Tx_IDType", contractData.getID_TypeName());
                        }

                    } else {
                        s.setFieldProperty("Tx_OtherName", "textfont", bf, null);
                        s.setFieldProperty("Tx_OtherName", "textsize", 10f, null);
                        s.setField("Tx_OtherName", contractData.getID_TypeName());
                        s.setField("Cb_Other", "on", true); // 保留原来设定的符合

                        //设置页面idType
                        s.setFieldProperty("Tx_IDType", "textfont", bf, null);
                        s.setFieldProperty("Tx_IDType", "textsize", 10f, null);
                        if (contractData.getID_TypeName() != null) {
                            s.setField("Tx_IDType", contractData.getID_TypeName());
                        }
                    }
                    if (!StringUtil.isEmpty(contractData.getID_Number())) {
                        //填写证件号码
                        int x = 1;
                        String[] numbers = contractData.getID_Number().trim().split("");
                        for (String number : numbers) {
                            if (StringUtils.isNumeric(number)) {
                                s.setFieldProperty("Tx_IDNumber" + x, "textfont", bf, null);
                                s.setFieldProperty("Tx_IDNumber" + x, "textsize", 10f, null);
                                s.setField("Tx_IDNumber" + x, number);
                                x++;
                            }
                        }
                        if (s.getFields().keySet().contains("Tx_IDNumber")) {
                            s.setFieldProperty("Tx_IDNumber", "textfont", bf, null);
                            s.setFieldProperty("Tx_IDNumber", "textsize", 10f, null);
                            s.setField("Tx_IDNumber", contractData.getID_Number());
                        }
                    }
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
                    image_warter = Image.getInstance(globalConfig.getLoanWarterMarkImg());
                    image_warter.setAbsolutePosition(0, 100);
                    image_warter.scalePercent(90);//缩放
                    image_warter.setRotationDegrees(37f);
                } else {
                    image_warter = Image.getInstance(globalConfig.getWarterMarkImg());
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
                    //ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, new Phrase(contractData.getTx_ContractNumber()),rect.getRight(),rect.getTop()-20, 0);
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
        } finally {
            if (stamper != null) {
                stamper.close();
            }
            reader.close();
        }

    }

    public String createPrevContract(ContractData contractData) throws Exception {

        String templateName = contractData.getSysTemplateName().toLowerCase().endsWith(".pdf") ? contractData.getSysTemplateName() : contractData.getSysTemplateName() + ".pdf";
        // 如果template存在则直接使用，否则调用接口下载; 如果指定更新模板，则重新下载
        String templatePath = globalConfig.getTemplateDirLocal() + templateName;
        File tempFile = new File(templatePath);
        if (!tempFile.exists() || (contractData.getSysReloadTemplate() != null && contractData.getSysReloadTemplate())) { // 调用七牛接口下载
            LoggerUtil.info(" begin download,url:" + contractData.getSysTemplateUrl());
            DownloadFileUtil.getFile(contractData.getSysTemplateUrl(), globalConfig.getTemplateDirLocal(), templateName);
        } else {
            LoggerUtil.info("template file exist,url:" + contractData.getSysTemplateUrl());
        }
        if (!tempFile.exists()) {
            throw new ContractException(CommonErrorCode.TEMPLATE_NOT_FOUND);
        }
        // 对template进行处理,处理成功返回合同本地地址
        //        String localContractPath_watermark = fillTagInPdf(templatePath, contractData, true);
        contractData.setTx_ContractNumber("0000000000000");
        contractData.setSysContractName("preview");
        String localContractPath = fillTagInPdf(templatePath, contractData, false);
        File localContract = new File(localContractPath);
        String remoteUrl = "";
        //        String remoteUrl_wartermark = "";
        if (localContract.exists()) {
            remoteUrl = uploadService.sshSftp(new File(localContractPath));
            //            remoteUrl_wartermark = uploadService.sshSftp(new File(localContractPath_watermark));
        }
        // 将文件url填入对应的订单表中
        /* KoContractAttachmentHandle contractAtt = new KoContractAttachmentHandle();
        KoContractAttachmentHandleExample contractAttExample = new KoContractAttachmentHandleExample();
        contractAtt.setPdfUrl(remoteUrl);
        contractAtt.setPdfWatermarkUrl(remoteUrl_wartermark);
        contractAttExample.createCriteria().andOrderIdEqualTo(contractData.getTx_ContractNumber());
        contractAttMapper.updateByExampleSelective(contractAtt, contractAttExample);*/
        return remoteUrl;

    }


}

