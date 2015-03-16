package com.example.mail.smtp;

import com.example.mail.mq.MailMq;
import com.example.mail.mq.MailMqFactory;
import com.example.mail.mq.model.MailInfo;
import com.example.mail.repository.*;
import com.example.mail.repository.model.User;
import com.example.mail.utils.MailUtils;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.ArrayList;
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

    private Integer userId;

    private boolean isHello = false;

    private BASE64Decoder decoder = new BASE64Decoder();


    private SMTPSession session = SMTPSession.INIT;

    private UserRepository userRepository = RepositoryFactory.getRepository(UserRepository.class);

    private MailBoxRepository mailBoxRepository = RepositoryFactory.getRepository(MailBoxRepository.class);

    private MailRepository mailRepository = RepositoryFactory.getRepository(MailRepository.class);

    private MessageRepository messageRepository = RepositoryFactory.getRepository(MessageRepository.class);

    private DNSRepository dnsRepository = RepositoryFactory.getRepository(DNSRepository.class);

    private MailMq mailMq = MailMqFactory.getMailMq(MailMq.class);



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        response("220 example.com Anti-spam GT for Coremail System (examplecom[20140526])");
        ctx.flush();
        reset();

    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(msg);

        String command = null;

        if(session != SMTPSession.DATA){//because in session data, command should not be parsed
            if(session == SMTPSession.NAME){//msg is user name
                userName = decodeBase64(msg);
                if(userName == null){
                    response("535 Error: authentication failed");
                    reset();
                }else {
                    response("334 UGFzc3dvcmQ6");//this is 334 password
                    session = SMTPSession.PASS;
                }
                return;

            }else if(session == SMTPSession.PASS){//msg is user password
                password = decodeBase64(msg);
                if(password == null){
                    response("535 Error: authentication failed");
                }else {
                    userId = userRepository.verifyUser(userName, password);
                    if (userId == null) {
                        response("535 Error: authentication failed");
                    }else{
                        response("235 Authentication succeeded");
                    }
                }
                reset();
                return;
            }

            if ("".equals(msg) || msg.length() < 4) {
                response("500 Error: bad syntax");
                return;
            }

            command = msg.substring(0,4).toUpperCase();

            if("HELO".equals(command) || "EHLO".equals(command)) {
                if ("HELO".equals(command)) {
                    response("250 OK");
                } else if ("EHLO".equals(command)) {
                    for (String ehlo : ehlos) {
                        response(ehlo);
                    }
                }
                isHello = true;
                return;
            }

            if("NOOP".equals(command)){
                response("250 OK");
            }else if("RSET".equals(command)){
                reset();
                response("250 OK");
            }else if("QUIT".equals(command)){
                response("221 Bye",true,true);
            }else if("AUTH".equals(command) ||
                    "MAIL".equals(command) ||
                    "RCPT".equals(command) ||
                    "DATA".equals(command)){
                if(!isHello){
                    response("503 Error: send HELO/EHLO first");
                }else{
                    if("AUTH".equals(command)){
                        response("334 dXNlcm5hbWU6");
                        session = SMTPSession.NAME;
                    }else if("MAIL".equals(command)){
                        mailCommand(msg);
                    }else if("RCPT".equals(command)){
                        rcptCommand(msg);
                    }else if("DATA".equals(command)){
                        if(session != SMTPSession.RCPT){
                            response("503 bad sequence of commands");
                            return;
                        }else{
                            session = SMTPSession.DATA;
                            response("354 End data with <CR><LF>.<CR><LF>");
                        }
                    }
                }

            }else {
                response("502 Error: command not implemented");
            }
        }else{
            dataCommand(msg);
        }
    }

    private void dataCommand(String msg) {
        if("".equals(msg)){
            data.append(MailUtils.LINE_END);
        }else if(msg.startsWith(".")){
            if(msg.length() == 1){
                dataFinish();
            }else{
                data.append(msg);
                data.append(MailUtils.LINE_END);
            }
        }else{
            data.append(msg);
            data.append(MailUtils.LINE_END);
        }
    }

    private void dataFinish() {
        MailInfo info = new MailInfo();
        info.setFrom(from);
        info.setTos(tos);
        info.setMessage(data.toString());
        mailMq.sendMessage(info);
        response("250 Mail OK queued as mx14,QMCowED5_0Vx1fZU+qpsDA--.663S2 1425462770");
        reset();
    }

    private void rcptCommand(String msg) {
        if(session != SMTPSession.MAIL && session != SMTPSession.RCPT){
            response("503 bad sequence of commands");

        }else{
            String rcpt = parseAddress(msg);
            if(rcpt == null || "".equals(rcpt)){
                response("500 Error: bad syntax");
            }else{

                if(userId == null){//no login user just accept rcpts local
                    User user = userRepository.selectUserByName(rcpt);
                    if(user == null){
                        response("550 Unknown user: "+rcpt);
                    }
                }
                tos.add(rcpt);
                session = SMTPSession.RCPT;
                response("250 Mail OK");

            }
        }
    }

    private void mailCommand(String msg) {
        from = parseAddress(msg);

        if(from == null){
            response("500 Error: bad syntax");
        }else{
            int begin = 0;
            int end = 0;
            begin = msg.indexOf("size=");
            if(begin > 0){
                end = msg.indexOf(' ', begin);
                String size = msg.substring(begin,end);
                if(Integer.valueOf(size) > DEFAULT_MAIL_MAX_SIZE){
                    response("552 Requested mail action aborted: exceeded mailsize limit");
                    return;
                }
            }

            if(userId != null){//if login from must equal to authorized account
                if(!userName.equals(from)){
                    response("553 Mail from must equal authorized user");
                    return;
                }
            }else {
                String fromDomain = MailUtils.parseDomain(from);
                String domain = dnsRepository.selectDnsByName(fromDomain);
                if (fromDomain.equals(domain)) {//if is local domain, must check user account
                    User user = userRepository.selectUserByName(from);
                    if (user == null) {
                        response("550 User not found:" + from);
                        return;
                    }
                }
            }
            session = SMTPSession.MAIL;
            response("250 Mail OK");
        }
    }

    private String parseAddress(String msg){
        int begin = msg.indexOf('<');
        int end = msg.indexOf('>');
        if(begin > 0 && end > 0){
            return msg.substring(begin+1,end);
        }else{
            return null;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void reset(){
        from = null;
        tos = new ArrayList<String>();
        data = new StringBuilder();
        session = SMTPSession.INIT;
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

    private String decodeBase64(String msg){

        try {
            return new String(decoder.decodeBuffer(msg));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

enum SMTPSession{
   INIT,MAIL,RCPT,DATA,NAME,PASS
}
