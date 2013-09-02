package com.grexdev.pimabank.menuprovider.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

@Slf4j
@RequiredArgsConstructor
public class HttpPageFromClasspathServer {

    private Container container = new Container() {

        @Override
        public void handle(Request request, Response response) {
            try (OutputStream outputStream = response.getOutputStream()) {
                String requestPath = request.getPath().getPath();
                
                if (requestPath.equals("/")) {
                    requestPath = "/index.html";
                }
                
                if (requestPath.endsWith(".html") == false) {
                    requestPath = requestPath + ".html";
                }
                
                InputStream pageContent = getPageContent(requestPath);
                IOUtils.copy(pageContent, outputStream);
                
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        private InputStream getPageContent(String pageName) {
            ClassLoader classloader = this.getClass().getClassLoader();
            String pagePath = pageSourceRoot + pageName;
            log.info("Serving HTTP content from classpath entry {}", pagePath);
            return classloader.getResourceAsStream(pagePath);
        }
    };

    private final String pageSourceRoot;
    
    private Server server;

    private Connection connection;

    public void startServer(int port) throws IOException {
        server = new ContainerServer(container);
        connection = new SocketConnection(server);
        SocketAddress address = new InetSocketAddress(port);
        connection.connect(address);
    }

    public void stopServer() throws IOException {
        server.stop();
        connection.close();
    }

}