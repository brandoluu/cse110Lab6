import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class FieldPanel extends JPanel {

  private JTextField languageField, yearField, queryField;

  public FieldPanel() {
    setLayout(new GridLayout(3, 2));

    JLabel languageLabel = new JLabel("Language:");
    languageField = new JTextField();
    add(languageLabel);
    add(languageField);

    JLabel yearLabel = new JLabel("Year:");
    yearField = new JTextField();
    add(yearLabel);
    add(yearField);

    JLabel queryLabel = new JLabel("Query:");
    queryField = new JTextField();
    add(queryLabel);
    add(queryField);
  }

  public String getLanguage() {
    return languageField.getText();
  }

  public String getYear() {
    return yearField.getText();
  }

  public String getQuery() {
    return queryField.getText();
  }
}

class ButtonPanel extends JPanel {

  private JButton postButton, getButton, putButton, deleteButton;

  public ButtonPanel() {
    setLayout(new GridLayout(2, 2));

    postButton = new JButton("Post");
    add(postButton);

    getButton = new JButton("Get");
    add(getButton);

    putButton = new JButton("Put");
    add(putButton);

    deleteButton = new JButton("Delete");
    add(deleteButton);
  }

  public JButton getPostButton() {
    return postButton;
  }

  public JButton getGetButton() {
    return getButton;
  }

  public JButton getPutButton() {
    return putButton;
  }

  public JButton getDeleteButton() {
    return deleteButton;
  }
}

public class MyServerUI extends JFrame {

  public final String URL = "http://localhost:8100/";

  private JButton postButton, getButton, putButton, deleteButton;
  private FieldPanel fieldPanel;
  private ButtonPanel buttonPanel;

  public MyServerUI() {
    setTitle("MyServerUI");
    setSize(600, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new GridLayout(2, 2));

    fieldPanel = new FieldPanel();
    add(fieldPanel);

    buttonPanel = new ButtonPanel();
    add(buttonPanel);

    postButton = buttonPanel.getPostButton();
    getButton = buttonPanel.getGetButton();
    putButton = buttonPanel.getPutButton();
    deleteButton = buttonPanel.getDeleteButton();

    postButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            String language = fieldPanel.getLanguage();
            String year = fieldPanel.getYear();
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(
              conn.getOutputStream()
            );
            out.write(language + "," + year);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(
              new InputStreamReader(conn.getInputStream())
            );
            String response = in.readLine();
            in.close();
            JOptionPane.showMessageDialog(null, response);
          } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
          }
        }
      }
    );

    getButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            String query = fieldPanel.getQuery();
            URL url = new URL(URL + "?=" + query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
              new InputStreamReader(conn.getInputStream())
            );
            String response = in.readLine();
            in.close();
            JOptionPane.showMessageDialog(null, response);
          } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
          }
        }
      }
    );

    putButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            String language = fieldPanel.getLanguage();
            String year = fieldPanel.getYear();
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(
              conn.getOutputStream()
            );
            out.write(language + "," + year);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(
              new InputStreamReader(conn.getInputStream())
            );
            String response = in.readLine();
            in.close();
            JOptionPane.showMessageDialog(null, response);
          } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
          }
        }
      }
    );

    deleteButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            String query = fieldPanel.getQuery();
            URL url = new URL(URL + "?=" + query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            BufferedReader in = new BufferedReader(
              new InputStreamReader(conn.getInputStream())
            );
            String response = in.readLine();
            in.close();
            JOptionPane.showMessageDialog(null, response);
          } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
          }
        }
      }
    );
  }

  public static void main(String[] args) {
    MyServerUI serverUI = new MyServerUI();
    serverUI.setVisible(true);
  }
}