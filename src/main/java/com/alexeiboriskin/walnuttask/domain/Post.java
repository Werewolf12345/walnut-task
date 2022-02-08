package com.alexeiboriskin.walnuttask.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Builder
public class Post {

    private long id;
    private String author;
    private long authorId;
    private long likes;
    private double popularity;
    private long reads;
    private List<String> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
