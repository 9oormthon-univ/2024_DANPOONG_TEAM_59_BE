package com.goorm.dapum.domain.postLike.entity;

import com.goorm.dapum.core.base.BaseEntity;
import com.goorm.dapum.domain.member.entity.Member;
import com.goorm.dapum.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean status = true; // true: liked, false: unliked

    public PostLike(Post post, Member member, boolean b) {
        this.post = post;
        this.member = member;
        this.status = b;
    }

    public void updateStatus(boolean b) {
        this.status = b;
    }
}
