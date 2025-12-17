package com.awesome.thesis.logic.application.service.security;

import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Value("${security.configuration.admin}")
    private Set<Integer> ids;

    @Autowired
    ProfilEditor editor;

    private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User originalUser = defaultService.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());

        Integer id = originalUser.getAttribute("id");
        if (ids.contains(id)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (editor.contains(id.toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_BETREUENDE"));
        }
        return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
    }
}
