package com.gpersist.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import jxl.*;
import jxl.write.*;

import com.gpersist.entity.publics.*;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.entity.system.SysPmt;

public class ExcelUtils {
    public static <T> InputStream GetExeclStream(ExportSetting es, List<ExportColumn> cols, List<T> lists) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        WritableWorkbook workbook;
        try {
            workbook = Workbook.createWorkbook(out);
            WritableSheet sheet = workbook.createSheet(es.getSheetname(), 0);

            WritableCellFormat wcfCenter = new WritableCellFormat();
            wcfCenter.setAlignment(jxl.format.Alignment.CENTRE);

            WritableCellFormat wcfleft = new WritableCellFormat();
            wcfleft.setAlignment(jxl.format.Alignment.LEFT);

            WritableCellFormat wcfright = new WritableCellFormat();
            wcfright.setAlignment(jxl.format.Alignment.RIGHT);

            int nowrow = 0;

            sheet.mergeCells(0, nowrow, cols.size() - 1, nowrow);
            sheet.addCell(new jxl.write.Label(0, nowrow, es.getTitle(), wcfCenter));
            nowrow++;

            if (!ToolUtils.StringIsEmpty(es.getDaterange())) {
                if (cols.size() > 0)
                    sheet.mergeCells(0, nowrow, cols.size() - 1, nowrow);
                sheet.addCell(new jxl.write.Label(0, nowrow, es.getDaterange(), wcfright));
                nowrow++;
            }

            int columnindex = 0;
            for (ExportColumn col : cols) {
                sheet.setColumnView(columnindex, col.getWidth() / 6 > 4 ? col.getWidth() / 6 : 4);
                sheet.addCell(new jxl.write.Label(columnindex, nowrow, col.getText(), wcfCenter));
                columnindex++;
            }

            if (cols.size() > 0)
                nowrow++;

            String value = "";
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

            for (T t : lists) {
                columnindex = 0;
                for (ExportColumn ec : cols) {
                    Field f = t.getClass().getDeclaredField(ec.getDataindex());
                    f.setAccessible(true);
                    value = "";

                    if (f.get(t) != null) {
                        if (f.get(t) instanceof java.util.Date)
                            value = sp.format(f.get(t));
                        else {
                            value = f.get(t).toString();

                            if (ec.getFormat().indexOf("0.0") >= 0) {
                                try {
                                    DecimalFormat df = new DecimalFormat(ec.getFormat());
                                    value = df.format(Double.parseDouble(value));
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                    sheet.addCell(new jxl.write.Label(columnindex, nowrow, value, wcfright));
                    columnindex++;
                }

                if (cols.size() > 0)
                    nowrow++;
            }

            if (!ToolUtils.StringIsEmpty(es.getBottom())) {
                if (cols.size() > 0) {
                    sheet.mergeCells(0, nowrow, cols.size() - 1, nowrow);
                    sheet.addCell(new jxl.write.Label(0, nowrow, es.getBottom(), wcfright));
                    nowrow++;
                }
            }

            OnlineUser ou = ToolUtils.GetOnlineUser();

            if ((ou != null) && !ToolUtils.StringIsEmpty(ou.getUser().getUserid())) {
                if (cols.size() >= 3) {
                    if (es.isHasexporter()) {
                        sheet.mergeCells(0, nowrow, 1, nowrow);
                        sheet.addCell(new jxl.write.Label(0, nowrow, "制表人：" + ou.getUser().getUsername(), wcfleft));
                    }

                    if (es.isHasexportdate()) {
                        sheet.mergeCells(2, nowrow, cols.size() - 1, nowrow);
                        sheet.addCell(new jxl.write.Label(2, nowrow,
                                "制表日期：" + ToolUtils.GetHMFmtDate(ToolUtils.GetNowDate()), wcfright));
                    }
                } else {
                    String ed = "";

                    if (es.isHasexporter())
                        ed += "制表人：" + ou.getUser().getUsername() + "  ";

                    if (es.isHasexportdate())
                        ed += "制表日期：" + ToolUtils.GetHMFmtDate(ToolUtils.GetNowDate());

                    sheet.mergeCells(0, nowrow, cols.size() - 1, nowrow);
                    sheet.addCell(new jxl.write.Label(0, nowrow, ed, wcfCenter));
                }
            }

            workbook.write();
            workbook.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public static <T> InputStream GetPmtExeclStream(SysPmt pmt, List<T> lists) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        WritableWorkbook workbook;
        try {
            workbook = Workbook.createWorkbook(out);
            WritableSheet sheet = workbook.createSheet(pmt.getPmtname(), 0);

            WritableCellFormat wcfCenter = new WritableCellFormat();
            wcfCenter.setAlignment(jxl.format.Alignment.CENTRE);

            WritableCellFormat wcfleft = new WritableCellFormat();
            wcfleft.setAlignment(jxl.format.Alignment.LEFT);

            WritableCellFormat wcfright = new WritableCellFormat();
            wcfright.setAlignment(jxl.format.Alignment.RIGHT);

            int nowrow = 0;

            sheet.mergeCells(0, nowrow, 1, nowrow);
            sheet.addCell(new jxl.write.Label(0, nowrow, pmt.getPmtname(), wcfCenter));
            nowrow++;

            sheet.setColumnView(0, 15);
            sheet.addCell(new jxl.write.Label(0, nowrow, pmt.getPmtname() + "编号", wcfCenter));

            sheet.setColumnView(1, 30);
            sheet.addCell(new jxl.write.Label(1, nowrow, pmt.getPmtname() + "名称", wcfCenter));

            nowrow++;

            String value = "";

            for (T t : lists) {
                Field f = t.getClass().getDeclaredField("id");
                f.setAccessible(true);
                value = f.get(t).toString();
                sheet.addCell(new jxl.write.Label(0, nowrow, value, wcfright));

                f = t.getClass().getDeclaredField("name");
                f.setAccessible(true);
                value = f.get(t).toString();
                sheet.addCell(new jxl.write.Label(1, nowrow, value, wcfright));

                nowrow++;
            }

            workbook.write();
            workbook.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
