package org.neoflex.common.exceptions.telegram;

public class UnexpectedMessageException extends Exception{
    public UnexpectedMessageException() {
        super("Unexpected telegram message exception!");
    }
}
