package net.cwfk.titools

import spock.lang.Unroll

/**
 * Created by matthew on 4/10/16.
 */
class TiFilesTest extends spock.lang.Specification {

    @Unroll
    def "test fromByte #value"() {
        when:
        TiFile tiFile = dv80s1()
        byte bvalue = value

        then:
        tiFile.fromByte(bvalue) == expected

        where:
        value | expected
        0x80  | 0x80
        0x7f  | 0x7F
        0x81  | 0x81
        0xFF  | 255
    }

    def "test dis/var 80 small file sector size"() {
        when:
        TiFile tiFile = dv80s1()

        then:
        tiFile.sectors == 1
        tiFile.recordsPerSector() == 3
        tiFile.flags() == 0x80
        !tiFile.isInternal()
        tiFile.isVariable()
        !tiFile.isProtected()
        !tiFile.isProgram()
    }

    def "test dis/var 80 missing filename"() {
        when:
        TiFile tiFile = dv80s1()

        then:
        tiFile.filename() == nullname()
    }

    private TiFile dv80s1() {
        new TiFile(this.class.getResourceAsStream("DISVAR80.bin"))
    }

    private String nullname() {
        new String('\0').padLeft(10, '\0')
    }
}
