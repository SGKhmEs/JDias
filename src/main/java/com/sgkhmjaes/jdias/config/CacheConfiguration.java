package com.sgkhmjaes.jdias.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.sgkhmjaes.jdias.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.AspectVisibility.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Aspect.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Aspect.class.getName() + ".aspectVisibilities", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Aspect.class.getName() + ".contacts", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".contacts", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".posts", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".photos", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".participations", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".events", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".messages", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".conversations", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".aspects", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName() + ".aspectVisiblitis", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName() + ".likes", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName() + ".tags", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Contact.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
