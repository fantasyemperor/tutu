package cloud.codechun.tutu.service.impl;

import cloud.codechun.tutu.api.aliyunai.MiandanDws;
import cloud.codechun.tutu.api.aliyunai.TiaomaDws;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cloud.codechun.tutu.model.entity.Errordws;
import cloud.codechun.tutu.service.ErrordwsService;
import cloud.codechun.tutu.mapper.ErrordwsMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
* @author 17763
* @description 针对表【errorDws】的数据库操作Service实现
* @createDate 2025-11-30 15:55:14
*/
@Service
@Slf4j
public class ErrordwsServiceImpl extends ServiceImpl<ErrordwsMapper, Errordws>
    implements ErrordwsService{

    @Autowired
    private MiandanDws  miandanDws;
    @Autowired
    private TiaomaDws tiaomaDws;


    @Value("${aliYunAi.apiKey}")
    String apiKey;

    @Autowired
    private ErrordwsMapper errordwsMapper;

    @Resource
    // 自动注入一个线程池的实例
    private ThreadPoolExecutor threadPoolExecutor;




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


    /**
     *  使用线程池 调用ai分析  以下
     */



        String encodeImageToBase64(String imagePath) throws IOException {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(imageBytes);
        }


        void imageListHandle(String localPath1,String localPath2,String localPath3,String localPath4,String localPath5,String filename,String pName)
                throws ApiException, NoApiKeyException, UploadFileException, IOException {

            String base64Image1 = encodeImageToBase64(localPath1); // Base64编码
            String base64Image2 = encodeImageToBase64(localPath2);
            String base64Image3 = encodeImageToBase64(localPath3);
            String base64Image4 = encodeImageToBase64(localPath4);
            String base64Image5 = encodeImageToBase64(localPath5);


            MultiModalConversation conv = new MultiModalConversation();

            List<Map<String, Object>> content = new ArrayList<>();




            content.add(Map.of("image", "data:image/jpeg;base64," + base64Image1));
            content.add(Map.of("image", "data:image/jpeg;base64," + base64Image2));
            content.add(Map.of("image", "data:image/jpeg;base64," + base64Image3));
            content.add(Map.of("image", "data:image/jpeg;base64," + base64Image4));
            content.add(Map.of("image", "data:image/jpeg;base64," + base64Image5));

// 添加文本指令
            content.add(Map.of("text", "按顺序分析每一张图片并回答\n" +
                    "\n" +
                    "严格按照以下逻辑按顺序判断每张图片\n" +
                    "1.是否是规则长方体包裹\n" +
                    "否 ->回答1异形件并停止分析\n" +
                    "是 ->继续分析 \n" +
                    "2.是否有面单\n" +
                    "否->判断下一张图片，如果是最后一张则回答2无面单并停止分析 \n" +
                    "有->继续分析\n" +
                    "3.面单是否褶皱或者破损或者被遮挡\n" +
                    "是->回答3面单褶皱或者破损或者被遮挡并停止分析\n" +
                    "否->继续分析\n" +
                    "4.面单是否能清晰的读到条码\n" +
                    "是->回答4条码清晰并停止分析\n" +
                    "否->回答3面单褶皱破损并停止分析\n" +
                    "\n" +
                    "注意：\n" +
                    "1.面单可能破损褶皱导致面单不是规则的长方形，破损褶皱的面单是有面单" +
                    "2.只需要回答不需要给出分析\n" +
                    "3.停止分析就给出回答然后立即结束" +
                    "4.一共只回答一次" +
                    "5.棍状长条状物体不属于规则长方体包裹"));

            MultiModalMessage userMessage = MultiModalMessage.builder()
                    .role(Role.USER.getValue())
                    .content(content)
                    .build();


            MultiModalConversationParam param = MultiModalConversationParam.builder()
                    // 新加坡和北京地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                    .apiKey(apiKey)
                    .model("qwen-vl-plus")
                    .messages(Arrays.asList(userMessage))
                    .build();

            MultiModalConversationResult result = conv.call(param);
            String answer = result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text").toString();
            System.out.println(answer);
            char firstChar = answer.charAt(0);


            Errordws errordws = new Errordws();
            errordws.setName(filename);
            errordws.setReason(firstChar);
            errordws.setPname(pName);


            errordwsMapper.insert(errordws);

        }


        //遍历父文件夹
        void processParentFolder(String parentFolder) {
            File parent = new File(parentFolder);
            String parentName = parent.getName();



            File[] subFolders = parent.listFiles(File::isDirectory);

            if (subFolders == null) return;


            List<Future<String>> futures = new ArrayList<>();

            for (File subFolder : subFolders) {

                CompletableFuture.runAsync(() -> {
                    // 打印一条日志信息，包括任务名称和执行线程的名称
                    log.info("任务执行中：" + subFolder + "，执行人：" + Thread.currentThread().getName());
                    try {

                        analyzeByAi(subFolder, parentName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 异步任务在threadPoolExecutor中执行
                }, threadPoolExecutor);



            }


        }


       @Override
        public void  run(String path) {
            try {

                //文件夹绝对路径
//                String parentFolder = "C:\\Users\\17763\\Desktop\\222\\2025-11-29-2-1\\2-DWS-4";
                String parentFolder = path;
                processParentFolder(parentFolder);



            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }


    /**
     * 调用 AI 分析单个子文件夹（示意）
     */
    private String analyzeByAi(File folder,String parentName) throws InterruptedException {

                        File[] images = folder.listFiles((dir, name) ->
                        name.toLowerCase().endsWith(".jpg") ||
                                name.toLowerCase().endsWith(".jpeg") ||
                                name.toLowerCase().endsWith(".png")
                        );

        if (images == null || images.length < 5) {
            System.out.println("子文件夹 " + folder.getName() + " 图片不足五张，跳过。");
            return "跳过：" + folder.getName();
        }
        // 按名字排序，确保固定顺序
        Arrays.sort(images);

        System.out.println(folder.getName());



        try {

//                System.out.println(images[0].getAbsolutePath().replace("\\", "/"));
            imageListHandle(
                    images[0].getAbsolutePath().replace("\\", "/"),
                    images[1].getAbsolutePath().replace("\\", "/"),
                    images[2].getAbsolutePath().replace("\\", "/"),
                    images[3].getAbsolutePath().replace("\\", "/"),
                    images[4].getAbsolutePath().replace("\\", "/"),
                    folder.getName(),parentName
            );


        } catch (Exception e) {
            System.err.println("处理子文件夹时出错: " + folder.getName());
            e.printStackTrace();
        }

        return "完成分析：" + folder.getName();
    }
    }







