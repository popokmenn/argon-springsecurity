package com.naufal.argon.configuration.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.naufal.argon.Repository.UserRepository;
import com.naufal.argon.model.User;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        HttpSession session = request.getSession();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        User user = userRepository.findByUsername(userDetail.getUsername());

        session.setAttribute("user", user);

        if (response.isCommitted()) {
            // logger.debug(
            // "Response has already been committed. Unable to redirect to "
            // + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        // boolean isUser = false;
        // boolean isAdmin = false;
        // Collection<? extends GrantedAuthority> authorities
        // = authentication.getAuthorities();
        // for (GrantedAuthority grantedAuthority : authorities) {
        // if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
        // isUser = true;
        // break;
        // } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
        // isAdmin = true;
        // break;
        // }
        // }
        //
        // if (isUser) {
        // return "/homepage.html";
        // } else if (isAdmin) {
        // return "/console.html";
        // } else {
        // throw new IllegalStateException();
        // }
        return "/dashboard";
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
