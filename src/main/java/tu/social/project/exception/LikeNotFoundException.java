package tu.social.project.exception;

public class LikeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Like not found for user with ID %s and post ID %s";
    private final String userId;
    private final String postId;
    public LikeNotFoundException(String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
    }
    @Override
    public String getMessage() {
        return MESSAGE.formatted(userId, postId);
    }
}
