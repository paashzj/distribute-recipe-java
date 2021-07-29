package com.github.shoothzj.distribute.api;

/**
 * @author hezhangjian
 */
public class LockException extends Exception {

    public LockException(String message) {
        super(message);
    }

    public LockException(Exception e) {
        super(e);
    }

}
