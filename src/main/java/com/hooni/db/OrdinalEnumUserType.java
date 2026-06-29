
package com.hooni.db;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.usertype.ParameterizedType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class OrdinalEnumUserType
    extends EnumUserType implements ParameterizedType
{
    public void setParameterValues(Properties params)
    {
       String enumClassName = params.getProperty("enum");
       if (enumClassName == null)
          throw new MappingException("enum parameter not specified");
       try
       {
           _enumClass = Class.forName(enumClassName);
           _enumValues = _enumClass.getEnumConstants();
       }
       catch (ClassNotFoundException e)
       {
          throw new MappingException("enum " + enumClassName + " not found", e);
       }
    }
    
    @Override
    public Class<?> returnedClass() { return _enumClass; }

    @Override
    public int[] sqlTypes() { return SQL_TYPES; }

    @Override
    public int getSqlType() { return Types.INTEGER; }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
        throws HibernateException, SQLException
    {
        if (names[0] == null)
            return null;

        int ord = rs.getInt(names[0]);
        if (rs.wasNull())
            return null;
        
        return _enumValues[ord];
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index)
        throws HibernateException, SQLException
    {
        if (value == null)
            st.setNull(index, Types.INTEGER);
        else
            st.setInt(index, ((Enum)value).ordinal());
    }

    private Class _enumClass;
    private Object[] _enumValues;

    private static final int[] SQL_TYPES = { Types.INTEGER };
}
