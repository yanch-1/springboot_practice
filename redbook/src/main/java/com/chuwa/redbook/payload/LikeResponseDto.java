package com.chuwa.redbook.payload;

import com.chuwa.redbook.entity.PostLike;
import com.chuwa.redbook.entity.security.User;

import java.util.List;


public class LikeResponseDto {

    private long total;

    private List<User> users;

    private List<PostLike> details;


    public LikeResponseDto() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<PostLike> getDetails() {
        return details;
    }

    public void setDetails(List<PostLike> details) {
        this.details = details;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
