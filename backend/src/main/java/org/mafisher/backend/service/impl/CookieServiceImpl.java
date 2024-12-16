package org.mafisher.backend.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.mafisher.backend.service.CookieService;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImpl implements CookieService {
    @Override
    public Cookie getNewCookie(String arg, String value) {
        Cookie cookie = new Cookie(arg, value);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        return cookie;
    }

    @Override
    public Cookie deleteCookie(String arg) {
        Cookie cookie = new Cookie(arg, null);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

//    public String getJwtCookie(HttpServletRequest request){
//        String jwt = "";
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("jwt")) {
//                    jwt = cookie.getValue();
//                    return jwt;
//                }
//            }
//        }
////        return "";
//        throw new CustomException(BusinessErrorCodes.BAD_COOKIE);
//    }

}
