package com.hooni.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DbSession 
{
	public DbSession()
	{
		this(null);
	}
	
	public DbSession(String username)
	{
		_conn = null;
		_username =  username;
	}
	
	
	abstract protected void openDbSession();
	
	public void commitTransaction()
	{
			try
			{
				_conn.commit();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
	}
	
	public void rollbackTransaction()
	{
		try
		{
			_conn.rollback();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	 public DbSession getSession()
	 {

		 if (_conn == null) // check _conn and not _session because _conn is something we MUST close even if session creation blew up
			 openDbSession();
		 return this;

	 }

	
	
    public void commit() { getSession().commitTransaction(); }
    public void rollback() { getSession().rollbackTransaction(); }

	public void close()
    {
        if (_conn != null)
        {
            try { _conn.close(); }
			catch(Throwable t) { _log.error(t.getMessage(), t); }
            _conn = null;
        }
    }
	
	public Connection getConnection()
	{
		return getSession()._conn;
	}
	

	private static final Logger _log = LoggerFactory.getLogger(DbSession.class);

	protected Connection _conn;
    protected String _username;
}
