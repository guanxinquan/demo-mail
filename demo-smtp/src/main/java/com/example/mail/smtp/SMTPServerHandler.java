package com.example.mail.smtp;

import com.example.mail.utils.MailUtils;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sun.misc.BASE64Decoder;

import java.util.List;

/**
 * Created by guanxinquan on 15-3-11.
 */
public class SMTPServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String[] ehlos = {"250-mail","250-AUTH LOGIN PLAIN", "250 AUTH=LOGIN PLAIN"};//

    private static final int DEFAULT_MAIL_MAX_SIZE = 73400320;//1MB

    private String from;//mail from (is reverse path ,eg . mail from:<from>)

    private List<String> tos;//mail tos (is rcpts , eg. rcpt to:<to>)

    private StringBuilder data;//data

    private ChannelHandlerContext ctx;

    private String userName;

    private String password;

    private BASE64Decoder decoder = new BASE64Decoder();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;

    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

    }

    /**
     * send msg to client with endline
     * @param msg
     */
    private void response(String msg){
        response(msg,true);
    }

    /**
     * send msg to client
     * @param msg
     * @param endLined if true msg with line end
     */
    private void response(String msg,boolean endLined){
        response(msg,endLined,false);
    }

    /**
     * send msg to client
     * @param msg
     * @param endLined if true msg with line end
     * @param closed if true ,send finish and close the connection.
     */
    private void response(String msg,boolean endLined,boolean closed){
        if(endLined && closed){
            ctx.write(msg + MailUtils.LINE_END).addListener(ChannelFutureListener.CLOSE);
        }else if(endLined){
            ctx.write(msg + MailUtils.LINE_END);
        }else{
            ctx.write(msg);
        }
    }
}
