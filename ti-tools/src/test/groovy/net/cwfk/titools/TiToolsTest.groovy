package net.cwfk.titools

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by matthew on 4/16/16.
 *
 * Test for the command line interface
 */
class TiToolsTest extends Specification {

    @Unroll
    def "Test file type detection: #args"() {
        when:
        TiTools tools = new TiTools()
        tools.out = new ByteArrayOutputStream()
        tools.go(command, resourcePath(resource))
        String result = outputString(tools.out)

        then:
        result.startsWith(expected)

        where:
        command | resource          | expected
        "-s"    | "DISVAR80.tifile" | "TIFILE: "
        "-s"    | "DISVAR80.v9t9"   | "V9T9: "
    }

    static String outputString(OutputStream out) {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out
        return baos.toString()
    }

    static String resourcePath(String resource) {
        return [System.getProperty("user.dir"), "src", "test", "resources", "net", "cwfk", "titools", resource].join(File.separator)
    }
}
