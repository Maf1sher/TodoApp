package org.mafisher.backend.service;

import jakarta.servlet.http.Cookie;

public interface CookieService {
    Cookie getNewCookie(String arg, String value);
    Cookie deleteCookie(String arg);
}
