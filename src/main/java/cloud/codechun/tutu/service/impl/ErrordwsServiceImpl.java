package cloud.codechun.tutu.service.impl;

import cloud.codechun.tutu.api.aliyunai.MiandanDws;
import cloud.codechun.tutu.api.aliyunai.TiaomaDws;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cloud.codechun.tutu.model.entity.Errordws;
import cloud.codechun.tutu.service.ErrordwsService;
import cloud.codechun.tutu.mapper.ErrordwsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

/**
* @author 17763
* @description 针对表【errorDws】的数据库操作Service实现
* @createDate 2025-11-30 15:55:14
*/
@Service
public class ErrordwsServiceImpl extends ServiceImpl<ErrordwsMapper, Errordws>
    implements ErrordwsService{

    @Autowired
    private MiandanDws  miandanDws;
    @Autowired
    private TiaomaDws tiaomaDws;




    @Override
    public void jushi(String path) {

        //调用单张图片分析有无面单方法
//        String answer = miandanDws.run(path);


        //调用单张图片分析有无条码方法
//        String answer2 = tiaomaDws.run(path);


            File parent = new File(path);
            File[] subFolders = parent.listFiles(File::isDirectory);

            if (subFolders == null) return;

            for (File folder : subFolders) {
                File[] images = folder.listFiles((dir, name) ->
                        name.toLowerCase().endsWith(".jpg") ||
                                name.toLowerCase().endsWith(".jpeg") ||
                                name.toLowerCase().endsWith(".png")
                );

                if (images == null || images.length < 5) {
                    System.out.println("子文件夹 " + folder.getName() + " 图片不足五张，跳过。");
                    continue;
                }

                // 按名字排序，确保固定顺序
                Arrays.sort(images);

//                System.out.println(folder.getName());

                try {

//                System.out.println(images[0].getAbsolutePath().replace("\\", "/"));
                    ;
                    for(File image:images){
                        String imagePath = image.getPath();
                        //调用单张图片分析有无面单方法
                        String answer= miandanDws.run(imagePath);
                        if(answer.equals("y")){
                            // 调用单张图片分析有无条码方法
                            String answer2 = tiaomaDws.run(imagePath);
                            if(answer2.equals("y")){
                                System.out.println("有条码");

                            }else {
                                System.out.println("无条码");
                            }

                        }else {
                            System.out.println("无面单");
                        }



                    }
                    System.out.println("-------");


                } catch (Exception e) {
                    System.err.println("处理子文件夹时出错: " + folder.getName());
                    e.printStackTrace();
                }
            }
        }






}




