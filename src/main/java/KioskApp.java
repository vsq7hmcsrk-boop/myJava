import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KioskApp extends JFrame {

    // ==========================
    // 상품 클래스
    // ==========================
    class Product {
        String category;
        String name;
        int price;
        String option;

        public Product(String category, String name, int price, String option) {
            this.category = category;
            this.name = name;
            this.price = price;
            this.option = option;
        }
    }

    // ==========================
    // 장바구니 클래스
    // ==========================
    class CartItem {
        Product product;
        int quantity;

        public CartItem(Product product) {
            this.product = product;
            this.quantity = 1;
        }
    }

    // ==========================
    // 데이터
    // ==========================
    private List<Product> productList = new ArrayList<>();
    private Map<String, CartItem> cart = new LinkedHashMap<>();

    private int totalPrice = 0;

    // 관리자 기능
    private static final String ADMIN_PASSWORD = "1234";
    private int totalSales = 0;
    private Map<String, Integer> salesCount = new LinkedHashMap<>();

    // UI
    private JPanel menuGridPanel;
    private JTextArea cartArea;
    private JButton checkoutBtn;

    public KioskApp() {

        initProductData();

        setTitle("편의점 키오스크");
        setSize(450, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // ==========================
        // 상단 패널
        // ==========================
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel categoryPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10));

        String[] categories = {
                "음료",
                "스낵",
                "간편식",
                "기타"
        };

        for (String category : categories) {

            JButton btnCategory = new JButton(category);
            btnCategory.setPreferredSize(new Dimension(80, 40));

            btnCategory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMenuGrid(category);
                }
            });

            categoryPanel.add(btnCategory);
        }

        JButton adminBtn = new JButton("관리자 모드");

        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String password = JOptionPane.showInputDialog(
                        KioskApp.this,
                        "암호를 입력하세요."
                );

                if (password == null) return;

                if (password.equals(ADMIN_PASSWORD)) {
                    showAdminPage();
                } else {
                    JOptionPane.showMessageDialog(
                            KioskApp.this,
                            "암호가 틀렸습니다."
                    );
                }
            }
        });

        topPanel.add(categoryPanel, BorderLayout.CENTER);
        topPanel.add(adminBtn, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ==========================
        // 메뉴 영역
        // ==========================
        menuGridPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        menuGridPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        JScrollPane menuScrollPane =
                new JScrollPane(menuGridPanel);

        menuScrollPane.setBorder(null);

        add(menuScrollPane, BorderLayout.CENTER);

        // ==========================
        // 하단 영역
        // ==========================
        JPanel bottomPanel =
                new JPanel(new BorderLayout(5, 5));

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        bottomPanel.setPreferredSize(
                new Dimension(450, 250)
        );

        cartArea = new JTextArea();
        cartArea.setEditable(false);

        JScrollPane cartScrollPane =
                new JScrollPane(cartArea);

        bottomPanel.add(cartScrollPane,
                BorderLayout.CENTER);

        checkoutBtn =
                new JButton("결제하기 (0원)");

        checkoutBtn.setPreferredSize(
                new Dimension(450, 60)
        );

        checkoutBtn.setBackground(
                new Color(50, 150, 250)
        );

        checkoutBtn.setForeground(Color.WHITE);

        checkoutBtn.setFont(
                new Font(
                        "맑은 고딕",
                        Font.BOLD,
                        18
                )
        );

        checkoutBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        if (cart.isEmpty()) {
                            JOptionPane.showMessageDialog(
                                    KioskApp.this,
                                    "장바구니가 비어있습니다."
                            );
                            return;
                        }

                        // 매출 누적
                        totalSales += totalPrice;

                        // 판매량 통계
                        for (CartItem item :
                                cart.values()) {

                            String name =
                                    item.product.name;

                            salesCount.put(
                                    name,
                                    salesCount.getOrDefault(
                                            name,
                                            0
                                    ) + item.quantity
                            );
                        }

                        JOptionPane.showMessageDialog(
                                KioskApp.this,
                                totalPrice +
                                        "원 결제가 완료되었습니다!"
                        );

                        cart.clear();

                        updateCartDisplay();
                    }
                });

        bottomPanel.add(
                checkoutBtn,
                BorderLayout.SOUTH
        );

        add(bottomPanel, BorderLayout.SOUTH);

        updateMenuGrid("음료");
        updateCartDisplay();

        setVisible(true);
    }

    // ==========================
    // 상품 데이터
    // ==========================
    private void initProductData() {

        productList.add(
                new Product(
                        "음료",
                        "탄산음료",
                        2000,
                        "1+1 이벤트"
                )
        );

        productList.add(
                new Product(
                        "음료",
                        "생수",
                        1000,
                        "-"
                )
        );

        productList.add(
                new Product(
                        "음료",
                        "맥주",
                        3300,
                        "성인 인증"
                )
        );

        productList.add(
                new Product(
                        "스낵",
                        "감자칩",
                        1800,
                        "2+1 이벤트"
                )
        );

        productList.add(
                new Product(
                        "스낵",
                        "컵라면",
                        1200,
                        "-"
                )
        );

        productList.add(
                new Product(
                        "간편식",
                        "삼각김밥",
                        1700,
                        "컵라면과 같이 구매시 200원 할인"
                )
        );

        productList.add(
                new Product(
                        "간편식",
                        "도시락",
                        5800,
                        "-"
                )
        );

        productList.add(
                new Product(
                        "기타",
                        "담배",
                        4500,
                        "성인 인증"
                )
        );
    }

    // ==========================
    // 메뉴 갱신
    // ==========================
    private void updateMenuGrid(
            String category
    ) {

        menuGridPanel.removeAll();

        for (Product p : productList) {

            if (p.category.equals(category)) {

                String btnText =
                        "<html><center><b>"
                                + p.name
                                + "</b><br>"
                                + p.price
                                + "원<br>"
                                + "<font color='gray'>"
                                + (p.option.equals("-")
                                ? ""
                                : p.option)
                                + "</font></center></html>";

                JButton btnMenu =
                        new JButton(btnText);

                btnMenu.setBackground(
                        Color.WHITE
                );

                btnMenu.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(
                                    ActionEvent e
                            ) {
                                addToCart(p);
                            }
                        });

                menuGridPanel.add(btnMenu);
            }
        }

        menuGridPanel.revalidate();
        menuGridPanel.repaint();
    }

    // ==========================
    // 장바구니 담기
    // ==========================
    private void addToCart(Product p) {

        if (p.option.contains("성인 인증")) {

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "본 상품은 성인인증이 필요합니다.\n인증하시겠습니까?",
                            "성인 인증",
                            JOptionPane.YES_NO_OPTION
                    );

            if (result != JOptionPane.YES_OPTION)
                return;
        }

        if (cart.containsKey(p.name)) {
            cart.get(p.name).quantity++;
        } else {
            cart.put(
                    p.name,
                    new CartItem(p)
            );
        }

        updateCartDisplay();
    }

    // ==========================
    // 장바구니 출력
    // ==========================
    private void updateCartDisplay() {

        cartArea.setText(
                "[장바구니 내역]\n" +
                        "====================\n"
        );

        totalPrice = 0;

        for (CartItem item :
                cart.values()) {

            Product p = item.product;

            int qty = item.quantity;

            int paidQty = qty;

            String promoText = "";

            if (p.option.contains("1+1")) {

                promoText = " [1+1 적용]";

                paidQty =
                        qty - (qty / 2);

            } else if (p.option.contains("2+1")) {

                paidQty =
                        qty - (qty / 3);

                if (qty >= 3) {
                    promoText =
                            " [2+1 적용]";
                }
            }

            int subTotal =
                    paidQty * p.price;

            totalPrice += subTotal;

            cartArea.append(
                    p.name
                            + " x "
                            + qty
                            + "개 : "
                            + subTotal
                            + "원"
                            + promoText
                            + "\n"
            );
        }

        int samgakQty =
                cart.containsKey("삼각김밥")
                        ? cart.get("삼각김밥").quantity
                        : 0;

        int ramenQty =
                cart.containsKey("컵라면")
                        ? cart.get("컵라면").quantity
                        : 0;

        int comboCount =
                Math.min(
                        samgakQty,
                        ramenQty
                );

        if (comboCount > 0) {

            int discount =
                    comboCount * 200;

            totalPrice -= discount;

            cartArea.append(
                    "--------------------\n"
            );

            cartArea.append(
                    "[콤보 할인] 삼각김밥 + 컵라면 "
                            + comboCount
                            + "세트 : -"
                            + discount
                            + "원\n"
            );
        }

        checkoutBtn.setText(
                "결제하기 ("
                        + totalPrice
                        + "원)"
        );
    }

    // ==========================
    // 관리자 페이지
    // ==========================
    private void showAdminPage() {

        JFrame adminFrame =
                new JFrame("관리자 모드");

        adminFrame.setSize(400, 500);
        adminFrame.setLocationRelativeTo(this);

        JTextArea area =
                new JTextArea();

        area.setEditable(false);

        StringBuilder sb =
                new StringBuilder();

        sb.append("===== 관리자 모드 =====\n\n");

        sb.append("오늘 총 매출\n");
        sb.append(totalSales)
                .append("원\n\n");

        sb.append(
                "많이 팔린 상품 TOP 3\n\n"
        );

        ArrayList<Map.Entry<String,Integer>>
                list =
                new ArrayList<>(
                        salesCount.entrySet()
                );

        list.sort(
                (a, b) ->
                        b.getValue()
                                - a.getValue()
        );

        if(list.isEmpty()) {
            sb.append("판매 내역이 없습니다.");
        } else {

            for(int i = 0;
                i < Math.min(3, list.size());
                i++) {

                sb.append(
                        (i + 1)
                                + "위 : "
                                + list.get(i).getKey()
                                + " ("
                                + list.get(i).getValue()
                                + "개)\n"
                );
            }
        }

        area.setText(sb.toString());

        adminFrame.add(
                new JScrollPane(area)
        );

        adminFrame.setVisible(true);
    }

    // ==========================
    // 실행
    // ==========================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new KioskApp();
                    }
                });
    }
}