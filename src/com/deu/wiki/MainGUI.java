package com.deu.wiki;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MainGUI extends JFrame {
    private JPanel MainFrame = null;
    private JPanel UserFrame = null;
    private JPanel ReportFrame = null;
    private JPanel SongFrame = null;
    private JPanel PostDetailFrame = null;
    private CardLayout cards = new CardLayout();
    public static String Login_ID = ""; 
    private int search_mode;
    private boolean Is_admin;
    public MainGUI() {
    	search_mode = 0;
        setTitle("������Ű");
        setBounds(500, 300, 1280, 720);
        setLocationRelativeTo(null); // ���α׷��� ���߾ӿ� ��
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(cards);
        MainFrame = new showFrame();
        ReportFrame = new ReportGUI();
        SongFrame = new SongGUI();
        PostDetailFrame = new PostDetailGUI();
        add("Main", MainFrame);
        add("Report", ReportFrame);
        add("PostDetail", PostDetailFrame);
        setVisible(true);
    }

    class showFrame extends JPanel {
        private JTextField tfDbIp, tfDbUsername, tfIdx, tfName, tfId, tfPassword;
        private JTextField tfName2, tfId2, tfPassword2;
        private JPasswordField pfDbPassword;
        private JTable table;
        private JButton btnLogin;
        private JPanel pSouth;
        private JPanel pWest;
        JButton btnSearch;
        JButton btnInsert;
        JButton btnSelect;
        JButton btnUpdate;
        JButton btnDelete;
        JButton btnReport_list;
        JButton btnReport;
        JButton btnReport_post;
        JButton btnReport_review;
        JButton btnPostDetail;
        JButton btnsort;
        JButton btnUpdate2;
        JButton btnCancel2;

        boolean isLogin; // �α��� ���� ǥ���� ����
        private String name2, password2, id2;
        private String col_name; 
        private String search_keyword;
        private String sort_type;
        private String Click_keyword;
        public showFrame() {
        	col_name = "keyword";
        	search_keyword = "";
        	sort_type = "asc";
        	Click_keyword = "";
        	Is_admin = false;
            setLayout(new BorderLayout());
            // ================= ��� DB ���� �г� ==================
            JPanel pNorth = new JPanel();
            add(pNorth, BorderLayout.PAGE_START);
            // GridLayout ���� ����(4���� ĭ ����)
            pNorth.setLayout(new GridLayout(1, 4));

            // DB IP�ּ� �Է� �г�
            JPanel pDbIp = new JPanel();
            pNorth.add(pDbIp);

//            pDbIp.add(new JLabel("IP"));
//            tfDbIp = new JTextField(10);
//            tfDbIp.setText("192.168.56.1"); // �ӽ÷� IP �ּҸ� �̸� �Է�
//            pDbIp.add(tfDbIp);

            // DB Username �Է� �г�
            JPanel pDbUsername = new JPanel();
            pNorth.add(pDbUsername);

            pDbUsername.add(new JLabel("ID(�й�)"));
            tfDbUsername = new JTextField(10);
            pDbUsername.add(tfDbUsername);

            // DB Password �Է� �г�
            JPanel pDbPassword = new JPanel();
            pNorth.add(pDbPassword);

            pDbPassword.add(new JLabel("��й�ȣ"));
            pfDbPassword = new JPasswordField(10);
            pDbPassword.add(pfDbPassword);

            // DB Login ��ư �г�
            JPanel pDbLogin = new JPanel();
            pNorth.add(pDbLogin);

            btnLogin = new JButton("�α���");
            pDbLogin.add(btnLogin);

            // ================= �ϴ� ��ư �г� ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);


            btnSearch = new JButton("�Խù� �˻�");
            btnInsert = new JButton("�Խù� �߰�");
            btnUpdate = new JButton("�Խù� ����");
            btnDelete = new JButton("�Խù� ����");
            btnSelect = new JButton("�Խù� ���");
            btnsort = new JButton("����");
            btnReport = new JButton("�Խù� �Ű�");
            
            btnPostDetail = new JButton("�Խñ� �� ȭ��");
            btnReport_post = new JButton("�Ű� �ʰ� �Խñ�");
            btnReport_review = new JButton("�Ű� �ʰ� ���");
            btnReport_list = new JButton("�Ű� ���� Ȯ��");     // ���� ����
            ActionListener btnListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnSelect) {
                    	search_mode = 0;
                        select();
                    	Click_keyword = "";
                    	tfIdx.setText("");
                        tfName.setText("");
                    }  else if (e.getSource() == btnSearch) {
                        search();
                    	Click_keyword = "";
                    	tfIdx.setText("");
                        tfName.setText("");
                    } else if (e.getSource() == btnsort) {
                        sort();
                    	Click_keyword = "";
                    	tfIdx.setText("");
                        tfName.setText("");
                    }
                }
            };

            btnSelect.addActionListener(btnListener);
            btnSearch.addActionListener(btnListener);
            btnsort.addActionListener(btnListener);
//            btnSong = new JButton("�뷡 ����");

            pSouth.add(btnSearch);
            pSouth.add(btnInsert);
            pSouth.add(btnUpdate);
            pSouth.add(btnDelete);
            pSouth.add(btnSelect);
            pSouth.add(btnsort);
            pSouth.add(btnPostDetail);
            pSouth.add(btnReport_post);
            pSouth.add(btnReport_review);
            pSouth.add(btnReport);
            pSouth.add(btnReport_list);
            

//            pSouth.add(btnSong);

            btnReport.setVisible(false);
            btnInsert.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            btnReport_post.setVisible(false);
            btnReport_review.setVisible(false);
            btnReport_list.setVisible(false);
