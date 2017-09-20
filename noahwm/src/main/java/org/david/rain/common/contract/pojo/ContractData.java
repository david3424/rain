package org.david.rain.common.contract.pojo;

public class ContractData {
	
	/**
	 * 文件，目录数据以sys开头
	 */
	private String sysTemplateUrl;  		//php下载模板接口地址
	private String sysTemplateName; 		//模板名称
	private String sysContractName;			//生成的合同名称
	private Boolean sysReloadTemplate;       //强制更新模板
	
	private String Tx_ContractNumber; 		//方舟订单号
	private String Tx_CustomerName	;		//登录的用户姓名
	private String Tx_Year;				//用户下单时间  2015
	private String Tx_Month;				//用户下单时间  12
	private String Tx_Day;					//用户下单时间  21
	private String Tx_DayAll;				//2015年12月21日
	
	//读取合格投资者结果，根据结果打标签，若符合其中一项则打勾。
	private String Cb_LawI;				//1）资产不低于300万；
	private String Cb_LawII;				//2）近3年收入不低于50万；
	private String Cb_LawIII;				//3）从业人员。
	
	//php接口字段
	private String ID_Type;   	 			//证件类型
	private String ID_TypeName;				//证件类型名称
	private String ID_Number;				//证件号码
	
	//证件类型
	private String Cb_IDCard;        		//身份证
	private String Cb_Passport;			//护照
	private String Cb_HK;					//港澳通行证
	private String Cb_TW;					//台胞证，台湾居民往来大陆通行证
	

//	private String	Tx_IDType;  			//该用户的证件类型以文本的形式输入
//	private String	Tx_IDNumber; 			//用户注册的身份证号
	
	//通过用户的投资金额定为到用户的分档
	private String Cb_Level1;
	private String Cb_Level2;
	private String Cb_Level3;
	private String Cb_Level4;
	private String Cb_Level5;
	private String Cb_Level6;
	
	
	private String Tx_CardNumber; 			//用户绑定的银行卡的卡号
	private String Tx_BankName;			//用户绑定的银行卡的所属银行名称
	
	//默认是万元
	private String Tx_PayAmount;			//用户的总投资金额
	private String Tx_PayCapital;			//购买金额的中文大写
	private String Tx_OrderAmount;			//用户的下单金额（请在另类产品使用）
	private String Tx_OrderAmountCapital;	//用户的下单金额大写（请在另类产品使用）
	private String Tx_InitialAmount;		//首次缴款金额
	private String Tx_InitialAmountCapital;//首次缴款金额大写
	private String Tx_PostAmount;			//用户下单金额-首次缴款金额的差
	private String Tx_PostAmountCapital;	//用户下单金额-首次缴款金额的差 大写
	private String Tx_ChargeFee;    		//认购申购手续费，单位万元
	private String Tx_ChargeFeeCapital;	//认购申购手续费大写，单位万元

	private String Tx_JoiningFee;			//加入费，单位是万元
	private String Tx_JoiningFeeCapital;   //加入费大写，单位是万元

	
	// 下列单位是元
	private String Tx_PayAmountYuan;			//用户的总投资金额
	private String Tx_PayCapitalYuan;			//购买金额的中文大写
	private String Tx_OrderAmountYuan;			//用户的下单金额（请在另类产品使用）
	private String Tx_OrderAmountCapitalYuan;	//用户的下单金额大写（请在另类产品使用）
	private String Tx_InitialAmountYuan;		//首次缴款金额
	private String Tx_InitialAmountCapitalYuan;//首次缴款金额大写
	private String Tx_PostAmountYuan;			//用户下单金额-首次缴款金额的差
	private String Tx_PostAmountCapitalYuan;	//用户下单金额-首次缴款金额的差 大写

	private String Tx_ChargeFeeYuan;			//认购申购手续费，单位元
	private String Tx_ChargeFeeCapitalYuan;	//认购申购手续费大写，单位元

