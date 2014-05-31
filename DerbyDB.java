import java.sql.*;
import java.util.ArrayList;

/**
 * Created by keta on 2014/05/31.
 */
public class DerbyDB extends DataSource {
    @Override
    public ArrayList getRunners() {
        ArrayList runners = new ArrayList();

        Connection connection = getConnection();
        if( connection != null )
        {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM runners");
                while (resultSet.next()) {
                    String name = resultSet.getString("NAME");
                    int speed = resultSet.getInt("RUNNERSPEED");
                    int rest = resultSet.getInt("RESTPERCENTAGE");

                    ThreadRunner runner = new ThreadRunner(name, rest, speed);
                    runners.add(runner);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        shutdownDB();
        return runners;
    }

    private Connection getConnection()
    {
        Connection connection = null;
        String dbDirectory = "./";
        System.setProperty("derby.system.home", dbDirectory);
        String dbUrl = "jdbc:derby:runners";
        try {
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    private void shutdownDB()
    {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if( e.getMessage().equals("Derby system shutdown.") )
            {
                System.out.println("Database is disconnected.");
            }
        }
    }



}
