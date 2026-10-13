package utilities;

/** Stores the selected options. Member 4 implements argument parsing. */
public class CommandLineOptions {
    private final String filePath;
    private final char compareType;
    private final char sortType;

    /**
     * Stores options. Parsed type characters must be lowercase.
     * @param filePath input file path
     * @param compareType o, u, or d
     * @param sortType b, s, i, m, q, or z
     */
    public CommandLineOptions(String filePath, char compareType, char sortType) {
        this.filePath = filePath;
        this.compareType = compareType;
        this.sortType = sortType;
    }

    /** @return input file path */
    public String getFilePath() { return filePath; }
    /** @return comparison type */
    public char getCompareType() { return compareType; }
    /** @return sorting type */
    public char getSortType() { return sortType; }

    /**
     * Reads attached -f, -t, and -s options in any order, ignoring option case.
     * @param args command-line arguments
     * @return validated options with lowercase type characters
     * @throws IllegalArgumentException when arguments are missing or invalid
     */
    public static CommandLineOptions parse(String[] args) {
        // TODO Member 4: keep file path case unchanged and explain invalid inputs.
        throw new UnsupportedOperationException("Argument parsing is not implemented yet.");
    }
}
