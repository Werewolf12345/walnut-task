package com.alexeiboriskin.walnuttask.controllers;


import com.alexeiboriskin.walnuttask.domain.Post;
import com.alexeiboriskin.walnuttask.domain.PostResponse;
import com.alexeiboriskin.walnuttask.exceptions.ParamValidationException;
import com.alexeiboriskin.walnuttask.services.PostService;
import com.alexeiboriskin.walnuttask.utils.SortDirection;
import com.alexeiboriskin.walnuttask.utils.SortField;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private static final String DEFAULT_SORT = "id";
    private static final String DEFAULT_SORT_DIRECTION = "asc";

    private final PostService postService;

    @GetMapping("/posts")
    public PostResponse getPosts(@RequestParam(required = false) String[] tags,
                               @RequestParam(required = false) String sortBy,
                               @RequestParam(required = false) String direction) {

        if(tags == null) {
            throw new ParamValidationException("Tags parameter is required");
        }

        if(sortBy == null) {
            sortBy = DEFAULT_SORT;
        } else if(SortField.fromString(sortBy) == null) {
            throw new ParamValidationException("sortBy parameter is invalid");
        }

        if(direction == null) {
            direction = DEFAULT_SORT_DIRECTION;
        } else if(SortDirection.fromString(direction) == null) {
            throw new ParamValidationException("direction parameter is invalid");
        }

        List<Post> posts = new ArrayList<>(postService.getPostsByTagsList(tags));
        posts = postService.sortByField(posts, SortField.fromString(sortBy), SortDirection.fromString(direction));

        return PostResponse.builder().posts(posts).build();
    }
}
