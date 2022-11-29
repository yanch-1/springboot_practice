package com.chuwa.redbook.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class LikeDto {


    private long post_id;

    private long user_id;

    @NotNull
    @Min(value = 0, message = "Please Enter 1 for Like, 0 for unlike")
    @Max(value = 1, message = "Please Enter 1 for Like, 0 for unlike")
    private long status;

    public LikeDto() {
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
