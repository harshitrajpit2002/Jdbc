

import java.sql.*;
import java.util.Scanner;
import java.sql.DriverManager;

public class task {

    public static void main(String[] args) throws Exception {

        try {
            // onnection establishment 

            String url = "jdbc:mysql://localhost:3306/Harshitchutiya";
            String username = "root";
            String password = "Harshit@123";
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            // table creation 
            String createTableQuery = "CREATE TABLE IF NOT EXISTS demo_6(" +
                    "StudentName VARCHAR(30) NOT NULL," +
                    "CollegeName VARCHAR(50) NOT NULL," +
                    "Round1Marks FLOAT CHECK (Round1Marks BETWEEN 0 AND 10)," +
                    "Round2Marks FLOAT CHECK (Round2Marks BETWEEN 0 AND 10)," +
                    "Round3Marks FLOAT CHECK (Round3Marks BETWEEN 0 AND 10)," +
                    "TechnicalRoundMarks FLOAT CHECK (TechnicalRoundMarks BETWEEN 0 AND 20)," +
                    "TotalMarks FLOAT GENERATED ALWAYS AS (Round1Marks + Round2Marks + Round3Marks + TechnicalRoundMarks),"
                    +
                    "Result VARCHAR(15) GENERATED ALWAYS AS (CASE WHEN TotalMarks < 35 THEN 'Rejected' ELSE 'Selected' END))";

            statement.executeUpdate(createTableQuery);
            statement.close();

           
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Enter your name (max 30 character):");
            String name = sc.next();
            while (true) {
                if(name.length() < 5){
                    break;

                }
                else {
                    System.out.println("Enter your name (max 30 character):");
                     name = sc.next();
                }
                
            }
        
            System.out.println("Enter your College name");
            String collegename = sc.next();

            while (true) {
                if(collegename.length() < 8){
                    break;

                }
                else {
                    System.out.println("Enter your name (max 30 character):");
                     collegename = sc.next();
                }
                
            }
            
            System.out.println("Enter your round 1 marks");
            Float round1Marks = sc.nextFloat();
            System.out.println("Enter your round 2 marks");
            Float round2Marks = sc.nextFloat();

            System.out.println("Enter your round 3 marks");
            Float round3Marks = sc.nextFloat();

            System.out.println("Enter your tech round marks");
            Float TechnicalRoundMarks = sc.nextFloat();

            if (name.length()<=30){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO demo_6(StudentName, CollegeName, Round1Marks, Round2Marks, Round3Marks, TechnicalRoundMarks) VALUES (?,?,?,?,?,?)");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, collegename);
            preparedStatement.setFloat(3, round1Marks);
            preparedStatement.setFloat(4, round2Marks);
            preparedStatement.setFloat(5, round3Marks);
            preparedStatement.setFloat(6, TechnicalRoundMarks);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            }
            else{
                System.out.println("you are exceed length");
            }
            // display
            Statement displayStatement = connection.createStatement();
            ResultSet resultSet = displayStatement
                    .executeQuery("SELECT * , RANK() OVER(ORDER BY TotalMarks DESC) as rank_1 FROM demo_6");

                    
            while (resultSet.next()) {
                // Display fetched data
                System.out.println("StudentName: " + resultSet.getString("StudentName") +
                        ", CollegeName: " + resultSet.getString("CollegeName") +
                        ", Round1Marks: " + resultSet.getFloat("Round1Marks") +
                        ", Round2Marks: " + resultSet.getFloat("Round2Marks") +
                        ", Round3Marks: " + resultSet.getFloat("Round3Marks") +
                        ", TechnicalRoundMarks: " + resultSet.getFloat("TechnicalRoundMarks") +
                        ",TotalMarks : " + resultSet.getFloat("TotalMarks") +
                        ", Result: " + resultSet.getString("Result") +
                        ", rank_1: " + resultSet.getInt("rank_1"));
            }

            resultSet.close();
            displayStatement.close();
            connection.close();

        }

    catch(

    Exception e)
    {
        e.printStackTrace();
    }
}}