	private String Tx_JoiningFeeYuan;			//加入费，单位是元
	private String Tx_JoiningFeeCapitalYuan;	//加入费大写，单位是元


	/*小贷新加*/
	private String Tx_ProductDate;			//产品当天日期
	private String Tx_LoanAmount;			//产品包放款金额
	private String Tx_LoanDate;			//放款日期
	private String Tx_EndDate;			//截止日期
	private String Tx_ValueDate;			//起息日
	private String Tx_ProductName;			//营销产品名称
	private String Tx_ProductId;			//产品Id
	private String Tx_AnnualBenefitRate;			//年化利率


	/*机构新加*/
	private String JG_MingChen; //机构全称
	private String JG_YingYeZhiZhao; //营业执照
	private String JG_DaiMa; //组织机构代码
	private String JG_FaRen; //法定代表人
	private String JG_LianXiRen; //联系人
	private String JG_Year; //联系人
	private String JG_Month; //联系人
	private String JG_Day; //联系人


	public ContractData(){
		
	}

	public String getJG_Year() {
		return JG_Year;
	}

	public void setJG_Year(String JG_Year) {
		this.JG_Year = JG_Year;
	}

	public String getJG_Month() {
		return JG_Month;
	}

	public void setJG_Month(String JG_Month) {
		this.JG_Month = JG_Month;
	}

	public String getJG_Day() {
		return JG_Day;
	}

	public void setJG_Day(String JG_Day) {
		this.JG_Day = JG_Day;
	}

	public String getJG_MingChen() {
		return JG_MingChen;
	}

	public void setJG_MingChen(String JG_MingChen) {
		this.JG_MingChen = JG_MingChen;
	}

	public String getJG_YingYeZhiZhao() {
		return JG_YingYeZhiZhao;
	}

	public void setJG_YingYeZhiZhao(String JG_YingYeZhiZhao) {
		this.JG_YingYeZhiZhao = JG_YingYeZhiZhao;
	}

	public String getJG_DaiMa() {
		return JG_DaiMa;
	}

	public void setJG_DaiMa(String JG_DaiMa) {
		this.JG_DaiMa = JG_DaiMa;
	}

	public String getJG_FaRen() {
		return JG_FaRen;
	}

	public void setJG_FaRen(String JG_FaRen) {
		this.JG_FaRen = JG_FaRen;
	}

	public String getJG_LianXiRen() {
		return JG_LianXiRen;
	}

	public void setJG_LianXiRen(String JG_LianXiRen) {
		this.JG_LianXiRen = JG_LianXiRen;
	}

	public String getTx_ProductDate() {
		return Tx_ProductDate;
	}

	public void setTx_ProductDate(String tx_ProductDate) {
		Tx_ProductDate = tx_ProductDate;
	}

	public String getTx_LoanAmount() {
		return Tx_LoanAmount;
	}

	public void setTx_LoanAmount(String tx_LoanAmount) {
		Tx_LoanAmount = tx_LoanAmount;
	}

	public String getTx_LoanDate() {
		return Tx_LoanDate;
	}

	public void setTx_LoanDate(String tx_LoanDate) {
		Tx_LoanDate = tx_LoanDate;
	}

	public String getTx_EndDate() {
		return Tx_EndDate;
	}

	public void setTx_EndDate(String tx_EndDate) {
		Tx_EndDate = tx_EndDate;
	}

	public String getTx_ValueDate() {
		return Tx_ValueDate;
	}

	public void setTx_ValueDate(String tx_ValueDate) {
		Tx_ValueDate = tx_ValueDate;
	}

	public String getTx_ProductName() {
		return Tx_ProductName;
	}

	public void setTx_ProductName(String tx_ProductName) {
		Tx_ProductName = tx_ProductName;
	}

	public String getTx_ProductId() {
		return Tx_ProductId;
	}

