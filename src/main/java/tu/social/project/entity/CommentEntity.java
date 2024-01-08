package tu.social.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = CommentEntity.TABLE_NAME)
public class CommentEntity {
	public static final String TABLE_NAME = "comments";

	public static final String ID_COLUMN = "comment_id";

	public static final String CONTENT_COLUMN = "content";

	private String id;
	private String content;
	private PostEntity post;
	private UserEntity author;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = ID_COLUMN, updatable = false, unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = CONTENT_COLUMN, nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne
	@JoinColumn(name = PostEntity.ID_COLUMN, referencedColumnName = PostEntity.ID_COLUMN, nullable = false)
	public PostEntity getPost() {
		return post;
	}

	public void setPost(PostEntity post) {
		this.post = post;
	}

	@ManyToOne
	@JoinColumn(name = UserEntity.ID_COLUMN, referencedColumnName = UserEntity.ID_COLUMN, nullable = false)
	public UserEntity getAuthor() {
		return author;
	}

	public void setAuthor(UserEntity author) {
		this.author = author;
	}
}
