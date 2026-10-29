

public class Table {

    private static final int MAX_COLUMNS = 20;
    private static final int MAX_ROWS    = 100;

    private final String name;

    private final String[]   columns     = new String[MAX_COLUMNS];
    private       int        columnCount = 0;

    private final String[][] rows        = new String[MAX_ROWS][];
    private       int        rowCount    = 0;

    public Table(String name) {
        this.name = name;
    }

    public String getName()        { return name; }
    public int    getColumnCount() { return columnCount; }
    public int    getRowCount()    { return rowCount; }
    public String getColumn(int i) { return columns[i]; }
    public boolean hasColumns()    { return columnCount > 0; }

    public void addColumn(String column) {
        if (column == null || column.isBlank()) {
            throw new IllegalArgumentException("пустое имя столбца");
        }
        if (columnCount >= MAX_COLUMNS) {
            throw new IllegalStateException("достигнут лимит столбцов: " + MAX_COLUMNS);
        }
        for (int i = 0; i < columnCount; i++) {
            if (columns[i].equals(column)) {
                throw new IllegalArgumentException("столбец уже есть: " + column);
            }
        }
        columns[columnCount++] = column;
    }

    public void addRow(String[] values) {
        if (rowCount >= MAX_ROWS) {
            throw new IllegalStateException("достигнут лимит строк: " + MAX_ROWS);
        }
        String[] row = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            row[i] = (values != null && i < values.length && values[i] != null)
                    ? values[i] : "";
        }
        rows[rowCount++] = row;
    }

    private void printLine(String[] values, int count, int[] widths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            String v = values[i];
            if (v.length() > widths[i]) v = v.substring(0, widths[i] - 1) + "…";
            sb.append(v);
            for (int s = v.length(); s < widths[i]; s++) sb.append(' ');
            if (i < count - 1) sb.append(" | ");
        }
        System.out.println(sb);
    }
    
    private int[] computeWidths() {
        int[] widths = new int[columnCount];
        for (int i = 0; i < columnCount; i++) widths[i] = columns[i].length();
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if (rows[r][c].length() > widths[c]) widths[c] = rows[r][c].length();
            }
        }
        for (int i = 0; i < columnCount; i++) {
            if (widths[i] > 30) widths[i] = 30;
        }
        return widths;
    }
    
    private void printSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < widths.length; i++) {
            for (int s = 0; s < widths[i]; s++) sb.append('-');
            if (i < widths.length - 1) sb.append("-+-");
        }
        System.out.println(sb);
    }

    public void printQueryResult() {
        if (columnCount == 0) {
            System.out.println("(в таблице нет столбцов)");
            return;
        }
        int[] widths = computeWidths();
        printLine(columns, columnCount, widths);
        if (rowCount == 0) {
            System.out.println("(строк нет)");
            return;
        };
        
        printSeparator(widths);
        for (int r = 0; r < rowCount; r++) {
            printLine(rows[r], columnCount, widths);
        }
    }
}
