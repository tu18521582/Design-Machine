package com.company;

public class Message {
    public static void showMessageWelcome() {
        System.out.println("***************************");
        System.out.println("** Xin chào quý khách!!! **");
        System.out.println("***************************");
    }

    public static void showMessageCongratulate(Product productPromotion) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Chúc mừng bạn đã may mắn nhận được 1 sản phẩm " + productPromotion.getName() + " miễn phí!!!");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void showMessageInputError() {
        System.out.println("********************************************************");
        System.out.println("** Bạn nhập thông tin không hợp lệ, vui lòng nhập lại **");
        System.out.println("********************************************************");
    }
}
