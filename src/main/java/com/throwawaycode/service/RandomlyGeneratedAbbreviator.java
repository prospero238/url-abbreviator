package com.throwawaycode.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.throwawaycode.RedirectConfig;
import com.throwawaycode.bean.UrlAbbreviation;
import com.throwawaycode.dao.UrlAbbreviationRepository;
import com.throwawaycode.exception.AbbreviationNotFoundException;

@Service
public class RandomlyGeneratedAbbreviator implements AbbreviatorService {

    private static final Logger LOG = LoggerFactory.getLogger(RandomlyGeneratedAbbreviator.class);

    @Resource
    private RedirectConfig redirectConfig;

    @Resource
    private UrlAbbreviationRepository urlAbbreviationRepository;

    @Override
    public UrlAbbreviation findOrCreate(String url) {
        UrlAbbreviation urlAbbreviation;
        urlAbbreviation= urlAbbreviationRepository.findByUrl(url);

        if (urlAbbreviation == null) {
            urlAbbreviation = new UrlAbbreviation();
            urlAbbreviation.setPathAbbreviation(RandomStringUtils.randomAlphanumeric(5));
            urlAbbreviation.setUrl(url);
            urlAbbreviationRepository.save(urlAbbreviation);
        } else {
            LOG.debug("found abbr: {}", urlAbbreviation);
        }
        return urlAbbreviation;
    }



    @Override
    public String findFullUrl(String path) {
        UrlAbbreviation byPathAbbreviation = urlAbbreviationRepository.findByPathAbbreviation(path);
        if (byPathAbbreviation == null) {
            throw new AbbreviationNotFoundException("No abbreviation '" + path + "' exists in the system");
        }
        return byPathAbbreviation.getUrl();
    }
    @Override
    public String abbreviate(String url) {
        UrlAbbreviation generate = findOrCreate(url);
        return composeFullUrl(generate);
    }

    public String composeFullUrl(UrlAbbreviation generate) {
        return "http://" + redirectConfig.getBaseDomain() + ":" + redirectConfig.getLocalServerPort() + redirectConfig.getRedirectPath() + "/" + generate.getPathAbbreviation();
    }

}
