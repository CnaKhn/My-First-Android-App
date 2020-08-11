package com.cnakhn.faradarscompletion.DataModel.Product;

import android.content.Context;

import com.cnakhn.faradarscompletion.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductPullParser {
    private Context context;
    private List<Product> productList;
    private Product currentProduct;
    private String currentTag;

    public ProductPullParser(Context context) {
        this.context = context;
    }

    public List<Product> parseXml() {
        productList = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            InputStream inputStream = context.getResources().openRawResource(R.raw.flowers);
            parser.setInput(inputStream, null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    handleStartTag(parser.getName());

                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(parser.getText());

                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = null;

                }
                eventType = parser.next();
            }
            inputStream.close();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return productList;
    }

    private void handleStartTag(String tagName) {
        if ("product".equals(tagName)) {
            currentProduct = new Product();
            productList.add(currentProduct);
        } else {
            currentTag = tagName;
        }
    }

    private void handleText(String text) {
        if (currentProduct == null || currentTag == null) return;

        switch (currentTag) {
            case "productId":
                currentProduct.setId(Long.parseLong(text));
                break;

            case "category":
                currentProduct.setCategory(text);
                break;

            case "name":
                currentProduct.setName(text);
                break;

            case "instructions":
                currentProduct.setInstructions(text);
                break;

            case "price":
                currentProduct.setPrice(Double.parseDouble(text));
                break;

            case "photo":
                currentProduct.setPhoto(text);
                break;

            default:
                break;
        }
    }
}