//            btnSong.setVisible(false);

            // �α��� ��ư �̺�Ʈ ó��
            btnLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dbLogin();

                    // ��ư �����ϴ� ������
                    ActionListener btnListener = new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() == btnInsert) {
                                insert();
                            	Click_keyword = "";
                            	tfIdx.setText("");
                                tfName.setText("");
                            } else if (e.getSource() == btnDelete) {
                                delete();
                            	Click_keyword = "";
                            	tfIdx.setText("");
                                tfName.setText("");
                            } else if (e.getSource() == btnUpdate) {
                                update();
                            	Click_keyword = "";
                            	tfIdx.setText("");
                                tfName.setText("");
                            } else if (e.getSource() == btnReport){
                                report();
                            	Click_keyword = "";
                            	tfIdx.setText("");
                                tfName.setText("");
                            } else if (e.getSource() == btnReport_post){
                            	search_mode = 1;
                                select();
                            	Click_keyword = "";
                            	tfIdx.setText("");
                                tfName.setText("");
                            }
                        }
                    };


                    // ��ư ������ ���� ����
                    btnInsert.addActionListener(btnListener);
                    btnDelete.addActionListener(btnListener);
                    btnUpdate.addActionListener(btnListener);
                    btnReport.addActionListener(btnListener);
                    btnReport_post.addActionListener(btnListener);
                }
            });
            btnReport_list.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e)
                {
                	if (Click_keyword.equals("")) {
            		
	            	} else {
	                    ChangePanel("Report");
	                	ReportGUI d = (ReportGUI)ReportFrame;
	                	d.showFrame(Click_keyword, 0);
	            	}
                }
            });
            btnPostDetail.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                	if (Click_keyword.equals("")) {
                		
                	} else {
	                    ChangePanel("PostDetail");
	                	PostDetailGUI d = (PostDetailGUI)PostDetailFrame;
	                	if (Is_admin) {
		                	d.showFrame(Click_keyword, 2);
	                	}
	                	else {
		                	d.showFrame(Click_keyword, 0);
	                	}
                	}
                }
            });
            btnReport_review.addMouseListener(new MouseAdapter() {
            	@Override
                public void mousePressed(MouseEvent e) {
                    ChangePanel("PostDetail");
                	PostDetailGUI d = (PostDetailGUI)PostDetailFrame;
                	d.showFrame("�Ű� ����", 1);
                }
            });
            // ================= ���� ��� ���� �Է� �г� ==================
            pWest = new JPanel();
            add(pWest, BorderLayout.LINE_START);
            // �г� 5�� �� ���� ���� GridLayout(5, 1) ����
            pWest.setLayout(new GridLayout(5, 1));

            // �� �ະ�� �Է� �׸� ���� JLabel + JTextField �� �г� ����
            JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pIdx);

            pIdx.add(new JLabel("��   ȣ"));
            tfIdx = new JTextField(10);
            tfIdx.setEditable(false); // �ؽ�Ʈ�ʵ� ���� �Ұ� ����
            pIdx.add(tfIdx);

            JPanel pName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pName);
            pName.add(new JLabel("Ű �� ��"));
            tfName = new JTextField(10);
//            tfName.setEditable(false);
            pName.add(tfName);
            

//            JPanel pId = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//            pWest.add(pId);
//            pId.add(new JLabel("��� ��ȣ"));
//            tfId = new JTextField(10);
////            tfId.setEditable(false);
//            pId.add(tfId);

//            JPanel pPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//            pWest.add(pPassword);
//            pPassword.add(new JLabel("�н�����"));
//            tfPassword = new JTextField(10);
//            pPassword.add(tfPassword);

            // ================= �߾� ��� ���� ��� �г� ==================
            // ��ũ�ѹ� ����� ���� JScrollPane ��ü�� �����Ͽ� Center ������ ����
            JScrollPane scrollPane = new JScrollPane();
            add(scrollPane, BorderLayout.CENTER);

            // JTable ��ü�� �����Ͽ� JScrollPane �� ViewPort ������ ����
            table = new JTable();
            table.getTableHeader().setReorderingAllowed(false); // �� �̵� �Ұ� ����
            scrollPane.setViewportView(table);

            // ���̺� �÷��� ǥ�ø� ���� Vector ��ü�� �÷����� ������ �� DefaultTableModel ��ü�� �߰�
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("��ȣ");
            columnNames.add("Ű����");
            columnNames.add("�ۼ���");
            columnNames.add("��ȸ��");
            columnNames.add("����");
            columnNames.add("�����");
            columnNames.add("�ۼ���");

            DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // �� ���� �Ұ����ϵ��� ����
                }

            };

            // JTable �� DefaultTableModel ��ü �߰�
            table.setModel(dtm);

            // ���̺� ���� ���ڵ� �Ǵ� �÷� Ŭ�� �� �̺�Ʈ ó��
            table.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // ���̺��� ���콺�� Ŭ���� ��� ȣ��Ǵ� �޼���
                    // ���õ� �÷��� ��, �� ��ȣ ���
//				System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

                    // ���õ� �÷��� ������ ���
