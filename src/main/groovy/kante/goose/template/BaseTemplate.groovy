package kante.goose.template

/**
 * Created by moh on 3/30/16.
 */
interface BaseTemplate
{

    public String init(String table);
    public String allFiles(String table);

    public String insert(Map props);
    public String delete(Map props);
}
