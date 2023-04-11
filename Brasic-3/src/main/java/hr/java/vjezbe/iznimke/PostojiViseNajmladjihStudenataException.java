package hr.java.vjezbe.iznimke;

/**
 * Iznimka koja se baci kada se pronade vise najmladih studenata
 * tj. kada nije moguce odrediti jednog najmljadeg studenata.
 */

public class PostojiViseNajmladjihStudenataException extends RuntimeException{

    public PostojiViseNajmladjihStudenataException() {
    }

    public PostojiViseNajmladjihStudenataException(String message) {
        super(message);
    }

    public PostojiViseNajmladjihStudenataException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostojiViseNajmladjihStudenataException(Throwable cause) {
        super(cause);
    }

    public PostojiViseNajmladjihStudenataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
