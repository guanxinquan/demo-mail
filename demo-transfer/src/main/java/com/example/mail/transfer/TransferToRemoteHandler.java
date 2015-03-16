package com.example.mail.transfer;

import com.example.mail.utils.MailUtils;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class TransferToRemoteHandler extends SimpleChannelInboundHandler<String>{

    private TransferTransaction session = TransferTransaction.INIT;

    private ChannelHandlerContext ctx;

    private String from;

    private List<String> rcpts;

    private String data;


    private List<String> successes = new ArrayList<String>();

    private int rcptsIndex = 0;

    private boolean finished = false;


    public TransferToRemoteHandler(String from, List<String> rcpts, String data) {
        this.from = from;
        this.rcpts = rcpts;
        this.data = data;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

        System.err.println(msg);

        if(session == TransferTransaction.INIT){
            initTransaction(msg);
        }else if(session == TransferTransaction.HELO){
            heloTransaction(msg);
        }else if(session == TransferTransaction.MAIL){
            mailTransaction(msg);
        }else if(session == TransferTransaction.RCPT){
            rcptTransaction(msg);
        }else if(session == TransferTransaction.DATA){
            dataTransaction(msg);
        }else if(session == TransferTransaction.DATA_TRAN){
            dataTranTransaction(msg);
        }else if(session == TransferTransaction.QUIT){
            quitTransaction(msg);
        }
    }

    private void quitTransaction(String msg) {
        if(msg.startsWith("221")){
            ctx.close();
        }else{
            resetAndFinish();
        }
    }

    private void dataTranTransaction(String msg) {
        if(msg.startsWith("250")){

            finished = true;

            session = TransferTransaction.QUIT;
            writeCommand("quit");
        }else{
            resetAndFinish();
        }
    }

    private void dataTransaction(String msg) {
        if(msg.startsWith("354")){
            session = TransferTransaction.DATA_TRAN;
            ctx.write(data);
            System.err.println(data);
            writeCommand(".");
        }else{
            resetAndFinish();
        }
    }

    private void rcptTransaction(String msg) {

        if(msg.startsWith("250")){
            successes.add(rcpts.get(rcptsIndex - 1));
        }else if(msg.startsWith("550")){//unknow user

        }
        if(rcptsIndex < rcpts.size()){
            writeCommand("rcpt to:<" + rcpts.get(rcptsIndex) + ">");
            rcptsIndex++;
        }else {
            session = TransferTransaction.DATA;
            writeCommand("data");
        }

    }

    private void mailTransaction(String msg) {
        if(msg.startsWith("250")){
            session = TransferTransaction.RCPT;
            writeCommand("rcpt to:<" + rcpts.get(0) + ">");
            rcptsIndex++;
        }else{
            resetAndFinish();
        }
    }

    private void heloTransaction(String msg) {
        if(msg.startsWith("250 ")){//以250+sp为开始的，表示是echo的最后一行
            session = TransferTransaction.MAIL;
            writeCommand("mail from:<" + from + ">");
        }else if(msg.startsWith("250-")){//以250-开始的表示后面还有额外的信息
           // System.err.println(msg);
        }else{
            resetAndFinish();
        }
    }

    private void initTransaction(String msg) {
        if(msg.startsWith("220")){
            writeCommand("ehlo "+ from);
            session = TransferTransaction.HELO;
        }else{
            resetAndFinish();
        }
    }

    private void resetAndFinish() {
        successes = new ArrayList<String>();
        rcptsIndex = 0;
        finished = false;
        ctx.write("rset"+ MailUtils.LINE_END).addListener(ChannelFutureListener.CLOSE);
    }

    private void writeCommand(String msg){
        System.err.println(msg+MailUtils.LINE_END);
        ctx.write(msg+MailUtils.LINE_END);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent e = (IdleStateEvent) evt;
            if(e.state() == IdleState.READER_IDLE){
                ctx.close();
            }else if(e.state() == IdleState.WRITER_IDLE){

            }else if(e.state() == IdleState.ALL_IDLE){

            }
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<String> getSuccesses() {
        return successes;
    }
}

enum TransferTransaction{
    INIT,HELO,MAIL,RCPT,DATA,DATA_TRAN,QUIT
}