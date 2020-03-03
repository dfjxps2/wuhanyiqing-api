package io.dfjinxin.modules.outsider.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.dfjinxin.modules.outsider.excel.model.ESheet;

public interface Reader {
    public List<ESheet> read(final InputStream is) throws IOException;
}
