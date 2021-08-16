import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui{
  private static JFrame mainFrame = null;
  private static JPanel childFrame = null;
  private static Color background = new Color(0xeeeeee);
  static final JButton[] buttons = new JButton[10];
  static final JButton backButton = new JButton("Go Back");
  static final String[] promptLine = {
    "Add a customer",
    "Add a mechanic",
    "Add a car",
    "Insert a service request",
    "Close a service request",
    "List customers with bills less than 100",
    "List customers with more than 20 cars",
    "<html><center>List Make, Model, and Year of all cars build before 1995 having less than 50000 miles</center></html>",
    "<html><center>List Make, model and number of service requests for the first " + 
    "<br>k cars with the highest number of service orders</center></html>",
    "<html><center>List the first name, last name and total bill of customers in descending " + 
    "<br>order of their total bill for all cars brought to the mechanic</center></html>"
  };
  private static void createMainFrame(){
    mainFrame  = new JFrame("Vroom Dealer's DBMS");
    mainFrame.setResizable(false);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(700,600);
  }
  // Option 1
  private static JPanel addCustomerPane(){
    JLabel mainLabel = new JLabel(promptLine[0]);
    mainLabel.setHorizontalAlignment(JLabel.CENTER);
    mainLabel.setVerticalAlignment(JLabel.CENTER);
    mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
    JPanel addCustomerPane = new JPanel();
    addCustomerPane.setLayout(new GridLayout(7, 1, 0, 15));
    addCustomerPane.setBorder(new EmptyBorder(25, 25, 25, 25));
    addCustomerPane.add(mainLabel);
    // First Name,
    JTextField firstName = new JTextField(32);
    firstName.setBorder(new TitledBorder("Enter first name"));
    firstName.setBackground(background);
    addCustomerPane.add(firstName);
    // Last Name
    JTextField lastName = new JTextField(32);
    lastName.setBorder(new TitledBorder("Enter last name"));
    lastName.setBackground(background);
    addCustomerPane.add(lastName);
    // Phone Number
    JTextField phoneNumber = new JTextField(13);
    phoneNumber.setBorder(new TitledBorder("Enter phone number"));
    phoneNumber.setBackground(background);
    addCustomerPane.add(phoneNumber);
    // Address
    JTextArea address = new JTextArea();
    JScrollPane scroll = new JScrollPane(address);
    scroll.setBorder(BorderFactory.createEmptyBorder());
    address.setBorder(new TitledBorder("Enter address"));
    address.setBackground(background);
    addCustomerPane.add(scroll);

    // Submission Button
    JButton submissionButton = new JButton("Add customer");
    submissionButton.addActionListener((e)->{
      try{
      String firstNameStr = firstName.getText();
      String lastNameStr = lastName.getText();
      String phoneNumberStr = phoneNumber.getText();
      String addressStr = address.getText();
      System.out.println(firstNameStr);
      System.out.println(lastNameStr);
      System.out.println(phoneNumberStr);
      System.out.println(addressStr);
      }catch(Exception exc){

      }
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
    GridLayout gridLayout = new GridLayout(6, 1, 0 , 15);
    addMechanicPane.setLayout(gridLayout);
    addMechanicPane.setBorder(new EmptyBorder(25, 25, 25, 25));
    addMechanicPane.add(mainLabel);

    // First Name,
    JTextField firstName = new JTextField(32);
    firstName.setBorder(new TitledBorder("Enter first name"));
    firstName.setBackground(background);
    addMechanicPane.add(firstName);
    // Last Name
    JTextField lastName = new JTextField(32);
    lastName.setBorder(new TitledBorder("Enter last name"));
    lastName.setBackground(background);
    addMechanicPane.add(lastName);
    // Experience
    JTextField experience = new JTextField(5);
    experience.setBorder(new TitledBorder("Enter years of experience"));
    addMechanicPane.add(experience);
    // Submission Button
    JButton submissionButton = new JButton("Add mechanic");
    experience.setBackground(background);
    Color orButtonColor = submissionButton.getBackground();
    addMechanicPane.add(submissionButton);

    submissionButton.addActionListener((e)->{
      try{
        String firstNameStr = firstName.getText();
        String lastNameStr = lastName.getText();
        int experienceInt = Integer.parseInt(experience.getText());
        submissionButton.setBackground(Color.green);
        // NEED DBMS LOGIC
        Timer timer = new Timer(500, event->{submissionButton.setBackground(orButtonColor);});
        timer.setRepeats(false);
        timer.start();

      }catch(Exception exc){
        submissionButton.setText("Error");
        Timer timer2 = new Timer(500, event->{submissionButton.setText("Add mechanic");submissionButton.setBackground(orButtonColor);});
        timer2.setRepeats(false);
        timer2.start();
        submissionButton.setBackground(Color.RED);
      }

    });

    return addMechanicPane;
  }
  // Option 3
  private static JPanel addCarPane(){
    JLabel mainLabel = new JLabel(promptLine[2]);
    mainLabel.setHorizontalAlignment(JLabel.CENTER);
    mainLabel.setVerticalAlignment(JLabel.CENTER);
    mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
    JPanel addCarPane = new JPanel();
    addCarPane.setLayout(new GridLayout(6, 1, 0, 15));
    addCarPane.setBorder(new EmptyBorder(25, 25, 25, 25));
    addCarPane.add(mainLabel);

    // VIN NUMBER,
    JTextField vinNumber = new JTextField(32);
    vinNumber.setBorder(new TitledBorder("Enter VIN number"));
    vinNumber.setBackground(background);
    addCarPane.add(vinNumber);
    // MAKE
    JTextField carMake = new JTextField(32);
    carMake.setBorder(new TitledBorder("Enter car make"));
    carMake.setBackground(background);
    addCarPane.add(carMake);
    // MODEL 
    JTextField carModel = new JTextField(15);
    carModel.setBorder(new TitledBorder("Enter car model"));
    carModel.setBackground(background);
    addCarPane.add(carModel);
    // SUBMISSION BUTTON
    JButton submissionButton = new JButton("Add car");
    submissionButton.addActionListener((e)->{
      try{
      String vinNumberStr = vinNumber.getText();
      String carMakeStr = carMake.getText();
      String carModelStr = carModel.getText();
      System.out.println(vinNumberStr) ;
      System.out.println(carMakeStr);
      System.out.println(carModelStr);
      }catch(Exception exc){

      }
    });
    addCarPane.add(submissionButton);

    return addCarPane;
  }
  // Option 4
  private static JPanel insertServiceRequestPane(){
    // main label
    JLabel mainLabel = new JLabel(promptLine[3]);
    mainLabel.setHorizontalAlignment(JLabel.CENTER);
    mainLabel.setVerticalAlignment(JLabel.CENTER);
    mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
    
    // main panel
    JPanel insertServiceRequestPane = new JPanel();
    insertServiceRequestPane.setLayout(new GridLayout(4, 1, 0, 15));
    insertServiceRequestPane.setBorder(new EmptyBorder(25, 25, 25, 25));
    insertServiceRequestPane.add(mainLabel);
    
    // Customer Last Name
    JTextField lastName = new JTextField(32);
    lastName.setBorder(BorderFactory.createTitledBorder("Enter customer's lastname"));
    lastName.setBackground(background);
    // Submission Button
    JButton submissionButton = new JButton("Get customers");
    submissionButton.addActionListener(
        (e)->{
          try{
            String lastNameStr = lastName.getText();
            System.out.println(lastNameStr);
          }catch(Exception exc){

          }
        });
    // adding childrens
    insertServiceRequestPane.add(lastName);
    insertServiceRequestPane.add(submissionButton);
    return insertServiceRequestPane;
  }
  // Option 5
  private static JPanel closeServiceRequestPane(){
    JLabel mainLabel = new JLabel(promptLine[4]);
    mainLabel.setHorizontalAlignment(JLabel.CENTER);
    mainLabel.setVerticalAlignment(JLabel.CENTER);
    mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));

    JPanel closeServiceRequestPane = new JPanel();
    closeServiceRequestPane.setLayout(new GridLayout(5, 1, 0, 15));
    closeServiceRequestPane.setBorder(new EmptyBorder(25, 25, 25, 25));
    closeServiceRequestPane.add(mainLabel);

    // request rid
    JTextField requestId = new JTextField(13);
    requestId.setBorder(BorderFactory.createTitledBorder("Enter request id"));
    requestId.setBackground(background);
    
    // mechanic sid
    JTextField mechanicId = new JTextField(13);
    mechanicId.setBorder(BorderFactory.createTitledBorder("Enter mechanic's employee id"));
    mechanicId.setBackground(background);

    // submission button
    JButton submissionButton = new JButton("Close service request");
    submissionButton.addActionListener(
        (e)->{
          try{
            String requestIdStr = requestId.getText();
            String mechanicIdStr = mechanicId.getText();
            System.out.println(requestIdStr);
            System.out.println(mechanicIdStr);
          }catch(Exception exc){

          }
        });
    closeServiceRequestPane.add(requestId);
    closeServiceRequestPane.add(mechanicId);
    closeServiceRequestPane.add(submissionButton);
    return closeServiceRequestPane;
  }
  // Option 6,7
  private static JPanel displayResultPane(String title){
    JLabel mainLabel = new JLabel(title);
    mainLabel.setHorizontalAlignment(JLabel.CENTER);
    mainLabel.setVerticalAlignment(JLabel.CENTER);
    mainLabel.setFont(new Font("Courier", Font.PLAIN, 15));
    JPanel displayResultPane = new JPanel();
    displayResultPane.setLayout(new GridLayout(3, 1, 0, 15));
    displayResultPane.setBorder(new EmptyBorder(25, 25, 25, 25));
    displayResultPane.add(mainLabel);
    return displayResultPane;
  }
  private static class ButtonActionListener implements ActionListener{
    private int page;
    public static JPanel[] internalPanel = null;
    public ButtonActionListener(int page){
      this.page = page;
    }

    @Override
    public void actionPerformed(ActionEvent e){
      if(internalPanel == null){
        System.out.println("[ButtonActionListener] internalPanel not initialized");
        return;
      }else{
        internalPanel[page].add(backButton);
        mainFrame.setContentPane(internalPanel[page]);
        mainFrame.revalidate();
        mainFrame.repaint();
      }
      return;
    }
  }
  private static void addChildFrame(){
    childFrame = new JPanel();
    childFrame.setBorder(new EmptyBorder(25, 25, 25, 25));
    childFrame.setLayout(new GridLayout(11, 1, 0, 15));
    JLabel label = new JLabel("Choose your option!");
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setVerticalAlignment(JLabel.CENTER);
    label.setFont(new Font("Courier", Font.PLAIN, 20));
    childFrame.add(label);

    for(int i = 0; i < buttons.length; i++) {
      buttons[i] = new JButton(promptLine[i]);
      buttons[i].setBounds(5, 5, 90, 200);
      childFrame.add(buttons[i]);
    }
    mainFrame.add(childFrame);
  }
  public static void initBackButton(){
    backButton.addActionListener(e->{
      mainFrame.setContentPane(childFrame);
      mainFrame.revalidate();
      mainFrame.repaint();
    });
    return;
  }

  public static void initPages(){
    for(int i = 0; i < buttons.length; i++){
      buttons[i].addActionListener(new ButtonActionListener(i));
    }
    return;
  }
  public static void initInternalPanes(){
    ButtonActionListener.internalPanel = new JPanel[]{
            addCustomerPane(), // option 1
            addMechanicPane(), // option 2
            addCarPane(),      // option 3
            insertServiceRequestPane(), // option 4
            closeServiceRequestPane(),  // option 5
            displayResultPane(promptLine[5]), // option 6
            displayResultPane(promptLine[6]), // option 7
            displayResultPane(promptLine[7]), // option 8
            displayResultPane(promptLine[8]), // option 9
            displayResultPane(promptLine[9]), // option 10
    };
  }
  public static void main(String[] args) {
    //Create and set up the window.
    createMainFrame();
    initBackButton();
    addChildFrame();
    initInternalPanes();
    initPages();
    // LISTING require injecting database response into gui
    buttons[5].addActionListener((e)->{
      ButtonActionListener.internalPanel[5].add(new JButton("Something is here to stay"));
    });
    buttons[6].addActionListener((e)->{
    });
    buttons[7].addActionListener((e)->{
    });
    buttons[8].addActionListener((e)->{
    });
    buttons[9].addActionListener((e)->{
    });
    mainFrame.setVisible(true);
  }
}
