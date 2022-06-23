package dao;

public class AquaDaoException extends Exception {

	private static final long serialVersionUID = 4837647993106405367L;
	private Throwable cause = null;

	public AquaDaoException(String message) {
		super(message);
	}

	public AquaDaoException(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}

}
