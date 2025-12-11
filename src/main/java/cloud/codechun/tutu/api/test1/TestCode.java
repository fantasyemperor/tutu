package cloud.codechun.tutu.api.test1;

import cloud.codechun.tutu.model.entity.Errordws;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import cloud.codechun.tutu.mapper.ErrordwsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


//有两张示例图
@Component
public class TestCode {

    @Value("${aliYunAi.apiKey}")
    String apiKey;

    @Autowired
     private ErrordwsMapper errordwsMapper;

     int z1 = 0;
     int z2 = 0;
     int z3 = 0;
     int z4 = 0;
     int z5 = 0;



    String encodeImageToBase64(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(imageBytes);
    }


    void videoImageListSample(String localPath1,String localPath2,String localPath3,String localPath4,String localPath5,String filename)
            throws ApiException, NoApiKeyException, UploadFileException, IOException {

        String base64Image1 = encodeImageToBase64(localPath1); // Base64编码
        String base64Image2 = encodeImageToBase64(localPath2);
        String base64Image3 = encodeImageToBase64(localPath3);
        String base64Image4 = encodeImageToBase64(localPath4);
        String base64Image5 = encodeImageToBase64(localPath5);

        //参考图
        String base64Image6 = encodeImageToBase64("C:/Users/17763/Desktop/duibi/B0_B3_53_6D_F1_8A-20251128045933845.jpg");
        String base64Image7 = encodeImageToBase64("C:/Users/17763/Desktop/duibi/B0_B3_53_6D_F1_54-20251128040515892.jpg");

        //C:/Users/17763/Desktop/222/2025-11-29-2-1/2-DWS-4/0096a2d7-f2aa-4e5e-8361-ec624231f6bb/34_BD_20_16_70_3D-20251130052140726.jpg


        MultiModalConversation conv = new MultiModalConversation();
//        Map<String, Object> params = Map.of(
//                "video", Arrays.asList(
//                        "data:image/jpeg;base64," + base64Image1,
//                        "data:image/jpeg;base64," + base64Image2,
//                        "data:image/jpeg;base64," + base64Image3,
//                        "data:image/jpeg;base64," + base64Image4,
//                        "data:image/jpeg;base64," + base64Image5
//                ),
//                // 若模型属于Qwen2.5-VL系列且传入图像列表时，可设置fps参数，表示图像列表是由原视频每隔 1/fps 秒抽取的，其他模型设置则不生效
//                "fps",2
//        );
//        MultiModalMessage userMessage = MultiModalMessage.builder()
//                .role(Role.USER.getValue())
//                .content(Arrays.asList(params,
//                        Collections.singletonMap("text", "五张图片是一个快递除了底面的五个面，分析图片没有读到面单条码的原因，原因只能是以下， 1面单朝下：五张图片都没有看到面单，图片上物体有类似白色纸张也不属于此原因 五张图片都没有看到面单才有可能是此原因 2面单褶皱与破损，五张图片至少存在一张图片有面单，面单上有任何褶皱破损不清晰均属于这一条 3异形件：物体不是规则的快递包裹 4面单未识别：五张图片至少有一张图片存在面单，面单平滑完整没有任何褶皱，没有任何遮挡，必须能清晰看到条码才属于这个原因 5其他：不属于以上四条  只回答原因")))
//                .build();
        List<Map<String, Object>> content = new ArrayList<>();

// 添加五张图片
        //参考图
        content.add(Map.of("image", "data:image/jpeg;base64," + base64Image6));
        content.add(Map.of("image", "data:image/jpeg;base64," + base64Image7));



        content.add(Map.of("image", "data:image/jpeg;base64," + base64Image1));
        content.add(Map.of("image", "data:image/jpeg;base64," + base64Image2));
        content.add(Map.of("image", "data:image/jpeg;base64," + base64Image3));
        content.add(Map.of("image", "data:image/jpeg;base64," + base64Image4));
        content.add(Map.of("image", "data:image/jpeg;base64," + base64Image5));

// 添加文本指令
        content.add(Map.of("text", "前两张图片是原因是面单未识别的参考图，后五张图片是一个快递除了底面的五个面，分析图片没有读到面单条码的原因，原因只能是以下：\n" +
                "1 面单朝下：五张图片都没有看到面单，图片上物体有类似白色纸张也不属于此原因，五张图片都没有看到面单和纸张类似物且不是异形件才有可能是此原因\n" +
                "2 面单褶皱与破损：五张图片至少存在一张图片有面单，面单上有任何褶皱破损不清晰均属于这一条，存在面单或者纸张类似物但是不能清晰的读出条码属于这一条\n" +
                "3 异形件：物体不是规则的快递包裹\n" +
                "4 面单未识别：五张图片至少有一张图片存在面单，面单平滑完整没有任何褶皱，没有任何遮挡，必须能清晰看到条码才属于这个原因\n" +
                "5 其他：不属于以上四条\n" +
                "只回答原因编号（1/2/3/4/5）"));

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
        if (firstChar == '1') {
            z1 = z1 + 1;
        }
        if (firstChar == '2') {
            z2 = z2 + 1;
        }
        if (firstChar == '3') {
            z3 = z3 + 1;
        }
        if (firstChar == '4') {
            z4 = z4 + 1;
        }
        if (firstChar == '5') {
            z5 = z5 + 1;
        }

        Errordws errordws = new Errordws();
        errordws.setName(filename);
        errordws.setReason(firstChar);

        errordwsMapper.insert(errordws);

    }


    void processParentFolder(String parentFolder) {
        File parent = new File(parentFolder);
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

            System.out.println("正在分析子文件夹：" + folder.getName());

            try {

                System.out.println(images[0].getAbsolutePath().replace("\\", "/"));
                videoImageListSample(
                        images[0].getAbsolutePath().replace("\\", "/"),
                        images[1].getAbsolutePath().replace("\\", "/"),
                        images[2].getAbsolutePath().replace("\\", "/"),
                        images[3].getAbsolutePath().replace("\\", "/"),
                        images[4].getAbsolutePath().replace("\\", "/"),
                        folder.getName()
                );


            } catch (Exception e) {
                System.err.println("处理子文件夹时出错: " + folder.getName());
                e.printStackTrace();
            }
        }
    }


    public void  run() {
        try {

            //文件夹绝对路径
            String parentFolder = "C:\\Users\\17763\\Desktop\\222\\2025-11-29-2-1\\2-DWS-4";
            processParentFolder(parentFolder);

            //"C:\\Users\\17763\\Desktop\\222\\2025-11-29-2-1\\2-DWS-1"
            System.out.println("1面单朝下:"+z1);
            System.out.println("2面单褶皱与破损:"+z2);
            System.out.println("3异形件:"+z3);
            System.out.println("4面单未识别:"+z4);
            System.out.println("5其他:"+z5);



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }
}
