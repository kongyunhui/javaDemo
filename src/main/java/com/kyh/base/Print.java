package com.kyh.base;

/**
 * Created by kongyunhui on 2017/11/17.
 */
public class Print {
    public static void main(String[] args){
        String str ="{\n" +
                "  \"applicationName\": \"aa\",\n" +
                "  \"applicationVer\": \"1.0\",\n" +
                "  \"attrInfos\": [\n" +
                "    {\n" +
                "      \"attrName\": \" MANAGEMENT_PORT\",\n" +
                "      \"attrValue\": \" 9999\",\n" +
                "      \"imageAttrId\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"autoDeploy\": 1,\n" +
                "  \"autoUpdate\": 1,\n" +
                "  \"configChangedPolicy\": \"B\",\n" +
                "  \"containerInfos\": [\n" +
                "    {\n" +
                "      \"image\": \"10.45.80.1/public/nginx:1.11.10\",\n" +
                "      \"imagePullPolicy\": \"B\",\n" +
                "      \"ports\": [\n" +
                "        {\n" +
                "          \"containerPort\": 80,\n" +
                "\t\t\t  \"targetPortNumber\": 80\n" +
                "        }\n" +
                "      ],\n" +
                "      \"resource\": {\n" +
                "        \"cpu\": 0.1,\n" +
                "        \"cpuLimit\": 4,\n" +
                "        \"memory\": 512,\n" +
                "        \"memoryLimit\": 4096\n" +
                "      },\n" +
                "      \"volumeInfos\": [\n" +
                "        \n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"description\": \"testinstance\",\n" +
                "  \"iconUrl\": \"http://xx.png\",\n" +
                "  \"probeEnabled\": 1,\n" +
                "  \"projectId\": 561923,\n" +
                "  \"publishType\": \"A\",\n" +
                "  \"refreshConfigCmd\": \"nginx -s reload\",\n" +
                "  \"replicas\": 1,\n" +
                "  \"restartPolicy\": \"A\",\n" +
                "  \"stateful\": 0,\n" +
                "  \"tenantId\": 1,\n" +
                "  \"useLocalTZ\": 1,\n" +
                "  \"userId\": 2,\n" +
                "  \"zoneId\": 9\n" +
                "}";
        System.out.println(str.replace("\t","").replace("\n", "").replace(" ", ""));

        String res = "";
    }
}
