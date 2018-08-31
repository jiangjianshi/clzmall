package com.clzmall.admin.exception;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author jjs
 *
 */
public class DefaultTemplateExceptionHandler implements TemplateExceptionHandler {
	
    public static final Logger logger = LoggerFactory.getLogger(DefaultTemplateExceptionHandler.class);

    @Override
    public void handleTemplateException(TemplateException te, Environment env, java.io.Writer out)
            throws TemplateException {
    	logger.error("Template Exception: " + te.getMessage() + ", FTL instruction stack: " + te.getFTLInstructionStack(), te);
    }
}
