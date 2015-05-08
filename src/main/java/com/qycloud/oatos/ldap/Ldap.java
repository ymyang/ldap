package com.qycloud.oatos.ldap;


/**
 * ldap
 * 
 * @author yang
 * 
 */
public class Ldap {

	public static final AbstractLdap get() {
		LdapConfig ldapConfig = getLdapConfig();
		return get(ldapConfig);
	}

	public static final AbstractLdap get(LdapConfig config) {
		AbstractLdap instance = null;
		if (config.isEnabled()) {
			String ldapType = config.getLdapType();
			System.out.println("ldap type:" + ldapType + ", url:" + config.getLdapUrl() + ", DomainName:" + config.getDomainName() + ", user:" + config.getUserName());
			if (LdapType.AD.name().equalsIgnoreCase(ldapType)) {
				instance = new WinLdap(config);
			} else if (LdapType.OpenLdap.name().equalsIgnoreCase(ldapType)) {
				instance = new OpenLdap(config);
			} else if (LdapType.SunOneDirectory.name().equalsIgnoreCase(ldapType)) {
				instance = new OpenLdap(config);
			} else if (LdapType.Domino.name().equalsIgnoreCase(ldapType)) {
				instance = new DominoLdap(config);
			} else {
				System.err.println("errorNotSupported");
			}
		} else {
			System.err.println("errorNotSupported");
		}
		return instance;
	}

	public static LdapConfig getLdapConfig() {
		LdapConfig ldapConfig = null;
		if (ldapConfig == null) {
			ldapConfig = new LdapConfig();
			ldapConfig.setEnabled(false);
			String url = ConfigUtil.getValue(LdapConfigKey.LDAP_PROVIDER_URL);
			if (url != null && !"".equals(url.trim())) {
				ldapConfig.setEnabled(true);
				ldapConfig.setLdapUrl(url);
				ldapConfig.setDomainName(ConfigUtil.getValue(LdapConfigKey.LDAP_BASEDN));
				ldapConfig.setUserName(ConfigUtil.getValue(LdapConfigKey.LDAP_PRINCIPAL));
				ldapConfig.setPassword(ConfigUtil.getValue(LdapConfigKey.LDAP_PASSWORD));
				ldapConfig.setLdapType(ConfigUtil.getValue(LdapConfigKey.lDAP_TYPE));
				ldapConfig.setSyncUser("true".equalsIgnoreCase(ConfigUtil.getValue(LdapConfigKey.LDAP_TIMER_ENABLE)));
			}
		}
		return ldapConfig;
	}

}