package com.grexdev.pimabank.menuprovider.parser.regex;

/**
 * CharSequence that noticed thread interrupts as might be necessary to recover from a loose regex on unexpected challenging
 * input.
 */
public class InterruptibleCharSequence implements CharSequence {

    private CharSequence inner;

    public InterruptibleCharSequence(CharSequence inner) {
        this.inner = inner;
    }

    public char charAt(int index) {
        if (Thread.interrupted()) { // clears flag if set
            throw new RuntimeException(new InterruptedException());
        }
        return inner.charAt(index);
    }

    public int length() {
        return inner.length();
    }

    public CharSequence subSequence(int start, int end) {
        return new InterruptibleCharSequence(inner.subSequence(start, end));
    }

    @Override
    public String toString() {
        return inner.toString();
    }
}