//				System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

                    showAdminInfo(); // ���õ� ���� ��� �÷� �����͸� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
                    System.out.println(table.getSelectedRow() + table.getSelectedColumn());
                    switch(table.getSelectedColumn()) {
                    case 1:
                    	col_name = "keyword";
                    	break;
                    case 3:
                    	col_name = "view_count";
                    	break;
                    case 4:
                    	col_name = "AVG_SCORE";
                    	break;
                    case 5:
                    	col_name = "review_count";
                    	break;
                    case 6:
                    	col_name = "created_at";
                    	break;
                    }
                	sort_type = "asc";
                }
            });
        }

        // �α��� ��� �����ϴ� dbLogin() �޼��� ����
        void dbLogin() {
            // DB ���̵�� �н����带 �����ͼ� DAO Ŭ������ login() �޼��� ȣ��
            // => �α��� ���� ����� ���Ϲ޾� "���̵� ����", "�н����� Ʋ��", "�α��� ����" �� ������ �з�
            // 0. �α��� ��ư vs �α׾ƿ� ��ư �Ǻ�
            // 1. �α��� ��ư�� ���
            // 1-1. ���̵� �Ǵ� �н����尡 �߸����� ��� ���޼��� ��� �� �ش� �ʵ忡 Ŀ�� ��û
            // 1-2. �α��� ������ ��� "�α���" ��ư�� �ؽ�Ʈ�� "�α׾ƿ�"���� ���� ��
            // IP, Username, Password �ؽ�Ʈ�ʵ� �Է� �Ұ� ����
            // 2. �α׾ƿ� ��ư�� ���
            // "�α׾ƿ�" ��ư �ؽ�Ʈ�� "�α���"���� ���� ��
            // Username, Password �ؽ�Ʈ�ʵ� �Է� ���� ����

            if (!isLogin) { // �α��� ���°� �ƴ� ���
                // ------------- �Է� üũ --------------

                String username = tfDbUsername.getText();
                String password = new String(pfDbPassword.getPassword());


                    if (username.length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "���̵� �Է����ּ���!", "���� ����", JOptionPane.ERROR_MESSAGE);
                    tfDbUsername.requestFocus();
                    return;
                } else if (password.length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "��й�ȣ�� �Է����ּ���!", "���� ����", JOptionPane.ERROR_MESSAGE);
                    pfDbPassword.requestFocus();
                    return;
                }

                MainDTO dto = new MainDTO(username, password);
                MainDAO dao = MainDAO.getInstance();
                String result = dao.login(dto);
                if (result.equals("none")) { // ���̵� ���� ���
                    JOptionPane.showMessageDialog(rootPane, "�α��� ������ ��ġ���� �ʽ��ϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
                    tfDbUsername.requestFocus();
                    return;
                } else if (result.equals("null")) { // �г����� ���� ���
                    String input = JOptionPane.showInputDialog(rootPane, "ȯ���մϴ� �г����� �Է����ּ���.", "�г��� �Է�",  JOptionPane.INFORMATION_MESSAGE);
                    if(input != null) {
                    	dto.setnickname(input);
                    	int nick_result = dao.input_nick(dto);
                    	if(nick_result == 1) {
                    		JOptionPane.showMessageDialog(rootPane, input + "�� ȯ���մϴ�.", "�α��� ����", JOptionPane.PLAIN_MESSAGE);
                    	}
                    	else {
                    		return;
                    	}
                    }
                    else {
                    	return;
                    }
                } else if(result.equals("stop")) {
                	JOptionPane.showMessageDialog(rootPane, "�Ű� �������� ���� �������� �Դϴ�.(���� ������ ����)", "�α��� ����", JOptionPane.ERROR_MESSAGE);
                    tfDbUsername.requestFocus();
                    return;
                } else {
                	JOptionPane.showMessageDialog(rootPane, result.trim() + "�� ȯ���մϴ�.", "�α��� ����", JOptionPane.PLAIN_MESSAGE);
                }

                // �α��� �������� ���
//                tfDbIp.setEditable(false);
//                tfDbUsername.setEditable(false);
//                pfDbPassword.setEditable(false);
                Login_ID = dto.getId();
                btnLogin.setText("�α׾ƿ�");
//                if (username.equals("admin")) {
                if(dto.getType().equals("admin")) {
//                    tfName.setVisible(true);
//                    tfId.setVisible(true);
//                	   tfName.setEditable(true);
//                       tfId.setEditable(true);
//                    tfPassword.setVisible(true);
                	Is_admin = true;
                    btnReport_post.setVisible(true);
                    btnReport_review.setVisible(true);
                    btnReport.setVisible(true);
                    btnInsert.setVisible(true);
//                    btnUpdate.setVisible(true);
//                    btnDelete.setVisible(true);
                    btnReport_list.setVisible(true); //�Ű�Խñ�
                    btnPostDetail.setVisible(true);
                    btnPostDetail.setVisible(true);
                    table.setVisible(true);
//                    btnSong.setVisible(true);
                } else {
                	Is_admin = false;
                    btnPostDetail.setVisible(true);
                    btnReport.setVisible(true);
                	btnInsert.setVisible(true);
//                    btnTotal.setVisible(true); //�Ű�Խñ�
//                    btnUser.setVisible(true);  // �Ű���
//                    btnSong.setVisible(true);
                }
                isLogin = true; // �α��� ���·� ����
            } else { // �α��� ���¿��� �α׾ƿ� ��ư�� Ŭ������ ���
            	Is_admin = false;
//                tfDbIp.setEditable(true);
                tfDbUsername.setEditable(true);
                pfDbPassword.setEditable(true);
//                tfName.setEditable(false);
//                tfId.setEditable(false);
                btnReport_post.setVisible(false);
                btnReport_review.setVisible(false);
                tfDbUsername.setText("");
                pfDbPassword.setText("");
                btnReport.setVisible(false);
                btnPostDetail.setVisible(false);
                btnLogin.setText("�α���");
                btnInsert.setVisible(false);
                btnUpdate.setVisible(false);
                btnDelete.setVisible(false);
                btnReport_list.setVisible(false);
                DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//   			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
               // => ��, addRow() ���� �޼��尡 ����

                dtm.setRowCount(0);
//                btnSong.setVisible(false);
                Login_ID = "";
                isLogin = false; // �α׾ƿ� ���·� ����
            }
        }

        // �Խù� �߰�
        public void insert() {
            if (!isLogin) {
                JOptionPane.showMessageDialog(rootPane, "�α��� �ʿ�", "�α��� ����", JOptionPane.ERROR_MESSAGE);
                tfDbUsername.requestFocus();
                return;
            }

            String title = tfName.getText();
//            String password = tfPassword.getText();

            // �Է� �׸� üũ
            if (title.length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Ű���� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                tfName.requestFocus();
                return;
            }
//            else if (password.length() == 0) {
//                JOptionPane.showMessageDialog(rootPane, "�н����� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
//                tfPassword.requestFocus();
//                return;
//            }

            MainDTO dto = new MainDTO(title);
            MainDAO dao = MainDAO.getInstance();
            int result = dao.insert(dto, Login_ID); // �Խù� �߰� �� ����� ����

            // �Խù� �߰� ���� �Ǻ�
            if (result == 0) { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "�Խù��� �߰��� �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "�Խù��� �߰��Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // �Խù� ����
        public void update() {
            if (table.getSelectedRow() == -1) { // ���̺� �� ���� �ȵ��� ��� -1 ���ϵ�
                return;
            }

            // ���̺� �� �������� ��� â �� ������ �����Ͽ� ���õ� ��� ���� ǥ��
            JFrame editFrame = new JFrame("Update"); // �� ������ ����
            // ��ġ ���� �� ���� �θ� �������� ��ġ ��ǥ ���� �޾Ƽ� ���(doubleŸ���̹Ƿ� int�� ����ȯ)
            editFrame.setBounds(800, 200, 250, 300);
            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ���� �����Ӹ� ����

            JPanel pWest2 = new JPanel();
            editFrame.add(pWest2, BorderLayout.CENTER);
            // �г� 5�� �� ���� ���� GridLayout(5, 1) ����
            pWest2.setLayout(new GridLayout(5, 1));

            // �� �ະ�� �Է� �׸� ���� JLabel + JTextField �� �г� ����
            JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pIdx);

            pIdx.add(new JLabel("��   ȣ"));
            JTextField tfIdx2 = new JTextField(10);
            tfIdx2.setText(tfIdx.getText());
            tfIdx2.setHorizontalAlignment(tfIdx2.CENTER);
            tfIdx2.setEditable(false); // �ؽ�Ʈ�ʵ� ���� �Ұ� ����
            pIdx.add(tfIdx2);

            JPanel pName2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pName2);

            pName2.add(new JLabel("��   ��"));
            tfName2 = new JTextField(10);
            tfName2.setHorizontalAlignment(tfIdx2.CENTER);
            pName2.add(tfName2);

            JPanel pId2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pId2);

            pId2.add(new JLabel("�� �� ��"));
            tfId2 = new JTextField(10);
            tfId2.setHorizontalAlignment(tfIdx2.CENTER);
            pId2.add(tfId2);

            JPanel pPassword2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pPassword2);

