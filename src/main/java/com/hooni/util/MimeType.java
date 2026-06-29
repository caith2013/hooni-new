package com.hooni.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple MimeType class to replace Apache Fulcrum's MimeType.
 * Provides basic MIME type handling with parameter support.
 */
public class MimeType {

    // Common MIME type constants
    public static final MimeType TEXT_HTML = new MimeType("text/html");
    public static final MimeType TEXT_PLAIN = new MimeType("text/plain");
    public static final MimeType TEXT_XML = new MimeType("text/xml");
    public static final MimeType APPLICATION_JSON = new MimeType("application/json");
    public static final MimeType IMAGE_JPEG = new MimeType("image/jpeg");
    public static final MimeType IMAGE_GIF = new MimeType("image/gif");
    public static final MimeType IMAGE_PNG = new MimeType("image/png");

    private String mimeType;
    private Map<String, String> parameters;

    /**
     * Creates a MimeType with the given MIME type string.
     *
     * @param mimeType the MIME type string (e.g., "text/html")
     */
    public MimeType(String mimeType) {
        this.mimeType = mimeType;
        this.parameters = new HashMap<>();
    }

    /**
     * Adds a parameter to this MIME type.
     *
     * @param name the parameter name
     * @param value the parameter value
     */
    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    /**
     * Gets a parameter value.
     *
     * @param name the parameter name
     * @return the parameter value, or null if not found
     */
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    /**
     * Gets the base MIME type string without parameters.
     *
     * @return the MIME type string
     */
    public String getBaseType() {
        return this.mimeType;
    }

    /**
     * Returns the complete MIME type with parameters as a string.
     * Format: "type/subtype; param1=value1; param2=value2"
     *
     * @return the complete MIME type string
     */
    @Override
    public String toString() {
        if (this.parameters.isEmpty()) {
            return this.mimeType;
        }

        StringBuilder sb = new StringBuilder(this.mimeType);
        for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
            sb.append("; ").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }
}

