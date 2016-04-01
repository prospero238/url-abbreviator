package com.throwawaycode.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.throwawaycode.RedirectConfig;
import com.throwawaycode.bean.UrlAbbreviation;
import com.throwawaycode.dao.UrlAbbreviationRepository;
import com.throwawaycode.exception.AbbreviationGenerationException;
import com.throwawaycode.exception.AbbreviationNotFoundException;

@Service
public class RandomlyGeneratedAbbreviator implements AbbreviatorService {

    private static final Logger LOG = LoggerFactory.getLogger(RandomlyGeneratedAbbreviator.class);
    private static final int ABBREVIATION_LENGTH = 5;

    private static final int MAX_GENERATION_ATTEMPTS = 20;

    @Resource
    private RedirectConfig redirectConfig;
    @Resource
    private UrlAbbreviationRepository urlAbbreviationRepository;

    public RandomlyGeneratedAbbreviator() {

    }

    public RandomlyGeneratedAbbreviator(UrlAbbreviationRepository urlAbbreviationRepository) {
        this.urlAbbreviationRepository=urlAbbreviationRepository;
    }

    @Override
    public UrlAbbreviation findOrCreate(String url) {
        UrlAbbreviation urlAbbreviation;
        urlAbbreviation= urlAbbreviationRepository.findByUrl(url);

        if (urlAbbreviation == null) {
            urlAbbreviation = new UrlAbbreviation();
            urlAbbreviation.setPathAbbreviation(createUniqueAbbreviation());
            urlAbbreviation.setUrl(url);
            urlAbbreviationRepository.save(urlAbbreviation);
        } else {
            LOG.debug("found abbr: {}", urlAbbreviation);
        }
        return urlAbbreviation;
    }

    protected String createUniqueAbbreviation() {
        String result = null;
        int attempts = 0;
        while (result == null && attempts < MAX_GENERATION_ATTEMPTS) {
            String randomAbbr = RandomStringUtils.randomAlphanumeric(ABBREVIATION_LENGTH);
            UrlAbbreviation existing = urlAbbreviationRepository.findByPathAbbreviation(randomAbbr);
            if (existing == null) {
                result = randomAbbr;
            }
            attempts++;
            LOG.debug("completion of attempt # {}, max:{}", attempts, MAX_GENERATION_ATTEMPTS);
        }
        if (result == null) {
            throw new AbbreviationGenerationException("unable to create unique abbreviation");
        }
        return result;
    }

    @Override
    public String findFullUrl(String path) {
        UrlAbbreviation urlAbbreviation = urlAbbreviationRepository.findByPathAbbreviation(path);
        if (urlAbbreviation == null) {
            throw new AbbreviationNotFoundException("No abbreviation '" + path + "' exists in the system");
        }
        return urlAbbreviation.getUrl();
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
