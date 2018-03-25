package com.example.ducvietho.moki.utils;

import com.example.ducvietho.moki.data.model.Product;

/**
 * Created by ducvietho on 23/03/2018.
 */

public interface OnClickProcessProduct {
    void acceptSell(Product product);
    void cancelSell(Product product);
}
