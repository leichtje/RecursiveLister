import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RecursiveListerFrame extends JFrame {

        JPanel mainPnl, titlePnl, displayPnl, btnPnl;
        JLabel titleLbl;
        JScrollPane scroller;
        JTextArea displayTA;
        JButton quitBtn, searchBtn;


        public RecursiveListerFrame()
        {
            setTitle("Jonathon's Recursive Directory Lister");
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;
            setSize(3*(screenWidth / 4), 3*(screenHeight / 4));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mainPnl = new JPanel();
            mainPnl.setLayout(new BorderLayout());

            add(mainPnl);
            createTitlePanel();
            createDisplayPanel();
            createButtonPanel();

            setVisible(true);
            setResizable(false);
        }

        private void createTitlePanel()
        {
            titlePnl = new JPanel();

            titleLbl = new JLabel("Recursive Directory Lister");
            titleLbl.setFont(new Font("Monospaced", Font.PLAIN, 48));

            titleLbl.setVerticalTextPosition(JLabel.BOTTOM);
            titleLbl.setHorizontalTextPosition(JLabel.CENTER);

            titlePnl.add(titleLbl);
            mainPnl.add(titlePnl, BorderLayout.NORTH);
        }


        private void createDisplayPanel()
        {
            displayPnl = new JPanel();

            displayTA = new JTextArea(20, 60);
            scroller = new JScrollPane(displayTA);

            displayTA.setEditable(false);
            displayTA.setFont(new Font("Dialog",Font.PLAIN,16));

            displayPnl.add(scroller);
            mainPnl.add(displayPnl, BorderLayout.CENTER);
        }

        private void createButtonPanel() {
            btnPnl = new JPanel();
            btnPnl.setLayout(new GridLayout(1, 2));

            searchBtn = new JButton("Search");
            searchBtn.setFont(new Font("Times New Roman", Font.PLAIN, 48));
            quitBtn = new JButton("Quit");
            quitBtn.setFont(new Font("Times New Roman", Font.PLAIN, 48));

            btnPnl.add(searchBtn);
            btnPnl.add(quitBtn);

            mainPnl.add(btnPnl, BorderLayout.SOUTH);

            quitBtn.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }});
            searchBtn.addActionListener(e -> search());
        }
        private void search() {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Choose A Directory: ");

            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File chosenDir = chooser.getSelectedFile();
                displayTA.setText("Chosen Directory:   " + chosenDir + "\n\n");
                displayTA.append("Sub-directories, Files from Sub-directories, and Files from chosen Directory are Listed Below." +"\n\n\n");

                listNames(chosenDir);
            } else
                displayTA.append("File not found! Try Again!");
        }
        private void listNames(File f) {
            File[] Names = f.listFiles();
            if (Names != null) {
                for (File n : Names) {
                    displayTA.append(n + "\n\n");

                    if (n.isDirectory()) {
                        listNames(n);
                    }
                }
            }
        }
}
