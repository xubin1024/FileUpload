package com.javaWeb.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * ClassName:fileServlet
 * Package:com.javaWeb.servlets
 * Description:<br/>
 *
 * @date:2019/11/9
 * @author:
 */

public class fileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respose) throws ServletException, IOException {
        System.out.println("来了嘛");
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            throw new RuntimeException("不是正确请求方式");
        }


        // Configure a repository (to ensure a secure temp location is used)
        /*ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);*/


        try {
            // Create a factory for disk-based file items创建fileitems工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();

            //设置临时文件
            String tempPath=this.getServletContext().getRealPath("/temp");

            File temp=new File(tempPath);
            if (!temp.exists()){
                temp.mkdirs();
            }
            factory.setRepository(temp);

            // Create a new file upload handler创建文件上传核心组件
            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setHeaderEncoding("UTF-8");
            //  解析请求，获取到所有的item
            List<FileItem> items = upload.parseRequest(request);

            for (FileItem item:items){
                if (item.isFormField()){//普通表单项
                    String fieldName=item.getFieldName();
                    System.out.println(fieldName);
                    String fieldValue=item.getString();
                    System.out.println(fieldValue);
                }else {                 //不是普通表单项
//                    String fieldName=item.getFieldName();
                    String fileName=item.getName();//获取文件名
                    //输入流，获取文件的内容
                    InputStream is=item.getInputStream();
                    //获取文件保存在服务器的路径
                    String realPath = this.getServletContext().getRealPath("/images");

                    System.out.println(realPath);

                    //创建目标文件，将来用于保存于上传文件
                    File filePath=new File(realPath);
                    File file=new File(realPath,fileName);
                    //创建文件输出流
                    if(!filePath.exists()){
                        filePath.mkdirs();
                    }
                    OutputStream os = new FileOutputStream(file);
                    int length= -1;
                    byte[] bytes=new byte[1024];
                    while ((length=is.read(bytes)) != -1){
                        os.write(bytes,0,length);
                    }

                    os.close();
                    is.close();
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }


    }
}