//            pPassword2.add(new JLabel("�н�����"));
//            tfPassword2 = new JTextField(10);
//            tfPassword2.setHorizontalAlignment(tfIdx2.CENTER);
//            pPassword2.add(tfPassword2);

            JPanel pSouth2 = new JPanel();
            editFrame.add(pSouth2, BorderLayout.SOUTH);

            btnUpdate2 = new JButton("����");
            btnCancel2 = new JButton("���");

            pSouth2.add(btnUpdate2);
            pSouth2.add(btnCancel2);
            name2 = tfName2.getText();
            id2 = tfId2.getText();
            password2 = tfPassword2.getText();

            // ��ư �� �� �����ϴ� ������
            ActionListener btnListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnUpdate2) {
                        // �Է� �׸� üũ
                        if (tfName2.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "�̸� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                            tfName2.requestFocus();
                            return;
                        } else if (tfId2.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "���̵� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                            tfId2.requestFocus();
                            return;
                        } 
                        else if (tfPassword2.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "�н����� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                            tfPassword2.requestFocus();
                            return;
                        }
                        MainDTO dto = new MainDTO(Integer.parseInt(tfIdx2.getText()), tfName2.getText(),
                                tfId2.getText(), tfPassword2.getText());
                        MainDAO dao = MainDAO.getInstance();
                        int result = dao.update(dto); // �Խù� ���� �� ����� ����

                        // �Խù� ���� ���� �Ǻ�
                        if (result == 0) { // �������� ���
                            JOptionPane.showMessageDialog(rootPane, "�Խù��� ������ �� �����ϴ�.", "����",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        } else { // �������� ���
                            JOptionPane.showMessageDialog(rootPane, "�Խù��� �����Ͽ����ϴ�.", "����",
                                    JOptionPane.INFORMATION_MESSAGE);
                            editFrame.setVisible(false);
                        }
                    } else if (e.getSource() == btnCancel2) {
                        editFrame.setVisible(false);
                    }
                }
            };
            // ��ư ������ ���� ����
            btnUpdate2.addActionListener(btnListener);
            btnCancel2.addActionListener(btnListener);

            editFrame.setVisible(true);
        }

        // �Խù� �˻�
        public void search() {
        	MainDAO dao = MainDAO.getInstance();
            // �Խù� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
        	String keyword = tfName.getText();
            Vector<Vector> data = dao.selectOne(keyword, "keyword", "asc", search_mode);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�
            
            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate();
            search_keyword = keyword;
        }

        public void report(){
        	if (Click_keyword.equals("")) {
        		JOptionPane.showMessageDialog(rootPane, "�Ű��� �Խñ��� ������ �ּ���.", "�Ű� �Խñ� ����", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	String input = JOptionPane.showInputDialog(rootPane, "�Ű� ������ �ۼ��� �ּ���.", Click_keyword + " �Ű�",  JOptionPane.WARNING_MESSAGE);
            if(input != null) {
            	if(input.equals("")) {
            		JOptionPane.showMessageDialog(rootPane, "������ �����ϴ�!", "�Ű� ���", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	ReportDAO dao = ReportDAO.getInstance();
            	ReportDTO dto = new ReportDTO(Login_ID,Click_keyword,input);
            	int result = dao.insert(dto);
            	if (result == 0) { // �������� ���
                    JOptionPane.showMessageDialog(rootPane, "�Ű� ���� �߻�.", "����", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(result == -1) {
                	JOptionPane.showMessageDialog(rootPane, "�̹� �Ű� �ϼ̽��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(result == -2) {
                	JOptionPane.showMessageDialog(rootPane, "�α����� ���ּ���.", "����", JOptionPane.ERROR_MESSAGE);
                    return;
                } else { // �������� ���
                    JOptionPane.showMessageDialog(rootPane, Click_keyword + " �Խñ��� �Ű��Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                    Static.pNum = null;
                }
            }
            else {
            	return;
            }
        }
        // �Խù� ���
        public void select() {
//			if(!isLogin) {
//				JOptionPane.showMessageDialog(
//						rootPane, "�α��� �ʿ�", "�α��� ����", JOptionPane.ERROR_MESSAGE);
//				tfDbUsername.requestFocus();
//				return;
//			}

        	MainDAO dao = MainDAO.getInstance();
            // �Խù� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
            Vector<Vector> data = dao.select("keyword", search_mode);
            
            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // ������ ����(���� �׸���)
            search_keyword = "";
        }
        public void sort() {
        	MainDAO dao = MainDAO.getInstance();
            // �Խù� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
            Vector<Vector> data = dao.selectOne(search_keyword, col_name, sort_type, search_mode);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate();
            if(sort_type.equals("asc")) {
            	sort_type = "desc";
            }
            else {
            	sort_type = "asc";
            }
        }
        // �Խù�����
        public void delete() {
            if (!isLogin) {
                JOptionPane.showMessageDialog(rootPane, "�α��� �ʿ�", "�α��� ����", JOptionPane.ERROR_MESSAGE);
                tfDbUsername.requestFocus();
                return;
            }

            // InputDialog ����Ͽ� ������ �Խù� ��ȣ �Է¹ޱ�
            String idx = JOptionPane.showInputDialog(rootPane, "������ �Խù� ��ȣ�� �Է��ϼ���.");
//			System.out.println(idx);

            while (idx == null || idx.length() == 0) {
                // ���(null) Ŭ�� �Ǵ� �ƹ��͵� �Է����� �ʰ� Ȯ�� Ŭ�� ��
                if (idx == null) { // ��� ��ư Ŭ������ ��� �ƹ� ���� X
                    return; // ���� �޼��� ����
                }

                // �ƹ� ��ȣ�� �Է����� �ʰ�(�ν�Ʈ���� ����) Ȯ�� ��ư Ŭ������ ��� �޼��� ǥ��
                JOptionPane.showMessageDialog(rootPane, "��ȣ �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);

                // �ٽ� �Է¹ޱ�
                idx = JOptionPane.showInputDialog(rootPane, "������ �Խù� ��ȣ�� �Է��ϼ���.");
            }

            // ������ ��ȣ�� �Է��� ���
            // => ����ǥ������ ����Ͽ� ��ȣ�� �Է¹޵��� ó���� �� �ִ�!
            // => \\d : ����, {1,} : ��Ģ�� 1�� �̻� �ݺ� => \\d{1,} : ���� 1�ڸ� �̻�
            if (!Pattern.matches("\\d{1,}", idx)) {
                JOptionPane.showMessageDialog(rootPane, "���� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                return;
            }

            MainDAO dao = MainDAO.getInstance();

            int result = dao.delete(Integer.parseInt(idx));
            // �Խù� ���� ���� �Ǻ�
            if (result == 0) { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "�Խù��� ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "�Խù��� �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        public void showAdminInfo() {
            // Ŭ���� �࿡ ���� ��� ���� �����ͼ� ���� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
            int row = table.getSelectedRow();

            tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String Ÿ������ ����ȯ
            tfName.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String Ÿ������ ����ȯ
            Click_keyword= table.getValueAt(row, 1).toString();
//            tfId.setText((String) table.getValueAt(row, 2)); // Object(String) -> String Ÿ������ ����ȯ
//            tfPassword.setText((String) table.getValueAt(row, 3)); // Object(String) -> String Ÿ������ ����ȯ
        }
    }

    ///
    /// �Ű� ���� ���� ȭ��
    ///

    public class ReportGUI extends JPanel {
        private JTextField tfIdx, tfDate, tfSum;
        private JTable table;
        private JPanel pSouth;
        private JPanel pWest;
        JButton btnDelete;
        JButton btnBack;
        String Post_name ="";
        int mode = 0;
        public ReportGUI() {
        }

        public void showFrame(String Post_name, int mode) {
        	removeAll();
        	this.mode = mode;
        	this.Post_name = Post_name;
            setLayout(new BorderLayout());

            // ================= �ϴ� ��ư �г� ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);
            btnDelete = new JButton("�Ű� ���� ����(�Ű� ���)");
            btnBack = new JButton("�ڷ� ����");

            pSouth.add(btnDelete);
            pSouth.add(btnBack);

//            btnUpdate.addMouseListener(new MouseAdapter() {
//                public void mousePressed(MouseEvent e) {
//                    update();
//                }
//            });
            btnDelete.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    delete();
                }
            });
            btnBack.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    ChangePanel("Main");
                }
            });

            // ================= ���� ���� ���� �Է� �г� ==================
            pWest = new JPanel();
            add(pWest, BorderLayout.LINE_START);
            // �г� 5�� �� ���� ���� GridLayout(5, 1) ����
            pWest.setLayout(new GridLayout(5, 1));

            // �� �ະ�� �Է� �׸� ���� JLabel + JTextField �� �г� ����
            JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pIdx);

            pIdx.add(new JLabel("��   ȣ"));
            tfIdx = new JTextField(10);
            tfIdx.setEditable(false); // �ؽ�Ʈ�ʵ� ���� �Ұ� ����
            pIdx.add(tfIdx);

            // ================= �߾� ���� ���� ��� �г� ==================
            // ��ũ�ѹ� ����� ���� JScrollPane ��ü�� �����Ͽ� Center ������ ����
            JScrollPane scrollPane = new JScrollPane();
            add(scrollPane, BorderLayout.CENTER);

            // JTable ��ü�� �����Ͽ� JScrollPane �� ViewPort ������ ����
            table = new JTable();
            table.getTableHeader().setReorderingAllowed(false); // �� �̵� �Ұ� ����
            scrollPane.setViewportView(table);

            // ���̺� �÷��� ǥ�ø� ���� Vector ��ü�� �÷����� ������ �� DefaultTableModel ��ü�� �߰�
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("�Ű��ȣ");
            columnNames.add("�Ű���");
            columnNames.add("�Խñ� �̸�("+Post_name+")");
            columnNames.add("��� ����(�Խñ� ������ Ȯ�κҰ�)");
            columnNames.add("�Ű� ����");

//			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
            DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // �� ���� �Ұ����ϵ��� ����
                }

            };

            // JTable �� DefaultTableModel ��ü �߰�
            table.setModel(dtm);

            // ���̺� ���� ���ڵ� �Ǵ� �÷� Ŭ�� �� �̺�Ʈ ó��
            table.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // ���̺��� ���콺�� Ŭ���� ��� ȣ��Ǵ� �޼���
                    // ���õ� �÷��� ��, �� ��ȣ ���
//					System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

                    // ���õ� �÷��� ������ ���
//					System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

                    showReportInfo(); // ���õ� ���� ��� �÷� �����͸� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
                }
            });
            if (mode == 0) {
            	select_post();
            }else {
            	select_review(Integer.toString(mode));
            }
            
            setVisible(true);
        }

        // ���� ����
        public void insert() {

            String date = null;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            date = format1.format(time);

            // �Է� �׸� üũ
            if (date == null) {
                JOptionPane.showMessageDialog(rootPane, "��¥ �Է� ����!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
//            TotalDTO dto = new TotalDTO(date, Static.total);
//
//            TotalDAO dao = TotalDAO.getInstance();
//            int result = dao.insert(dto); // �������� �� ����� ����
//
//            // ���� �߰� ���� �Ǻ�
//            if (result == 0) { // �������� ���
//                JOptionPane.showMessageDialog(rootPane, "���⸦ ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
//                return;
//            } else { // �������� ���
//                JOptionPane.showMessageDialog(rootPane, "���⸦ �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
//                dto = new TotalDTO(0, date, 0);
//            }
        }

        // ���� ����
        public void update() {
            if (table.getSelectedRow() == -1) { // ���̺� �� ���� �ȵ��� ��� -1 ���ϵ�
                return;
            }

            // ���̺� �� �������� ��� â �� ������ �����Ͽ� ���õ� ��� ���� ǥ��
            JFrame editFrame = new JFrame("Update"); // �� ������ ����
            // ��ġ ���� �� ���� �θ� �������� ��ġ ��ǥ ���� �޾Ƽ� ���(doubleŸ���̹Ƿ� int�� ����ȯ)
            editFrame.setBounds(800, 200, 250, 300);
            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ���� �����Ӹ� ����

            JPanel pWest2 = new JPanel();
            editFrame.add(pWest2, BorderLayout.CENTER);
            // �г� 5�� �� ���� ���� GridLayout(5, 1) ����
            pWest2.setLayout(new GridLayout(5, 1));

            // �� �ະ�� �Է� �׸� ���� JLabel + JTextField �� �г� ����
            JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pIdx);

            pIdx.add(new JLabel("��   ȣ"));
            JTextField tfIdx2 = new JTextField(10);
            tfIdx2.setText(tfIdx.getText());
            tfIdx2.setHorizontalAlignment(tfIdx2.CENTER);
            tfIdx2.setEditable(false); // �ؽ�Ʈ�ʵ� ���� �Ұ� ����
            pIdx.add(tfIdx2);

            JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pDate);

            pDate.add(new JLabel("��   ¥"));
            JTextField tfName2 = new JTextField(10);
            pDate.add(tfName2);

            JPanel pId2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pId2);

            pId2.add(new JLabel("��   ��"));
            JTextField tfSum = new JTextField(10);
            pId2.add(tfSum);

            JPanel pSouth2 = new JPanel();
            editFrame.add(pSouth2, BorderLayout.SOUTH);

            JButton btnUpdate2 = new JButton("����");
            JButton btnCancel2 = new JButton("���");

            pSouth2.add(btnUpdate2);
            pSouth2.add(btnCancel2);

            // ��ư �� �� �����ϴ� ������
            ActionListener btnListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnUpdate2) {
                        // �Է� �׸� üũ
                        if (tfName2.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "��¥ �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                            tfName2.requestFocus();
                            return;
                        } else if (tfSum.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "���� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                            tfSum.requestFocus();
                            return;
                        }
//                        TotalDTO dto = new TotalDTO(Integer.parseInt(tfIdx.getText()), tfName2.getText(),
//                                Integer.parseInt(tfSum.getText()));
//                        TotalDAO dao = TotalDAO.getInstance();
//                        int result = dao.update(dto); // ���� ���� �� ����� ����

                        // ���� ���� ���� �Ǻ�
//                        if (result == 0) { // �������� ���
//                            JOptionPane.showMessageDialog(rootPane, "������ ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        } else { // �������� ���
//                            JOptionPane.showMessageDialog(rootPane, "������ �����Ͽ����ϴ�.", "����",
//                                    JOptionPane.INFORMATION_MESSAGE);
//                            editFrame.setVisible(false);
//                        }
                    } else if (e.getSource() == btnCancel2) {
                        editFrame.setVisible(false);
                    }
                }
            };
            // ��ư ������ ���� ����
            btnUpdate2.addActionListener(btnListener);
            btnCancel2.addActionListener(btnListener);

            editFrame.setVisible(true);
        }

        // ���� ��� ��ȸ
        public void select_post() {
        	ReportDAO dao = ReportDAO.getInstance();

            // �Ű� ��� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
            Vector<Vector> data = dao.select_post(Post_name);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // ������ ����(���� �׸���)
        }
        
        public void select_review(String review_num) {
        	ReportDAO dao = ReportDAO.getInstance();

            // �Ű� ��� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
            Vector<Vector> data = dao.select_review(review_num);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // ������ ����(���� �׸���)
        }

        // ���� ��� ����
        public void delete() {
        	// InputDialog ����Ͽ� ������ ��� ��ȣ �Է¹ޱ�
            String idx = JOptionPane.showInputDialog(rootPane, "������ �Ű� ���� ��ȣ�� �Է��ϼ���.");

            while (idx == null || idx.length() == 0) {
                // ���(null) Ŭ�� �Ǵ� �ƹ��͵� �Է����� �ʰ� Ȯ�� Ŭ�� ��
                if (idx == null) { // ��� ��ư Ŭ������ ��� �ƹ� ���� X
                    return; // ���� �޼��� ����
                }

                // �ƹ� ��ȣ�� �Է����� �ʰ�(�ν�Ʈ���� ����) Ȯ�� ��ư Ŭ������ ��� �޼��� ǥ��
                JOptionPane.showMessageDialog(rootPane, "��ȣ �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);

                // �ٽ� �Է¹ޱ�
                idx = JOptionPane.showInputDialog(rootPane, "������ �Ű� ���� ��ȣ�� �Է��ϼ���.");
            }

            // ������ ��ȣ�� �Է��� ���
            // => ����ǥ������ ����Ͽ� ��ȣ�� �Է¹޵��� ó���� �� �ִ�!
            // => \\d : ����, {1,} : ��Ģ�� 1�� �̻� �ݺ� => \\d{1,} : ���� 1�ڸ� �̻�
            if (!Pattern.matches("\\d{1,}", idx)) {
                JOptionPane.showMessageDialog(rootPane, "���� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ReportDAO dao = ReportDAO.getInstance();

            int result = dao.delete(idx);
            // ��� ���� ���� �Ǻ�
            if (result == 0) { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "�Ű� ������ ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "�Ű� ������ �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                if (mode == 0) {
                	select_post();
                }else {
                	select_review(Integer.toString(mode));
                }
                
            }

        }

        public void showReportInfo() {
            // Ŭ���� �࿡ ���� ��� ���� �����ͼ� ���� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
            int row = table.getSelectedRow();

            tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String Ÿ������ ����ȯ
        }

    }

    ///
    /// ��۰��� ȭ��
    ///

    public class PostDetailGUI extends JPanel {
        private JTextField tfIdx, tfpNum, tfPoint;
        private JTable table;
        private JPanel pSouth;
        private JPanel pWest;
        JButton btnInsert;
        JButton btnReport;
        JButton btnReport_list;
        JButton btnUpdate;
        JButton btnDelete;
        JButton btnBack;
        String Post_name;
        public PostDetailGUI() {
        }

        public void showFrame(String Post_name, int mode) {
        	removeAll();
        	this.Post_name = Post_name;
            setLayout(new BorderLayout());

            // ================= �ϴ� ��ư �г� ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);

            btnInsert = new JButton("��� �߰�");
            btnUpdate = new JButton("��� ����");
            btnDelete = new JButton("��� ����");
            btnReport = new JButton("�Ű� �ϱ�");
            btnReport_list = new JButton("�Ű� ����");
            btnBack = new JButton("�ڷΰ���");

            pSouth.add(btnInsert);
            pSouth.add(btnUpdate);
            pSouth.add(btnDelete);
            pSouth.add(btnReport);
            pSouth.add(btnReport_list);
            pSouth.add(btnBack);
            

            btnInsert.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    insert();
                }
            });
            btnUpdate.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    update();
                }
            });
            btnDelete.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    delete();
                }
            });
            btnReport.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    report();
                }
            });
            btnReport_list.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                	if (tfIdx.getText().equals("")) {
	            	} else {
	                    ChangePanel("Report");
	                	ReportGUI d = (ReportGUI)ReportFrame;
	                	d.showFrame(Post_name, Integer.parseInt(tfIdx.getText()));
	            	}
                }
            });
            btnBack.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    ChangePanel("Main");
                }
            });
            // ================= ���� ��� ���� �Է� �г� ==================
            pWest = new JPanel();
            add(pWest, BorderLayout.LINE_START);
            // �г� 5�� �� ���� ���� GridLayout(5, 1) ����
            pWest.setLayout(new GridLayout(5, 1));

            // �� �ະ�� �Է� �׸� ���� JLabel + JTextField �� �г� ����
            JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pIdx);

            pIdx.add(new JLabel("��   ȣ"));
            tfIdx = new JTextField(10);
            tfIdx.setEditable(false); // �ؽ�Ʈ�ʵ� ���� �Ұ� ����
            tfIdx.setText("");
            pIdx.add(tfIdx);

            JPanel ppNum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(ppNum);
            ppNum.add(new JLabel("�� ��"));
            tfpNum = new JTextField(10);
            ppNum.add(tfpNum);

            JPanel pPoint = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pPoint);
            pPoint.add(new JLabel("�� ��"));
            tfPoint = new JTextField(10);
            pPoint.add(tfPoint);

            // ================= �߾� ��� ���� ��� �г� ==================
            // ��ũ�ѹ� ����� ���� JScrollPane ��ü�� �����Ͽ� Center ������ ����
            JScrollPane scrollPane = new JScrollPane();
            add(scrollPane, BorderLayout.CENTER);

            // JTable ��ü�� �����Ͽ� JScrollPane �� ViewPort ������ ����
            table = new JTable();
            table.getTableHeader().setReorderingAllowed(false); // �� �̵� �Ұ� ����
            scrollPane.setViewportView(table);

            // ���̺� �÷��� ǥ�ø� ���� Vector ��ü�� �÷����� ������ �� DefaultTableModel ��ü�� �߰�
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("��۹�ȣ");
            columnNames.add(Post_name + " ��� ����");
            columnNames.add("�ۼ���");
            columnNames.add("����");
