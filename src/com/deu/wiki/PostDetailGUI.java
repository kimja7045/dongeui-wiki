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

        // ================= 하단 버튼 패널 ==================
        pSouth = new JPanel();
        add(pSouth, BorderLayout.PAGE_END);

        btnInsert = new JButton("댓글 검색");
//            btnUpdate = new JButton("신고 게시글 수정");
        btnDelete = new JButton("신고 게시글 삭제");
        btnSelect = new JButton("신고 게시글 목록");
        btnBack = new JButton("뒤로 가기");

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

        // ================= 좌측 댓글 정보 입력 패널 ==================
        pWest = new JPanel();
        add(pWest, BorderLayout.LINE_START);
        // 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
        pWest.setLayout(new GridLayout(5, 1));

        // 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
        JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pWest.add(pIdx);

        pIdx.add(new JLabel("번   호"));
        tfIdx = new JTextField(10);
        tfIdx.setEditable(false); // 텍스트필드 편집 불가 설정
        pIdx.add(tfIdx);

        JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pWest.add(pDate);
        pDate.add(new JLabel("키 워 드"));
        tfDate = new JTextField(10);
        pDate.add(tfDate);

        JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pWest.add(pSum);
        pSum.add(new JLabel("내   용"));
        tfSum = new JTextField(10);
        pSum.add(tfSum);

        // ================= 중앙 댓글 정보 출력 패널 ==================
        // 스크롤바 기능을 위해 JScrollPane 객체를 생성하여 Center 영역에 부착
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        // JTable 객체를 생성하여 JScrollPane 의 ViewPort 영역에 부착
        table = new JTable();
        table.getTableHeader().setReorderingAllowed(false); // 셀 이동 불가 설정
        scrollPane.setViewportView(table);

        // 테이블 컬럼명 표시를 위해 Vector 객체에 컬럼명을 저장한 후 DefaultTableModel 객체에 추가
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("번호");
        columnNames.add("키워드");
        columnNames.add("내용");

//			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 불가능하도록 설정
            }

        };

        // JTable 에 DefaultTableModel 객체 추가
        table.setModel(dtm);

        // 테이블 내의 레코드 또는 컬럼 클릭 시 이벤트 처리
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // 테이블을 마우스로 클릭할 경우 호출되는 메서드
                // 선택된 컬럼의 행, 열 번호 출력
//					System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

                // 선택된 컬럼의 데이터 출력
