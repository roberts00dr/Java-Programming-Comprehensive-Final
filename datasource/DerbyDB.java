package datasource;

import finalproject.ThreadRunner;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Keisuke Ueda on 2014/05/31.
 * This class connects to derby database and get columns from it.
 * Then, creates ThreadRunner objects.
 */
public class DerbyDB implements DataSource {

    private Connection connection = null;

    /**
     * Constructor
     * This constructor never asks where is the database.
     */
    public DerbyDB() {
        String dbDirectory = "./";
        System.setProperty("derby.system.home", dbDirectory);
        String dbUrl = "jdbc:derby:runners";
        try {
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    /**
     * Get ArrayList object which contains ThreadRunner objects.
     * The ResultSet methods such as getInt() throws exception when the data is invalid.
     */
    public ArrayList<ThreadRunner> getRunners() {
        ArrayList<ThreadRunner> runners = new ArrayList<ThreadRunner>();
        ResultSet resultSet = select();

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int speed = resultSet.getInt("RUNNERSPEED");
                int rest = resultSet.getInt("RESTPERCENTAGE");

                ThreadRunner runner = new ThreadRunner(name, rest, speed);
                runners.add(runner);
            }
        } catch (SQLException e)
        {
            System.err.println("A column seems to have invalid data. Ignored the column.");
        }

        return runners;
    }

    /**
     * Execute SQL statement.
     *
     * @return ResultSet of the statement.
     */
    private ResultSet select() {
        ResultSet resultSet = null;

        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM runners");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultSet;
    }


    /**
     * pseudo destructor
     * shutdown database.
     *
     * @throws Throwable
     */
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {
                if (e.getMessage().equals("Derby system shutdown.")) {
                    System.out.println("Database is disconnected.");
                }
            }
        }
    }
}
