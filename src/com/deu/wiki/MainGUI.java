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
        setTitle("동의위키");
        setBounds(500, 300, 1280, 720);
        setLocationRelativeTo(null); // 프로그램이 정중앙에 뜸
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

        boolean isLogin; // 로그인 여부 표시할 변수
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
            // ================= 상단 DB 접속 패널 ==================
            JPanel pNorth = new JPanel();
            add(pNorth, BorderLayout.PAGE_START);
            // GridLayout 으로 변경(4개의 칸 생성)
            pNorth.setLayout(new GridLayout(1, 4));

            // DB IP주소 입력 패널
            JPanel pDbIp = new JPanel();
            pNorth.add(pDbIp);

//            pDbIp.add(new JLabel("IP"));
//            tfDbIp = new JTextField(10);
//            tfDbIp.setText("192.168.56.1"); // 임시로 IP 주소를 미리 입력
//            pDbIp.add(tfDbIp);

            // DB Username 입력 패널
            JPanel pDbUsername = new JPanel();
            pNorth.add(pDbUsername);

            pDbUsername.add(new JLabel("ID(학번)"));
            tfDbUsername = new JTextField(10);
            pDbUsername.add(tfDbUsername);

            // DB Password 입력 패널
            JPanel pDbPassword = new JPanel();
            pNorth.add(pDbPassword);

            pDbPassword.add(new JLabel("비밀번호"));
            pfDbPassword = new JPasswordField(10);
            pDbPassword.add(pfDbPassword);

            // DB Login 버튼 패널
            JPanel pDbLogin = new JPanel();
            pNorth.add(pDbLogin);

            btnLogin = new JButton("로그인");
            pDbLogin.add(btnLogin);

            // ================= 하단 버튼 패널 ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);


            btnSearch = new JButton("게시물 검색");
            btnInsert = new JButton("게시물 추가");
            btnUpdate = new JButton("게시물 수정");
            btnDelete = new JButton("게시물 삭제");
            btnSelect = new JButton("게시물 목록");
            btnsort = new JButton("정렬");
            btnReport = new JButton("게시물 신고");
            
            btnPostDetail = new JButton("게시글 상세 화면");
            btnReport_post = new JButton("신고 초과 게시글");
            btnReport_review = new JButton("신고 초과 댓글");
            btnReport_list = new JButton("신고 내역 확인");     // 원래 매출
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
//            btnSong = new JButton("노래 관리");

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

            // 로그인 버튼 이벤트 처리
            btnLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dbLogin();

                    // 버튼 구별하는 리스너
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


                    // 버튼 리스너 동시 연결
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
                	d.showFrame("신고 내역", 1);
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

            JPanel pName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pName);
            pName.add(new JLabel("키 워 드"));
            tfName = new JTextField(10);
//            tfName.setEditable(false);
            pName.add(tfName);
            

//            JPanel pId = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//            pWest.add(pId);
//            pId.add(new JLabel("댓글 번호"));
//            tfId = new JTextField(10);
////            tfId.setEditable(false);
//            pId.add(tfId);

//            JPanel pPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//            pWest.add(pPassword);
//            pPassword.add(new JLabel("패스워드"));
//            tfPassword = new JTextField(10);
//            pPassword.add(tfPassword);

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
            columnNames.add("작성자");
            columnNames.add("조회수");
            columnNames.add("평점");
            columnNames.add("리뷰수");
            columnNames.add("작성일");

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
//				System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

                    // 선택된 컬럼의 데이터 출력
