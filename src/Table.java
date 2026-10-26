

public class Table {

    private static final int MAX_COLUMNS = 10;
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
}