package com.icbc.kfzx.personalfinancial.controller;



import com.baidu.aip.speech.AipSpeech;
import org.apache.commons.io.IOUtils;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Arrays;


@Controller
@RequestMapping(value="/test")
public class testController {

    /**
     * 接收微信小程序发送的字符串
     * @param model
     * @param data
     * @return
     */
    @RequestMapping(value = "/sendData/{data}")
    @ResponseBody
    String testReceice(Model model, @PathVariable(value = "data") String data){
        System.out.println("开始");
        System.out.println(data);
        return "SUCCESS-LIMK";
    }

    @RequestMapping(value = "/sendVoice",method = RequestMethod.POST)
    @ResponseBody
    String testReceice(Model model,@RequestParam("testfile") MultipartFile testfile){
        if (!testfile.isEmpty()) {
            byte[] bytes = new byte[0];
            try {

                bytes = testfile.getBytes();

                File file = new File("voice.mp3");//这里指明上传文件保存的地址
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(bytes);
                bos.close();
                fos.close();
                //boolean flag = convertMP32Pcm("voice.mp3","voice.pcm");
                //System.out.println(flag);
                return "SUCCESS-VOICE";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "FAIL-LIMK";
    }
    public static final String APP_ID = "16697658";
    public static final String API_KEY = "z8byo4jFgqSDs1vy2fTOPfhC";
    public static final String SECRET_KEY = "xy5g4bcOmaabSu5eHntUTfiRmjoLFMkq";

    @RequestMapping(value = "/toHome")
    String thymeleaf(Model model){
        model.addAttribute("name","limk");
        //convert("voice.mp3","voice.pcm", avcodec.AV_CODEC_ID_MP3,16000,9600,1);
        convert("voice.mp3","voice.wav", avcodec.AV_CODEC_ID_PCM_S16LE,16000,256000,1);
        convertAudioFiles("voice.wav","voice.pcm");
        //convert("voice.wav","voice.pcm", avcodec.AV_CODEC_ID_PCM_S16LE,16000,16,1);
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port);  //  设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        JSONObject res = client.asr("voice.pcm", "pcm", 16000, null);
        System.out.println(res.toString(2));
        System.out.println("successsssssss");
        return "hello";
    }

//    public boolean convertMP32Pcm(String mp3filepath, String pcmfilepath){
//        try {
//            //获取文件的音频流，pcm的格式
//            AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);
//            //将音频转化为  pcm的格式保存下来
//            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmfilepath));
//            return true;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    private AudioInputStream getPcmAudioInputStream(String mp3filepath) {
//        File mp3 = new File(mp3filepath);
//        AudioInputStream audioInputStream = null;
//        AudioFormat targetFormat = null;
//        try {
//            AudioInputStream in = null;
//
//            //读取音频文件的类
//            MpegAudioFileReader mp = new MpegAudioFileReader();
//            in = mp.getAudioInputStream(mp3);
//            AudioFormat baseFormat = in.getFormat();
//
//            //设定输出格式为pcm格式的音频文件
//            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
//                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
//
//            //输出到音频
//            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return audioInputStream;
//    }

public static String convertAudioFiles(String wavfilepath,String pcmfilepath){
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;
    try {
        fileInputStream = new FileInputStream(wavfilepath);
        fileOutputStream = new FileOutputStream(pcmfilepath);
        byte[] wavbyte = InputStreamToByte(fileInputStream);
        byte[] pcmbyte = Arrays.copyOfRange(wavbyte, 44, wavbyte.length);
        fileOutputStream.write(pcmbyte);
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(fileOutputStream);
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    return pcmfilepath;
}
    /**
     * 输入流转byte二进制数据
     * @param fis
     * @return
     * @throws IOException
     */
    private static byte[] InputStreamToByte(FileInputStream fis) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        long size = fis.getChannel().size();
        byte[] buffer = null;
        if (size <= Integer.MAX_VALUE) {
            buffer = new byte[(int) size];
        } else {
            buffer = new byte[8];
            for (int ix = 0; ix < 8; ++ix) {
                int offset = 64 - (ix + 1) * 8;
                buffer[ix] = (byte) ((size >> offset) & 0xff);
            }
        }
        int len;
        while ((len = fis.read(buffer)) != -1) {
            byteStream.write(buffer, 0, len);
        }
        byte[] data = byteStream.toByteArray();
        IOUtils.closeQuietly(byteStream);
        return data;
    }

    /**
 * 通用音频格式参数转换
 *
 * @param inputFile
 *            -导入音频文件
 * @param outputFile
 *            -导出音频文件
 * @param audioCodec
 *            -音频编码
 * @param sampleRate
 *            -音频采样率
 * @param audioBitrate
 *            -音频比特率
 */
    public static void convert(String inputFile, String outputFile, int audioCodec, int sampleRate, int audioBitrate,
                               int audioChannels) {
        Frame audioSamples = null;
        // 音频录制（输出地址，音频通道）
        FFmpegFrameRecorder recorder = null;
        //抓取器
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);

        // 开启抓取器
        if (start(grabber)) {
            recorder = new FFmpegFrameRecorder(outputFile, audioChannels);
            recorder.setAudioOption("crf", "0");
            recorder.setAudioCodec(audioCodec);
            recorder.setAudioBitrate(audioBitrate);
            recorder.setAudioChannels(audioChannels);
            recorder.setSampleRate(sampleRate);
            recorder.setAudioQuality(0);
            recorder.setAudioOption("aq", "10");
            // 开启录制器
            if (start(recorder)) {
                try {
                    // 抓取音频
                    while ((audioSamples = grabber.grab()) != null) {
                        recorder.setTimestamp(grabber.getTimestamp());
                        recorder.record(audioSamples);
                    }

                } catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
                    System.err.println("抓取失败");
                } catch (Exception e) {
                    System.err.println("录制失败");
                }
                stop(grabber);
                stop(recorder);
            }
        }

    }

    public static boolean start(FrameGrabber grabber) {
        try {
            grabber.start();
            return true;
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e2) {
            try {
                System.err.println("首次打开抓取器失败，准备重启抓取器...");
                grabber.restart();
                return true;
            } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
                try {
                    System.err.println("重启抓取器失败，正在关闭抓取器...");
                    grabber.stop();
                } catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
                    System.err.println("停止抓取器失败！");
                }
            }

        }
        return false;
    }

    public static boolean start(FrameRecorder recorder) {
        try {
            recorder.start();
            return true;
        } catch (Exception e2) {
            try {
                System.err.println("首次打开录制器失败！准备重启录制器...");
                recorder.stop();
                recorder.start();
                return true;
            } catch (Exception e) {
                try {
                    System.err.println("重启录制器失败！正在停止录制器...");
                    recorder.stop();
                } catch (Exception e1) {
                    System.err.println("关闭录制器失败！");
                }
            }
        }
        return false;
    }

    public static boolean stop(FrameGrabber grabber) {
        try {
            grabber.flush();
            grabber.stop();
            return true;
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
            return false;
        } finally {
            try {
                grabber.stop();
            } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
                System.err.println("关闭抓取器失败");
            }
        }
    }

    public static boolean stop(FrameRecorder recorder) {
        try {
            recorder.stop();
            recorder.release();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                recorder.stop();
            } catch (Exception e) {

            }
        }
    }



}
