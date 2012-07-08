package org.gonnot.prototype.visualformula;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 */
class VLexer {
    public List<VToken> parse(String line) {
        List<VToken> tokens = new ArrayList<VToken>();
        Scanner scanner = new Scanner(line);
        while (scanner.hasNext()) {
            tokens.add(tokenFrom(scanner.next()));
        }
        return tokens;
    }


    private VToken tokenFrom(String tokenInString) {
        if ("+".equals(tokenInString)) {
            return VToken.add();
        }
        if ("-".equals(tokenInString)) {
            return VToken.minus();
        }
        return VToken.number(tokenInString);
    }
}
