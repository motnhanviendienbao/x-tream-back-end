package com.example.xtream.security.basicAuthen.cache;

import com.example.xtream.security.basicAuthen.modelUserDetail.UserBase;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Task for worker
 */
public class CleanupTTL implements Runnable{

    /**
     * Flag
     */
    private  boolean stopFlag;

    /**
     * Cache passed from outside injection
     */
    private final BasicUserCache userCache;

    /**
     * Logger
     */
    private final Logger logger = LogManager.getLogger(CleanupTTL.class);

    /**
     * constructor
     * @param userCache cache from outside
     */
    public CleanupTTL( BasicUserCache userCache) {
        logger.info("[NgocTu-Server]: Initial Worker Clean Up . . .");
        this.userCache = userCache;
        this.stopFlag = true;
    }

    /**
     * Task defined for worker
     */
    @Override
    public void run() {
        try {
            while (stopFlag) {
                for (String var2 : this.userCache.getCache().keySet()) {
                    // Todo: it is possible to be race condition
                    UserBase var3 = (UserBase) this.userCache.getCache().get(var2);
                    logger.info("[NgocTu-Server]: Thread clean up run with key {} - value {}", var2, var3);
                    logger.info("[NgocTu-Server]: Thread clean up run with username {} - password {} - author {}", var3.getUsername(), var3.getPassword(), var3.getAuthorities());

                    if (Objects.nonNull(var2) && Objects.nonNull(var3) && var3.isExpire()) {
                        synchronized (this.userCache.getLock()) {
                            this.userCache.getCache().remove(var2);
                        }
                        logger.info("[NgocTu-Server]: Thread clean up success key {} - value {}", var2, var3);
                    }
                }
                // todo: this test for 1 minute
                logger.info("[NgocTu-Server]: Thread clean up sleep 1 minute");
                logger.info("[NgocTu-Server]: Cache size notify from Thread Worker: {}",this.userCache.getCache().size());
                Thread.sleep(30 * 1000);
            }
        } catch (Exception e ) {
            logger.info("[NgocTu-Server]: Thread clean up encountered error with {}", e.toString());
        }
    }

    /**
     * Set Flag control
     * @param b boolean value
     */
    public void setStopFlag(Boolean b) {
        this.stopFlag = b;
    }
}
