package cloud.codechun.tutu.api.aliyunai;

import java.util.Arrays;
import java.util.HashMap;

import cloud.codechun.tutu.exception.BusinessException;
import cloud.codechun.tutu.exception.ErrorCode;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//单张图片判断条码清不清晰
//todo未改ai提示词

@Component
public class TiaomaDws {


    @Value("${aliYunAi.apiKey}")
    String apiKey;

    public  String callWithLocalFile(String localPath)
            throws ApiException, NoApiKeyException, UploadFileException {
        String filePath = "file:///"+localPath;
        MultiModalConversation conv = new MultiModalConversation();
        MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                .content(Arrays.asList(new HashMap<String, Object>(){{put("image", filePath);}},
                        new HashMap<String, Object>(){{put("text", "\"分析图片上的面单有无清晰的条码，如果条码清晰完整回答y，如果没有回答n，\" +\n" +
                                "                                \"注意：\" +\n" +
                                "                                \"1.条码可能被遮挡或者破损，遮挡和破损的面单回答n\" +\n" +
                                "                                \"2.只需要回答，不需要过程\"");}})).build();


        MultiModalConversationParam param = MultiModalConversationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                // 新加坡和北京地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                .apiKey(apiKey)
                .model("qwen3-vl-plus")  // 此处以qwen3-vl-plus为例，可按需更换模型名称。模型列表：https://help.aliyun.com/zh/model-studio/models
                .messages(Arrays.asList(userMessage))
                .build();
        MultiModalConversationResult result = conv.call(param);
        String answer = result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text").toString();
        System.out.println(answer);
        return   answer;
    }



    //传入需要分析图片的绝对路径，返回ai回答
    public  String run(String localPath) {


        //这里填图片的绝对地址
//        String s1 = changePath("C:\\Users\\17763\\IdeaProjects\\tutu\\src\\main\\java\\1ac0443f-8f37-4260-9138-6315e9317caf\\34_BD_20_44_6B_45-20251127215859322.jpg");

        String s1 = changePath(localPath);

        try {

            String result = callWithLocalFile(s1);
            return result;
        } catch (ApiException | NoApiKeyException | UploadFileException e) {
            System.out.println(e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

    }

    String changePath(String path1)
    {
        String windowsPath = path1;
        String unixPath = windowsPath.replace("\\", "/");
        return unixPath;
    }
}