	public void setTx_ProductId(String tx_ProductId) {
		Tx_ProductId = tx_ProductId;
	}

	public String getTx_AnnualBenefitRate() {
		return Tx_AnnualBenefitRate;
	}

	public void setTx_AnnualBenefitRate(String tx_AnnualBenefitRate) {
		Tx_AnnualBenefitRate = tx_AnnualBenefitRate;
	}

	public String getSysTemplateUrl() {
		return sysTemplateUrl;
	}

	public void setSysTemplateUrl(String sysTemplateUrl) {
		this.sysTemplateUrl = sysTemplateUrl;
	}

	public String getSysTemplateName() {
		return sysTemplateName;
	}

	public void setSysTemplateName(String sysTemplateName) {
		this.sysTemplateName = sysTemplateName;
	}

	public String getSysContractName() {
		return sysContractName;
	}

	public void setSysContractName(String sysContractName) {
		this.sysContractName = sysContractName;
	}

	public String getTx_ContractNumber() {
		return Tx_ContractNumber;
	}

	public void setTx_ContractNumber(String tx_ContractNumber) {
		Tx_ContractNumber = tx_ContractNumber;
	}

	public String getTx_CustomerName() {
		return Tx_CustomerName;
	}

	public void setTx_CustomerName(String tx_CustomerName) {
		Tx_CustomerName = tx_CustomerName;
	}

	public String getTx_Year() {
		return Tx_Year;
	}

	public void setTx_Year(String tx_Year) {
		Tx_Year = tx_Year;
	}

	public String getTx_Month() {
		return Tx_Month;
	}

	public void setTx_Month(String tx_Month) {
		Tx_Month = tx_Month;
	}

	public String getTx_Day() {
		return Tx_Day;
	}

	public void setTx_Day(String tx_Day) {
		Tx_Day = tx_Day;
	}

	public String getTx_DayAll() {
		return Tx_DayAll;
	}

	public void setTx_DayAll(String tx_DayAll) {
		Tx_DayAll = tx_DayAll;
	}

	public String getCb_LawI() {
		return Cb_LawI;
	}

	public void setCb_LawI(String cb_LawI) {
		Cb_LawI = cb_LawI;
	}

	public String getCb_LawII() {
		return Cb_LawII;
	}

	public void setCb_LawII(String cb_LawII) {
		Cb_LawII = cb_LawII;
	}

	public String getCb_LawIII() {
		return Cb_LawIII;
	}

	public void setCb_LawIII(String cb_LawIII) {
		Cb_LawIII = cb_LawIII;
	}

	public String getCb_Level1() {
		return Cb_Level1;
	}

	public void setCb_Level1(String cb_Level1) {
		Cb_Level1 = cb_Level1;
	}

	public String getCb_Level2() {
		return Cb_Level2;
	}

	public void setCb_Level2(String cb_Level2) {
		Cb_Level2 = cb_Level2;
	}

	public String getCb_Level3() {
		return Cb_Level3;
	}

	public void setCb_Level3(String cb_Level3) {
		Cb_Level3 = cb_Level3;
	}

	public String getCb_Level4() {
		return Cb_Level4;
	}

	public void setCb_Level4(String cb_Level4) {
		Cb_Level4 = cb_Level4;
	}

	public String getCb_Level5() {
		return Cb_Level5;
	}

	public void setCb_Level5(String cb_Level5) {
		Cb_Level5 = cb_Level5;
	}

	public String getCb_Level6() {
		return Cb_Level6;
	}

	public void setCb_Level6(String cb_Level6) {
		Cb_Level6 = cb_Level6;
	}

	public String getTx_CardNumber() {
		return Tx_CardNumber;
	}

	public void setTx_CardNumber(String tx_CardNumber) {
		Tx_CardNumber = tx_CardNumber;
	}

	public String getTx_BankName() {
		return Tx_BankName;
	}

