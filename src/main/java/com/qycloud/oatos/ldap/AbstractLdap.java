package com.qycloud.oatos.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public abstract class AbstractLdap {

	protected LdapContext ldapContext = null;

	protected LdapConfig ldapConfig;

	public AbstractLdap(LdapConfig ldapConfig) {
		init(ldapConfig);
	}

	/**
	 * 初始化ldap
	 * 
	 * @param ldapConfig
	 */
	private void init(LdapConfig ldapConfig) {
		this.ldapConfig = ldapConfig;
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapConfig.getLdapUrl());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, ldapConfig.getUserName());
		env.put(Context.SECURITY_CREDENTIALS, ldapConfig.getPassword());
		// 此处若不指定用户名和密码,则自动转换为匿名登录
		try {
			ldapContext = new InitialLdapContext(env, null);

			search("(&(cn=test)(objectclass=person))");
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 验证密码
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean auth(String userName, String password) {
		boolean auth = false;
		try {
			ldapContext.addToEnvironment(Context.SECURITY_PRINCIPAL, getUserDN(userName));
			ldapContext.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
			ldapContext.reconnect(null);
			auth = true;
		} catch (NamingException ex) {
			auth = false;
		}
		return auth;
	}

	/**
	 * 取单个ldap用户信息
	 * 
	 * @param userName
	 * @return
	 */
	public LdapUser getUser(String userName) {
		LdapUser user = null;
		try {
			NamingEnumeration<SearchResult> rs = search(getUserFilter(userName));
			if (rs.hasMoreElements()) {
				SearchResult sr = rs.next();
				user = toLdapUser(sr);

				// TODO debug
				System.out.println("---getLdapUsers item----------------------");
				System.out.println("getName() : " + sr.getName());
				System.out.println("getNameInNamespace() : " + sr.getNameInNamespace());

				NamingEnumeration<? extends Attribute> attrs = sr.getAttributes().getAll();
				while (attrs.hasMore()) {
					Attribute attr = attrs.next();
					System.out.println(attr.getID() + "===" + getAttributeValue(attr));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}

	/**
	 * 取所有ldap用户
	 * 
	 * @return
	 */
	public List<LdapUser> getUsers() {
		List<LdapUser> users = new ArrayList<LdapUser>();
		try {
			NamingEnumeration<SearchResult> rs = search(getUserFilter());
			while (rs.hasMoreElements()) {
				SearchResult sr = rs.next();
				users.add(toLdapUser(sr));

				// TODO debug
				System.out.println("---getLdapUsers item----------------------");
				System.out.println("getName() : " + sr.getName());
				System.out.println("getNameInNamespace() : " + sr.getNameInNamespace());

				NamingEnumeration<? extends Attribute> attrs = sr.getAttributes().getAll();
				while (attrs.hasMore()) {
					Attribute attr = attrs.next();
					System.out.println(attr.getID() + "===" + getAttributeValue(attr));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return users;
	}

	public List<LdapOU> getOU() {
		List<LdapOU> ous = new ArrayList<LdapOU>();
		try {
			NamingEnumeration<SearchResult> rs = search("(objectclass=organizationalUnit)");
			while (rs.hasMoreElements()) {
				SearchResult sr = rs.next();
				LdapOU ou = new LdapOU();
				ou.setNamespace(sr.getNameInNamespace());

				Attributes attrs = sr.getAttributes();
				ou.setName(getAttributeValue(attrs.get("ou")));
				ous.add(ou);

				// TODO debug
				System.out.println("---getOU item----------------------");
				System.out.println("getName() : " + sr.getName());
				System.out.println("getNameInNamespace() : " + sr.getNameInNamespace());

				NamingEnumeration<? extends Attribute> all = sr.getAttributes().getAll();
				while (all.hasMore()) {
					Attribute attr = all.next();
					System.out.println(attr.getID() + "===" + getAttributeValue(attr));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ous;
	}

	public List<LdapGroup> getGroup() {
		List<LdapGroup> groups = new ArrayList<LdapGroup>();
		try {
			NamingEnumeration<SearchResult> rs = search("(objectclass=group)");
			while (rs.hasMoreElements()) {
				SearchResult sr = rs.next();
				LdapGroup group = new LdapGroup();
				group.setNamespace(sr.getNameInNamespace());

				Attributes attrs = sr.getAttributes();
				group.setName(getAttributeValue(attrs.get("cn")));
				group.setMember(getAttributeValue(attrs.get("member")));
				group.setMemberOf(getAttributeValue(attrs.get("memberOf")));
				groups.add(group);

				// TODO debug
				System.out.println("---getGroup item----------------------");
				System.out.println("getName() : " + sr.getName());
				System.out.println("getNameInNamespace() : " + sr.getNameInNamespace());

				NamingEnumeration<? extends Attribute> all = sr.getAttributes().getAll();
				while (all.hasMore()) {
					Attribute attr = all.next();
					System.out.println(attr.getID() + "===" + getAttributeValue(attr));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return groups;
	}

	protected NamingEnumeration<SearchResult> search(String filter) throws NamingException {
		// Create the search controls
		SearchControls searchCtls = new SearchControls();

		// Specify the search scope
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		// Search for objects using the filter
		// "ou=client,dc=test,dc=com"
		NamingEnumeration<SearchResult> rs = ldapContext.search(ldapConfig.getDomainName(), filter, searchCtls);

		return rs;
	}

	protected String getAttributeValue(Attribute attr) throws NamingException {
		StringBuilder sb = new StringBuilder();
		if (attr != null) {
			NamingEnumeration<?> values = attr.getAll();
			while (values.hasMoreElements()) {
				Object v = values.next();
				sb.append(v.toString()).append(";");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		return sb.toString();
	}

	protected abstract String getUserFilter();

	protected abstract String getUserFilter(String userName);

	protected abstract String getUserDN(String userName);

	protected abstract LdapUser toLdapUser(SearchResult sr) throws NamingException;

}
