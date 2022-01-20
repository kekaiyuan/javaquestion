package com.kky.juc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProducerAndConsumer {

}

class Product {
    List<String> productList = new ArrayList<>();

    public synchronized void putProduct(String product){
        productList.add(product);
    }

    public synchronized void getProduct(){
//        productList.
    }
}
