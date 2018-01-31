package com.core.config;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by himanshu.virmani on 08/12/15.
 */
@Configuration
@ConfigurationProperties(prefix = "requestLog")
public class WebConfiguration {

    private String logFileName;
    private String maxHistory;
    private String logTimeZone;

    @Bean
    public EmbeddedServletContainerFactory jettyConfigBean() {
        JettyEmbeddedServletContainerFactory jef = new JettyEmbeddedServletContainerFactory();
        jef.addServerCustomizers(new JettyServerCustomizer() {
            public void customize(Server server) {
                HandlerCollection handlers = new HandlerCollection();
                for (Handler handler : server.getHandlers()) {
                    handlers.addHandler(handler);
                }
                RequestLogHandler reqLogs = new RequestLogHandler();
                NCSARequestLog reqLogImpl = new NCSARequestLog(logFileName);
                reqLogImpl.setRetainDays(Integer.parseInt(maxHistory));
                reqLogImpl.setLogTimeZone(logTimeZone);
                reqLogImpl.setAppend(true);
                reqLogImpl.setExtended(true);
                reqLogImpl.setPreferProxiedForAddress(true);
                reqLogImpl.setLogCookies(true);
                reqLogImpl.setLogServer(true);
                reqLogs.setRequestLog(reqLogImpl);
                handlers.addHandler(reqLogs);
                server.setHandler(handlers);
            }
        });
        return jef;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public void setMaxHistory(String maxHistory) {
        this.maxHistory = maxHistory;
    }

    public void setLogTimeZone(String logTimeZone) {
        this.logTimeZone = logTimeZone;
    }
}
