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
    private Money moneyInserted = new Money(0);
    private ArrayList<Product> productsBought = new ArrayList<>();
    private int budgetPromotion = 0;
    private Product productPromotion;

    int remainingMoney = 0;
    int percentChange = 10;
    Scanner in = new Scanner(System.in);

    public Machine() {
        products = new Product[3];
        products[0] = new Product(1,"Coke", 10000);
        products[1] = new Product(2,"Pepsi", 10000);
        products[2] = new Product(3,"Soda", 20000);
    }

    public void startMachine() {
        run = true;

        boolean isNewDay = checkIsNewDay();
        //if new day start, reset promotion
        if (isNewDay) {
            if (budgetPromotion < 50000) {
                percentChange = 50;
            }
            else {
                percentChange = 10;
            }
            budgetPromotion = 0;
            productPromotion = null;
        }
        insertMoney();
        while (run) {
            showProducts();
            selectProduct();
            calculateChange();
        }
        printBill();
    }

    public void stopMachine() {
        System.out.println("Tiền trả lại bạn là: " + remainingMoney);
        run = false;
        System.exit(0);
    }

    public void insertMoney() {
        System.out.println("Vui lòng nhập số tiền với các mệnh giá 10000, 20000, 50000, 100000, 200000");
        int moneyInput = in.nextInt();
        if(checkMoneyValid(moneyInput)) {
            System.out.println("Bạn đã nhập số tiền là: " + moneyInput);
            moneyInserted.setVal(remainingMoney + moneyInput);
            remainingMoney = moneyInserted.getVal();
            System.out.println("Số tiền hiện tại của bạn là: " + remainingMoney);
        }
        else {
            Message.showMessageInputError();
            insertMoney();
        }
    }

    public void showProducts() {
        System.out.println("Danh sách sản phẩm: ");
        for (Product product: products) {
            System.out.println(product.getId() +"         "+ product.getName() + "         " + product.getPrice() + " vnd");
        }
    }

    public void selectProduct() {
        System.out.println("------Mời bạn chọn sản phẩm------");
        System.out.println("* Nếu muốn thoát hãy nhập 0");
        System.out.println("* Nhập 1 để mua Coke, 2 để mua Pepsi, 3 để mua Soda");
        System.out.println("* Nếu muốn nhập thêm tiền hãy nhập 4");
        switch (in.nextInt()) {
            case 0:
                stopMachine();
                break;
            case 1:
                productsBought.add(products[0]);
                System.out.println("Bạn đã chọn mua " + productsBought.get(productsBought.size()-1).getName());
                break;
            case 2:
                productsBought.add(products[1]);
                System.out.println("Bạn đã chọn mua " + productsBought.get(productsBought.size()-1).getName());
                break;
            case 3:
                productsBought.add(products[2]);
                System.out.println("Bạn đã chọn mua " + productsBought.get(productsBought.size()-1).getName());
                break;
            case 4:
                insertMoney();
                break;
            default:
                Message.showMessageInputError();
                selectProduct();
        }
    }

    public void calculateChange() {
        //productsBought not empty
        remainingMoney = productsBought.size() > 0
                ? remainingMoney - productsBought.get(productsBought.size()-1).getPrice()
                : moneyInserted.getVal();
        int indexProductCurrent = productsBought.size();
        if (indexProductCurrent != 0) {
            if (remainingMoney > 0) {
                System.out.println("Hiện tại tiền trong máy của bạn còn lại là: " + remainingMoney);
                releaseSelectedProduct(productsBought.get(productsBought.size()-1));
                showQuestion();
            }
            else if (remainingMoney < 0) {
                System.out.println("Rất tiếc, số tiền hiện tại của bạn không đủ, vui lòng nhập thêm để mua sản phẩm này.");
                productsBought.remove(productsBought.size()-1);
                remainingMoney = Math.abs(remainingMoney);
                showQuestion();
            }
            else if(remainingMoney == 0) {
                System.out.println("Bạn đã nhập đủ số tiền để mua sản phẩm này! Nếu muốn mua thêm vui lòng nhập thêm tiền");
                releaseSelectedProduct(productsBought.get(productsBought.size()-1));
                showQuestion();
            }
        }
    }

    public void releaseSelectedProduct(Product product) {
        System.out.println("-------------------------------------------");
        System.out.println("Xin mời bạn nhận sản phẩm " + product.getName());
        System.out.println("------------------------------------------");
        setUpPromotion();
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
        System.out.println("************************************************************************");
        System.out.println("**Bạn có muốn tiếp tục mua hàng không? Nếu có nhấn 1, nếu không nhấn 0**");
        System.out.println("************************************************************************");
        switch (in.nextInt()) {
            case 0:
                System.out.println("Xin cảm ơn bạn đã sử dụng dịch vụ của chúng tôi. Hẹn gặp lại");
                if (productsBought.size()!=0) {
                    printBill();
                }
                stopMachine();
                return;
            case 1:
                System.out.println("Mời bạn tiếp tục mua hàng");
                if(remainingMoney <= 0) {
                    System.out.println("Số tiền hiện tại của bạn không đủ, vui lòng nhập thêm tiền");
                    insertMoney();
                }
                break;
            default:
                Message.showMessageInputError();
                showQuestion();
        }
    }

    public void setUpPromotion() {
        int countCoke = 0, countPepsi = 0, countSoda = 0;
        if (productsBought.size() >= 3) {
            for (int j = 0; j <= productsBought.size() - 1; j++) {
                if (productsBought.get(j).getId() == 1) {
                    countCoke++;
                    if (countCoke == 3) {
                        productPromotion = products[0];
                    }
                } else if (productsBought.get(j).getId() == 2) {
                    countPepsi++;
                    if (countPepsi == 3) {
                        productPromotion = products[1];
                    }
                } else {
                    countSoda++;
                    if (countSoda == 3) {
                        productPromotion = products[2];
                    }
                }
            }
        }

        if (productPromotion != null) {
            int numberRandom = new Random().nextInt(10);
            if (budgetPromotion + productPromotion.getPrice() < 50000) {
                if (percentChange == 10) {
                    //10% chance
                    if(numberRandom == 9) {
                        Message.showMessageCongratulate(productPromotion);
                        budgetPromotion = budgetPromotion + productPromotion.getPrice();
                    }
                }
                else {
                    //50% chance
                    if(numberRandom < 5) {
                        Message.showMessageCongratulate(productPromotion);
                        budgetPromotion = budgetPromotion + productPromotion.getPrice();
                    }
                }
            }
        }

    }

    public boolean checkIsNewDay() {
        SimpleDateFormat formatMinutes = new SimpleDateFormat("mm");
        String getMinutes = formatMinutes.format(new Date());

        SimpleDateFormat formatHours = new SimpleDateFormat("HH");
        String getHours = formatHours.format(new Date());

        if(getHours == "00" && getMinutes == "00") {
            return true;
        }
        return false;
    }
}
