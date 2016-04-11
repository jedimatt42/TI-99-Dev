package net.cwfk.titools

/**
 * Created by matthew on 4/10/16.
 */
class V9T9 extends TiHeader {

    boolean isV9t9(String blocks) {
        byte[] header = headerBytes(blocks)
        return header[13] == 0x02
    }
}
