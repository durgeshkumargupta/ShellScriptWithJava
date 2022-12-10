package com.durgesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class FirstWayScriptWithJava
{
    private static class ProcessReader implements Callable
    {

        private InputStream inputStream;
        public ProcessReader(InputStream inputStream)
        {
            this.inputStream = inputStream;
        }

        @Override
        public Object call() throws Exception {
            return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList());
        }
    }
    public static void main(String[] args) {
        boolean isWindow = System.getProperty("os.name").toLowerCase().startsWith("windows");

        ProcessBuilder builder = new ProcessBuilder();

        if(isWindow)
        {
            builder.command(System.getProperty("user.dir") + "\\scripts\\echo.bat");
        }else{
            builder.command("sh","-c", System.getProperty("user.dir") + "scripts/echo.sh");
        }

        ExecutorService pool = Executors.newSingleThreadExecutor();

        try {
            Process process = builder.start();
            ProcessReader task = new ProcessReader(process.getInputStream());
            Future<List<String>> future = pool.submit(task);

            List<String> results = future.get();

            for(String res : results)
            {
                System.out.println(res);
            }
            int exitCode = process.waitFor();
            System.out.println("Exit Code: "+exitCode);
        }catch (IOException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }
    }
}
