package com.example.administrator.japanhouse.im;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by   admin on 2018/4/20.
 */

public class ImManager {
    /**
     * 文本消息
     *
     * @param msg    消息内容
     * @param userId 用户ID
     */
    public static void sendTextMessage(String msg, String userId) {
        TextMessage myTextMessage = TextMessage.obtain(msg);
        Message myMessage = Message.obtain(userId, Conversation.ConversationType.PRIVATE, myTextMessage);

        RongIMClient.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
                Log.e("MainActivity", "——发送成功—-");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
                Log.e("MainActivity", "——onError—-" +
                        errorCode);
            }
        });
    }

    /**
     * 图片消息
     *
     * @param imgUrl 图片地址
     * @param userId 用户ID
     */
    public static void sendImgMessage(Context context, String imgUrl, String userId) {
        /**
         * @param thumUri  缩略图地址。
         * @param localUri 大图地址。
         * @param isFull 是否发送原图。
         */
        ImageMessage myImageMessage = ImageMessage.obtain(null, getUri(imgUrl, context));

        /**
         * <p>根据会话类型，发送图片消息。</p>
         *
         * @param type        会话类型。
         * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
         * @param content     消息内容，例如 {@link TextMessage}, {@link ImageMessage}。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
         * @param callback    发送消息的回调。
         */
        RongIMClient.getInstance().sendImageMessage(Conversation.ConversationType.PRIVATE, userId, myImageMessage, null, null, new RongIMClient.SendImageMessageCallback() {

            @Override
            public void onAttached(Message message) {
                //保存数据库成功
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode code) {
                //发送失败
            }

            @Override
            public void onSuccess(Message message) {
                //发送成功
            }

            @Override
            public void onProgress(Message message, int progress) {
                //发送进度
            }
        });
    }

    /**
     * 语音消息
     *
     * @param voicePath 语音路径（AMR）
     * @param userId    用户ID
     */
    public static void sendVoiceMessage(String voicePath, String userId) {
        /**
         * 获取语音消息实体。
         *
         * @param Uri       语音 Uri 。
         * @param duration  语音时长（单位：秒）。
         */

        File voiceFile = new File(voicePath);
        VoiceMessage vocMsg = VoiceMessage.obtain(Uri.fromFile(voiceFile), 10);

        /**
         * <p>发送消息。
         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
         * 中的方法回调发送的消息状态及消息体。</p>
         *
         * @param message     将要发送的消息体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
         * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
         */
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, userId, vocMsg, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });
    }

    /**
     * path转uri
     */
    private static Uri getUri(String path, Context context) {
        Uri uri = null;
        if (path != null) {
            path = Uri.decode(path);
//            Log.d(TAG, "path2 is " + path);
            ContentResolver cr = context.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns._ID},
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
            } else {
                Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
//                Log.d(TAG, "uri_temp is " + uri_temp);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri;
    }

    public static void sendImgAndText(String userId) {
        RichContentMessage richContentMessage = RichContentMessage.obtain("微聊分享微聊分享微聊分享微聊分享微聊分享微聊分享微聊分享微聊分享微聊分享微聊分享微聊分享微聊分享", "这是微聊分享的内容这是微聊分享的内容这是微聊分享的内容这是微聊分享的内容这是微聊分享的内容这是微聊分享的内容这是微聊分享的内容这是微聊分享的内容", "http://f9.topitme.com/9/37/30/11224703137bb30379o.jpg");
        richContentMessage.setUrl("https://map.baidu.com/");

        //"9517" 为目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
        //Conversation.ConversationType.PRIVATE 为会话类型。
        Message myMessage = Message.obtain(userId, Conversation.ConversationType.PRIVATE, richContentMessage);

        /**
         * <p>发送消息。
         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
         * 中的方法回调发送的消息状态及消息体。</p>
         *
         * @param message     将要发送的消息体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
         * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
         */
        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
                Log.e("MainActivity", "——onSuccess—-IT");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
                Log.e("MainActivity", "——onError—-IT" +
                        errorCode);
            }
        });
    }
}
