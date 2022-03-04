package com.example.file;

import java.io.File;

public class DeleteFileUtil {
    //删除文件，可以是单个文件或文件夹

    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败：" + fileName + "文件不存在");
            return false;
        }else {
            if (file.isFile()) {
                return deleteFile(fileName);
            }else {
                return deleteDirectory(fileName);
            }
        }
    }

    public static boolean deleteDirectory(String fileName) {
        // TODO Auto-generated method stub
        if (!fileName.endsWith(File.separator)) {
            fileName = fileName + File.separator;
        }
        File file = new File(fileName);
        if (!file.exists() || !file.isDirectory()) {
            System.out.println("删除目录失败" + fileName + "目录不存在！");
            return false;
        }

        boolean flag = true;

        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("删除目录失败");
            return false;
        }

        // 删除当前目录
        if (file.delete()) {
            System.out.println("删除目录" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除目录" + fileName + "失败！");
            return false;
        }

    }

    public static boolean deleteFile(String fileName) {
        // TODO Auto-generated method stub
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        }else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

}