	public void setTx_BankName(String tx_BankName) {
		Tx_BankName = tx_BankName;
	}

	public String getTx_PayAmount() {
		return Tx_PayAmount;
	}

	public void setTx_PayAmount(String tx_PayAmount) {
		Tx_PayAmount = tx_PayAmount;
	}

	public String getTx_PayCapital() {
		return Tx_PayCapital;
	}

	public void setTx_PayCapital(String tx_PayCapital) {
		Tx_PayCapital = tx_PayCapital;
	}

	public String getTx_OrderAmount() {
		return Tx_OrderAmount;
	}

	public void setTx_OrderAmount(String tx_OrderAmount) {
		Tx_OrderAmount = tx_OrderAmount;
	}

	public String getTx_OrderAmountCapital() {
		return Tx_OrderAmountCapital;
	}

	public void setTx_OrderAmountCapital(String tx_OrderAmountCapital) {
		Tx_OrderAmountCapital = tx_OrderAmountCapital;
	}

	public String getTx_InitialAmount() {
		return Tx_InitialAmount;
	}

	public void setTx_InitialAmount(String tx_InitialAmount) {
		Tx_InitialAmount = tx_InitialAmount;
	}

	public String getTx_InitialAmountCapital() {
		return Tx_InitialAmountCapital;
	}

	public void setTx_InitialAmountCapital(String tx_InitialAmountCapital) {
		Tx_InitialAmountCapital = tx_InitialAmountCapital;
	}

	public String getTx_PostAmount() {
		return Tx_PostAmount;
	}

	public void setTx_PostAmount(String tx_PostAmount) {
		Tx_PostAmount = tx_PostAmount;
	}

	public String getTx_PostAmountCapital() {
		return Tx_PostAmountCapital;
	}

	public void setTx_PostAmountCapital(String tx_PostAmountCapital) {
		Tx_PostAmountCapital = tx_PostAmountCapital;
	}
	

