package com.coffeehouse.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import java.util.List;


@Transactional
public class RoleSecurityService implements UserDetailsService {


    /**
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return null;
    }

    private List<GrantedAuthority> getAuthority() throws Exception {
        return null;
    }
}