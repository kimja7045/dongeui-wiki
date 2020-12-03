package com.deu.wiki;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

public class PostDetailGUI extends JPanel {
    private JTextField tfIdx, tfDate, tfSum;
    private JTable table;
    private JPanel pSouth;
    private JPanel pWest;
    JButton btnInsert;
    JButton btnSelect;
    JButton btnUpdate;
    JButton btnDelete;
    JButton btnBack;
    private CardLayout cards = new CardLayout();
    Container rootPane;

    public PostDetailGUI(CardLayout cards, Container getContentPane) {
        showFrame();
        this.cards = cards;
        this.rootPane = getContentPane;
    }

    public void ChangePanel(String S) {
        cards.show(rootPane, S);
    }

    public void showFrame() {
        setLayout(new BorderLayout());

        // ================= �ϴ� ��ư �г� ==================
        pSouth = new JPanel();
        add(pSouth, BorderLayout.PAGE_END);

        btnInsert = new JButton("��� �˻�");
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

    // ��� ����
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
//            int result = dao.insert(dto); // ������� �� ����� ����
//
//            // ��� �߰� ���� �Ǻ�
//            if (result == 0) { // �������� ���
//                JOptionPane.showMessageDialog(rootPane, "��۸� ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
//                return;
//            } else { // �������� ���
//                JOptionPane.showMessageDialog(rootPane, "��۸� �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
//                dto = new TotalDTO(0, date, 0);
//            }
    }

    // ��� ����
    public void update() {
        if (table.getSelectedRow() == -1) { // ���̺� �� ���� �ȵ��� ��� -1 ���ϵ�
            return;
        }

        // ���̺� �� �������� ��� â �� ������ �����Ͽ� ���õ� ȸ�� ���� ǥ��
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
                        JOptionPane.showMessageDialog(rootPane, "��� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                        tfSum.requestFocus();
                        return;
                    }
//                        TotalDTO dto = new TotalDTO(Integer.parseInt(tfIdx.getText()), tfName2.getText(),
//                                Integer.parseInt(tfSum.getText()));
//                        TotalDAO dao = TotalDAO.getInstance();
//                        int result = dao.update(dto); // ��� ���� �� ����� ����

                    // ��� ���� ���� �Ǻ�
//                        if (result == 0) { // �������� ���
//                            JOptionPane.showMessageDialog(rootPane, "����� ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        } else { // �������� ���
//                            JOptionPane.showMessageDialog(rootPane, "����� �����Ͽ����ϴ�.", "����",
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
//            // ��� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
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

    // ���� ����
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
//
//            TotalDAO dao = TotalDAO.getInstance();
//
//            int result = dao.delete(Integer.parseInt(idx));
//            // ��� ���� ���� �Ǻ�
//            if (result == 0) { // �������� ���
//                JOptionPane.showMessageDialog(rootPane, "��۸� ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
//                return;
//            } else { // �������� ���
//                JOptionPane.showMessageDialog(rootPane, "��۸� �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
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