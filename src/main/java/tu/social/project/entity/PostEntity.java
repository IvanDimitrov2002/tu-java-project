package tu.social.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = PostEntity.TABLE_NAME)
public class PostEntity {
  public static final String TABLE_NAME = "posts";
  public static final String ID_COLUMN = "post_id";
  public static final String TITLE_COLUMN = "title";
  public static final String CONTENT_COLUMN = "content";
  public static final String AUTHOR_ID_COLUMN = "author_id";

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = ID_COLUMN, updatable = false, unique = true, nullable = false)
  private String id;

  @Column(name = TITLE_COLUMN, nullable = false)
  private String title;

  @Column(name = CONTENT_COLUMN, nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = AUTHOR_ID_COLUMN, referencedColumnName = UserEntity.ID_COLUMN, nullable = false)
  private UserEntity author;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public void setAuthor(UserEntity author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}