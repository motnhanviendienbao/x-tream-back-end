package com.example.xtream.security.jwtAuthen.cache;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Worker implements Runnable{
    private static final Logger logger = LogManager.getLogger(Worker.class);

    private boolean forceStop;
    private Map<String, CacheObject> cacheFromOutside;

    Worker(Map<String, CacheObject> cacheFromOutside) {
        this.forceStop = true;
        this.cacheFromOutside = cacheFromOutside;
    }

    @Override
    public void run() {
        while (forceStop)
        {
            try {
                // get key set
                Set<String> keySet = this.cacheFromOutside.keySet();
                // loop through key set to check TTL
                for (String key : keySet) {
                    if (this.cacheFromOutside.get(key).isExpire()) {
                        this.cacheFromOutside.remove(key);
                        logger.info("Worker remove cache with key {}",key);

                    }
                }
                // got relax after a working shift
                logger.info("Worker got relax in 2*60*60");
                Thread.sleep(2*60*60);
            } catch (InterruptedException e) {
                throw new RuntimeException("error in thread clean up worker");
            }
        }
    }
}
