package tu.social.project.exception;

public class UnauthorizedUserException extends RuntimeException {

    private static final String MESSAGE = "You are not authorized to make this request!";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
