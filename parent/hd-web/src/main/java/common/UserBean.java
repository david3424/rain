
package common;

import java.io.Serializable;

/**
 * bean UserBean
 *
 * 用户信息
 */
public class UserBean implements Serializable
{

	public UserBean() { }
	public UserBean( Integer _userid, String _username, String _passwd, String _prompt,
				 String _answer, String _truename, String _idnumber, String _email,
				 String _mobilenumber, String _province, String _city, String _phonenumber,
				 String _address, String _postalcode, Integer _gender, String _birthday,
				 long _creatime, String _qq, String _passwd2, Integer _bindstatus)
	{
		userid	= _userid;
		username	= _username;
		passwd	= _passwd;
		prompt	= _prompt;
		answer	= _answer;
		truename	= _truename;
		idnumber	= _idnumber;
		email	= _email;
		mobilenumber	= _mobilenumber;
		province	= _province;
		city	= _city;
		phonenumber	= _phonenumber;
		address	= _address;
		postalcode	= _postalcode;
		gender	= _gender;
		birthday	= _birthday;
		creatime	= _creatime;
		qq	= _qq;
		passwd2	= _passwd2;
		bindstatus	= _bindstatus;
	}

	/**
	 * 用户ID
	 */
	public Integer userid;
	/**
	 * 用户名称,数据库限制最大32字节
	 */
	public String username;
	/**
	 * 密码
	 */
	public String passwd;
	/**
	 * 提示问题,数据库限制最大32字节
	 */
	public String prompt;
	/**
	 * 提示问题答案,数据库限制最大32字节
	 */
	public String answer;
	/**
	 * 真实姓名,数据库限制最大32字节
	 */
	public String truename;
	/**
	 * 身份证号码,数据库限制32字节
	 */
	public String idnumber;
	/**
	 * 电子邮件,数据库限制最大64字节
	 */
	public String email;
	/**
	 * 移动电话,数据库限制最大32字节
	 */
	public String mobilenumber;
	/**
	 * 省份,数据库限制最大32字节
	 */
	public String province;
	/**
	 * 城市,数据库限制最大32字节
	 */
	public String city;
	/**
	 * 电话,数据库限制最大32字节
	 */
	public String phonenumber;
	/**
	 * 地址,数据库限制最大64字节
	 */
	public String address;
	/**
	 * 邮政编码,数据库限制最大8字节
	 */
	public String postalcode;
	/**
	 * 性别. 0 表示 男; 1 表示 女; 2 表示 未知
	 */
	public Integer gender;
	/**
	 * 生日. 格式: 1985-02-01
	 */
	public String birthday;
	/**
	 * 创建时间,unix时间
	 */
	public long creatime;
	/**
	 * QQ号码,数据库限制32字节
	 */
	public String qq;
	/**
	 * 二级密码
	 */
	public String passwd2;
	/**
	 * 含义同getUserType接口的返回值。
	 */
	public Integer bindstatus;


	public void dump() { dump(System.out); }
	public void dump(java.io.PrintStream ps)
	{
		ps.println( "userid = " + userid );
		ps.println( "username = " + username );
		ps.println( "passwd = " + passwd );
		ps.println( "prompt = " + prompt );
		ps.println( "answer = " + answer );
		ps.println( "truename = " + truename );
		ps.println( "idnumber = " + idnumber );
		ps.println( "email = " + email );
		ps.println( "mobilenumber = " + mobilenumber );
		ps.println( "province = " + province );
		ps.println( "city = " + city );
		ps.println( "phonenumber = " + phonenumber );
		ps.println( "address = " + address );
		ps.println( "postalcode = " + postalcode );
		ps.println( "gender = " + gender );
		ps.println( "birthday = " + birthday );
		ps.println( "creatime = " + creatime );
		ps.println( "qq = " + qq );
		ps.println( "passwd2 = " + passwd2 );
		ps.println( "bindstatus = " + bindstatus );
	}
}
