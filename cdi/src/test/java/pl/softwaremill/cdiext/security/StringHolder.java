package pl.softwaremill.cdiext.security;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class StringHolder {
    private final String content;

    public StringHolder(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
