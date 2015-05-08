package com.qycloud.oatos.ldap;

/**
 * open ldap
 * 
 * @author yang
 * 
 */
public class DominoLdap extends OpenLdap {

	public DominoLdap(LdapConfig ldapConfig) {
		super(ldapConfig);
	}

	@Override
	protected String getUserDN(String userName) {
		return userName;
	}

}
