
import Models.Connection.DBConnection;
public class App {

    public static void main(String[] args) {
        DBConnection.getConnection();

        javax.swing.SwingUtilities.invokeLater(() -> {
            new Views.Checkout().setVisible(true);
        });

    }
}

