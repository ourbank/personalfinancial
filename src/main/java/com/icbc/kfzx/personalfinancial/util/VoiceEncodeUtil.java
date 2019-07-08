package com.icbc.kfzx.personalfinancial.util;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Arrays;

public class VoiceEncodeUtil {

    /**
     * 接收前端的音频，最后转化成为JSON字符串
     * 通过调用VoiceEncodUtil和VoiceRecognitionUtil中的方法
     * @param receFile 多分片的音频文件
     * @return
     */
    public static  String getJsonOfVoice(MultipartFile receFile){
       if(!voiceReceive(receFile)){
           return "VOICE_RECEIVE_FAIL";
       }
       convert("voice.mp3","voice.wav", avcodec.AV_CODEC_ID_PCM_S16LE,16000,9600,1);
       VoiceEncodeUtil.convertWav2Pcm("voice.wav","voice.pcm");
       String result = VoiceRecognitionUtil.voiceToStr("voice.pcm");
       System.out.println("识别结束：结果是：");
       System.out.println(result);
       return result;
    }

    /**
     * 负责将前端接收到的文件转化为mp3格式保存，统一使用voice.mp3，不提供音频保存功能
     * @param receFile 多分片的音频文件
     * @return
     */
    public static boolean voiceReceive(MultipartFile receFile){
        if (!receFile.isEmpty()) {
            byte[] bytes = new byte[0];
            try {
                bytes = receFile.getBytes();
                // 是否存到本地，或者直接调用下面的方法
                File file = new File("voice.mp3");
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(bytes);
                bos.close();
                fos.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 通用音频格式参数转换,经测试不支持 pcm 格式的转换，如将 mp3 转成 wav
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

    private static boolean start(FrameGrabber grabber) {
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

    private static boolean start(FrameRecorder recorder) {
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

    private static boolean stop(FrameGrabber grabber) {
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

    private static boolean stop(FrameRecorder recorder) {
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

    /**
     * wav 格式转 pcm格式
     * @param wavfilepath  eg：voice.wav
     * @param pcmfilepath  eg:voice.pcm
     * @return
     */
    public static String convertWav2Pcm(String wavfilepath,String pcmfilepath){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(wavfilepath);
            fileOutputStream = new FileOutputStream(pcmfilepath);
            byte[] wavbyte = InputStreamToByte(fileInputStream);
            byte[] pcmbyte = Arrays.copyOfRange(wavbyte, 44, wavbyte.length);
            fileOutputStream.write(pcmbyte);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pcmfilepath;
    }

    /**
     * 输入流转byte二进制数据
     * @param fis
     * @return
     * @throws IOException
     */
    private static byte[] InputStreamToByte(FileInputStream fis){
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        long size = 0;
        byte[] buffer = null;
        byte[] data = null;
        try {
            size = fis.getChannel().size();
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
            data = byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
