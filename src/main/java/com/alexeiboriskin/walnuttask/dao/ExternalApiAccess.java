package com.alexeiboriskin.walnuttask.dao;

import com.alexeiboriskin.walnuttask.domain.Post;
import com.alexeiboriskin.walnuttask.domain.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "posts")
public class ExternalApiAccess {

    private static final String POSTS_URI = "https://api.hatchways.io/assessment/blog/posts?tag={tag}";

    private final RestTemplate restTemplate;

    @Cacheable
    @Async
    public CompletableFuture<List<Post>> getPostsByTag(String tag) {

        PostResponse postResponse =
                restTemplate.getForObject(POSTS_URI, PostResponse.class, tag);

        return CompletableFuture.completedFuture(postResponse.getPosts());
    }
}
