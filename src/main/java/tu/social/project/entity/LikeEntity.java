package tu.social.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = LikeEntity.TABLE_NAME)
public class LikeEntity {
    public static final String TABLE_NAME = "likes";
    public static final String ID_COLUMN = "like_id";
    public static final String USER_ID_COLUMN = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = ID_COLUMN, updatable = false, unique = true, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN, nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = PostEntity.ID_COLUMN, referencedColumnName = PostEntity.ID_COLUMN, nullable = false)
    private PostEntity post;

    public String getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
