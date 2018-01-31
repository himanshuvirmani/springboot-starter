package com.core.util.exception;

/**
 * Created by ankit.c on 22/06/16.
 */
public class ExceptionUtils {
    public static String formatLogMessage(long id) {
        return String.format("Error handling a request: %016x.", id);
    }

    public static String formatErrorMessage(long id) {
        return String.format("There was an error processing your request. It has been logged (ID %016x).", id);
    }

    public static String toString(Throwable cause) {
        StringBuilder sb = new StringBuilder(cause.toString());
        cause = cause.getCause();
        while (cause != null) {
            sb.append("; nested exception is ").append(cause);
            cause = cause.getCause();
        }
        return sb.toString();
    }

    public static String buildMessage(Throwable cause) {
        StringBuilder sb = new StringBuilder();
        if (cause.getMessage() != null)
            sb.append(cause.getMessage());
        cause = cause.getCause();
        while (cause != null) {
            if (cause.getMessage() != null)
                sb.append("; ").append(cause.getMessage());
            cause = cause.getCause();
        }
        return sb.toString();
    }
}
