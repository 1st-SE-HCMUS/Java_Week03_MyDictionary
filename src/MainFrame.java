import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Genius Doan on 3/23/2017.
 */
public class MainFrame extends JFrame {

    private static final int MARGIN_TOP = 80;
    private static final int MARGIN = 16;
    private static final int LINE_HEIGHT = 32;
    JTabbedPane tpToolbox;
    JTabbedPane tpMeaning;
    JLabel lblKeyword;
    JTextField tfSearch;
    JButton btnEngVie;
    JButton btnVieEng;
    JButton btnCheck;
    JButton btnFavourite;
    JTextArea txtMeaning;
    JList<String> listKeyword;
    JRadioButton rbBegin;
    JRadioButton rbContain;
    JList<String> favouriteList;
    int translateMode = DictionaryManager.MODE_ENG_VIE;
    int searchMode = DictionaryManager.SEARCH_STARTS_WITH;

    // constructor
    MainFrame()
    {
        super();
    }
    MainFrame( String title )
    {
        super( title );                      // invoke the JFrame constructor
        setSize( 800, 600 );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setLayout( new FlowLayout() );       // set the layout manager
        setLayout(null);

        initData();

        JLabel logo = new JLabel(makeIcon("logo.png", 36,36));
        logo.setBounds(MARGIN, MARGIN, 48,48);
        add(logo);

        JLabel jTitle = new JLabel("My Dictionary");
        jTitle.setBounds(MARGIN + 56, MARGIN, 160,LINE_HEIGHT);
        jTitle.setFont(new Font("Sans-serif", Font.BOLD, 24));
        add(jTitle);

        JLabel jSubTitle = new JLabel("1412477 © 2017");
        jSubTitle.setBounds(MARGIN + 56, MARGIN + 20, 160,LINE_HEIGHT);
        jSubTitle.setFont(new Font("Sans-serif", Font.PLAIN, 10));
        add(jSubTitle);

        JLabel lblFind = new JLabel("Find");  // construct a JLabel
        lblFind.setBounds(MARGIN,MARGIN_TOP, 40, LINE_HEIGHT);
        add(lblFind);                        // add the lblKeyword to the JFrame
        tfSearch = new JTextField();
        tfSearch.setBounds(48,MARGIN_TOP, 140, LINE_HEIGHT);
        add(tfSearch);
        btnCheck = new JButton();
        btnCheck.setBounds(200, MARGIN_TOP, LINE_HEIGHT, LINE_HEIGHT);
        btnCheck.setIcon(makeIcon("translate.png",24,24));
        add(btnCheck);

        tpToolbox = new JTabbedPane();
        tpToolbox.setBounds(280,MARGIN_TOP,480,64);

        JPanel pnlDict = new JPanel();
        btnEngVie = new JButton("English - Vietnam");
        btnEngVie.setBounds(16,8, 40,16);
        btnEngVie.setIcon(makeIcon("english.png",16,16));

        pnlDict.add(btnEngVie);
        btnVieEng = new JButton("Vietnam - English");
        btnVieEng.setBounds(56,8, 40,16);
        btnVieEng.setIcon(makeIcon("vietnam.png",16,16));
        pnlDict.add(btnVieEng);

        lblKeyword = new JLabel("Keyword");
        lblKeyword.setBounds(280, 144, 240, LINE_HEIGHT + 8);
        lblKeyword.setFont(new Font("Sans-serif", Font.BOLD, 16));
        lblKeyword.setIcon(makeIcon("speaker.png",16,16));
        add(lblKeyword);

        JPanel pnlTool = new JPanel();
        btnFavourite = new JButton("Add to Favourite");
        btnFavourite.setBounds(16,8, 40,16);
        btnFavourite.setIcon(makeIcon("fav.png",16,16));
        pnlTool.add(btnFavourite);

        tpToolbox.addTab("Dictionary", null, pnlDict, "Click to show Dictionary menu");
        tpToolbox.addTab("Tool", null, pnlTool, "Click to show Toolbox");
        add(tpToolbox);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(MARGIN, 180, 240, 360);
        add(scrollPane);


        listKeyword = new JList<>(DictionaryManager.getInstance(translateMode).getKeywordArray());
        listKeyword.setBounds(MARGIN, 180, 240, 360);
        add(listKeyword);

        scrollPane.setViewportView(listKeyword);

        tpMeaning = new JTabbedPane();
        tpMeaning.setBounds(280, 180, 480, 360);
        txtMeaning = new JTextArea("Meaning...");
        txtMeaning.setEditable(false);

        favouriteList = new JList<>();
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(MARGIN, 180, 240, 360);
        scrollPane2.setViewportView(favouriteList);

        tpMeaning.addTab("Meaning",null,txtMeaning, "Click to show meaning of keyword");
        tpMeaning.addTab("Favourite", null,favouriteList,"Click to show favourite keyword list");
        add(tpMeaning);


        //In initialization code:
        //Create the radio buttons.
        rbBegin = new JRadioButton("Starts with word",true);
        rbBegin.setBounds(MARGIN, MARGIN_TOP + 40, 120,LINE_HEIGHT);
        add(rbBegin);

        rbContain = new JRadioButton("Contains word");
        rbContain.setBounds(MARGIN + 120, MARGIN_TOP + 40, 120,LINE_HEIGHT);
        add(rbContain);

        //Group the radio buttons.
        ButtonGroup rbGroup = new ButtonGroup();
        rbGroup.add(rbBegin);
        rbGroup.add(rbContain);

        //Set event
        listKeyword.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    JList source = (JList)e.getSource();
                    if (source.getSelectedIndex() >= 0) {
                        String keyword = source.getSelectedValue().toString();
                        lblKeyword.setText(keyword);
                        txtMeaning.setText(DictionaryManager.getInstance(translateMode).getMeaning(keyword));
                    }
                }
            }
        });

        btnEngVie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                translateMode = DictionaryManager.MODE_ENG_VIE;
                listKeyword.setListData(DictionaryManager.getInstance(translateMode).getKeywordArray());
                listKeyword.clearSelection();
                JOptionPane.showMessageDialog(null, "English - Vietnamese mode enabled");
            }
        });

        btnVieEng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                translateMode = DictionaryManager.MODE_VIE_ENG;
                listKeyword.setListData(DictionaryManager.getInstance(translateMode).getKeywordArray());
                listKeyword.clearSelection();
                JOptionPane.showMessageDialog(null, "Vietnamese - English mode enabled");
            }
        });

        tfSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String searchString = tfSearch.getText();
                if (Character.isAlphabetic(c) || Character.isDigit(c) || Character.isSpaceChar(c))
                    searchString += e.getKeyChar();
                listKeyword.setListData(DictionaryManager.getInstance(translateMode).search(searchString, searchMode));
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        rbBegin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMode = DictionaryManager.SEARCH_STARTS_WITH;
                DictionaryManager.getInstance(translateMode).search(tfSearch.getText(), searchMode);
            }
        });

        rbContain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMode = DictionaryManager.SEARCH_CONTAINS;
                DictionaryManager.getInstance(translateMode).search(tfSearch.getText(), searchMode);
            }
        });

        tpMeaning.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (tpMeaning.getSelectedIndex() == 1)
                {
                    //Select tab favourite
                    favouriteList.setListData(DictionaryManager.getInstance(translateMode).getFavouriteArray());
                }
            }
        });

        btnFavourite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DictionaryManager.getInstance(translateMode).saveToFavourite(listKeyword.getSelectedValue());
            }
        });

        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] res = DictionaryManager.getInstance(translateMode).search(tfSearch.getText(),searchMode);

                if (res.length > 0) {
                    listKeyword.setListData(res);
                    txtMeaning.setText(DictionaryManager.getInstance(translateMode).getMeaning(res[0]));
                }
            }
        });
    }

    private void initData()
    {
        DictionaryManager.getInstance(DictionaryManager.MODE_ENG_VIE).loadDataFromFile("Anh_Viet.xml");
        DictionaryManager.getInstance(DictionaryManager.MODE_VIE_ENG).loadDataFromFile("Viet_Anh.xml");
    }

    private ImageIcon makeIcon(String imgName ,int width, int height)
    {
        try {
            Image img = ImageIO.read(getClass().getResource(imgName));
            img = img.getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
