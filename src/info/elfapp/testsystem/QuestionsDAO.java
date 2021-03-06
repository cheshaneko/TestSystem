package info.elfapp.testsystem;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static info.elfapp.testsystem.Consts.СolumnsName.Questions.*;


public class QuestionsDAO {

    private DataSource dataSource;
    private final String QUESTION = "Question";

    public QuestionsDAO() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/tsystemdb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String getUserQuestion(int questionID) {
        String r = null;

        try {
            Connection connection = dataSource.getConnection();
            String query = "SELECT " + QUESTION + " FROM Questions WHERE ID = ?";
            PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setInt(1, questionID);
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                r = new String();
                r = resultSet.getString(QUESTION);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    public void addQuestions(String quest, String answ) throws SQLException {
        Connection connection = dataSource.getConnection();
        String query = "INSERT INTO " + Consts.Tables.QUESTIONS + "(" +
                QUEST + "," +
                ANSWER + ") VALUES (\'" +
                quest + "\', \'"+ answ+ "\');";
        connection.createStatement().execute(query);
        connection.close();
    }

}
