package com.qycloud.oatos.ldap;

/**
 * group
 * @author yang
 *
 */
public class LdapGroup {

	/**
	 * name
	 */
	private String name;

	/**
	 * getNameInNamespace()
	 */
	private String namespace;

	/**
	 * member
	 */
	private String member;

	/**
	 * memberOf
	 */
	private String memberOf;

	public LdapGroup() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getMemberOf() {
		return memberOf;
	}

	public void setMemberOf(String memberOf) {
		this.memberOf = memberOf;
	}

}
