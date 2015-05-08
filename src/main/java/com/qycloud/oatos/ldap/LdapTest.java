package com.qycloud.oatos.ldap;

import java.util.List;

public class LdapTest {

	public static void main(String[] args) {
		if (args != null) {
			if (args.length == 1) {
				LdapUser user = Ldap.get().getUser(args[0]);
				if (user != null) {
					System.out.println(user.toString());
				} else {
					System.err.println(args[0] + " not found");
				}
			} else if (args.length >= 2) {
				boolean auth = Ldap.get().auth(args[0], args[1]);
				System.out.println("auth:" + auth);
			} else {
				List<LdapUser> users = Ldap.get().getUsers();
				System.out.println("getLdapUsers:" + users.size());
			}
		} else {
			List<LdapUser> users = Ldap.get().getUsers();
			System.out.println("getLdapUsers:" + users.size());
		}
	}

}