//			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
            DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // �� ���� �Ұ����ϵ��� ����
                }

            };

            // JTable �� DefaultTableModel ��ü �߰�
            table.setModel(dtm);

            // ���̺� ���� ���ڵ� �Ǵ� �÷� Ŭ�� �� �̺�Ʈ ó��
            table.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // ���̺��� ���콺�� Ŭ���� ��� ȣ��Ǵ� �޼���
                    // ���õ� �÷��� ��, �� ��ȣ ���
//					System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

                    // ���õ� �÷��� ������ ���
//					System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

                    showReviewInfo(); // ���õ� ���� ��� �÷� �����͸� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
                }
            });
            if(mode == 0) {
	            select();
	            up_count();
	            btnReport_list.setVisible(false);
            } else if (mode == 1) {
            	select_report();
            	btnInsert.setVisible(false);
            	btnUpdate.setVisible(false);
            	btnDelete.setVisible(false);
	            btnReport.setVisible(false);
            	btnReport_list.setVisible(true);
            } else if (mode == 2) {
	            select();
	            up_count();
	            btnReport_list.setVisible(true);
            }
            setVisible(true);
        }
        //��ȸ�� ����
        public void up_count() {
        	ReviewDAO dao = ReviewDAO.getInstance();
            int result = dao.up_count(Post_name);
        }
        // ��� �� ����Ʈ �߰�
        public void insert() {

            // �Է� �׸� üũ
            if (tfpNum.getText().length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "����� �ۼ����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tfPoint.getText().length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "������ �ۼ����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("\\d{1,1}", tfPoint.getText())) {
                JOptionPane.showMessageDialog(rootPane, "������ 1~5 ���� �Է�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                return;
            }

        	if(Integer.parseInt(tfPoint.getText()) > 5) {
        		tfPoint.setText("5");
        	}
        	else if (Integer.parseInt(tfPoint.getText()) < 0) {
        		tfPoint.setText("0");
        	}
        	
            ReviewDTO dto = new ReviewDTO(tfpNum.getText(), Integer.parseInt(tfPoint.getText()), MainGUI.Login_ID);
            ReviewDAO dao = ReviewDAO.getInstance();

            int result = dao.insert(dto, Post_name); // ����߰� �� ����� ����

            // ��� �߰� ���� �Ǻ�
            if (result == 0) { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "����� �߰��� �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(result == -1) {
            	JOptionPane.showMessageDialog(rootPane, "����� �̹� �ۼ��ϼ̽��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else if(result == -2) {
            	JOptionPane.showMessageDialog(rootPane, "�α����� ���ּ���.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "����� �߰��Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                Static.pNum = null;
                select();
            }
        }

        // ��� ����
        public void update() {
        	if (tfpNum.getText().length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "������ ����� �ۼ����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tfPoint.getText().length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "������ ������ �ۼ����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("\\d{1,1}", tfPoint.getText())) {
                JOptionPane.showMessageDialog(rootPane, "������ 1~5 ���� �Է�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(Integer.parseInt(tfPoint.getText()) > 5) {
        		tfPoint.setText("5");
        	}
        	else if (Integer.parseInt(tfPoint.getText()) < 0) {
        		tfPoint.setText("0");
        	}
        	
            ReviewDTO dto = new ReviewDTO(tfpNum.getText(), Integer.parseInt(tfPoint.getText()), MainGUI.Login_ID);
            ReviewDAO dao = ReviewDAO.getInstance();

            int result = dao.update(dto, Post_name); // ����߰� �� ����� ����

            // ��� �߰� ���� �Ǻ�
            if (result == 0) { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "����� ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(result == -1) {
            	JOptionPane.showMessageDialog(rootPane, "�Է��� ����� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else if(result == -2) {
            	JOptionPane.showMessageDialog(rootPane, "�α����� ���ּ���.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "����� �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                Static.pNum = null;
                select();
            }
        }

        // �Ű� ��� ��� ��ȸ
        public void select() {
            ReviewDAO dao = ReviewDAO.getInstance();

            // �Ű� ��� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
            Vector<Vector> data = dao.select(Post_name);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // ������ ����(���� �׸���)
        }

        public void select_report() {
            ReviewDAO dao = ReviewDAO.getInstance();

            // �Ű� ��� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
            Vector<Vector> data = dao.select_report();

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // ������ ����(���� �׸���)
        }
        
        public void report(){
        	if (tfIdx.getText().equals("")) {
        		JOptionPane.showMessageDialog(rootPane, "�Ű��� ����� ������ �ּ���.", "�Ű� �Խñ� ����", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	String input = JOptionPane.showInputDialog(rootPane, "�Ű� ������ �ۼ��� �ּ���.", "�Ű� ��ȣ " + tfIdx.getText(),  JOptionPane.WARNING_MESSAGE);
            if(input != null) {
            	if(input.equals("")) {
            		JOptionPane.showMessageDialog(rootPane, "������ �����ϴ�!", "�Ű� ���", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	ReportDAO dao = ReportDAO.getInstance();
            	ReportDTO dto = new ReportDTO(Login_ID,Post_name,input,tfIdx.getText());
            	int result = dao.insert_review(dto);
            	if (result == 0) { // �������� ���
                    JOptionPane.showMessageDialog(rootPane, "�Ű� ���� �߻�.", "����", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(result == -1) {
                	JOptionPane.showMessageDialog(rootPane, "�̹� �Ű� �ϼ̽��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(result == -2) {
                	JOptionPane.showMessageDialog(rootPane, "�α����� ���ּ���.", "����", JOptionPane.ERROR_MESSAGE);
                    return;
                } else { // �������� ���
                    JOptionPane.showMessageDialog(rootPane, "�Ű� ��ȣ "+ tfIdx.getText() + "��(��) �Ű��Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                    Static.pNum = null;
                }
            }
            else {
            	return;
            }
        }
        
        // �Ű� ��� ����
        public void delete() {

            // InputDialog ����Ͽ� ������ ��� ��ȣ �Է¹ޱ�
            String idx = JOptionPane.showInputDialog(rootPane, "������ ��� ��ȣ�� �Է��ϼ���.");
//			System.out.println(idx);

            while (idx == null || idx.length() == 0) {
                // ���(null) Ŭ�� �Ǵ� �ƹ��͵� �Է����� �ʰ� Ȯ�� Ŭ�� ��
                if (idx == null) { // ��� ��ư Ŭ������ ��� �ƹ� ���� X
                    return; // ���� �޼��� ����
                }

                // �ƹ� ��ȣ�� �Է����� �ʰ�(�ν�Ʈ���� ����) Ȯ�� ��ư Ŭ������ ��� �޼��� ǥ��
                JOptionPane.showMessageDialog(rootPane, "��ȣ �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);

                // �ٽ� �Է¹ޱ�
                idx = JOptionPane.showInputDialog(rootPane, "������ ��� ��ȣ�� �Է��ϼ���.");
            }

            // ������ ��ȣ�� �Է��� ���
            // => ����ǥ������ ����Ͽ� ��ȣ�� �Է¹޵��� ó���� �� �ִ�!
            // => \\d : ����, {1,} : ��Ģ�� 1�� �̻� �ݺ� => \\d{1,} : ���� 1�ڸ� �̻�
            if (!Pattern.matches("\\d{1,}", idx)) {
                JOptionPane.showMessageDialog(rootPane, "���� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ReviewDAO dao = ReviewDAO.getInstance();

            int result = dao.delete(idx, MainGUI.Login_ID);
            // ��� ���� ���� �Ǻ�
            if (result == 0) { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "����� ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "��۸� �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                select();
            }

        }

        public void showReviewInfo() {
            // Ŭ���� �࿡ ���� ��� ���� �����ͼ� ���� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
            int row = table.getSelectedRow();

            tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String Ÿ������ ����ȯ
            tfpNum.setText("");
            tfPoint.setText("");
//            tfpNum.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String Ÿ������ ����ȯ
//            tfPoint.setText((String) table.getValueAt(row, 2)); // Object(double) -> String Ÿ������ ����ȯ
        }
    }

    ///
    /// �뷡 ���� ȭ��
    ///

    public class SongGUI extends JPanel {
        private JTextField tfIdx, tfDate, tfSum;
        private JTable table;
        private JPanel pSouth;
        private JPanel pWest;
        JButton btnSelect;
        JButton btnUp;
        JButton btnDown;
        JButton btnBack;

        public SongGUI() {
            showFrame();
        }

        public void showFrame() {
            setLayout(new BorderLayout());

            // ================= �ϴ� ��ư �г� ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);

            btnSelect = new JButton("�뷡 ���");
            btnUp = new JButton("��");
            btnDown = new JButton("��");
            btnBack = new JButton("�ڷ� ����");

            pSouth.add(btnSelect);
            pSouth.add(btnUp);
            pSouth.add(btnDown);
            pSouth.add(btnBack);

            btnSelect.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    select();
                }
            });
            btnUp.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
//                    up();
                }
            });
            btnDown.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
//                    down();
                }
            });
            btnBack.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    ChangePanel("Main");
                }
            });

            // ================= ���� ���� ���� �Է� �г� ==================
//			pWest = new JPanel();
//			add(pWest, BorderLayout.LINE_START);
//			// �г� 5�� �� ���� ���� GridLayout(5, 1) ����
//			pWest.setLayout(new GridLayout(5, 1));
//
//			// �� �ະ�� �Է� �׸� ���� JLabel + JTextField �� �г� ����
//			JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//			pWest.add(pIdx);
//
//			pIdx.add(new JLabel("��   ȣ"));
//			tfIdx = new JTextField(10);
//			tfIdx.setEditable(false); // �ؽ�Ʈ�ʵ� ���� �Ұ� ����
//			pIdx.add(tfIdx);
//
//			JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//			pWest.add(pDate);
//			pDate.add(new JLabel("��   ¥"));
//			tfDate = new JTextField(10);
//			pDate.add(tfDate);
//
//			JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//			pWest.add(pSum);
//			pSum.add(new JLabel("��   ��"));
//			tfSum = new JTextField(10);
//			pSum.add(tfSum);

            // ================= �߾� ���� ���� ��� �г� ==================
            // ��ũ�ѹ� ����� ���� JScrollPane ��ü�� �����Ͽ� Center ������ ����
            JScrollPane scrollPane = new JScrollPane();
            add(scrollPane, BorderLayout.CENTER);

            // JTable ��ü�� �����Ͽ� JScrollPane �� ViewPort ������ ����
            table = new JTable();
            table.getTableHeader().setReorderingAllowed(false); // �� �̵� �Ұ� ����
            scrollPane.setViewportView(table);

            // ���̺� �÷��� ǥ�ø� ���� Vector ��ü�� �÷����� ������ �� DefaultTableModel ��ü�� �߰�
            String[] columnNames = { "����", "����", "�뷡" };

//			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
            DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // �� ���� �Ұ����ϵ��� ����
                }

            };

            // JTable �� DefaultTableModel ��ü �߰�
            table.setModel(dtm);

            setVisible(true);
        }

        // ���� ��� ��ȸ
        public void select() {

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

//            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
//            for (int i = 0; i < Static.trackList.size(); i++) {
//                StringTokenizer st = new StringTokenizer(Static.trackList.get(i).getListMusic(), "-");
//                String num = "" + (i + 1);
//                String Singer = st.nextToken().trim();
//                String Song = st.nextToken().trim();
//                dtm.addRow(new String[] { num, Singer, Song });
//            }
            invalidate(); // ������ ����(���� �׸���)
        }

//        public void up() {
//            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
//            int row = table.getSelectedRow();
//            String temp = new String();
//            if (row < Static.trackList.size() && row > 0) {
//                for (int i = 1; i < 3; i++) {
//                    temp = (String) table.getValueAt(row, i);
//                    table.setValueAt(table.getValueAt(row - 1, i), row, i);
//                    table.setValueAt(temp, row - 1, i);
//                }
//                table.setRowSelectionInterval(row - 1, row - 1);
//                Collections.swap(Static.trackList, row - 1, row);
//            }
//            invalidate(); // ������ ����(���� �׸���)
//        }

//        public void down() {
//            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
//            int row = table.getSelectedRow();
//            String temp = new String();
//            if (row < Static.trackList.size() - 1 && row >= 0) {
//                for (int i = 1; i < 3; i++) {
//                    temp = (String) table.getValueAt(row, i);
//                    table.setValueAt(table.getValueAt(row + 1, i), row, i);
//                    table.setValueAt(temp, row + 1, i);
//                }
//                table.setRowSelectionInterval(row + 1, row + 1);
//                Collections.swap(Static.trackList, row + 1, row);
//            }
//            invalidate(); // ������ ����(���� �׸���)
//        }

    }

    public void ChangePanel(String S) {
        cards.show(this.getContentPane(), S);
    }
}
class Static {
    static int total = 0;
    static String playingMusic;
    static String pNum;
    static int n; // �뷡 �ε���
//    static ArrayList<Track> trackListAll;
//    static ArrayList<Track> trackList;
}