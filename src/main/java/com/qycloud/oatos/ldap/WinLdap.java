package com.qycloud.oatos.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/**
 * ad ldap
 * 
 * @author yang
 * 
 */
public class WinLdap extends AbstractLdap {

	public WinLdap(LdapConfig ldapConfig) {
		super(ldapConfig);
	}

	@Override
	protected String getUserFilter() {
		return "(&(objectclass=user)(!(objectclass=computer)))";
	}

	@Override
	protected String getUserFilter(String userName) {
		return String.format("(&(sAMAccountName=%s)(objectclass=user))", userName);
	}

	@Override
	protected String getUserDN(String userName) {
		StringBuilder sb = new StringBuilder();
		String[] strs = ldapConfig.getDomainName().split(",");
		if (strs != null && strs.length > 0) {
			for (String str : strs) {
				if (str.toLowerCase().startsWith("dc")) {
					String[] s = str.split("=");
					if (s.length == 2) {
						sb.append(s[1].trim()).append(".");
					}
				}
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return userName + "@" + sb.toString();
	}

	@Override
	protected LdapUser toLdapUser(SearchResult sr) throws NamingException {
		Attributes attrs = sr.getAttributes();

		LdapUser user = new LdapUser();
		user.setNamespace(sr.getNameInNamespace());
		user.setUserName(getAttributeValue(attrs.get("sAMAccountName")));
		user.setRealName(getAttributeValue(attrs.get("displayName")));
		user.setJobTitle(getAttributeValue(attrs.get("title")));
		user.setMail(getAttributeValue(attrs.get("mail")));
		user.setPhone(getAttributeValue(attrs.get("telephoneNumber")));
		user.setMobile(getAttributeValue(attrs.get("mobile")));
		user.setMemberOf(getAttributeValue(attrs.get("memberOf")));

		return user;
	}

}
