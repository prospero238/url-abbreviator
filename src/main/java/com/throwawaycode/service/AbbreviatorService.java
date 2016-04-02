package com.throwawaycode.service;

import com.throwawaycode.bean.UrlAbbreviation;

public interface AbbreviatorService {

    UrlAbbreviation findOrCreate(String url);

    String findFullUrlByPath(String path);

    String abbreviate(String url);

}
