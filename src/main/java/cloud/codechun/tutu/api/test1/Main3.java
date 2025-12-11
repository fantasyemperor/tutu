package cloud.codechun.tutu.api.test1;// DashScope SDK版本需要不低于2.18.3
import java.util.Arrays;
import java.util.Map;
import java.util.Collections;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;

//文件路径传入
public class Main3 {

// 若使用新加坡地域的模型，请取消下列注释
//  static {Constants.baseHttpApiUrl="https://dashscope-intl.aliyuncs.com/api/v1";}

    String s1= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\picture\\34_BD_20_44_6B_2D-20251124215759123.jpg");
    String s2= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\picture\\34_BD_20_44_6B_C9-20251124215759232.jpg");
    String s3= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\picture\\34_BD_20_44_6B_CB-20251124215759088.jpg");
    String s4= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\picture\\34_BD_20_44_6B_CF-20251124215759200.jpg");
    String s5= changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\picture\\34_BD_20_44_6C_33-20251124215759091.jpg");

    private static final String MODEL_NAME = "qwen3-vl-plus";  // 此处以qwen3-vl-plus为例，可按需更换模型名称。模型列表：https://help.aliyun.com/zh/model-studio/models
    public static void videoImageListSample(String localPath1, String localPath2, String localPath3, String localPath4,String localPath5)
            throws ApiException, NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        String filePath1 = "file:///" + localPath1;
        String filePath2 = "file:///" + localPath2;
        String filePath3 = "file:///" + localPath3;
        String filePath4 = "file:///" + localPath4;
        String filePath5 = "file:///" + localPath4;
        Map<String, Object> params = Map.of(
                "video", Arrays.asList(filePath1,filePath2,filePath3,filePath4),
                // 若模型属于Qwen2.5-VL系列且传入图像列表时，可设置fps参数，表示图像列表是由原视频每隔 1/fps 秒抽取的，其他模型设置则不生效
                "fps",2);
        MultiModalMessage userMessage = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(Arrays.asList(params,
                        Collections.singletonMap("text", "这五张图片是一个物体的除了底面的五个面，请描述一下这个物体")))
                .build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                // 新加坡和北京地域的API Key不同。获取API Key：https://www.alibabacloud.com/help/zh/model-studio/get-api-key
                .apiKey("sk-d563d84432c44bd5a720b0794dbd3659")
                .model(MODEL_NAME)
                .messages(Arrays.asList(userMessage)).build();
        MultiModalConversationResult result = conv.call(param);
        System.out.print(result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text"));
    }
    public static void main(String[] args) {


        try {
            videoImageListSample(
                    "s1",
                    "s2",
                    "s3",
                    "s4",
                    "s5");
        } catch (ApiException | NoApiKeyException | UploadFileException e) {
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