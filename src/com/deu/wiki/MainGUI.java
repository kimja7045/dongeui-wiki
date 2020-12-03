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
    private JPanel AdminFrame = null;
    private JPanel UserFrame = null;
    private JPanel TotalFrame = null;
    private JPanel SongFrame = null;
    private JPanel PostDetailFrame = null;
    private CardLayout cards = new CardLayout();

    public MainGUI() {
        setTitle("������Ű");
        setBounds(500, 300, 1280, 720);
        setLocationRelativeTo(null); // ���α׷��� ���߾ӿ� ��
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(cards);
        AdminFrame = new showFrame();
        TotalFrame = new TotalGUI();
        UserFrame = new UserGUI();
        SongFrame = new SongGUI();
        PostDetailFrame = new PostDetailGUI(cards, this.getContentPane());
        add("Admin", AdminFrame);
        add("Total", TotalFrame);
        add("User", UserFrame);
        add("PostDetail", PostDetailFrame);
        add("Song", SongFrame);
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
        JButton btnTotal;
        JButton btnUser;
        JButton btnReport;
        JButton btnSong;
        JButton btnPostDetail;

        JButton btnUpdate2;
        JButton btnCancel2;

        boolean isLogin; // �α��� ���� ǥ���� ����
        private String name2, password2, id2;

        public showFrame() {
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
            btnReport = new JButton("�Խù� �Ű�");

            btnPostDetail = new JButton("�Խñ� �� ȭ��");
            btnTotal = new JButton("�Ű� �Խù� ����");     // ���� ����
            btnUser = new JButton("�Ű� ��� ����");

//            btnSong = new JButton("�뷡 ����");

            pSouth.add(btnSearch);
            pSouth.add(btnInsert);
            pSouth.add(btnUpdate);
            pSouth.add(btnDelete);
            pSouth.add(btnSelect);
            pSouth.add(btnReport);
            pSouth.add(btnPostDetail);
            pSouth.add(btnTotal);
            pSouth.add(btnUser);

//            pSouth.add(btnSong);

            btnReport.setVisible(false);
            btnInsert.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            btnTotal.setVisible(false);
            btnPostDetail.setVisible(false);
            btnUser.setVisible(false);
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
                            } else if (e.getSource() == btnSelect) {
                                select();
                            } else if (e.getSource() == btnDelete) {
                                delete();
                            } else if (e.getSource() == btnUpdate) {
                                update();
                            } else if (e.getSource() == btnSearch) {
                                search();
                            } else if (e.getSource() == btnReport){
                                report();
                            }
                        }
                    };

                    // ��ư ������ ���� ����
                    btnInsert.addActionListener(btnListener);
                    btnSelect.addActionListener(btnListener);
                    btnDelete.addActionListener(btnListener);
                    btnUpdate.addActionListener(btnListener);
                }
            });
            btnTotal.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ChangePanel("Total");
                }
            });
            btnPostDetail.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ChangePanel("PostDetail");
                }
            });
            btnUser.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ChangePanel("User");
                }
            });
//            btnSong.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mousePressed(MouseEvent e) {
//                    ChangePanel("Song");
//                }
//            });
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
            tfName.setEditable(false);
            pName.add(tfName);


            JPanel pId = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pId);
            pId.add(new JLabel("�� ��"));
            tfId = new JTextField(10);
            tfId.setEditable(false);
            pId.add(tfId);

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
            columnNames.add("����");
            columnNames.add("��ȸ ��");
            columnNames.add("��� ��");


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

