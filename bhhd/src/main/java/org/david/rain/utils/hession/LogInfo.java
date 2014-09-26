package org.david.rain.utils.hession;

public class LogInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9003680367297471978L;
	/**
	 * 操作者ID
	 */
	public int userId = 0;
	/**
	 * 操作者名称
	 */
	public String userName = "";
	/**
	 * 原因
	 */
	public String reason = "";

	public LogInfo() {
	}

	public LogInfo(int _userId, String _userName, String _reason) {
		userId = _userId;
		userName = _userName;
		reason = _reason;
	}

	public String toString() {
		return "LogInfo(" + "userId=" + userId + ", userName='" + userName
				+ '\'' + ", reason='" + reason + '\'' + ')';
	}

	public void dump() {
		dump(System.out);
	}

	public void dump(java.io.PrintStream ps) {
		ps.println("userId = " + userId);
		ps.println("userName = " + userName);
		ps.println("reason = " + reason);
	}

}
