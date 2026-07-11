package com.hooni.component;

import com.hooni.web.util.HttpUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;

@Component
@RequestScope
public class CookieComp
{
    private final HttpServletRequest _req;
    private final HttpServletResponse _res;
    private Map<String, Cookie> _cookieMap;

    public  CookieComp(HttpServletRequest req, HttpServletResponse res)
    {
        this._req = req;
        this._res = res;
        _cookieMap = HttpUtil.getCookies(req);
    }

    public HttpServletRequest getRequest() {
        return _req;
    }

    public HttpServletResponse getResponse() {
        return _res;
    }

    public Map<String, Cookie> getCookieMap() {
        return _cookieMap;
    }
}
