
class Fb2Txt {

    boolean isV9t9(String blocks) {
        new File(blocks).withDataInputStream { DataInput bin ->
            byte[] fname = new byte[10]
            bin.readFully(fname)
            byte[] header = new byte[118]
            bin.readFully(header)
            return header[3] == 0x02
        }
    }

    boolean isTifiles(String blocks) {
        new File(blocks).withDataInputStream { DataInput bin ->
            return bin.readByte() == 0x07 // that is very incomplete...
        }
    }

    String trimEnd(String code) {
        def trimmed = code.reverse().stripIndent().reverse()
        trimmed.allWhitespace ? "" : trimmed
    }

    def convert(String blocks, String txt) {
        def hasHeader = isV9t9(blocks) || isTifiles(blocks)

        new File(txt).withPrintWriter { tout ->
            new File(blocks).withInputStream { bin ->
                byte[] bytes = new byte[64]

                if (hasHeader) {
                    // skip 128 bytes.
                    bin.skip(128)
                }

                int lineNum = 0
                int emptyLines = 0

                while( -1 != bin.read(bytes) ) {
                    if (lineNum % 16 == 0) {
                        if ( emptyLines == 16 ) {
                            return // quit as soon as I see one empty block.
                        }
                        emptyLines = 0
                        if ( lineNum != 0 ) tout.println();
                        tout.println("## BLOCK: " + ((lineNum / 16) + 1))
                    }
                    def code = trimEnd( new String(bytes) )

                    tout.println(code)
                    if (code.isEmpty()) {
                        emptyLines++;
                    } else {
                        emptyLines = 0;
                    }

                    lineNum++
                }
            }
        }
    }

}

def util = new Fb2Txt()
util.convert("a", "b.txt")