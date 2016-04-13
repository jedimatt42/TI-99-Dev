package net.cwfk.titools

/**
 * Created by matthew on 4/10/16.
 */
class V9T9 extends TiHeader {

    final byte[] header

    V9T9(String filename) {
        header = headerBytes(filename)
    }

    V9T9(InputStream inputStream) {
        header = headerBytes(inputStream)
    }

    @Override
    boolean isValid() {
        true // maybe
    }

    @Override
    int recordLength() {
        return fromByte(header[19]) + (fromByte(header[18]) * 256)
    }

    @Override
    int recordsPerSector() {
        return fromByte(header[13])
    }

    @Override
    int recordCount() {
        return fromByte(header[18])
    }

    @Override
    int eofOffset() {
        return fromByte(header[16])
    }

    @Override
    int getSectors() {
        return fromByte(header[15]) + (fromByte(header[14]) * 256)
    }

    @Override
    int flags() {
        return fromByte(header[12])
    }

    @Override
    String filename() {
        return new String(header, 0, 10)
    }
}
