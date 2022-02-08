package com.alexeiboriskin.walnuttask.services;

import com.alexeiboriskin.walnuttask.dao.ExternalApiAccess;
import com.alexeiboriskin.walnuttask.domain.Post;
import com.alexeiboriskin.walnuttask.utils.SortDirection;
import com.alexeiboriskin.walnuttask.utils.SortField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final ExternalApiAccess externalApiAccess;

    public Set<Post> getPostsByTagsList(String[] tags) {

        Set<Post> postSet = new HashSet<>();
        List<CompletableFuture<List<Post>>> completableFutureList = new ArrayList<>();

        for (String tag : tags) {
            completableFutureList.add(externalApiAccess.getPostsByTag(tag));
        }

        CompletableFuture
                .allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .join();

        completableFutureList.forEach(c-> {
            try {
                postSet.addAll(new HashSet<>(c.get()));
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
            }
        });

        return postSet;
    }

    public List<Post> sortByField(List<Post> posts, SortField sortField, SortDirection sortDirection) {

        return posts.stream().sorted((p1, p2) -> {
            switch (sortField) {
                case ID:
                    return sortDirection == SortDirection.ASC ?
                            Long.compare(p1.getId(), p2.getId()) :
                            Long.compare(p1.getId(), p2.getId()) * -1;
                case LIKES:
                    return sortDirection == SortDirection.ASC ?
                            Long.compare(p1.getLikes(), p2.getLikes()) :
                            Long.compare(p1.getLikes(), p2.getLikes()) * -1;
                case READS:
                    return sortDirection == SortDirection.ASC ?
                            Long.compare(p1.getReads(), p2.getReads()) :
                            Long.compare(p1.getReads(), p2.getReads()) * -1;
                case POPULARITY:
                    return sortDirection == SortDirection.ASC ?
                            Double.compare(p1.getPopularity(), p2.getPopularity()) :
                            Double.compare(p1.getPopularity(), p2.getPopularity()) * -1;
                default:
                    return 0;
            }
        }).collect(Collectors.toList());
    }
}
