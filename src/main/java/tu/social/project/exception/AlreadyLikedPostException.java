package tu.social.project.exception;

public class AlreadyLikedPostException extends RuntimeException {
    private static final String MESSAGE = "You already liked this post!";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
