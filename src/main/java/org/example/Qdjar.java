package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classname: Qdjar
 * Package: org.example
 * Description:
 *
 * @Author dxw
 * @Create 2023/12/4 13:18
 * Version 1.0
 */
public class Qdjar {
    public static void main(String[] args) throws Exception {
        // 替换为您的 JAR 包路径
        String jarPath = "D:\\desktop\\boosgit\\hd--ips\\target\\Dxw-ips-35808.jar";
//        定义命令行
        String command = "java -jar " + jarPath;
//        se2(jarPath, command);
//        se(jarPath);
//        killPort(35808);
        executeBat("D:/desktop/SQL2/bat/killPort.bat");
        System.out.println("command = " + command);
    }

    public static void se(String jarPath) throws Exception {
        // 定义命令行参数
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
//        String jarPath = "path/to/your/application.jar"; // 替换为您的 JAR 包路径
        String[] command = {javaBin, "-jar", jarPath};

        // 创建 ProcessBuilder 对象
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // 启动服务
        try {
            Process process = processBuilder.start();
            // 可以在这里获取输出流并进行操作
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void se2(String jarPath, String command) throws Exception {
//        String jarPath = "path/to/your/application.jar"; // 替换为您的 JAR 包路径

        // 定义命令行
//        String command = "java -jar " + jarPath;

        // 启动服务
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void killPort(Integer portNumber) throws Exception {
        System.out.println("portNumber = " + portNumber);

        try {
            // 构造命令查询指定端口的进程信息
            String command = "netstat -b -a -n -o | findstr " + portNumber;
            Process process = Runtime.getRuntime().exec(command);

            // 读取进程输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // 打印 netstat 命令的输出

                // 获取进程的 PID
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length > 4) {
                    String pid = tokens[tokens.length - 1];

                    // 构造命令杀掉指定 PID 的进程
                    String killCommand = "taskkill /F /PID " + pid;
                    Process killProcess = Runtime.getRuntime().exec(killCommand);

                    // 等待命令执行完毕
                    int exitCode = killProcess.waitFor();
                    if (exitCode == 0) {
                        System.out.println("成功杀掉 PID 为 " + pid + " 的进程。");
                    } else {
                        System.out.println("无法杀掉 PID 为 " + pid + " 的进程。");
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 执行批处理 "D:/1.bat",只需要传入绝对路劲
     *
     * @return
     */
    public static String executeBat(String strcmd) throws Exception {

        Runtime rt = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
        Process ps = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
        try {
            ps = rt.exec(strcmd);   //该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
            ps.waitFor();  //等待子进程完成再往下执行。
        } catch (IOException e1) {

            System.out.println("e1 = " + e1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("e = " + e);
        }
        int i = ps.exitValue();  //接收执行完毕的返回值
        String status = "0";
        if (i == 0) {
            System.out.println("执行完成.");
            status = "1";
        } else {
            System.out.println("执行失败.");
        }
        ps.destroy();  //销毁子进程
        ps = null;
        return status;
    }
}


