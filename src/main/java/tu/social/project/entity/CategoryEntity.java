package tu.social.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = CategoryEntity.TABLE_NAME)
public class CategoryEntity {

    public static final String TABLE_NAME = "categories";
    public static final String ID_COLUMN = "category_id";
    public static final String NAME_COLUMN = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = ID_COLUMN, updatable = false, unique = true, nullable = false)
    private String id;

    @Column(name = NAME_COLUMN, nullable = false, unique = true)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}