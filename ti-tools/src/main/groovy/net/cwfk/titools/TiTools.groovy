package net.cwfk.titools

/**
 * Created by matthew on 4/10/16.
 *
 * CLI interface to toolset.
 */
class TiTools {

    OutputStream out = System.out

    public static void main(String[] args) {
        TiTools tools = new TiTools()
        tools.go(args)
    }

    public void go(String[] args) {

        CliBuilder cli = new CliBuilder(usage: 'titools')
        cli.s('show ti file header (v9t9 or tifiles)')
        OptionAccessor options = cli.parse(args)

        PrintWriter outWriter = new PrintWriter(out)

        try {
            validate(options)
        } catch ( Throwable e ) {
            println "ERROR: ${e.getMessage()}"
            cli.usage()
        }

        if (options.s) {
            def filename = options.arguments().get(0)
            TiFile tiFile = new TiFile(filename)
            if (tiFile.isValid()) {
                outWriter.println tiFile
            } else {
                V9T9 vfile = new V9T9(filename)
                outWriter.println vfile
            }
        }

        outWriter.flush()
        outWriter.close()
    }

    static boolean validate(OptionAccessor options) {
       options.s || error("command option required")
    }

    static boolean error(String message) {
        throw new IllegalArgumentException(message)
    }
}
