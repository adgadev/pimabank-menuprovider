package com.grexdev.pimabank.menuprovider.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class HttpWebPageFetcher {

    public InputStream downloadPage(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(15000);
        connection.setConnectTimeout(15000);

        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            try {
                inputStream = connection.getInputStream();
                outputStream = new ByteArrayOutputStream();
                IOUtils.copy(inputStream, outputStream);

                return new ByteArrayInputStream(outputStream.toByteArray());

            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }

        } else {
            throw new RuntimeException("Invalid HTTP response code :" + connection.getResponseCode());
        }
    }
}
