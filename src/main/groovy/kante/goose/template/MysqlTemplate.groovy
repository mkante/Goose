package kante.goose.template

/**
 * Created by moh on 3/30/16.
 */
class MysqlTemplate implements BaseTemplate
{
    @Override
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

    @Override
    public String allFiles(String table) {
        return "SELECT * FROM "+table+" ORDER BY created_at ASC";
    }

    @Override
    public String insert(Map props) {

        return "INSERT INTO "+props.table+""" SET file = '${props.file}'""";
    }

    @Override
    public String delete(Map props) {

        return "DELETE FROM "+props.table+""" WHERE file = '${props.file}'""";
    }
}
