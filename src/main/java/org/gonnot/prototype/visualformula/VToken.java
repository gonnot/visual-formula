package org.gonnot.prototype.visualformula;
/**
 *
 */
class VToken {
    private VTokenType type;
    private String tokenInString;

    static enum VTokenType {
        ADD,
        MINUS,
        MULTIPLY,
        DIVIDE,
        NUMBER;
    }


    public static VToken number(String number) {
        return new VToken(VTokenType.NUMBER, number);
    }


    public static VToken add() {
        return new VToken(VTokenType.ADD);
    }


    public static VToken minus() {
        return new VToken(VTokenType.MINUS);
    }


    public static VToken multiply() {
        return new VToken(VTokenType.MULTIPLY);
    }


    public static VToken divide() {
        return new VToken(VTokenType.DIVIDE);
    }


    protected VToken(VTokenType type) {
        this(type, null);
    }


    protected VToken(VTokenType type, String tokenInString) {
        this.type = type;
        this.tokenInString = tokenInString;
    }


    public VTokenType getType() {
        return type;
    }


    public String getTokenInString() {
        return tokenInString;
    }


    @Override
    public String toString() {
        return "VToken{" + type + (tokenInString != null ? tokenInString : "") + "}";
    }
}
