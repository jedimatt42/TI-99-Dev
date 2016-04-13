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

    int recordLength() {
        return fromByte(header[13])
    }

    int recordsPerSector() {
        return fromByte(header[11])
    }

    int recordCount() {
        return fromByte(header[15]) + (fromByte(header[14]) * 256)
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

    String filename() {
        // often not set.
        new java.lang.String(header, 0x10, 0x0A)
    }
}
