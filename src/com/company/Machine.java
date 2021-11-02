package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Machine {
    private boolean run;
    private Product[] products;
    private Money moneyInserted;
    private ArrayList<Product> productsBought = new ArrayList<>();
    private int budgetPromotion = 0;

    Product productPromotion;
    int moneyAfterBuy;
    int percentChange = 10;
    int purchaseQuantity = 0;

    Scanner in = new Scanner(System.in);

    public Machine() {
        products = new Product[3];
        products[0] = new Product(1,"Coke", 10000);
        products[1] = new Product(2,"Pepsi", 10000);
        products[2] = new Product(3,"Soda", 20000);
    }

    public void startMachine() {
        run = true;
        checkIsNewDay();
        insertMoney();
        while (run) {
            purchaseQuantity++;
            showProducts();
            selectProduct();
            calculateChange();
            showQuestion();
            if (purchaseQuantity>=2) {
                setUpPromotion();
            }
        }
        printBill();
    }

    public void stopMachine() {
        run = false;
    }

    public void insertMoney() {
        System.out.println("Vui lòng nhập số tiền với các mệnh giá 10000, 20000, 50000, 100000, 200000");
        int moneyInput = in.nextInt();
        if(checkMoneyValid(moneyInput)) {
            System.out.println("Bạn đã nhập số tiền là: " + moneyInput);
            moneyInserted = new Money(moneyInput);
            moneyAfterBuy = moneyInserted.getVal();
        }
        else {
            System.out.println("Bạn nhập số tiền không hợp lệ, vui lòng nhập lại");
            System.out.println("================================================");
            insertMoney();
        }
    }

    public void showProducts() {
        System.out.println("Danh sách sản phẩm: ");
        for (Product product: products) {
            System.out.println(product.getName() + "----" + product.getPrice() + " vnd");
        }
    }

    public void selectProduct() {
        System.out.println("Mời bạn chọn sản phẩm.");
        System.out.println("Nhập 1 để mua Coke, 2 để mua Pepsi, 3 để mua Soda");
        switch (in.nextInt()) {
            case 1:
                productsBought.add(products[0]);
                System.out.println("Bạn đã chọn mua " + productsBought.get(purchaseQuantity-1).getName());
                break;
            case 2:
                productsBought.add(products[1]);
                System.out.println("Bạn đã chọn mua " + productsBought.get(purchaseQuantity-1).getName());
                break;
            case 3:
                productsBought.add(products[2]);
                System.out.println("Bạn đã chọn mua " + productsBought.get(purchaseQuantity-1).getName());
                break;
            default:
                System.out.println("Bạn nhập sai thông tin, vui lòng nhập lại");
                selectProduct();
        }
    }

    public void calculateChange() {
        int change = moneyAfterBuy - productsBought.get(purchaseQuantity-1).getPrice();
        moneyAfterBuy = change;
        if (change < 0) {
            System.out.println("Số tiền bạn nhập không đủ mua sản phẩm này, vui lòng nhập lại!");
        }
        else if (change > 0) {
            System.out.println("Tiền thừa lại của bạn là: " + change);
        }
        else if(change == 0) {
            System.out.println("Bạn đã nhập đủ số tiền!");
        }
    }

    public void printBill() {
        System.out.println("---------------------------");
        System.out.println("Mua hàng thành công!!!");
        System.out.println("HÓA ĐƠN MUA HÀNG");
        System.out.println("Tên sản phẩm   " + "Giá");
        for (Product product: productsBought) {
            System.out.println(product.getName() + "           " + product.getPrice());
        }
        System.out.println("Xin cảm ơn");
        System.out.println("---------------------------");
    }

    public boolean checkMoneyValid(int money) {
        int[] listMoney = {10000, 20000, 50000, 100000, 200000};
        boolean isValid = IntStream.of(listMoney).anyMatch(n -> n == money);
        return isValid;
    }

    public void showQuestion() {
        System.out.println("Bạn có muốn tiếp tục mua hàng không? Nếu có nhấn Y, nếu không nhấn N");
        switch (in.next().charAt(0)) {
            case 'Y':
                System.out.println("Mời bạn tiếp tục mua hàng");
                break;
            case 'N':
                System.out.println("Xin cảm ơn bạn đã mua sản phẩm của chúng tôi. Hẹn gặp lại");
                run = false;
                return;
            default:
                System.out.println("Nhập sai thông tin, vui lòng nhập lại");
                showQuestion();
        }
    }

    public void setUpPromotion() {
        int countCoke = 0, countPepsi = 0, countSoda = 0;
        if (purchaseQuantity >= 3) {
            for (int j = 0; j <= productsBought.size()-1; j++) {
                 if (productsBought.get(j).getId() == 1) {
                     countCoke++;
                     if (countCoke == 3) {
                         productPromotion = products[0];
                     }
                 }
                 else if (productsBought.get(j).getId() == 2) {
                     countPepsi++;
                     if (countPepsi == 3) {
                         productPromotion = products[1];
                     }
                 }
                 else {
                     countSoda++;
                     if (countSoda == 3) {
                         productPromotion = products[2];
                     }
                 }
            }
        }

        if (productPromotion != null) {
            int numberRandom = new Random().nextInt(10);
            if (percentChange == 10) {
                if(numberRandom == 9 && budgetPromotion + productPromotion.getPrice() < 50000) {
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("Chúc mừng bạn đã may mắn nhận được 1 sản phẩm miễn phí!!!");
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    budgetPromotion = budgetPromotion + productPromotion.getPrice();
                }
            }
            else {
                if(numberRandom < 5 && budgetPromotion + productPromotion.getPrice() < 50000) {
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("Chúc mừng bạn đã may mắn nhận được 1 sản phẩm miễn phí!!!");
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    budgetPromotion = budgetPromotion + productPromotion.getPrice();
                }
            }
        }

        boolean isNewDay = checkIsNewDay();
        if (isNewDay) {
            if (budgetPromotion < 50000) {
                percentChange = 50;
            }
            budgetPromotion = 0;
        }
    }

    public boolean checkIsNewDay() {
        SimpleDateFormat formatMinutes = new SimpleDateFormat("mm");
        String getMinutes = formatMinutes.format(new Date());

        SimpleDateFormat formatHours = new SimpleDateFormat("HH");
        String getHours = formatHours.format(new Date());

        if(getHours == "12" && getMinutes == "00") {
            return true;
        }
        return false;
    }
}
