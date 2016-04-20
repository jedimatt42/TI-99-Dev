package net.cwfk.titools

/**
 * Created by matthew on 4/16/16.
 */
class ForthFormatter {

    static final int INDENT = 2
    static final String SPACES = "".padLeft(INDENT, " ")

    String format(String code) {
        int indent = 0
        def words = code.split(' ').collect { String word ->
            if (word == "DO") {
                indent++
                return [ word, "\n", leftIndent(indent) ]
            } else if (word == "+LOOP" || word == "LOOP") {
                return [ "\n", word, "\n" ]
            }
            return word
        }
        words = words.flatten()
        words.join(" ").replaceAll(" \n", "\n").replaceAll("\n ", "\n").replaceAll("\t ", "\t").replaceAll("\t", SPACES)
    }

    private String leftIndent(int indent) {
        if (indent != 0) {
            return "".padLeft(indent, "\t")
        } else {
            return ""
        }
    }
}
