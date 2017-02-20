package com.coffeehouse.common.expection;

/**
 * 404 Expection
 */
public class NotFoundExpection extends Exception {
    public NotFoundExpection(String msg) {
        super(msg);
    }
}
