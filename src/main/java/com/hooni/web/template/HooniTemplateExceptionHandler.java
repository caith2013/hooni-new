package com.hooni.web.template;

import java.io.Writer;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class HooniTemplateExceptionHandler implements TemplateExceptionHandler
{

	@SuppressWarnings("unused")
	public void handleTemplateException(TemplateException te, Environment env,
			Writer w) throws TemplateException
	{
		throw te;
	}

}