//					System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

                showTotalInfo(); // 선택된 행의 모든 컬럼 데이터를 WEST 영역 텍스트필드에 표시
            }
        });
        setVisible(true);
    }

    // 댓글 정산
    public void insert() {

        String date = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        date = format1.format(time);

        // 입력 항목 체크
        if (date == null) {
            JOptionPane.showMessageDialog(rootPane, "날짜 입력 오류!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
//            TotalDTO dto = new TotalDTO(date, Static.total);
//
//            TotalDAO dao = TotalDAO.getInstance();
//            int result = dao.insert(dto); // 댓글정산 후 결과값 리턴
//
//            // 댓글 추가 여부 판별
//            if (result == 0) { // 실패했을 경우
//                JOptionPane.showMessageDialog(rootPane, "댓글를 정산할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
//                return;
//            } else { // 성공했을 경우
//                JOptionPane.showMessageDialog(rootPane, "댓글를 정산하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
//                dto = new TotalDTO(0, date, 0);
//            }
    }

    // 댓글 수정
    public void update() {
        if (table.getSelectedRow() == -1) { // 테이블 셀 선택 안됐을 경우 -1 리턴됨
            return;
        }

        // 테이블 셀 선택했을 경우 창 새 프레임 생성하여 선택된 회원 정보 표시
        JFrame editFrame = new JFrame("Update"); // 새 프레임 생성
        // 위치 설정 시 기존 부모 프레임의 위치 좌표 값을 받아서 사용(double타입이므로 int형 형변환)
        editFrame.setBounds(800, 200, 250, 300);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재 프레임만 종료

        JPanel pWest2 = new JPanel();
        editFrame.add(pWest2, BorderLayout.CENTER);
        // 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
        pWest2.setLayout(new GridLayout(5, 1));

        // 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
        JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pWest2.add(pIdx);

        pIdx.add(new JLabel("번   호"));
        JTextField tfIdx2 = new JTextField(10);
        tfIdx2.setText(tfIdx.getText());
        tfIdx2.setHorizontalAlignment(tfIdx2.CENTER);
        tfIdx2.setEditable(false); // 텍스트필드 편집 불가 설정
        pIdx.add(tfIdx2);

        JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pWest2.add(pDate);

        pDate.add(new JLabel("날   짜"));
        JTextField tfName2 = new JTextField(10);
        pDate.add(tfName2);

        JPanel pId2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pWest2.add(pId2);

        pId2.add(new JLabel("매   출"));
        JTextField tfSum = new JTextField(10);
        pId2.add(tfSum);

        JPanel pSouth2 = new JPanel();
        editFrame.add(pSouth2, BorderLayout.SOUTH);

        JButton btnUpdate2 = new JButton("수정");
        JButton btnCancel2 = new JButton("취소");

        pSouth2.add(btnUpdate2);
        pSouth2.add(btnCancel2);

        // 버튼 두 개 구별하는 리스너
        ActionListener btnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnUpdate2) {
                    // 입력 항목 체크
                    if (tfName2.getText().length() == 0) {
                        JOptionPane.showMessageDialog(rootPane, "날짜 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                        tfName2.requestFocus();
                        return;
                    } else if (tfSum.getText().length() == 0) {
                        JOptionPane.showMessageDialog(rootPane, "댓글 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                        tfSum.requestFocus();
                        return;
                    }
//                        TotalDTO dto = new TotalDTO(Integer.parseInt(tfIdx.getText()), tfName2.getText(),
//                                Integer.parseInt(tfSum.getText()));
//                        TotalDAO dao = TotalDAO.getInstance();
//                        int result = dao.update(dto); // 댓글 수정 후 결과값 리턴

                    // 댓글 수정 여부 판별
//                        if (result == 0) { // 실패했을 경우
//                            JOptionPane.showMessageDialog(rootPane, "댓글을 수정할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        } else { // 성공했을 경우
//                            JOptionPane.showMessageDialog(rootPane, "댓글을 수정하였습니다.", "성공",
//                                    JOptionPane.INFORMATION_MESSAGE);
//                            editFrame.setVisible(false);
//                        }
                } else if (e.getSource() == btnCancel2) {
                    editFrame.setVisible(false);
                }
            }
        };
        // 버튼 리스너 동시 연결
        btnUpdate2.addActionListener(btnListener);
        btnCancel2.addActionListener(btnListener);

        editFrame.setVisible(true);
    }

    // 리뷰 목록 조회
    public void select() {
//            TotalDAO dao = TotalDAO.getInstance();
//            // 댓글 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
//            Vector<Vector> data = dao.select();

        DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
        // => 단, addRow() 등의 메서드가 없음

        dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

        // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
//            for (Vector rowData : data) {
//                dtm.addRow(rowData);
//            }
        invalidate(); // 프레임 갱신(새로 그리기)
    }

    // 리뷰 삭제
    public void delete() {

        // InputDialog 사용하여 삭제할 댓글 번호 입력받기
        String idx = JOptionPane.showInputDialog(rootPane, "삭제할 댓글 번호를 입력하세요.");
//			System.out.println(idx);

        while (idx == null || idx.length() == 0) {
            // 취소(null) 클릭 또는 아무것도 입력하지 않고 확인 클릭 시
            if (idx == null) { // 취소 버튼 클릭했을 경우 아무 동작 X
                return; // 현재 메서드 종료
            }

            // 아무 번호도 입력하지 않고(널스트링값 전달) 확인 버튼 클릭했을 경우 메세지 표시
            JOptionPane.showMessageDialog(rootPane, "번호 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);

            // 다시 입력받기
            idx = JOptionPane.showInputDialog(rootPane, "삭제할 댓글 번호를 입력하세요.");
        }

        // 삭제할 번호를 입력할 경우
        // => 정규표현식을 사용하여 번호만 입력받도록 처리할 수 있다!
        // => \\d : 숫자, {1,} : 규칙이 1번 이상 반복 => \\d{1,} : 숫자 1자리 이상
        if (!Pattern.matches("\\d{1,}", idx)) {
            JOptionPane.showMessageDialog(rootPane, "숫자 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
//
//            TotalDAO dao = TotalDAO.getInstance();
//
//            int result = dao.delete(Integer.parseInt(idx));
//            // 댓글 삭제 여부 판별
//            if (result == 0) { // 실패했을 경우
//                JOptionPane.showMessageDialog(rootPane, "댓글를 삭제할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
//                return;
//            } else { // 성공했을 경우
//                JOptionPane.showMessageDialog(rootPane, "댓글를 삭제하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
//            }

    }

    public void showTotalInfo() {
        // 클릭한 행에 대한 모든 정보 가져와서 좌측 WEST 영역 텍스트필드에 표시
        int row = table.getSelectedRow();

        tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String 타입으로 형변환
        tfDate.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String 타입으로 형변환
        tfSum.setText((String) table.getValueAt(row, 2)); // Object(double) -> String 타입으로 형변환
    }

}