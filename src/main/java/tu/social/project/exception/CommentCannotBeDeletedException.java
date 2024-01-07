package tu.social.project.exception;

public class CommentCannotBeDeletedException extends RuntimeException {

	private static final String MESSAGE = "Comment can only be deleted by post owner or comment owner!";

	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
