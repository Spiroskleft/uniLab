package jasper;

import net.sf.jasperreports.engine.export.CutsInfo;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 * Override default JRXlsExporter behavior in order to support
 * freepanes and auto filter ranges.
 */
public class WebEsepXlsExporter extends JRXlsExporter {

    public static class XlsExtraOptions {
        private Integer freezePaneRow = null;
        private Integer freezePaneCol = null;
        private String autoFilterRange = null;
        private Boolean onePagePerSheet = null;

        public XlsExtraOptions() {

        }

        public XlsExtraOptions(int freezePaneRow, int freezePaneCol, String autoFilterRange) {
            this(freezePaneRow, freezePaneCol, autoFilterRange, true);
        }

        private XlsExtraOptions(Integer freezePaneRow, Integer freezePaneCol, String autoFilterRange, boolean onePagePerSheet) {
            this.freezePaneRow = freezePaneRow;
            this.freezePaneCol = freezePaneCol;
            this.autoFilterRange = autoFilterRange;
            this.onePagePerSheet = onePagePerSheet;
        }

        public Integer getFreezePaneRow() {
            return freezePaneRow;
        }

        public Integer getFreezePaneCol() {
            return freezePaneCol;
        }

        public void setFreezePane(Integer freezePaneRow, Integer freezePaneCol) {
            this.freezePaneRow = freezePaneRow;
            this.freezePaneCol = freezePaneCol;
        }

        public String getAutoFilterRange() {
            return autoFilterRange;
        }

        public void setAutoFilterRange(String autoFilterRange) {
            this.autoFilterRange = autoFilterRange;
        }

        public boolean isOnePagePerSheet() {
            return onePagePerSheet != null && onePagePerSheet.booleanValue();
        }

        public void setOnePagePerSheet(Boolean onePagePerSheet) {
            this.onePagePerSheet = onePagePerSheet;
        }
    }

    private Integer freePaneRow = null;
    private Integer freePaneCol = null;
    private String autoFilterRange = null;

    public void setFreezePaneOptions(int row, int col) {
        this.freePaneRow = row;
        this.freePaneCol = col;
    }

    public void setAutoFilterRange(String autoFilterRange) {
        this.autoFilterRange = autoFilterRange;
    }

    @Override
    protected void createSheet(CutsInfo xCuts, SheetInfo name) {
        super.createSheet(xCuts, name);
//        if (this.freePaneRow != null && this.freePaneCol != null) {
//            this.sheet.createFreezePane(freePaneCol, freePaneRow);
//        }

        if (this.autoFilterRange != null && this.autoFilterRange.length() > 0){
            super.setAutoFilter(this.autoFilterRange);
        }
    }
}
