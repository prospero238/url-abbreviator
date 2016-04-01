package com.throwawaycode.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.throwawaycode.exception.AbbreviationNotFoundException;
import com.throwawaycode.service.AbbreviatorService;

public class RequestRedirector extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(RequestRedirector.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());


        AbbreviatorService abbreviatorService = context.getBean(AbbreviatorService.class);

        String requestUrl = req.getRequestURL().toString();
        String path = requestUrl.substring(requestUrl.lastIndexOf("/")+1);

        LOG.debug("path:{}", path);

        try {
            String fullUrl = abbreviatorService.findFullUrl(path);
            resp.sendRedirect(fullUrl);
        } catch (AbbreviationNotFoundException e) {
            resp.sendError(404);

        }


    }
}
