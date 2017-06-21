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
@AutoConfigureAfter(value = {MetricsConfiguration.class})
@AutoConfigureBefore(value = {WebConfigurer.class, DatabaseConfiguration.class})
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache
                = jHipsterProperties.getCache().getEhcache();

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
            cm.createCache(com.sgkhmjaes.jdias.domain.AccountDeletion.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Aspect.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Aspect.class.getName() + ".aspectMemberships", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Aspect.class.getName() + ".aspectVisibilities", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.AspectMembership.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.AspectVisiblity.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Contact.class.getName() + ".aspectMemberships", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Conversation.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Conversation.class.getName() + ".participants", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Conversation.class.getName() + ".messages", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Event.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Event.class.getName() + ".eventPatricipations", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.EventParticipation.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Like.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Message.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Participation.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Photo.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Poll.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Poll.class.getName() + ".pollanswers", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Poll.class.getName() + ".pollparticipants", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.PollAnswer.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.PollAnswer.class.getName() + ".pollanswers1S", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.PollParticipation.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName() + ".aspectVisiblities", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName() + ".likes", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Post.class.getName() + ".tags", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Profile.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Reshare.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Retraction.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.StatusMessage.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.StatusMessage.class.getName() + ".photos", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Tag.class.getName() + ".tagFollowings", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Tag.class.getName() + ".taggings", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Tagging.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.TagFollowing.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.UserAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.UserAccount.class.getName() + ".conversations", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.UserAccount.class.getName() + ".aspectmemberships", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.UserAccount.class.getName() + ".tagfollowings", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".contacts", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".posts", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".photos", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".participations", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".events", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".messages", jcacheConfiguration);
            cm.createCache(com.sgkhmjaes.jdias.domain.Person.class.getName() + ".conversations", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