	public String getID_Type() {
		return ID_Type;
	}
	public void setID_Type(String iD_Type) {
		ID_Type = iD_Type;
	}
	public String getID_TypeName() {
		return ID_TypeName;
	}
	public void setID_TypeName(String iD_TypeName) {
		ID_TypeName = iD_TypeName;
	}
	public String getID_Number() {
		return ID_Number;
	}
	public void setID_Number(String iD_Number) {
		ID_Number = iD_Number;
	}
	public String getCb_IDCard() {
		return Cb_IDCard;
	}
	public void setCb_IDCard(String cb_IDCard) {
		Cb_IDCard = cb_IDCard;
	}
	public String getCb_Passport() {
		return Cb_Passport;
	}
	public void setCb_Passport(String cb_Passport) {
		Cb_Passport = cb_Passport;
	}
	public String getCb_HK() {
		return Cb_HK;
	}
	public void setCb_HK(String cb_HK) {
		Cb_HK = cb_HK;
	}
	public String getCb_TW() {
		return Cb_TW;
	}
	public void setCb_TW(String cb_TW) {
		Cb_TW = cb_TW;
	}
	public Boolean getSysReloadTemplate() {
		return sysReloadTemplate;
	}
	public void setSysReloadTemplate(Boolean sysReloadTemplate) {
		this.sysReloadTemplate = sysReloadTemplate;
	}
	public String getTx_PayAmountYuan() {
		return Tx_PayAmountYuan;
	}
	public void setTx_PayAmountYuan(String tx_PayAmountYuan) {
		Tx_PayAmountYuan = tx_PayAmountYuan;
	}
	public String getTx_PayCapitalYuan() {
		return Tx_PayCapitalYuan;
	}
	public void setTx_PayCapitalYuan(String tx_PayCapitalYuan) {
		Tx_PayCapitalYuan = tx_PayCapitalYuan;
	}
	public String getTx_OrderAmountYuan() {
		return Tx_OrderAmountYuan;
	}
	public void setTx_OrderAmountYuan(String tx_OrderAmountYuan) {
		Tx_OrderAmountYuan = tx_OrderAmountYuan;
	}
	public String getTx_OrderAmountCapitalYuan() {
		return Tx_OrderAmountCapitalYuan;
	}
	public void setTx_OrderAmountCapitalYuan(String tx_OrderAmountCapitalYuan) {
		Tx_OrderAmountCapitalYuan = tx_OrderAmountCapitalYuan;
	}
	public String getTx_InitialAmountYuan() {
		return Tx_InitialAmountYuan;
	}
	public void setTx_InitialAmountYuan(String tx_InitialAmountYuan) {
		Tx_InitialAmountYuan = tx_InitialAmountYuan;
	}
	public String getTx_InitialAmountCapitalYuan() {
		return Tx_InitialAmountCapitalYuan;
	}
	public void setTx_InitialAmountCapitalYuan(String tx_InitialAmountCapitalYuan) {
		Tx_InitialAmountCapitalYuan = tx_InitialAmountCapitalYuan;
	}
	public String getTx_PostAmountYuan() {
		return Tx_PostAmountYuan;
	}
	public void setTx_PostAmountYuan(String tx_PostAmountYuan) {
		Tx_PostAmountYuan = tx_PostAmountYuan;
	}
	public String getTx_PostAmountCapitalYuan() {
		return Tx_PostAmountCapitalYuan;
	}
	public void setTx_PostAmountCapitalYuan(String tx_PostAmountCapitalYuan) {
		Tx_PostAmountCapitalYuan = tx_PostAmountCapitalYuan;
	}
	public String getTx_ChargeFee() {
		return Tx_ChargeFee;
	}
	public void setTx_ChargeFee(String tx_ChargeFee) {
		Tx_ChargeFee = tx_ChargeFee;
	}
	public String getTx_ChargeFeeCapital() {
		return Tx_ChargeFeeCapital;
	}
	public void setTx_ChargeFeeCapital(String tx_ChargeFeeCapital) {
		Tx_ChargeFeeCapital = tx_ChargeFeeCapital;
	}
	public String getTx_ChargeFeeYuan() {
		return Tx_ChargeFeeYuan;
	}
	public void setTx_ChargeFeeYuan(String tx_ChargeFeeYuan) {
		Tx_ChargeFeeYuan = tx_ChargeFeeYuan;
	}
	public String getTx_ChargeFeeCapitalYuan() {
		return Tx_ChargeFeeCapitalYuan;
	}
	public void setTx_ChargeFeeCapitalYuan(String tx_ChargeFeeCapitalYuan) {
		Tx_ChargeFeeCapitalYuan = tx_ChargeFeeCapitalYuan;
	}
	public String getTx_JoiningFee() {
		return Tx_JoiningFee;
	}
	public void setTx_JoiningFee(String tx_JoiningFee) {
		Tx_JoiningFee = tx_JoiningFee;
	}
	public String getTx_JoiningFeeYuan() {
		return Tx_JoiningFeeYuan;
	}
	public void setTx_JoiningFeeYuan(String tx_JoiningFeeYuan) {
		Tx_JoiningFeeYuan = tx_JoiningFeeYuan;
	}
	public String getTx_JoiningFeeCapital() {
		return Tx_JoiningFeeCapital;
	}
	public void setTx_JoiningFeeCapital(String tx_JoiningFeeCapital) {
		Tx_JoiningFeeCapital = tx_JoiningFeeCapital;
	}
	public String getTx_JoiningFeeCapitalYuan() {
		return Tx_JoiningFeeCapitalYuan;
	}
	public void setTx_JoiningFeeCapitalYuan(String tx_JoiningFeeCapitalYuan) {
		Tx_JoiningFeeCapitalYuan = tx_JoiningFeeCapitalYuan;
	}
	
	
	
	
	
}
