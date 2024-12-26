package fci.swe.advanced_software.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        if (supportsVirtualThreads()) {
            return Executors.newWorkStealingPool();
        } else {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(5);
            executor.setMaxPoolSize(10);
            executor.setQueueCapacity(100);
            executor.setThreadNamePrefix("AsyncThread-");
            executor.initialize();
            return executor;
        }
    }

    private boolean supportsVirtualThreads() {
        return System.getProperty("java.version").startsWith("23");
    }
}