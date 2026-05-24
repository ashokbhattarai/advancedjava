package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        EmployeeDAO dao = new EmployeeDAO();

        dao.insert("Ashok", "ashok@gmail.com");
//        dao.getAll();
//
//        dao.update(1, "Ashok Updated");
//        dao.delete(1);
    }
}