//				System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

                    showAdminInfo(); // 선택된 행의 모든 컬럼 데이터를 WEST 영역 텍스트필드에 표시
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

        // 로그인 기능 수행하는 dbLogin() 메서드 정의
        void dbLogin() {
            // DB 아이디와 패스워드를 가져와서 DAO 클래스의 login() 메서드 호출
            // => 로그인 수행 결과를 리턴받아 "아이디 없음", "패스워드 틀림", "로그인 성공" 세 가지로 분류
            // 0. 로그인 버튼 vs 로그아웃 버튼 판별
            // 1. 로그인 버튼일 경우
            // 1-1. 아이디 또는 패스워드가 잘못됐을 경우 경고메세지 출력 후 해당 필드에 커서 요청
            // 1-2. 로그인 성공할 경우 "로그인" 버튼의 텍스트를 "로그아웃"으로 변경 후
            // IP, Username, Password 텍스트필드 입력 불가 설정
            // 2. 로그아웃 버튼일 경우
            // "로그아웃" 버튼 텍스트를 "로그인"으로 변경 후
            // Username, Password 텍스트필드 입력 가능 설정

            if (!isLogin) { // 로그인 상태가 아닐 경우
                // ------------- 입력 체크 --------------

                String username = tfDbUsername.getText();
                String password = new String(pfDbPassword.getPassword());


                    if (username.length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "아이디를 입력해주세요!", "정보 오류", JOptionPane.ERROR_MESSAGE);
                    tfDbUsername.requestFocus();
                    return;
                } else if (password.length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "비밀번호를 입력해주세요!", "정보 오류", JOptionPane.ERROR_MESSAGE);
                    pfDbPassword.requestFocus();
                    return;
                }

                MainDTO dto = new MainDTO(username, password);
                MainDAO dao = MainDAO.getInstance();
                String result = dao.login(dto);
                if (result.equals("none")) { // 아이디가 없을 경우
                    JOptionPane.showMessageDialog(rootPane, "로그인 정보가 일치하지 않습니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
                    tfDbUsername.requestFocus();
                    return;
                } else if (result.equals("null")) { // 닉네임이 없을 경우
                    String input = JOptionPane.showInputDialog(rootPane, "환영합니다 닉네임을 입력해주세요.", "닉네임 입력",  JOptionPane.INFORMATION_MESSAGE);
                    if(input != null) {
                    	dto.setnickname(input);
                    	int nick_result = dao.input_nick(dto);
                    	if(nick_result == 1) {
                    		JOptionPane.showMessageDialog(rootPane, input + "님 환영합니다.", "로그인 성공", JOptionPane.PLAIN_MESSAGE);
                    	}
                    	else {
                    		return;
                    	}
                    }
                    else {
                    	return;
                    }
                } else if(result.equals("stop")) {
                	JOptionPane.showMessageDialog(rootPane, "신고 누적으로 인해 정지상태 입니다.(정지 해제는 문의)", "로그인 오류", JOptionPane.ERROR_MESSAGE);
                    tfDbUsername.requestFocus();
                    return;
                } else {
                	JOptionPane.showMessageDialog(rootPane, result.trim() + "님 환영합니다.", "로그인 성공", JOptionPane.PLAIN_MESSAGE);
                }

                // 로그인 성공했을 경우
//                tfDbIp.setEditable(false);
//                tfDbUsername.setEditable(false);
//                pfDbPassword.setEditable(false);
                Login_ID = dto.getId();
                btnLogin.setText("로그아웃");
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
                    btnReport_list.setVisible(true); //신고게시글
                    btnPostDetail.setVisible(true);
                    btnPostDetail.setVisible(true);
                    table.setVisible(true);
//                    btnSong.setVisible(true);
                } else {
                	Is_admin = false;
                    btnPostDetail.setVisible(true);
                    btnReport.setVisible(true);
                	btnInsert.setVisible(true);
//                    btnTotal.setVisible(true); //신고게시글
//                    btnUser.setVisible(true);  // 신고댓글
//                    btnSong.setVisible(true);
                }
                isLogin = true; // 로그인 상태로 변경
            } else { // 로그인 상태에서 로그아웃 버튼을 클릭했을 경우
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
                btnLogin.setText("로그인");
                btnInsert.setVisible(false);
                btnUpdate.setVisible(false);
                btnDelete.setVisible(false);
                btnReport_list.setVisible(false);
                DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//   			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
               // => 단, addRow() 등의 메서드가 없음

                dtm.setRowCount(0);
//                btnSong.setVisible(false);
                Login_ID = "";
                isLogin = false; // 로그아웃 상태로 변경
            }
        }

        // 게시물 추가
        public void insert() {
            if (!isLogin) {
                JOptionPane.showMessageDialog(rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
                tfDbUsername.requestFocus();
                return;
            }

            String title = tfName.getText();
//            String password = tfPassword.getText();

            // 입력 항목 체크
            if (title.length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "키워드 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                tfName.requestFocus();
                return;
            }
//            else if (password.length() == 0) {
//                JOptionPane.showMessageDialog(rootPane, "패스워드 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
//                tfPassword.requestFocus();
//                return;
//            }

            MainDTO dto = new MainDTO(title);
            MainDAO dao = MainDAO.getInstance();
            int result = dao.insert(dto, Login_ID); // 게시물 추가 후 결과값 리턴

            // 게시물 추가 여부 판별
            if (result == 0) { // 실패했을 경우
                JOptionPane.showMessageDialog(rootPane, "게시물을 추가할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // 성공했을 경우
                JOptionPane.showMessageDialog(rootPane, "게시물을 추가하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // 게시물 수정
        public void update() {
            if (table.getSelectedRow() == -1) { // 테이블 셀 선택 안됐을 경우 -1 리턴됨
                return;
            }

            // 테이블 셀 선택했을 경우 창 새 프레임 생성하여 선택된 댓글 정보 표시
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

            JPanel pName2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pName2);

            pName2.add(new JLabel("이   름"));
            tfName2 = new JTextField(10);
            tfName2.setHorizontalAlignment(tfIdx2.CENTER);
            pName2.add(tfName2);

            JPanel pId2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pId2);

            pId2.add(new JLabel("아 이 디"));
            tfId2 = new JTextField(10);
            tfId2.setHorizontalAlignment(tfIdx2.CENTER);
            pId2.add(tfId2);

            JPanel pPassword2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest2.add(pPassword2);

//            pPassword2.add(new JLabel("패스워드"));
//            tfPassword2 = new JTextField(10);
//            tfPassword2.setHorizontalAlignment(tfIdx2.CENTER);
//            pPassword2.add(tfPassword2);

            JPanel pSouth2 = new JPanel();
            editFrame.add(pSouth2, BorderLayout.SOUTH);

            btnUpdate2 = new JButton("수정");
            btnCancel2 = new JButton("취소");

            pSouth2.add(btnUpdate2);
            pSouth2.add(btnCancel2);
            name2 = tfName2.getText();
            id2 = tfId2.getText();
            password2 = tfPassword2.getText();

            // 버튼 두 개 구별하는 리스너
            ActionListener btnListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnUpdate2) {
                        // 입력 항목 체크
                        if (tfName2.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "이름 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                            tfName2.requestFocus();
                            return;
                        } else if (tfId2.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "아이디 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                            tfId2.requestFocus();
                            return;
                        } 
                        else if (tfPassword2.getText().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "패스워드 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                            tfPassword2.requestFocus();
                            return;
                        }
                        MainDTO dto = new MainDTO(Integer.parseInt(tfIdx2.getText()), tfName2.getText(),
                                tfId2.getText(), tfPassword2.getText());
                        MainDAO dao = MainDAO.getInstance();
                        int result = dao.update(dto); // 게시물 수정 후 결과값 리턴

                        // 게시물 수정 여부 판별
                        if (result == 0) { // 실패했을 경우
                            JOptionPane.showMessageDialog(rootPane, "게시물를 수정할 수 없습니다.", "실패",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        } else { // 성공했을 경우
                            JOptionPane.showMessageDialog(rootPane, "게시물를 수정하였습니다.", "성공",
                                    JOptionPane.INFORMATION_MESSAGE);
                            editFrame.setVisible(false);
                        }
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

        // 게시물 검색
        public void search() {
        	MainDAO dao = MainDAO.getInstance();
            // 게시물 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
        	String keyword = tfName.getText();
            Vector<Vector> data = dao.selectOne(keyword, "keyword", "asc", search_mode);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
            // => 단, addRow() 등의 메서드가 없음

            dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김
            
            // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate();
            search_keyword = keyword;
        }

        public void report(){
        	if (Click_keyword.equals("")) {
        		JOptionPane.showMessageDialog(rootPane, "신고할 게시글을 선택해 주세요.", "신고 게시글 선택", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	String input = JOptionPane.showInputDialog(rootPane, "신고 사유를 작성해 주세요.", Click_keyword + " 신고",  JOptionPane.WARNING_MESSAGE);
            if(input != null) {
            	if(input.equals("")) {
            		JOptionPane.showMessageDialog(rootPane, "내용이 없습니다!", "신고 취소", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	ReportDAO dao = ReportDAO.getInstance();
            	ReportDTO dto = new ReportDTO(Login_ID,Click_keyword,input);
            	int result = dao.insert(dto);
            	if (result == 0) { // 실패했을 경우
                    JOptionPane.showMessageDialog(rootPane, "신고 오류 발생.", "실패", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(result == -1) {
                	JOptionPane.showMessageDialog(rootPane, "이미 신고 하셨습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(result == -2) {
                	JOptionPane.showMessageDialog(rootPane, "로그인을 해주세요.", "실패", JOptionPane.ERROR_MESSAGE);
                    return;
                } else { // 성공했을 경우
                    JOptionPane.showMessageDialog(rootPane, Click_keyword + " 게시글을 신고하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    Static.pNum = null;
                }
            }
            else {
            	return;
            }
        }
        // 게시물 목록
        public void select() {
//			if(!isLogin) {
//				JOptionPane.showMessageDialog(
//						rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
//				tfDbUsername.requestFocus();
//				return;
//			}

        	MainDAO dao = MainDAO.getInstance();
            // 게시물 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
            Vector<Vector> data = dao.select("keyword", search_mode);
            
            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
            // => 단, addRow() 등의 메서드가 없음

            dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

            // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // 프레임 갱신(새로 그리기)
            search_keyword = "";
        }
        public void sort() {
        	MainDAO dao = MainDAO.getInstance();
            // 게시물 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
            Vector<Vector> data = dao.selectOne(search_keyword, col_name, sort_type, search_mode);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
            // => 단, addRow() 등의 메서드가 없음

            dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

            // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
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
        // 게시물삭제
        public void delete() {
            if (!isLogin) {
                JOptionPane.showMessageDialog(rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
                tfDbUsername.requestFocus();
                return;
            }

            // InputDialog 사용하여 삭제할 게시물 번호 입력받기
            String idx = JOptionPane.showInputDialog(rootPane, "삭제할 게시물 번호를 입력하세요.");
//			System.out.println(idx);

            while (idx == null || idx.length() == 0) {
                // 취소(null) 클릭 또는 아무것도 입력하지 않고 확인 클릭 시
                if (idx == null) { // 취소 버튼 클릭했을 경우 아무 동작 X
                    return; // 현재 메서드 종료
                }

                // 아무 번호도 입력하지 않고(널스트링값 전달) 확인 버튼 클릭했을 경우 메세지 표시
                JOptionPane.showMessageDialog(rootPane, "번호 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);

                // 다시 입력받기
                idx = JOptionPane.showInputDialog(rootPane, "삭제할 게시물 번호를 입력하세요.");
            }

            // 삭제할 번호를 입력할 경우
            // => 정규표현식을 사용하여 번호만 입력받도록 처리할 수 있다!
            // => \\d : 숫자, {1,} : 규칙이 1번 이상 반복 => \\d{1,} : 숫자 1자리 이상
            if (!Pattern.matches("\\d{1,}", idx)) {
                JOptionPane.showMessageDialog(rootPane, "숫자 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            MainDAO dao = MainDAO.getInstance();

            int result = dao.delete(Integer.parseInt(idx));
            // 게시물 삭제 여부 판별
            if (result == 0) { // 실패했을 경우
                JOptionPane.showMessageDialog(rootPane, "게시물를 삭제할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // 성공했을 경우
                JOptionPane.showMessageDialog(rootPane, "게시물를 삭제하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        public void showAdminInfo() {
            // 클릭한 행에 대한 모든 정보 가져와서 좌측 WEST 영역 텍스트필드에 표시
            int row = table.getSelectedRow();

            tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String 타입으로 형변환
            tfName.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String 타입으로 형변환
            Click_keyword= table.getValueAt(row, 1).toString();
//            tfId.setText((String) table.getValueAt(row, 2)); // Object(String) -> String 타입으로 형변환
//            tfPassword.setText((String) table.getValueAt(row, 3)); // Object(String) -> String 타입으로 형변환
        }
    }

    ///
    /// 신고 내역 관리 화면
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

            // ================= 하단 버튼 패널 ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);
            btnDelete = new JButton("신고 내역 삭제(신고 취소)");
            btnBack = new JButton("뒤로 가기");

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

            // ================= 좌측 매출 정보 입력 패널 ==================
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

            // ================= 중앙 매출 정보 출력 패널 ==================
            // 스크롤바 기능을 위해 JScrollPane 객체를 생성하여 Center 영역에 부착
            JScrollPane scrollPane = new JScrollPane();
            add(scrollPane, BorderLayout.CENTER);

            // JTable 객체를 생성하여 JScrollPane 의 ViewPort 영역에 부착
            table = new JTable();
            table.getTableHeader().setReorderingAllowed(false); // 셀 이동 불가 설정
            scrollPane.setViewportView(table);

            // 테이블 컬럼명 표시를 위해 Vector 객체에 컬럼명을 저장한 후 DefaultTableModel 객체에 추가
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("신고번호");
            columnNames.add("신고자");
            columnNames.add("게시글 이름("+Post_name+")");
            columnNames.add("댓글 내용(게시글 내역은 확인불가)");
            columnNames.add("신고 사유");

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

                    showReportInfo(); // 선택된 행의 모든 컬럼 데이터를 WEST 영역 텍스트필드에 표시
                }
            });
            if (mode == 0) {
            	select_post();
            }else {
            	select_review(Integer.toString(mode));
            }
            
            setVisible(true);
        }

        // 매출 정산
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
//            int result = dao.insert(dto); // 매출정산 후 결과값 리턴
//
//            // 매출 추가 여부 판별
//            if (result == 0) { // 실패했을 경우
//                JOptionPane.showMessageDialog(rootPane, "매출를 정산할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
//                return;
//            } else { // 성공했을 경우
//                JOptionPane.showMessageDialog(rootPane, "매출를 정산하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
//                dto = new TotalDTO(0, date, 0);
//            }
        }

        // 매출 수정
        public void update() {
            if (table.getSelectedRow() == -1) { // 테이블 셀 선택 안됐을 경우 -1 리턴됨
                return;
            }

            // 테이블 셀 선택했을 경우 창 새 프레임 생성하여 선택된 댓글 정보 표시
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
                            JOptionPane.showMessageDialog(rootPane, "매출 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                            tfSum.requestFocus();
                            return;
                        }
//                        TotalDTO dto = new TotalDTO(Integer.parseInt(tfIdx.getText()), tfName2.getText(),
//                                Integer.parseInt(tfSum.getText()));
//                        TotalDAO dao = TotalDAO.getInstance();
//                        int result = dao.update(dto); // 매출 수정 후 결과값 리턴

                        // 매출 수정 여부 판별
//                        if (result == 0) { // 실패했을 경우
//                            JOptionPane.showMessageDialog(rootPane, "매출을 수정할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        } else { // 성공했을 경우
//                            JOptionPane.showMessageDialog(rootPane, "매출을 수정하였습니다.", "성공",
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

        // 매출 목록 조회
        public void select_post() {
        	ReportDAO dao = ReportDAO.getInstance();

            // 신고 댓글 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
            Vector<Vector> data = dao.select_post(Post_name);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
            // => 단, addRow() 등의 메서드가 없음

            dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

            // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // 프레임 갱신(새로 그리기)
        }
        
        public void select_review(String review_num) {
        	ReportDAO dao = ReportDAO.getInstance();

            // 신고 댓글 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
            Vector<Vector> data = dao.select_review(review_num);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
            // => 단, addRow() 등의 메서드가 없음

            dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

            // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // 프레임 갱신(새로 그리기)
        }

        // 매출 기록 삭제
        public void delete() {
        	// InputDialog 사용하여 삭제할 댓글 번호 입력받기
            String idx = JOptionPane.showInputDialog(rootPane, "삭제할 신고 내역 번호를 입력하세요.");

            while (idx == null || idx.length() == 0) {
                // 취소(null) 클릭 또는 아무것도 입력하지 않고 확인 클릭 시
                if (idx == null) { // 취소 버튼 클릭했을 경우 아무 동작 X
                    return; // 현재 메서드 종료
                }

                // 아무 번호도 입력하지 않고(널스트링값 전달) 확인 버튼 클릭했을 경우 메세지 표시
                JOptionPane.showMessageDialog(rootPane, "번호 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);

                // 다시 입력받기
                idx = JOptionPane.showInputDialog(rootPane, "삭제할 신고 내역 번호를 입력하세요.");
            }

            // 삭제할 번호를 입력할 경우
            // => 정규표현식을 사용하여 번호만 입력받도록 처리할 수 있다!
            // => \\d : 숫자, {1,} : 규칙이 1번 이상 반복 => \\d{1,} : 숫자 1자리 이상
            if (!Pattern.matches("\\d{1,}", idx)) {
                JOptionPane.showMessageDialog(rootPane, "숫자 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ReportDAO dao = ReportDAO.getInstance();

            int result = dao.delete(idx);
            // 댓글 삭제 여부 판별
            if (result == 0) { // 실패했을 경우
                JOptionPane.showMessageDialog(rootPane, "신고 내역을 삭제할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // 성공했을 경우
                JOptionPane.showMessageDialog(rootPane, "신고 내역을 삭제하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                if (mode == 0) {
                	select_post();
                }else {
                	select_review(Integer.toString(mode));
                }
                
            }

        }

        public void showReportInfo() {
            // 클릭한 행에 대한 모든 정보 가져와서 좌측 WEST 영역 텍스트필드에 표시
            int row = table.getSelectedRow();

            tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String 타입으로 형변환
        }

    }

    ///
    /// 댓글관리 화면
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

            // ================= 하단 버튼 패널 ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);

            btnInsert = new JButton("댓글 추가");
            btnUpdate = new JButton("댓글 수정");
            btnDelete = new JButton("댓글 삭제");
            btnReport = new JButton("신고 하기");
            btnReport_list = new JButton("신고 내역");
            btnBack = new JButton("뒤로가기");

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
            tfIdx.setText("");
            pIdx.add(tfIdx);

            JPanel ppNum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(ppNum);
            ppNum.add(new JLabel("내 용"));
            tfpNum = new JTextField(10);
            ppNum.add(tfpNum);

            JPanel pPoint = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pWest.add(pPoint);
            pPoint.add(new JLabel("평 점"));
            tfPoint = new JTextField(10);
            pPoint.add(tfPoint);

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
            columnNames.add("댓글번호");
            columnNames.add(Post_name + " 댓글 내용");
            columnNames.add("작성자");
            columnNames.add("점수");
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

                    showReviewInfo(); // 선택된 행의 모든 컬럼 데이터를 WEST 영역 텍스트필드에 표시
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
        //조회수 증가
        public void up_count() {
        	ReviewDAO dao = ReviewDAO.getInstance();
            int result = dao.up_count(Post_name);
        }
        // 댓글 및 포인트 추가
        public void insert() {

            // 입력 항목 체크
            if (tfpNum.getText().length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "댓글을 작성해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tfPoint.getText().length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "점수를 작성해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("\\d{1,1}", tfPoint.getText())) {
                JOptionPane.showMessageDialog(rootPane, "점수는 1~5 정수 입력!", "입력 오류", JOptionPane.ERROR_MESSAGE);
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

            int result = dao.insert(dto, Post_name); // 댓글추가 후 결과값 리턴

            // 댓글 추가 여부 판별
            if (result == 0) { // 실패했을 경우
                JOptionPane.showMessageDialog(rootPane, "댓글을 추가할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(result == -1) {
            	JOptionPane.showMessageDialog(rootPane, "댓글을 이미 작성하셨습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            } else if(result == -2) {
            	JOptionPane.showMessageDialog(rootPane, "로그인을 해주세요.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // 성공했을 경우
                JOptionPane.showMessageDialog(rootPane, "댓글을 추가하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                Static.pNum = null;
                select();
            }
        }

        // 댓글 수정
        public void update() {
        	if (tfpNum.getText().length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "수정할 댓글을 작성해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tfPoint.getText().length() == 0) {
                JOptionPane.showMessageDialog(rootPane, "수정할 점수를 작성해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("\\d{1,1}", tfPoint.getText())) {
                JOptionPane.showMessageDialog(rootPane, "점수는 1~5 정수 입력!", "입력 오류", JOptionPane.ERROR_MESSAGE);
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

            int result = dao.update(dto, Post_name); // 댓글추가 후 결과값 리턴

            // 댓글 추가 여부 판별
            if (result == 0) { // 실패했을 경우
                JOptionPane.showMessageDialog(rootPane, "댓글을 수정할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(result == -1) {
            	JOptionPane.showMessageDialog(rootPane, "입력한 댓글이 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            } else if(result == -2) {
            	JOptionPane.showMessageDialog(rootPane, "로그인을 해주세요.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // 성공했을 경우
                JOptionPane.showMessageDialog(rootPane, "댓글을 수정하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                Static.pNum = null;
                select();
            }
        }

        // 신고 댓글 목록 조회
        public void select() {
            ReviewDAO dao = ReviewDAO.getInstance();

            // 신고 댓글 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
            Vector<Vector> data = dao.select(Post_name);

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
            // => 단, addRow() 등의 메서드가 없음

            dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

            // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // 프레임 갱신(새로 그리기)
        }

        public void select_report() {
            ReviewDAO dao = ReviewDAO.getInstance();

            // 신고 댓글 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
            Vector<Vector> data = dao.select_report();

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
            // => 단, addRow() 등의 메서드가 없음

            dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

            // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
            for (Vector rowData : data) {
                dtm.addRow(rowData);
            }
            invalidate(); // 프레임 갱신(새로 그리기)
        }
        
        public void report(){
        	if (tfIdx.getText().equals("")) {
        		JOptionPane.showMessageDialog(rootPane, "신고할 댓글을 선택해 주세요.", "신고 게시글 선택", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	String input = JOptionPane.showInputDialog(rootPane, "신고 사유를 작성해 주세요.", "신고 번호 " + tfIdx.getText(),  JOptionPane.WARNING_MESSAGE);
            if(input != null) {
            	if(input.equals("")) {
            		JOptionPane.showMessageDialog(rootPane, "내용이 없습니다!", "신고 취소", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	ReportDAO dao = ReportDAO.getInstance();
            	ReportDTO dto = new ReportDTO(Login_ID,Post_name,input,tfIdx.getText());
            	int result = dao.insert_review(dto);
            	if (result == 0) { // 실패했을 경우
                    JOptionPane.showMessageDialog(rootPane, "신고 오류 발생.", "실패", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(result == -1) {
                	JOptionPane.showMessageDialog(rootPane, "이미 신고 하셨습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(result == -2) {
                	JOptionPane.showMessageDialog(rootPane, "로그인을 해주세요.", "실패", JOptionPane.ERROR_MESSAGE);
                    return;
                } else { // 성공했을 경우
                    JOptionPane.showMessageDialog(rootPane, "신고 번호 "+ tfIdx.getText() + "을(를) 신고하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    Static.pNum = null;
                }
            }
            else {
            	return;
            }
        }
        
        // 신고 댓글 삭제
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
            ReviewDAO dao = ReviewDAO.getInstance();

            int result = dao.delete(idx, MainGUI.Login_ID);
            // 댓글 삭제 여부 판별
            if (result == 0) { // 실패했을 경우
                JOptionPane.showMessageDialog(rootPane, "댓글을 삭제할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                return;
            } else { // 성공했을 경우
                JOptionPane.showMessageDialog(rootPane, "댓글를 삭제하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                select();
            }

        }

        public void showReviewInfo() {
            // 클릭한 행에 대한 모든 정보 가져와서 좌측 WEST 영역 텍스트필드에 표시
            int row = table.getSelectedRow();

            tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String 타입으로 형변환
            tfpNum.setText("");
            tfPoint.setText("");
//            tfpNum.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String 타입으로 형변환
//            tfPoint.setText((String) table.getValueAt(row, 2)); // Object(double) -> String 타입으로 형변환
        }
    }

    ///
    /// 노래 관리 화면
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

            // ================= 하단 버튼 패널 ==================
            pSouth = new JPanel();
            add(pSouth, BorderLayout.PAGE_END);

            btnSelect = new JButton("노래 목록");
            btnUp = new JButton("↑");
            btnDown = new JButton("↓");
            btnBack = new JButton("뒤로 가기");

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

            // ================= 좌측 매출 정보 입력 패널 ==================
//			pWest = new JPanel();
//			add(pWest, BorderLayout.LINE_START);
//			// 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
//			pWest.setLayout(new GridLayout(5, 1));
//
//			// 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
//			JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//			pWest.add(pIdx);
//
//			pIdx.add(new JLabel("번   호"));
//			tfIdx = new JTextField(10);
//			tfIdx.setEditable(false); // 텍스트필드 편집 불가 설정
//			pIdx.add(tfIdx);
//
//			JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//			pWest.add(pDate);
//			pDate.add(new JLabel("날   짜"));
//			tfDate = new JTextField(10);
//			pDate.add(tfDate);
//
//			JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//			pWest.add(pSum);
//			pSum.add(new JLabel("매   출"));
//			tfSum = new JTextField(10);
//			pSum.add(tfSum);

            // ================= 중앙 매출 정보 출력 패널 ==================
            // 스크롤바 기능을 위해 JScrollPane 객체를 생성하여 Center 영역에 부착
            JScrollPane scrollPane = new JScrollPane();
            add(scrollPane, BorderLayout.CENTER);

            // JTable 객체를 생성하여 JScrollPane 의 ViewPort 영역에 부착
            table = new JTable();
            table.getTableHeader().setReorderingAllowed(false); // 셀 이동 불가 설정
            scrollPane.setViewportView(table);

            // 테이블 컬럼명 표시를 위해 Vector 객체에 컬럼명을 저장한 후 DefaultTableModel 객체에 추가
            String[] columnNames = { "순서", "가수", "노래" };

//			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
            DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // 셀 편집 불가능하도록 설정
                }

            };

            // JTable 에 DefaultTableModel 객체 추가
            table.setModel(dtm);

            setVisible(true);
        }

        // 매출 목록 조회
        public void select() {

            DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
            // => 단, addRow() 등의 메서드가 없음

            dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

//            // Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
//            for (int i = 0; i < Static.trackList.size(); i++) {
//                StringTokenizer st = new StringTokenizer(Static.trackList.get(i).getListMusic(), "-");
//                String num = "" + (i + 1);
//                String Singer = st.nextToken().trim();
//                String Song = st.nextToken().trim();
//                dtm.addRow(new String[] { num, Singer, Song });
//            }
            invalidate(); // 프레임 갱신(새로 그리기)
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
//            invalidate(); // 프레임 갱신(새로 그리기)
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
//            invalidate(); // 프레임 갱신(새로 그리기)
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
    static int n; // 노래 인덱스
//    static ArrayList<Track> trackListAll;
//    static ArrayList<Track> trackList;
}