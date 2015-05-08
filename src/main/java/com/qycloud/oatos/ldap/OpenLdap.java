package com.qycloud.oatos.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/**
 * open ldap
 * @author yang
 *
 */
public class OpenLdap extends AbstractLdap {

	public OpenLdap(LdapConfig ldapConfig) {
		super(ldapConfig);
	}

	@Override
	protected String getUserFilter() {
		return "(objectclass=person)";
	}

	@Override
	protected String getUserFilter(String userName) {
		return String.format("(&(uid=%s)(objectclass=person))", userName);
	}

	@Override
	protected String getUserDN(String userName) {
		LdapUser user = getUser(userName);
		if (user == null) {
			System.err.println(userName + " not found");
		}
		return user.getNamespace();
	}

	@Override
	protected LdapUser toLdapUser(SearchResult sr) throws NamingException {
		Attributes attrs = sr.getAttributes();

		LdapUser user = new LdapUser();
		user.setNamespace(sr.getNameInNamespace());
		user.setUserName(getAttributeValue(attrs.get("uid")));
		user.setRealName(getAttributeValue(attrs.get("cn")));
		user.setJobTitle(getAttributeValue(attrs.get("title")));
		user.setMail(getAttributeValue(attrs.get("mail")));
		user.setPhone(getAttributeValue(attrs.get("telephoneNumber")));
		user.setMobile(getAttributeValue(attrs.get("mobile")));
		user.setMemberOf(getAttributeValue(attrs.get("memberOf")));

		return user;
	}

}
