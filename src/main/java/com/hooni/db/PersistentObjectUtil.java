// $Id$
package com.hooni.db;

import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class PersistentObjectUtil
{

    public static Serializable getDbId(Object obj)
    {
        try
        {
            Method getIdMtd = obj.getClass().getMethod("getId", MTHD_NO_PARMS);
            
            if (getIdMtd == null)
                throw new NullPointerException("Can't find getId method for " + obj.getClass().getName());
            
            getIdMtd.setAccessible(true);
            return (Serializable) getIdMtd.invoke(obj, MTHD_NO_ARGS);
        }
        catch (Exception e)
        {
            throw new RuntimePersistentObjectUtilException("Unable to get id from " + obj.getClass().getName() + " :" + obj.toString(), e);
        }
    }

    
    
    /** Mainly for unit tests. */
    public static List fetchAll(Session session, Class tableClass)
    {
        return (session.createQuery("FROM " + tableClass.getName())).list();
    }
    
    
    
    public static int countRecords(Session session, Class tableClass)
    {
        Integer result = ( (Integer) session.createQuery("SELECT count(*) FROM " + tableClass.getName()).uniqueResult() );
        return (result == null)? 0 : result.intValue();
    }
    
    
    /** For convenience to make it easier to write Object.equals() methods
     * for db objects.
     * Subclasses are treated as if they represent the same table.
     */
    public static boolean dbequal(Object dbObj1, Object dbObj2)
    {
        if      ( dbObj1 == null && dbObj2 != null )
            return false;
        else if ( dbObj1 != null && dbObj2 == null )
            return false;
        else if ( dbObj1 == null && dbObj2 == null )
            return true;
        else if (dbObj1.getClass().isAssignableFrom(dbObj2.getClass()) || dbObj2.getClass().isAssignableFrom(dbObj1.getClass()))
            return getDbId(dbObj1).equals(getDbId(dbObj2));
        else
            return false;
    }

    
    /** Convenience method, checks .equals tolerating nulls.
     */
    public static boolean objsEqual(Object obj1, Object obj2)
    {
        if (obj1 == obj2)
            return true;
        else if (obj1 == null || obj2 == null)
            return false;
        else
            return obj1.equals(obj2);
    }


    /** Convenience method to make it easier to implement Object.toString()
     *  for db objects used for debugging and logging.
     */
    public static String fieldsToString(Object dbObj)
    {
        return fieldsToString(dbObj, 30); // some defult len
    }
    

    /** Convenience method to make it easier to implement Object.toString()
     *  for db objects used for debugging and logging.
     */
    public static String fieldsToString(Object dbObj, int maxLen)
    {
        if (dbObj == null)
            return "{ null }";
    
        if (! dbObj.getClass().getPackage().getName().startsWith("com.fotolog"))
            return "{ " + dbObj.toString() + " } ";
        
        Map<String,Object> fields = getObjectFieldValues(dbObj, Modifier.STATIC);
        List<String> fieldNames = new ArrayList<String>(fields.keySet());
        Collections.sort(fieldNames);
        
        StringBuffer sbuf = new StringBuffer("{");
        for( Iterator fieldNameItr = fieldNames.iterator(); fieldNameItr.hasNext(); )
        {
            String fieldName = (String) fieldNameItr.next();
            Object val = fields.get(fieldName);
            
            if ( val == null )
                continue;
            
            if ( fieldName.charAt(0) == '_')
                fieldName = fieldName.substring(1);
            
            sbuf.append(" ");
            sbuf.append(fieldName);
            sbuf.append("[");
            
            // Some String fields are very long, limit their len
            if (val instanceof String)
            {
                String strVal = (String) val;
                if ( strVal.length() > maxLen )
                    val = strVal.substring(0, maxLen).concat("...");
            }
            
            sbuf.append(val.toString());
            sbuf.append("]");
        }
        
        sbuf.append(" }");
        
        return sbuf.toString();
    }
    
    
    
    /** Turns a given object to a Map('field_name' -to- 'field value').  
     * @param excludeMask - filter mask, see java.lang.reflect.Modifier
     * */
    public static Map<String,Object> getObjectFieldValues(Object obj, int excludeMask)
    {
        if (obj == null)
            return Collections.emptyMap();
        
        Map<String,Object> result = new HashMap<String,Object>();
        Map allFields = getAllFields(obj.getClass());
        
        for( Iterator fieldItr = allFields.values().iterator(); fieldItr.hasNext(); )
        {
            Field f = (Field) fieldItr.next();
            f.setAccessible(true);
            
            if ( (f.getModifiers() & excludeMask) == excludeMask )
                continue;
            
            Object val;
            try { val = f.get(obj); }
            catch (IllegalAccessException e) { throw new RuntimeException(e); }
            
            result.put(f.getName(), val);
        }
        
        return result;
    }
    

    /** A hack to create a 'copy constructor' where .clone() can't be used. */
    public static void assignFields(Object obj, Map<String,Object> fieldValues)
    {
        try
        {
            Map<String,Field> fields = getAllFields(obj.getClass());
            
            for( Iterator fieldNameItr = fieldValues.keySet().iterator(); fieldNameItr.hasNext(); )
            {
                String fieldName = (String) fieldNameItr.next();
                Field f = fields.get(fieldName); 
                
                if ( f == null )
                    throw new NoSuchFieldException(fieldName);
                
                f.setAccessible(true);
                f.set(obj, fieldValues.get(f.getName()));
            }
        }
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
    }
    
    
    /** Name -to- Field map of this class's (and it's parent's) fields .*/
    public static Map<String,Field> getAllFields(Class clazz)
    {
        Map<String,Field> result = new HashMap<String,Field>();
        
        while ( clazz != null )
        {
            Field [] fields = clazz.getDeclaredFields();
            for ( int i = 0; i < fields.length; i++ )
            {
                if ( ! result.containsKey(fields[i].getName()) )
                    result.put( fields[i].getName(), fields[i] );
            }
            
            clazz = clazz.getSuperclass();
        }
        
        return result;
    }

    
    
    /** Convenience method to make it easier to implement Object.hashCode() */
    public static int dbObjHashCode(Object dbObj)
    {
        return getDbId(dbObj).hashCode();
    }
    

    
    
    
    
    
    

    private static final Object[] MTHD_NO_ARGS = new Object[]{};
    private static final Class[] MTHD_NO_PARMS = new Class[]{};

    
    
    private static class RuntimePersistentObjectUtilException
    extends RuntimeException
    {
        public RuntimePersistentObjectUtilException(Exception e)
        {
            super(e);
        }

        public RuntimePersistentObjectUtilException(String msg, Exception e)
        {
            super(msg, e);
        }

        private static final long serialVersionUID = 3372832116772181037L;
    }
}
