package net.cwfk.titools

import groovy.util.CliBuilder

/**
 * Created by matthew on 4/10/16.
 */
class TiTools {

    public static void main(String[] args) {
        CliBuilder cli = new CliBuilder(usage: 'titools')
        cli.s('show ti file header (v9t9 or tifiles)')
        OptionAccessor options = cli.parse(args)

        try {
            validate(options)
        } catch ( Throwable e ) {
            println "ERROR: ${e.getMessage()}"
            cli.usage()
        }

        if (options.s) {
            TiFile tiFile = new TiFile(options.arguments().get(0))
            if (tiFile.isValid()) {
                println tiFile
            } else {
                println "Not a TIFILE format file"
            }
        }
    }

    static boolean validate(OptionAccessor options) {
       options.s || error("command option required")
    }

    static boolean error(String message) {
        throw new IllegalArgumentException(message)
    }
}
