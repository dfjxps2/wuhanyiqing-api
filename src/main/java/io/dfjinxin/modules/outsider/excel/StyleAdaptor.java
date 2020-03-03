package io.dfjinxin.modules.outsider.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import io.dfjinxin.modules.outsider.excel.model.EStyle;

public interface StyleAdaptor {
    public CellStyle adaptTo(final Workbook wb, final EStyle style);
}
