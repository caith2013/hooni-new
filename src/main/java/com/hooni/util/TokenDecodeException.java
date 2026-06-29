
package com.hooni.util;

public class TokenDecodeException
    extends RuntimeException
{
    public TokenDecodeException(String encryptedText, String message) { this(encryptedText, message, null); }
    public TokenDecodeException(String encryptedText, Throwable cause) { this(encryptedText, cause.getMessage(), cause); }
    
    public TokenDecodeException(String encryptedText, String message, Throwable cause)
    {
        super("decoding token: ["+encryptedText+"]: "+message, cause);
    }

    private static final long serialVersionUID = 8935374513807419406L;

}
