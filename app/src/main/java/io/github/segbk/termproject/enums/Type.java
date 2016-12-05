package io.github.segbk.termproject.enums;

/**
 * Created by cssa on 2016-11-14.
 */

public enum Type {
    ITALIAN, GREEK, CHINESE, COLOMBIAN, CANADIAN, OTHER;

    private static final String[] Types = {"Italian","Greek","Chinese","Colombian","Canadian","Other"};

    public static String[] getTypes() {
        return Types;
    }
}
