package net.cwfk.titools

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by matthew on 4/10/16.
 *
 * Test header parsing for V9T9 file on a disk (matches TI FDC File Descriptor Record)
 */
class V9t9Test extends Specification {

    @Unroll
    def "test fromByte #value"() {
        when:
        V9T9 file = dv80s1()
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
        V9T9 file = dv80s1()

        then:
        file.sectors == 1
        file.recordsPerSector() == 3
        file.flags() == 0x80
        !file.isInternal()
        file.isVariable()
        !file.isProtected()
        !file.isProgram()
    }

    def "test dis/var 80 valid filename"() {
        when:
        V9T9 file = dv80s1()

        then:
        file.filename() == "DISVAR80".padRight(10, ' ')
    }

    private V9T9 dv80s1() {
        new V9T9(this.class.getResourceAsStream("DISVAR80.v9t9"))
    }

    private static String nullname() {
        new String('\0').padLeft(10, '\0')
    }
}
