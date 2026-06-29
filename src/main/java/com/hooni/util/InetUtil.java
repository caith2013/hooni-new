package com.hooni.util;

import java.net.UnknownHostException;

import org.apache.commons.lang.math.NumberUtils;

public final class InetUtil
{
    public static String getMyAddress()
    {
        try { return java.net.InetAddress.getLocalHost().getHostAddress(); }
        catch (UnknownHostException e) { return "unknown"; }
    }

    
    public static long byteArray2Long(final byte[] array)
    {
        long val = 0;
        val |= array[0] & 0xFF; val <<= 8;
        val |= array[1] & 0xFF; val <<= 8;
        val |= array[2] & 0xFF; val <<= 8;
        val |= array[3] & 0xFF;
        return val;
    }

    public static long ipAddr2Long(String ipaddr)
    {
        return byteArray2Long(ipAddr2ByteArray(ipaddr));
    }

    public static byte[] ipAddr2ByteArray(String ipaddr)
    {
        String[] components = ipaddr.split("\\.");
        byte[] bytes = { 0, 0, 0, 0 };

        for (int i = 0; i < bytes.length; ++i)
            bytes[i] = (byte)(NumberUtils.toInt(components[i]) & 0xFF);
        
        return bytes;
    }

  
    
    public static void main(String[] args)
    {
        for (String ip : args)
            System.out.println(ip + " - " + ipAddr2Long(ip));
    }
    
    private InetUtil()
    {
    }
}
