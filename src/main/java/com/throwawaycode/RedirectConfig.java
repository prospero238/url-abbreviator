package com.throwawaycode;

public class RedirectConfig {
    private String redirectPath;

    private String localServerPort;

    private String baseDomain;

    public RedirectConfig() {

    }

    public RedirectConfig(String redirectPath, String localServerPort, String baseDomain) {
        this.redirectPath = redirectPath;
        this.localServerPort = localServerPort;
        this.baseDomain = baseDomain;
    }
    public String getRedirectPath() {
        return redirectPath;
    }

    public String getLocalServerPort() {
        return localServerPort;
    }

    public String getBaseDomain() {
        return baseDomain;
    }
}
