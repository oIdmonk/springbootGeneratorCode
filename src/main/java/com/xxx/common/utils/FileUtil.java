package com.xxx.common.utils;

import com.xxx.common.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * 文件操作工具类（包含文件上传）
 *
 * @author xujingyang
 * @date 2018/05/28
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);



    /**
     * 获取文件类型
     *
     * @param fileName
     * @return
     */
    public static String getFileExtName(String fileName) {
        if (fileName != null) {
            int i = fileName.lastIndexOf('.');
            if (i > -1) {
                return fileName.substring(i + 1).toLowerCase();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return Boolean
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param file 文件file
     * @return Boolean
     */
    public static boolean deleteFile(File file) {
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件成功！");
                return true;
            } else {
                System.out.println("删除单个文件失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：文件不存在！");
            return false;
        }
    }


    /**
     * 美化文件显示大小 GB,MB..
     *
     * @param size
     * @return
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 指定格式美化文件显示大小 GB,MB..
     *
     * @param size 大小
     * @param type 类型
     * @return
     */
    public static String convertFileSizeToString(long size, String type) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (type.toLowerCase().equals("gb")) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (type.toLowerCase().equals("mb")) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (type.toLowerCase().equals("kb")) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    /**
     * 指定格式美化文件显示大小 GB,MB..
     *
     * @param size 大小
     * @param type 类型
     * @return
     */
    public static float convertFileSizeToFloat(long size, String type) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (type.toLowerCase().equals("gb")) {
            return (float) size / gb;
        } else if (type.toLowerCase().equals("mb")) {
            return (float) size / mb;
        } else if (type.toLowerCase().equals("kb")) {
            return (float) size / kb;
        } else {
            return (float) size;
        }
    }


    /**
     * 上传文件 (目录结构：baseDir/year/month/day/file)
     *
     * @param request
     * @param baseDir 上传位置（指定文件夹全路径 ex:E:/myimgdir）
     * @param request
     * @param baseDir
     * @return 返回文件指向服务器的地址集合
     * @throws IOException
     */
    public static List<String> uploadFile(HttpServletRequest request, String baseDir) throws IOException {

        List<String> strList = new ArrayList<>();

        //记录上传过程起始时的时间，用来计算上传时间
        int pre = (int) System.currentTimeMillis();


        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int day = cal.get(cal.DATE);

        String dirPath = baseDir + "/" + year + "/" + month + "/" + day;


        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //取得上传文件
                List<MultipartFile> files = multiRequest.getFiles(iter.next());
                for (MultipartFile file : files) {
                    if (file != null) {
                        //取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (!myFileName.trim().isEmpty()) {

                            logger.debug(myFileName);

                            //重命名上传后的文件名
                            String fileName = PrimaryKeyUtil.nextId() + "_" + file.getOriginalFilename();
                            //定义上传路径
                            File localFile = new File(dirPath, fileName);
                            if (!localFile.getParentFile().exists()) {
                                localFile.getParentFile().mkdirs();
                            }
                            file.transferTo(localFile);

                            strList.add(Const.BASE_PATH + "file/getFile?path=" + dirPath + "/" + fileName);
                        }
                    }
                }

            }
        }


        //记录上传该文件后的时间
        int finaltime = (int) System.currentTimeMillis();
        logger.debug((finaltime - pre) + "");

        return strList;
    }

}