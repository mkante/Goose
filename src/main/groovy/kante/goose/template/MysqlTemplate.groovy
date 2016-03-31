package kante.goose.template

/**
 * Created by moh on 3/30/16.
 */
class MysqlTemplate implements BaseTemplate
{
    public String init(String table) {

        String sql = """
        CREATE TABLE ${table} (
            id INT,
            file TEXT,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
        """;
        return sql;
    }

    public String allFiles(String table) {
        return "SELECT * FROM "+table+" ORDER BY created_at ASC";
    }
}
