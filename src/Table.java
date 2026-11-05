

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

    public int[][] findDuplicates() {
        if (rowCount == 0) {
            System.out.println("(строк нет)");
            return new int[0][];
        }
        boolean[] used = new boolean[rowCount];
        int[][] groups = new int[rowCount][];
        int groupCount = 0;
        for (int i = 0; i < rowCount; i++) {
            if (used[i]) {
                continue;
            }
            int[] members = new int[rowCount];
            int count = 0;
            for (int j = i; j < rowCount; j++) {
                if (used[j]) {
                    continue;
                }
                if (rowsEqual(rows[i], rows[j])) {
                    members[count] = j;
                    count++;
                    used[j] = true;
                }
            }
            if (count >= 2) {
                int[] group = new int[count];
                for (int k = 0; k < count; k++) {
                    group[k] = members[k];
                }
                groups[groupCount] = group;
                groupCount++;
            }
        }
        if (groupCount == 0) {
            System.out.println("Дублей не найдено.");
            return new int[0][];
        }
        for (int g = 0; g < groupCount; g++) {
            int[] group = groups[g];
            System.out.println("Группа дублей #" + (g + 1) + " (" + group.length + " шт.):");
            for (int k = 0; k < group.length; k++) {
                int idx = group[k];
                System.out.print("  строка #" + (idx + 1) + ": ");
                for (int c = 0; c < columnCount; c++) {
                    System.out.print(rows[idx][c]);
                    if (c < columnCount - 1) {
                        System.out.print(" | ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        int[][] result = new int[groupCount][];
        for (int g = 0; g < groupCount; g++) {
            result[g] = groups[g];
        }
        return result;
    }
}