//                AdminDTO dto = new AdminDTO(username, password);
//                AdminDAO dao = AdminDAO.getInstance();
//                int result = dao.login(dto);
                int result = 1;
                if (result == 0) { // ���̵� ���� ���
                    JOptionPane.showMessageDialog(rootPane, "�������� �ʴ� �����Դϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
                    tfDbUsername.requestFocus();
                    return;
                } else if (result == -1) { // �н����尡 Ʋ���� ���
                    JOptionPane.showMessageDialog(rootPane, "�н����尡 ��ġ���� �ʽ��ϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
                    pfDbPassword.requestFocus();
                    return;
                }

                // �α��� �������� ���
//                tfDbIp.setEditable(false);
                tfDbUsername.setEditable(false);
                pfDbPassword.setEditable(false);
                btnLogin.setText("�α׾ƿ�");
                if (username.equals("admin")) {
//                    tfName.setVisible(true);
//                    tfId.setVisible(true);
                	   tfName.setEditable(true);
                       tfId.setEditable(true);
//                    tfPassword.setVisible(true);

                    btnReport.setVisible(true);
                    btnInsert.setVisible(true);
//                    btnUpdate.setVisible(true);
//                    btnDelete.setVisible(true);
                    btnTotal.setVisible(true); //�Ű�Խñ�
                    btnPostDetail.setVisible(true);
                    table.setVisible(true);
                    btnUser.setVisible(true);  //�Ű���
//                    btnSong.setVisible(true);
                } else {
                    btnPostDetail.setVisible(true);
                    btnReport.setVisible(true);
                	btnInsert.setVisible(true);
//                    btnTotal.setVisible(true); //�Ű�Խñ�
//                    btnUser.setVisible(true);  // �Ű���
//                    btnSong.setVisible(true);
                }
                isLogin = true; // �α��� ���·� ����
            } else { // �α��� ���¿��� �α׾ƿ� ��ư�� Ŭ������ ���
//                tfDbIp.setEditable(true);
                tfDbUsername.setEditable(true);
                pfDbPassword.setEditable(true);
                tfName.setEditable(false);
                tfId.setEditable(false);
                tfDbUsername.setText("");
                pfDbPassword.setText("");
                btnReport.setVisible(false);
                btnPostDetail.setVisible(false);
                btnLogin.setText("�α���");
                btnInsert.setVisible(false);
                btnUpdate.setVisible(false);
                btnDelete.setVisible(false);
                btnTotal.setVisible(false);
                table.setVisible(false);
                btnUser.setVisible(false);
//                btnSong.setVisible(false);
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
            String content = tfId.getText();
//            String password = tfPassword.getText();

            // �Է� �׸� üũ
            if (title.length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Ű���� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                tfName.requestFocus();
                return;
            } else if (content.length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "���� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                tfId.requestFocus();
                return;
            } 
//            else if (password.length() == 0) {
//                JOptionPane.showMessageDialog(rootPane, "�н����� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
//                tfPassword.requestFocus();
//                return;
//            }

            AdminDTO dto = new AdminDTO(title, content);
            AdminDAO dao = AdminDAO.getInstance();
            int result = dao.insert(dto); // �Խù� �߰� �� ����� ����

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
                        AdminDTO dto = new AdminDTO(Integer.parseInt(tfIdx2.getText()), tfName2.getText(),
                                tfId2.getText(), tfPassword2.getText());
                        AdminDAO dao = AdminDAO.getInstance();
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

        }

        public void report(){

        }
        // �Խù� ���
        public void select() {
//			if(!isLogin) {
//				JOptionPane.showMessageDialog(
//						rootPane, "�α��� �ʿ�", "�α��� ����", JOptionPane.ERROR_MESSAGE);
//				tfDbUsername.requestFocus();
//				return;
//			}

        	AdminDAO dao = AdminDAO.getInstance();
            // �Խù� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
            Vector<Vector> data = dao.select();

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

            AdminDAO dao = AdminDAO.getInstance();

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
            tfId.setText((String) table.getValueAt(row, 2)); // Object(String) -> String Ÿ������ ����ȯ
//            tfPassword.setText((String) table.getValueAt(row, 3)); // Object(String) -> String Ÿ������ ����ȯ
        }
    }

    ///
    /// ������� ȭ��
    ///

    public class TotalGUI extends JPanel {
        private JTextField tfIdx, tfDate, tfSum;
        private JTable table;
        private JPanel pSouth;
        private JPanel pWest;
        JButton btnInsert;
        JButton btnSelect;
        JButton btnUpdate;
        JButton btnDelete;
        JButton btnBack;

        public TotalGUI() {
            showFrame();
        }

        public void showFrame() {
            setLayout(new BorderLayout());

            // ================= �ϴ� ��ư �г� ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);

            btnInsert = new JButton("���� ����");
//            btnUpdate = new JButton("�Ű� �Խñ� ����");
            btnDelete = new JButton("�Ű� �Խñ� ����");
            btnSelect = new JButton("�Ű� �Խñ� ���");
            btnBack = new JButton("�ڷ� ����");

            pSouth.add(btnInsert);
//            pSouth.add(btnUpdate);
            pSouth.add(btnDelete);
            pSouth.add(btnSelect);
            pSouth.add(btnBack);

            btnInsert.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    insert();
                }
            });
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
            btnSelect.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    select();
                }
            });
            btnBack.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    ChangePanel("Admin");
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

            JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pDate);
            pDate.add(new JLabel("Ű �� ��"));
            tfDate = new JTextField(10);
            pDate.add(tfDate);

            JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pSum);
            pSum.add(new JLabel("��   ��"));
            tfSum = new JTextField(10);
            pSum.add(tfSum);

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
            columnNames.add("��ȣ");
            columnNames.add("Ű����");
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

                    showTotalInfo(); // ���õ� ���� ��� �÷� �����͸� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
                }
            });
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
        public void select() {
//            TotalDAO dao = TotalDAO.getInstance();
//            // ���� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
//            Vector<Vector> data = dao.select();

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//			 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
            // => ��, addRow() ���� �޼��尡 ����

            dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

            // Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
//            for (Vector rowData : data) {
//                dtm.addRow(rowData);
//            }
            invalidate(); // ������ ����(���� �׸���)
        }

        // ���� ��� ����
        public void delete() {

            // InputDialog ����Ͽ� ������ ���� ��ȣ �Է¹ޱ�
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
//
//            TotalDAO dao = TotalDAO.getInstance();
//
//            int result = dao.delete(Integer.parseInt(idx));
//            // ���� ���� ���� �Ǻ�
//            if (result == 0) { // �������� ���
//                JOptionPane.showMessageDialog(rootPane, "���⸦ ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
//                return;
//            } else { // �������� ���
//                JOptionPane.showMessageDialog(rootPane, "���⸦ �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
//            }

        }

        public void showTotalInfo() {
            // Ŭ���� �࿡ ���� ��� ���� �����ͼ� ���� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
            int row = table.getSelectedRow();

            tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String Ÿ������ ����ȯ
            tfDate.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String Ÿ������ ����ȯ
            tfSum.setText((String) table.getValueAt(row, 2)); // Object(double) -> String Ÿ������ ����ȯ
        }

    }

    ///
    /// ��۰��� ȭ��
    ///

    public class UserGUI extends JPanel {
        private JTextField tfIdx, tfpNum, tfPoint;
        private JTable table;
        private JPanel pSouth;
        private JPanel pWest;
        JButton btnInsert;
        JButton btnSelect;
        JButton btnUpdate;
        JButton btnDelete;
        JButton btnBack;

        public UserGUI() {
            showFrame();
        }

        public void showFrame() {
            setLayout(new BorderLayout());

            // ================= �ϴ� ��ư �г� ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);

            btnInsert = new JButton("�Ű� ��� �߰�");
//            btnUpdate = new JButton("�Ű� ��� ����");
            btnDelete = new JButton("�Ű� ��� ����");
            btnSelect = new JButton("�Ű� ��� ���");
            btnBack = new JButton("�ڷΰ���");

            pSouth.add(btnInsert);
//            pSouth.add(btnUpdate);
            pSouth.add(btnDelete);
            pSouth.add(btnSelect);
            pSouth.add(btnBack);

            btnInsert.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    insert();
                }
            });
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
            btnSelect.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    select();
                }
            });
            btnBack.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    ChangePanel("Admin");
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

            JPanel ppNum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(ppNum);
            ppNum.add(new JLabel("Ű �� ��"));
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
            columnNames.add("��ȣ");
            columnNames.add("Ű����");
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

                    showUserInfo(); // ���õ� ���� ��� �÷� �����͸� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
                }
            });
            setVisible(true);
        }

        // ��� �� ����Ʈ �߰�
        public void insert() {

            // �Է� �׸� üũ
            if (tfpNum.getText() == null) {
                JOptionPane.showMessageDialog(rootPane, "��ȣ ����!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ReviewDTO dto = new ReviewDTO(tfpNum.getText());
            ReviewDAO dao = ReviewDAO.getInstance();

            int result = dao.insert(dto); // ����߰� �� ����� ����

            // ��� �߰� ���� �Ǻ�
            if (result == 0) { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "����� �߰��� �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "����� �߰��Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
                Static.pNum = null;
            }
        }

        // ��� ����
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

            pDate.add(new JLabel("�޴��� ��ȣ"));
            JTextField tfName2 = new JTextField(10);
            pDate.add(tfName2);

            JPanel pId2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pId2);

            pId2.add(new JLabel("�� �� Ʈ"));
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
                            JOptionPane.showMessageDialog(rootPane, "�޴��� ��ȣ �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                            tfName2.requestFocus();
                            return;
                        } else if (tfSum.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "����Ʈ �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                            tfSum.requestFocus();
                            return;
                        }
                        ReviewDTO dto = new ReviewDTO(Integer.parseInt(tfIdx.getText()), tfName2.getText(),
                                Integer.parseInt(tfSum.getText()));
                        ReviewDAO dao = ReviewDAO.getInstance();
                        int result = dao.update(dto); // ��� ���� �� ����� ����

                        // ��� ���� ���� �Ǻ�
                        if (result == 0) { // �������� ���
                            JOptionPane.showMessageDialog(rootPane, "����� ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else { // �������� ���
                            JOptionPane.showMessageDialog(rootPane, "����� �����Ͽ����ϴ�.", "����",
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

        // �Ű� ��� ��� ��ȸ
        public void select() {
            ReviewDAO dao = ReviewDAO.getInstance();

            // �Ű� ��� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
            Vector<Vector> data = dao.select();

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

            int result = dao.delete(Integer.parseInt(idx));
            // ��� ���� ���� �Ǻ�
            if (result == 0) { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "��۸� ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // �������� ���
                JOptionPane.showMessageDialog(rootPane, "��۸� �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        public void showUserInfo() {
            // Ŭ���� �࿡ ���� ��� ���� �����ͼ� ���� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
            int row = table.getSelectedRow();

            tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String Ÿ������ ����ȯ
            tfpNum.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String Ÿ������ ����ȯ
            tfPoint.setText((String) table.getValueAt(row, 2)); // Object(double) -> String Ÿ������ ����ȯ
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
                    ChangePanel("Admin");
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