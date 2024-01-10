package tu.social.project.exception;

public class PostNotFoundException extends RuntimeException {
    private static final String MESSAGE = "The post was not found!";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
