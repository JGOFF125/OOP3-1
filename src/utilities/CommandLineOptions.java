package utilities;

/**
 * I keep the file path and the two selected options here
 * I read them from the command line
 */
public class CommandLineOptions {
    /** I accept original, updated, or calculated difference */
    private static final String COMPARE_TYPES = "oud";
    /** I accept these six sorting options */
    private static final String SORT_TYPES = "bsimqz";

    /** I keep the path exactly as entered */
    private final String filePath;
    /** I store the selected comparison */
    private final char compareType;
    /** I store the selected sort */
    private final char sortType;

    /**
     * I store the path and lowercase option letters
     *
     * @param filePath input file path
     * @param compareType o, u, or d
     * @param sortType b, s, i, m, q, or z
     */
    public CommandLineOptions(String filePath, char compareType, char sortType) {
        this.filePath = filePath;
        this.compareType = compareType;
        this.sortType = sortType;
    }

    /**
     * I return the input path
     * @return input file path
     */
    public String getFilePath() { return filePath; }

    /**
     * I return the comparison option
     * @return comparison type
     */
    public char getCompareType() { return compareType; }

    /**
     * I return the sorting option
     * @return sorting type
     */
    public char getSortType() { return sortType; }

    /**
     * I read the three options in any order and ignore their case
     * I leave the path unchanged and lowercase only the option letters
     *
     * @param args command-line arguments
     * @return validated options
     * @throws IllegalArgumentException when arguments are missing or invalid
     */
    public static CommandLineOptions parse(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException(
                    "No arguments were given. All three options are required, with no space "
                    + "between an option and its value." + System.lineSeparator() + usage());
        }

        String filePath = null;
        Character compareType = null;
        Character sortType = null;

        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                throw new IllegalArgumentException("An empty argument was given. " + usage());
            }
            if (!isDash(arg.charAt(0))) {
                throw new IllegalArgumentException("Invalid argument \"" + arg
                        + "\": options must start with '-' (for example -fres/products1.txt, "
                        + "-td, -sb). If you put a space between an option and its value, "
                        + "remove it.");
            }
            if (arg.length() == 1) {
                throw new IllegalArgumentException(
                        "Invalid argument \"-\": expected -f, -t or -s followed by a value.");
            }

            char option = Character.toLowerCase(arg.charAt(1));
            String value = arg.substring(2);

            switch (option) {
                case 'f':
                    if (filePath != null) {
                        throw new IllegalArgumentException("The -f option was given more than once.");
                    }
                    filePath = parseFilePath(arg, value);
                    break;
                case 't':
                    if (compareType != null) {
                        throw new IllegalArgumentException("The -t option was given more than once.");
                    }
                    compareType = parseType(arg, value, COMPARE_TYPES, "-t",
                            "o (original price), u (updated price) or d (calculated difference)");
                    break;
                case 's':
                    if (sortType != null) {
                        throw new IllegalArgumentException("The -s option was given more than once.");
                    }
                    sortType = parseType(arg, value, SORT_TYPES, "-s",
                            "b (bubble), s (selection), i (insertion), m (merge), q (quick) "
                            + "or z (heap)");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown option \"" + arg
                            + "\". The only options are -f, -t and -s.");
            }
        }

        if (filePath == null) {
            throw new IllegalArgumentException(
                    "Missing the -f option (input file). Example: -fres/products1.txt");
        }
        if (compareType == null) {
            throw new IllegalArgumentException(
                    "Missing the -t option (compare type). Use -to, -tu or -td.");
        }
        if (sortType == null) {
            throw new IllegalArgumentException(
                    "Missing the -s option (sort algorithm). Use -sb, -ss, -si, -sm, -sq or -sz.");
        }

        return new CommandLineOptions(filePath, compareType, sortType);
    }

    /**
     * I get the file path and remove surrounding quotes if needed
     *
     * @param arg the whole argument, used in error messages
     * @param value text after the option letters
     * @return the file path
     */
    private static String parseFilePath(String arg, String value) {
        String path = value;
        if (path.length() >= 2 && path.startsWith("\"") && path.endsWith("\"")) {
            path = path.substring(1, path.length() - 1);
        }
        if (path.isEmpty()) {
            throw new IllegalArgumentException("The -f option needs a file name directly after it "
                    + "with no space, for example -fres/products1.txt (got \"" + arg + "\").");
        }
        return path;
    }

    /**
     * I check that the option has one allowed letter
     *
     * @param arg the whole argument, used in error messages
     * @param value text after the option letters
     * @param allowed the lowercase letters that are accepted
     * @param option option name, used in error messages
     * @param meaning readable list of accepted letters
     * @return the accepted letter in lowercase
     */
    private static char parseType(String arg, String value, String allowed, String option,
            String meaning) {
        if (value.length() != 1 || allowed.indexOf(Character.toLowerCase(value.charAt(0))) < 0) {
            throw new IllegalArgumentException("Invalid value for " + option + ": \"" + arg
                    + "\". Use exactly one of " + meaning + ", directly after " + option
                    + " (for example " + option + allowed.charAt(0) + ").");
        }
        return Character.toLowerCase(value.charAt(0));
    }

    /**
     * I accept regular dashes and the longer dashes pasted from documents
     *
     *
     * @param c character to check
     * @return true if the character can start an option
     */
    private static boolean isDash(char c) {
        return c == '-' || c == '\u2013' || c == '\u2014';
    }

    /**
     * I return an example of the command format
     * @return command format shown in error messages
     */
    private static String usage() {
        return "Usage: java -jar Sort.jar -f<file_name> -t<o|u|d> -s<b|s|i|m|q|z>";
    }
}
