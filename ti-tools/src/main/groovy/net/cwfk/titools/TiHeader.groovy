package net.cwfk.titools

/**
 * Created by matthew on 4/10/16.
 */
abstract class TiHeader {

    byte[] headerBytes(String blocks) {
        new File(blocks).withDataInputStream { DataInput bin ->
            byte[] header = new byte[128]
            bin.readFully(header)
            return header
        }
    }

    byte[] headerBytes(InputStream inputStream) {
        DataInputStream dis = new DataInputStream(inputStream)
        dis.with { DataInput bin ->
            byte[] header = new byte[128]
            bin.readFully(header)
            return header
        }
    }

    // Crap/java/no unsigned bytes... 0x80 and above move into sign bit, instead of staying put.
    int fromByte(byte b) {
        if (Math.abs(b) != b) {
            return 0x7F & b | 0x80
        } else {
            return b
        }
    }

}
