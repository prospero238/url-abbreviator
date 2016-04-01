package com.throwawaycode.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.throwawaycode.bean.UrlAbbreviation;
import com.throwawaycode.dao.UrlAbbreviationRepository;
import com.throwawaycode.service.AbbreviatorService;

@RestController("/admin")
public class UrlAbbreviationResource {

    @Resource
    AbbreviatorService abbreviatorService;

    @Resource
    UrlAbbreviationRepository urlAbbreviationRepository;

    @RequestMapping(value = "/admin/create", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public String findOrCreate(@RequestBody UrlAbbreviation urlAbbreviation) {
        return abbreviatorService.abbreviate(urlAbbreviation.getUrl());
    }
    @RequestMapping(value = "/admin/all", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public List<UrlAbbreviation> all() {
        Iterable<UrlAbbreviation> all = urlAbbreviationRepository.findAll();

        ArrayList<UrlAbbreviation> urlAbbreviations = new ArrayList<>();
        all.forEach(urlAbbreviation -> urlAbbreviations.add(urlAbbreviation));
        return urlAbbreviations;
    }
    public String create(@RequestBody String url) {
        return abbreviatorService.abbreviate(url);
    }


}
