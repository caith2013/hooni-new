
package com.hooni.util;

import java.util.Calendar;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class Token
{
    public static Token decode(String encryptedText)
    {
        Token t = decode(encryptedText, SECRET);
        if (t.isExpired())
            throw new TokenExpiredException(encryptedText);
        return t;
    }

    public static Token decodeDontCheck(String encryptedText)
    {
        return decode(encryptedText, SECRET);
    }

    
    public Token(String payload)
    {
        this(payload, DEFAULT_TIMEOUT);
    }

    public Token(String payload, long timeout)
    {
        this(payload, SECRET, timeout, Calendar.getInstance().getTimeInMillis()/1000);
    }

    public final String getPayload() { return _payload; }
    public final long getTimeout() { return _timeout; }
    public final long getTimestamp() { return _timestamp; }
    public final String getMd5() { return _md5; }

    public boolean isExpired()
    {
        // timeout = 0,  never expires
        return (_timeout != 0) && (_timestamp + _timeout < Calendar.getInstance().getTimeInMillis()/1000);
    }
    
    public String encode()
    {
        if (_encoded == null)
        {
            String data = makeData();
            String tokenHash =  DigestUtil.md5(data + HASH_SECRET);
            byte[] etext = CryptUtil.encryptDesEde(data + HASH_DELIMITER + tokenHash, DESEDE_KEY, DESEDE_INIT_VECTOR);
            _encoded = new String(Base64.encodeBase64(etext));
        }
        return _encoded;
    }

    
    protected Token(String payload, String secret, long timeout, long timestamp)
    {
        _payload = payload;
        _timeout = timeout;
        _timestamp = timestamp;

        _md5 = DigestUtil.md5(_payload + secret + _timeout + _timestamp);
    }
    
    String makeData()
    {
        StringBuffer b = new StringBuffer();
        b.append(_payload); b.append(DELIMITER);
        b.append(_timeout); b.append(DELIMITER);
        b.append(_timestamp); b.append(DELIMITER);
        b.append(_md5); 
        return b.toString();
    }


    private static Token decode(String encryptedText, String secret)
    {
        encryptedText = encryptedText.replace(' ','+');
        Token t;
        String md5;
        try
        {
            byte[] etext = Base64.decodeBase64(encryptedText.getBytes());
            String ctext = CryptUtil.decryptDesEde(etext, DESEDE_KEY, DESEDE_INIT_VECTOR);
            String content = ctext.substring(0, ctext.indexOf(HASH_DELIMITER));

            StringTokenizer lexer = new StringTokenizer(content, DELIMITER);
            String payload = lexer.nextToken();
            String timeOutString = lexer.nextToken();
            long timeout = (StringUtils.isEmpty(timeOutString) ? 0 : Long.parseLong(timeOutString));
            long timestamp = Long.parseLong(lexer.nextToken());

            t = new Token(payload, secret, timeout, timestamp);
            md5 = lexer.nextToken();
        }
        catch(Exception e)
        {
            // wrap any other exception in TokenDecodeException (should make it easier to search for in splunk)
            throw new TokenDecodeException(encryptedText, e);
        }


        if (!t.getMd5().equals(md5))
            throw new TokenDecodeException(encryptedText, "md5 sums don't match");
        
        return t;
    }
    
    protected static final String SECRET = "zkc09&kf(2kj!fuir4%u3*ylcms%ie@kusjn-";
    private static final long DEFAULT_TIMEOUT = 24*60*60; // 24 hours
    
    private static final char[] DUMB_DELIMITER = { '\002' };
    private static final String DELIMITER = new String(DUMB_DELIMITER);

    private static final String HASH_SECRET = "pskcv!9438*hn^5k86fg66#hte*7364*jd7-";
    private static final String HASH_DELIMITER = "|";

    private static final String DESEDE_KEY = "lsod98%uy#skci7u!xplsg5iuwc!kpq!"; 
    private static final String DESEDE_INIT_VECTOR = "*u^2fgK#";
    
    /*
    protected static final String SECRET = "zckj%!klkq294_e949cfkcze*dkjf797-";
    private static final long DEFAULT_TIMEOUT = 24*60*60; // 24 hours
    
    private static final char[] DUMB_DELIMITER = { '\002' };
    private static final String DELIMITER = new String(DUMB_DELIMITER);

    private static final String HASH_SECRET = "8dajdklj2394mvszo77fwxjdad8d84kd";
    private static final String HASH_DELIMITER = "|";

    private static final String DESEDE_KEY = "49jdla@##(akda890udakj*0"; 
    private static final String DESEDE_INIT_VECTOR = "*u^2fgK#";
*/
    
    private String _payload;
    private long _timeout;
    private long _timestamp;
    private String _md5;

    private String _encoded;
}
