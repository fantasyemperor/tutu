package cloud.codechun.tutu.api.aliyunai;

import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.alibaba.dashscope.aigc.multimodalconversation.*;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;

//多张图片本地base64传入
public class Main4 {

// 若使用新加坡地域的模型，请取消下列注释
// static {Constants.baseHttpApiUrl="https://dashscope-intl.aliyuncs.com/api/v1";}

    private static String encodeImageToBase64(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    static String s1= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\java\\1ac0443f-8f37-4260-9138-6315e9317caf\\34_BD_20_44_6B_4D-20251127215859276.jpg");
    static String s2= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\java\\1ac0443f-8f37-4260-9138-6315e9317caf\\34_BD_20_44_6B_8F-20251127215859305.jpg");
    static String s3= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\java\\1ac0443f-8f37-4260-9138-6315e9317caf\\34_BD_20_44_6B_45-20251127215859322.jpg");
    static String s4= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\java\\1ac0443f-8f37-4260-9138-6315e9317caf\\34_BD_20_44_6B_51-20251127215859186.jpg");
    static String s5= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\java\\1ac0443f-8f37-4260-9138-6315e9317caf\\34_BD_20_44_6B_79-20251127215859369.jpg");

    public static void videoImageListSample(String localPath1,String localPath2,String localPath3,String localPath4,String localPath5)
            throws ApiException, NoApiKeyException, UploadFileException, IOException {

        String base64Image1 = encodeImageToBase64(localPath1); // Base64编码
        String base64Image2 = encodeImageToBase64(localPath2);
        String base64Image3 = encodeImageToBase64(localPath3);
        String base64Image4 = encodeImageToBase64(localPath4);
        String base64Image5 = encodeImageToBase64(localPath5);

        MultiModalConversation conv = new MultiModalConversation();
        Map<String, Object> params = Map.of(
                "video", Arrays.asList(
                        "data:image/jpeg;base64," + base64Image1,
                        "data:image/jpeg;base64," + base64Image2,
                        "data:image/jpeg;base64," + base64Image3,
                        "data:image/jpeg;base64," + base64Image4,
                        "data:image/jpeg;base64," + base64Image5
                ),
                // 若模型属于Qwen2.5-VL系列且传入图像列表时，可设置fps参数，表示图像列表是由原视频每隔 1/fps 秒抽取的，其他模型设置则不生效
                    "fps",2
        );
        MultiModalMessage userMessage = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(Arrays.asList(params,
                        Collections.singletonMap("text", "五张图片是一个快递除了底面的五个面，分析图片没有读到面单条码的原因，原因只能是以下， 1面单朝下：图片没有看到面单，图片有类似白色纸张也不属于此原因 五张图片都没有看到面单才有可能是此原因 2面单褶皱与破损，面单上有任何褶皱破损不清晰均属于这一条 3异形件：物体不是规则的快递包裹 4面单未识别：面单平滑完整没有任何褶皱，没有任何遮挡，且条码清晰可见 5其他：不属于以上四条  只回答原因")))
                .build();

        MultiModalConversationParam param = MultiModalConversationParam.builder()
                // 新加坡和北京地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                .apiKey("sk-d563d84432c44bd5a720b0794dbd3659")
                .model("qwen-vl-max")
                .messages(Arrays.asList(userMessage))
                .build();

        MultiModalConversationResult result = conv.call(param);
        System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text"));
    }

    public static void main(String[] args) {
        try {
            // 将 xxx/football1.png 等替换为你本地图像的绝对路径
            videoImageListSample(
                    s1,
                    s2,
                    s3,
                    s4,
                    s5
            );
        } catch (ApiException | NoApiKeyException | UploadFileException | IOException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    static String changePath(String path1)
    {
        String windowsPath = path1;
        String unixPath = windowsPath.replace("\\", "/");
        return unixPath;
    }
}