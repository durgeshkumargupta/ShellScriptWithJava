package com.durgesh;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.InflaterInputStream;

public class SecondWayScriptWithJava
{
    public static void main(String[] args) {
        String[] cmd = new String[] {"\\scripts\\echo.bat"};
        ProcessBuilder builder = new ProcessBuilder();
        try {
            //Process process = builder.start();
            //Process process = builder.command("sh","-c", System.getProperty("user.dir") + "scripts/echo.sh").start();

            //Process process = builder.command(System.getProperty("user.dir") + "\\scripts\\echo.sh").start();
            Process process = builder.command(System.getProperty("user.dir") + "\\scripts\\echo.bat").start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String value = null;
            while((value = bufferedReader.readLine()) != null)
            {
                System.out.println(value);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
