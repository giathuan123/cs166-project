import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

public class gui{
  private static JFrame mainFrame = null; private static JPanel childFrame = null;
  static final JButton[] buttons = new JButton[7];
  static final JButton backButton = new JButton("Go Back");
  static final String[] promptLine = {
    "Add a customer",
    "Add a mechanic",
    "Add a car",
    "Insert a service request",
    "Close a service request",
    "List customers with bills less than 100",
    "List customers with more than 20 cars"
  };
  private static void createMainFrame(){
    mainFrame  = new JFrame("Vroom Dealer's DBMS");
    mainFrame.setResizable(false);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLayout(null);
    mainFrame.setLocation(400, 400);
    mainFrame.setSize(500,500);
  }
  // Option 1
  private static JPanel addCustomerPane(){
    JLabel mainLabel = new JLabel(promptLine[0]);
    mainLabel.setHorizontalAlignment(JLabel.CENTER);
    mainLabel.setVerticalAlignment(JLabel.CENTER);
    mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
    JPanel addCustomerPane = new JPanel();
    addCustomerPane.setLayout(new GridLayout(7, 1));
    addCustomerPane.setBackground(new Color(0xffffff));
    addCustomerPane.setBounds(25, 25, 450, 400);
    addCustomerPane.add(mainLabel);
    // First Name,
    JTextField firstName = new JTextField(32);
    firstName.setBorder(new TitledBorder("Enter first name"));
    addCustomerPane.add(firstName);
    // Last Name
    JTextField lastName = new JTextField(32);
    lastName.setBorder(new TitledBorder("Enter last name"));
    addCustomerPane.add(lastName);
    // Phone Number
    JTextField phoneNumber = new JTextField(13);
    phoneNumber.setBorder(new TitledBorder("Enter phone number"));
    addCustomerPane.add(phoneNumber);
    // Address
    JTextArea address = new JTextArea();
    JScrollPane scroll = new JScrollPane(address);
    scroll.setBorder(BorderFactory.createEmptyBorder());
    address.setBorder(new TitledBorder("Enter address"));
    addCustomerPane.add(scroll);

    JButton submissionButton = new JButton("Add customer");

    submissionButton.addActionListener((e)->{
      String firstNameStr = firstName.getText();
      String lastNameStr = lastName.getText();
      String phoneNumberStr = phoneNumber.getText();
      String addressStr = address.getText();
      System.out.println(firstNameStr);
      System.out.println(lastNameStr);
      System.out.println(phoneNumberStr);
      System.out.println(addressStr);
    });

    addCustomerPane.add(submissionButton);

    return addCustomerPane;
  }
  // Option 2
  private static JPanel addMechanicPane(){
    JLabel mainLabel = new JLabel(promptLine[1]);
    mainLabel.setHorizontalAlignment(JLabel.CENTER);
    mainLabel.setVerticalAlignment(JLabel.CENTER);
    mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
    JPanel addMechanicPane = new JPanel();
    addMechanicPane.setLayout(new GridLayout(7, 1, 0, 15));
    addMechanicPane.setBackground(new Color(0xffffff));
    addMechanicPane.setBounds(25, 25, 450, 400);
    addMechanicPane.add(mainLabel);

    // First Name,
    JTextField firstName = new JTextField(32);
    firstName.setBorder(new TitledBorder("Enter first name"));
    addMechanicPane.add(firstName);
    // Last Name
    JTextField lastName = new JTextField(32);
    lastName.setBorder(new TitledBorder("Enter last name"));
    addMechanicPane.add(lastName);
    // Experience
    JTextField experience = new JTextField(5);
    experience.setBorder(new TitledBorder("Enter years of experience"));
    addMechanicPane.add(experience);
    JButton submissionButton = new JButton("Add mechanic");

    submissionButton.addActionListener((e)->{
      try{
      String firstNameStr = firstName.getText();
      String lastNameStr = lastName.getText();
      int experienceInt = Integer.parseInt(experience.getText());
      System.out.println(firstNameStr);
      System.out.println(lastNameStr);
      System.out.println(experienceInt);
      }catch(Exception exc){
        System.out.println(exc.getMessage());
      }
    });
    addMechanicPane.add(submissionButton);

    return addMechanicPane;
  }
  // Option 3
  private static JPanel addCarPane(){
    JLabel mainLabel = new JLabel(promptLine[2]);
    mainLabel.setHorizontalAlignment(JLabel.CENTER);
    mainLabel.setVerticalAlignment(JLabel.CENTER);
    mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
    JPanel addCarPane = new JPanel();
    addCarPane.setLayout(new GridLayout(7, 1, 0, 15));
    addCarPane.setBackground(new Color(0xffffff));
    addCarPane.setBounds(25, 25, 450, 400);
    addCarPane.add(mainLabel);

    // VIN NUMBER,
    JTextField vinNumber = new JTextField(32);
    vinNumber.setBorder(new TitledBorder("Enter VIN number"));
    addCarPane.add(vinNumber);
    // MAKE
    JTextField carMake = new JTextField(32);
    carMake.setBorder(new TitledBorder("Enter car make"));
    addCarPane.add(carMake);
    // MODEL 
    JTextField carModel = new JTextField(15);
    carModel.setBorder(new TitledBorder("Enter car model"));
    addCarPane.add(carModel);

    JButton submissionButton = new JButton("Add car");
    submissionButton.addActionListener((e)->{
      String vinNumberStr = vinNumber.getText();
      String carMakeStr = carMake.getText();
      String carModelStr = carModel.getText();
      System.out.println(vinNumberStr) ;
      System.out.println(carMakeStr);
      System.out.println(carModelStr);
    });
    addCarPane.add(submissionButton);

    return addCarPane;
  }
  // Option 4
  private static JPanel insertServiceRequestPane(){
    JLabel mainLabel = new JLabel(promptLine[3]);
    JPanel insertServiceRequestPane = new JPanel();
    insertServiceRequestPane.setBounds(25, 25, 450, 400);
    insertServiceRequestPane.add(mainLabel);
    return insertServiceRequestPane;
  }
  // Option 5
  private static JPanel closeServiceRequestPane(){
    JLabel mainLabel = new JLabel(promptLine[4]);
    JPanel closeServiceRequestPane = new JPanel();
    closeServiceRequestPane.setBounds(25, 25, 450, 400);
    closeServiceRequestPane.add(mainLabel);
    return closeServiceRequestPane;
  }
  // Option 6,7
  private static JPanel displayResultPane(String title){
    JLabel mainLabel = new JLabel(title);
    JPanel displayResultPane = new JPanel();
    displayResultPane.setBounds(25, 25, 450, 400);
    displayResultPane.add(mainLabel);
    return displayResultPane;
  }
  private static void addChildFrame(){
    childFrame = new JPanel();
    childFrame.setBackground(new Color(0xFFFFFF));
    childFrame.setBounds(25, 25, 450, 400);
    childFrame.setLayout(new GridLayout(8, 1, 0, 15));
    JLabel label = new JLabel("Choose your option!");
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setVerticalAlignment(JLabel.CENTER);
    label.setFont(new Font("Courier", Font.PLAIN, 20));
    childFrame.add(label);
    for(int i = 0; i < 7; i++) {
      buttons[i] = new JButton(promptLine[i]);
      buttons[i].setBounds(5, 5, 90, 200);
      childFrame.add(buttons[i]);
    }
    mainFrame.add(childFrame);
  }
  public static void main(String[] args) {
    //Create and set up the window.
    createMainFrame();
    backButton.addActionListener(e->{
      mainFrame.remove(mainFrame.getContentPane());
      mainFrame.repaint();
      mainFrame.setContentPane(childFrame);
    });
    addChildFrame();
    JPanel[] internalPanel = {
            addCustomerPane(),
            addMechanicPane(),
            addCarPane(),
            insertServiceRequestPane(),
            closeServiceRequestPane(),
            displayResultPane("Display 1"),
            displayResultPane("Display 2")
    };

    buttons[0].addActionListener(e->{
      internalPanel[0].add(backButton);
      mainFrame.remove(childFrame);
      mainFrame.repaint();
      mainFrame.setContentPane(internalPanel[0]);
    });
    buttons[1].addActionListener(e->{
      internalPanel[1].add(backButton);
      mainFrame.remove(childFrame);
      mainFrame.repaint();
      mainFrame.setContentPane(internalPanel[1]);
    });
    buttons[2].addActionListener(e->{
      internalPanel[2].add(backButton);
      mainFrame.remove(childFrame);
      mainFrame.repaint();
      mainFrame.setContentPane(internalPanel[2]);
    });
    buttons[3].addActionListener(e->{
      internalPanel[3].add(backButton);
      mainFrame.remove(childFrame);
      mainFrame.repaint();
      mainFrame.setContentPane(internalPanel[3]);
    });
    buttons[4].addActionListener(e->{
      internalPanel[4].add(backButton);
      mainFrame.remove(childFrame);
      mainFrame.repaint();
      mainFrame.setContentPane(internalPanel[4]);
    });
    buttons[5].addActionListener(e->{
      internalPanel[5].add(backButton);
      mainFrame.remove(childFrame);
      mainFrame.repaint();
      mainFrame.setContentPane(internalPanel[5]);
    });
    buttons[6].addActionListener(e->{
      internalPanel[6].add(backButton);
      mainFrame.remove(childFrame);
      mainFrame.repaint();
      mainFrame.setContentPane(internalPanel[6]);
    });
    mainFrame.setVisible(true);
  }
}
