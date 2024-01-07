package tu.social.project.exception;

public class CommentCannotBeEditedException extends RuntimeException {

	private static final String MESSAGE = "Comment can only be edited by comment owner!";

	@Override
	public String getMessage() {
		return MESSAGE;
	}

}
