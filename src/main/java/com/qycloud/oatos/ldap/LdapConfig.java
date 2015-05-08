package com.qycloud.oatos.ldap;



/**
 * 对象名称：LDAP配置实体类
 * 作者 ：huhao
 * 创建日期 :2013-9-3 上午10:27:13
 * 对象内容: LDAP私有云服务的实体类
 */
public class LdapConfig {
	
	/**
	 * 企业ID
	 */
	private long entId ;
	
	/**
	 * 是否启动域配置
	 */
	private boolean enabled;
	
	/**
	 * 域地址
	 */
	private String ldapUrl;
	
	/**
	 * 域名
	 */
	private String domainName;
	
	/**
	 * 域管理员用户名
	 */
	private String userName;
	
	/**
	 * 域管理员密码
	 */
	private String password;
	
	/**
	 * 域类型
	 */
	private String ldapType;
	
	/**
	 * 是否自动同步域用户
	 */
	private boolean syncUser;

	/**
	 * 构造函数
	 */
	public LdapConfig() {
	}

	public long getEntId() {
		return entId;
	}

	public void setEntId(long entId) {
		this.entId = entId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLdapUrl() {
		return ldapUrl;
	}

	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLdapType() {
		return ldapType;
	}

	public void setLdapType(String ldapType) {
		this.ldapType = ldapType;
	}

	public boolean isSyncUser() {
		return syncUser;
	}

	public void setSyncUser(boolean syncUser) {
		this.syncUser = syncUser;
	}

}
