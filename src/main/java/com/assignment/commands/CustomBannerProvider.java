package com.assignment.commands;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomBannerProvider extends DefaultBannerProvider {

    public String getBanner() {
        StringBuffer buf = new StringBuffer();
        buf.append("=======================================" + OsUtils.LINE_SEPARATOR);
        buf.append("*                                     *" + OsUtils.LINE_SEPARATOR);
        buf.append("*            Super Simple Stock       *" + OsUtils.LINE_SEPARATOR);
        buf.append("*                                     *" + OsUtils.LINE_SEPARATOR);
        buf.append("=======================================" + OsUtils.LINE_SEPARATOR);
        buf.append("Version:" + this.getVersion());
        return buf.toString();
    }

    public String getWelcomeMessage() {
        return "Welcome to Super Simple Stock CLI";
    }

    @Override
    public String getProviderName() {
        return "SSS Banner";
    }
}
