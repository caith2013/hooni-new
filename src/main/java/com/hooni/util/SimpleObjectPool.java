
package com.hooni.util;

import java.util.ArrayList;
import java.util.List;

/** Simple pool for heavy-weight objects. */
public class SimpleObjectPool<T>
{
    public static interface ObjectFactory<T>
    {
        public T newInstance();
    }
    
    public SimpleObjectPool(int maxSize, ObjectFactory<T> factory)
    {
        _pool = new ArrayList<T>();
        _maxSize = maxSize;
        _factory = factory;
    }
    
    public T getFromPool()
    {
        synchronized(_pool)
        {
            if (_pool.size() > 0)
                return _pool.remove(_pool.size()-1);
        }
        
        return _factory.newInstance();
    }
    
    
    public void returnToPool(T o)
    {
        if ( o == null )
            return;
        
        synchronized(_pool)
        {
            if (_pool.size() < _maxSize)
                _pool.add(o);
        }
    }
    
    
    private int _maxSize;
    private List<T> _pool;
    private ObjectFactory<T> _factory;
}
