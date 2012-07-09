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
        NUMBER;
    }


    public static VToken number(String number) {
        return new VToken(VTokenType.NUMBER, number);
    }


    public static VToken add() {
        return new VToken(VTokenType.ADD, null);
    }


    public static VToken minus() {
        return new VToken(VTokenType.MINUS, null);
    }


    public static VToken multiply() {
        return new VToken(VTokenType.MULTIPLY, null);
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
