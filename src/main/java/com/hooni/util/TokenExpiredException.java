
package com.hooni.util;

public class TokenExpiredException
    extends TokenDecodeException
{
    public TokenExpiredException(String encryptedText) { super(encryptedText, "token expired"); }
    
    private static final long serialVersionUID = -2545032123510469276L;

}
