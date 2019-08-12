package io.digitopo.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.digitopo.application.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.digitopo.application.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.digitopo.application.domain.User.class.getName());
            createCache(cm, io.digitopo.application.domain.Authority.class.getName());
            createCache(cm, io.digitopo.application.domain.User.class.getName() + ".authorities");
            createCache(cm, io.digitopo.application.domain.Region.class.getName());
            createCache(cm, io.digitopo.application.domain.Country.class.getName());
            createCache(cm, io.digitopo.application.domain.Location.class.getName());
            createCache(cm, io.digitopo.application.domain.Department.class.getName());
            createCache(cm, io.digitopo.application.domain.Department.class.getName() + ".employees");
            createCache(cm, io.digitopo.application.domain.Task.class.getName());
            createCache(cm, io.digitopo.application.domain.Task.class.getName() + ".jobs");
            createCache(cm, io.digitopo.application.domain.Employee.class.getName());
            createCache(cm, io.digitopo.application.domain.Employee.class.getName() + ".jobs");
            createCache(cm, io.digitopo.application.domain.Job.class.getName());
            createCache(cm, io.digitopo.application.domain.Job.class.getName() + ".tasks");
            createCache(cm, io.digitopo.application.domain.JobHistory.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
