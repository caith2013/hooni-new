package com.hooni.component;

import com.hooni.service.IpService;
import com.hooni.service.IonosDnsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobScheduler {

	@Autowired
	private IpService ipService;

	@Autowired
	private IonosDnsClient ionosDnsClient;

    private String lastIp = null;

    @Scheduled(fixedRate = 5000)
    public void updateDns() throws InterruptedException {
        log.info(Thread.currentThread().getName() + " Update DNS job");
        try
        {
            String currentIp  = ipService.getPublicIp();
            log.info("my ipService.getPublicIp(): " + currentIp);
            if(!currentIp.equals(lastIp))
            {
                log.info("IP has changed. Updating DNS record.");
                ionosDnsClient.updateIp("hoo-ni.com", currentIp);
                log.info("DNS record updated to new IP: {} -> {} ", lastIp, currentIp);
                lastIp = currentIp;
            } else {
                log.info("IP has not changed. No update needed.");
            }
        } catch (Exception e) {
            log.error("Error updating DNS record: " + e.getMessage());
        }

    }

    @Scheduled(fixedRate = 5000)
    public void job2() {
        System.out.println(Thread.currentThread().getName() + " Job2");
    }
}
