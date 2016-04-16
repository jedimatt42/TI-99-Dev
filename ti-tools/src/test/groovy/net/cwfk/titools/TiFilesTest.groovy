package net.cwfk.titools

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by matthew on 4/10/16.
 *
 * Test parsing TIFILE xmodem header
 */
class TiFilesTest extends Specification {

    @Unroll
    def "test fromByte #value"() {
        when:
        TiFile file = dv80s1()
        byte bvalue = value

        then:
        file.fromByte(bvalue) == expected

        where:
        value | expected
        0x80  | 0x80
        0x7f  | 0x7F
        0x81  | 0x81
        0xFF  | 255
    }

    def "test dis/var 80 small file sector size"() {
        when:
        TiFile file = dv80s1()

        then:
        file.sectors == 1
        file.recordsPerSector() == 3
        file.flags() == 0x80
        !file.isInternal()
        file.isVariable()
        !file.isProtected()
        !file.isProgram()
    }

    def "test dis/var 80 missing filename"() {
        when:
        TiFile file = dv80s1()

        then:
        file.filename() == nullname()
    }

    private TiFile dv80s1() {
        new TiFile(this.class.getResourceAsStream("DISVAR80.tifile"))
    }

    private static String nullname() {
        new String('\0').padLeft(10, '\0')
    }
}
