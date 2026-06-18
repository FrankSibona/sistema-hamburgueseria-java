import Views.Home;
import Models.Connection.DBConnection;

public class App {
    public static void main(String[] args) {
        DBConnection.getConnection();

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Home vistaHome = new Home();
                vistaHome.setVisible(true);
            }
        });
    }
}