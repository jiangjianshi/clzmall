package com.clzmall.common.util.excel;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 */
public abstract class BaseConvert {
    public String getDateString(Date date) {
        return getDateFormat().format(date);
    }

    public abstract DateFormat getDateFormat();
}
