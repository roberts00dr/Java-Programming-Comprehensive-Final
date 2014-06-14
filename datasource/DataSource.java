package datasource;

import finalproject.ThreadRunner;

import java.util.ArrayList;

/**
 * Created by Keisuke Ueda on 2014/05/31.
 * This is an interface of data source.
 */
public interface DataSource {
    public abstract ArrayList<ThreadRunner> getRunners();

}
