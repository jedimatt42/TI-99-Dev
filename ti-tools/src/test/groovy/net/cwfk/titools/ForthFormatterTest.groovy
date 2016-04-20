package net.cwfk.titools

import spock.lang.Specification

/**
 * Created by matthew on 4/16/16.
 */
class ForthFormatterTest extends Specification {

    static String DOLOOP = """
5 1 DO I . LOOP CRAZY
"""

    static String DOLOOP_PRETTY = """
5 1 DO
  I .
LOOP
CRAZY
"""


    def "Format DO LOOP"() {
        when:
        ForthFormatter ff = new ForthFormatter()
        String result = ff.format(input)

        then:
        result == output

        where:
        input  | output
        DOLOOP | DOLOOP_PRETTY
    }

}
