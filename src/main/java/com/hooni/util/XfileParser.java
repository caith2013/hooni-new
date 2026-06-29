package com.hooni.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.hooni.Constants;


public class XfileParser
{
	public XfileParser(InputStream is)
	throws IOException, XmlPullParserException
{
    _entries = new Hashtable<String, String>();

    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    factory.setValidating(false);
    factory.setNamespaceAware(false);

    InputStreamReader reader = null;
    try
    {
        XmlPullParser xpp = factory.newPullParser();
        reader = new InputStreamReader(is, Constants.DEFAULT_ENCODING);
        xpp.setInput(reader);
        parse(xpp);
    }
    finally
    {
        if (reader != null)
            try { reader.close(); } catch (IOException e) { }
    }
}

public String getComment()
{
    return _comment;
}

public Hashtable<String,String> getEntries()
{
    return _entries;
}



private void parse(XmlPullParser xpp)
    throws XmlPullParserException, IOException
{
    int eventType = xpp.getEventType();
    String key = "";
    String text = "";
    do
    {
        switch (eventType)
        {
            case XmlPullParser.START_TAG:
                text = "";
                if (TAG_ENTRY.equals(xpp.getName()))
                    key = xpp.getAttributeValue(null,ATTR_KEY);
                break;
            case XmlPullParser.TEXT:
            case XmlPullParser.CDSECT:
                text = xpp.getText();
                break;
            case XmlPullParser.END_TAG:
                if (TAG_ENTRY.equals(xpp.getName()))
                {
                	_entries.put(key, text);
                }
                else if (TAG_COMMENT.equals(xpp.getName()))
                {
                    _comment = text;
                }
                else if (TAG_PROPERTIES.equals(xpp.getName()))
                {
                    // ignore
                }
                else
                {
                    throw new XmlPullParserException("Unknown tag: [" + xpp.getName() + "]");
                }
                break;
        }

        eventType = xpp.next();
    }
    while (eventType != XmlPullParser.END_DOCUMENT);
}

private static final String TAG_PROPERTIES = "properties";
private static final String TAG_COMMENT = "comment";
private static final String TAG_ENTRY = "entry";
private static final String ATTR_KEY = "key";


private String           _comment;
private Hashtable<String, String> _entries;
}
