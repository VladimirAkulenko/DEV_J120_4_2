/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_2.lists;

import dev_j120_4_2.models.CompanyInfo;
import dev_j120_4_2.models.ClientInfo;
import dev_j120_4_2.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class File {

    private static final String URL_CLIENT = "client.dat";
    private static final String URL_COMPANY = "companies.dat";
    private static final String[] URL = {URL_CLIENT, URL_COMPANY};


    public static String getURL_CLIENT() {
        return URL_CLIENT;
    }
    public static String getURL_COMPANY() {
        return URL_COMPANY;
    }

    public void saveClientsToFile(List<ClientInfo> client, List<CompanyInfo> companies) throws IOException {

        for(String url : URL)
        {
            Path path = Paths.get(url);
            if(!path.isAbsolute())
                path = path.toAbsolutePath();
            Path dir = path.getParent();
            if(!Files.isDirectory(dir))
                Files.createDirectories(dir);
            if(!Files.exists(path))
                Files.createFile(path);
            List<String> listString = new ArrayList<>();
            (url.equals(URL_CLIENT)? client : companies).forEach((s) -> {
                listString.add(s.toString());
            });
            Files.write(path, listString, StandardCharsets.UTF_8);
        }
    }

    public Map<String, List<String>> extractClientsFromFile() throws IOException{

        Map<String, List<String>> sourceMap = new HashMap<>();
        List<String> sourceList;

        for (String url : URL) {
            Path path = Paths.get(url);
            if(!path.isAbsolute())
                path = path.toAbsolutePath();
            if(!Files.exists(path))
                throw new IOException
                        ("The source data file was not found. The client database is empty.");
            sourceList = Files.readAllLines(path);
            sourceList = Utils.killerBOM(sourceList);
            if (url.equals(URL_CLIENT)) {
                sourceMap.put(URL_CLIENT, sourceList);
            } else {
                sourceMap.put(URL_COMPANY, sourceList);
            }
        }
        return sourceMap;
    }
}
