package com.grexdev.pimabank.menuprovider.parser.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.RequiredArgsConstructor;

import org.apache.commons.io.IOUtils;

import com.grexdev.pimabank.menuprovider.parser.RegexParserMenuProviderConfiguration;

@RequiredArgsConstructor
public class HttpWebPageFetcher {
    
    private final RegexParserMenuProviderConfiguration configuration;

    public InputStream downloadPage(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(configuration.getHttpFetcherReadTimeout());
        connection.setConnectTimeout(configuration.getHttpFetcherConnectionTimeout());

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
