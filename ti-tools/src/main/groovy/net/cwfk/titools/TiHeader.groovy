package net.cwfk.titools

/**
 * Created by matthew on 4/10/16.
 */
abstract class TiHeader {

    abstract boolean isValid();

    int PROGRAM = 0x01
    int INTERNAL = 0x02
    int PROTECTED = 0x04
    int VARIABLE = 0x80

    String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append("Status byte: ").append(flags()).append(" ")
        sb.append(isProtected() ? "Protected" : "Unprotected").append(" ")
        sb.append(isProgram() ? "Program" : "Data").append(" ")
        sb.append(isInternal() ? "Internal" : "Display").append(" ")
        sb.append(isVariable() ? "Variable" : "Fixed").append(" ")

        sb.append("Record length: ").append(recordLength()).append(" ")
        sb.append("Records per sector: ").append(recordsPerSector()).append(" ")
        sb.append("Sectors: ").append(getSectors()).append(" ")
        sb.append("Record count:").append(recordCount()).append(" ")
        sb.append("EOF Offset: ").append(eofOffset())
    }

    abstract int recordLength()

    abstract int recordsPerSector()

    abstract int recordCount()

    abstract int eofOffset()

    abstract int getSectors()

    abstract int flags()

    boolean isVariable() {
        return flags() & VARIABLE
    }

    boolean isProtected() {
        return flags() & PROTECTED
    }

    boolean isInternal() {
        return flags() & INTERNAL
    }

    boolean isProgram() {
        return flags() & PROGRAM
    }

    abstract String filename()


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
