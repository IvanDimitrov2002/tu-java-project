package tu.social.project.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "Category with name %s already exists!";
    private final String name;
    public CategoryAlreadyExistsException(String name) {
        this.name = name;
    }
    @Override
    public String getMessage() {
        return MESSAGE.formatted(this.name);
    }
}
