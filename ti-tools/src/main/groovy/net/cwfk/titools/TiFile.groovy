package net.cwfk.titools

/**
 * Created by matthew on 4/10/16.
 */
class TiFile extends TiHeader {

    final byte[] header

    TiFile(String filename) {
        header = headerBytes(filename)
    }

    TiFile(InputStream inputStream) {
        header = headerBytes(inputStream)
    }

    boolean isValid() {
        return (header[0] == '\07' &&
                header[1] == 'T' &&
                header[2] == 'I' &&
                header[3] == 'F' &&
                header[4] == 'I' &&
                header[5] == 'L' &&
                header[6] == 'E' &&
                header[7] == 'S')
    }

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

    int recordLength() {
        return fromByte(header[13])
    }

    int recordsPerSector() {
        return fromByte(header[11])
    }

    int recordCount() {
        return fromByte(header[14])
    }

    int eofOffset() {
        return fromByte(header[12])
    }

    int getSectors() {
        return fromByte(header[9]) + (fromByte(header[8]) * 256)
    }

    int flags() {
        return fromByte(header[10])
    }

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

    String filename() {
        // often not set.
        new java.lang.String(header, 0x10, 0x0A)
    }
}
