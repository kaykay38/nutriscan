package processor; // import math

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private String name; // first and last name
    private int age;
    private char bloodType;
    private int bloodPressureSystolic; // Systolic pressure
    private int bloodPressureDiastolic; // Diastolic pressure
    private char gender; // F = female
    private double weight; // person's weight in lbs
    private double fatPercent;

    // default constructor
    public User() {
    }

    public void setUserInfo(String name, int age, char bloodType, int systolicPressure, int diastolicPressure, char gender, double weight, double fatPercent) {
        this.name = name;
        this.age = age;
        this.bloodType = bloodType;
        this.bloodPressureSystolic = systolicPressure;
        this.bloodPressureDiastolic = diastolicPressure;
        this.gender = gender;
        this.weight = weight;
        this.fatPercent = fatPercent;
    }

    public double getBMI() {
        double heightMeters = weight / (Math.pow(fatPercent, 2));
        return weight / (heightMeters * heightMeters);
    }

    // Record the systolic pressure over diastolic pressure, such as "120/80 mm Hg."
    public boolean isHypertension() {
        return bloodPressureSystolic > 140 || bloodPressureDiastolic > 90;
    }

    // Storing the user data
    public void storeUserData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:nutrascan.db");

            // If the user data doesn't exist, create a new table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "name TEXT NOT NULL, " +
                    "age INTEGER, " +
                    "blood_type TEXT, " +
                    "blood_pressure_systolic INTEGER, " +
                    "blood_pressure_diastolic INTEGER, " +
                    "gender TEXT, " +
                    "weight REAL, " +
                    "fat_percent REAL " +
                    ")";
            connection.createStatement().execute(createTableSQL);

            // Insert the user data
            String insertSQL = "INSERT INTO users (name, age, blood_type, blood_pressure_systolic, blood_pressure_diastolic," +
                    " gender, weight, fat_percent) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, String.valueOf(bloodType));
            preparedStatement.setInt(4, bloodPressureSystolic);
            preparedStatement.setInt(5, bloodPressureDiastolic);
            preparedStatement.setString(6, String.valueOf(gender));
            preparedStatement.setDouble(7, weight);
            preparedStatement.setDouble(8, fatPercent);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User data stored successfully!");
            } else {
                System.out.println("User data storage failed.");
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

