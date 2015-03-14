package com.example.mail.transfer;

import com.example.mail.dns.DNSService;
import com.example.mail.dns.DNSServiceFactory;
import com.example.mail.dns.model.MXRecord;
import com.example.mail.utils.MailUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class TransferToRemote {

    private static final DNSService dnsService = DNSServiceFactory.getDNSService();

    public List<String> sendMail(final String from,final List<String> rcpts,final String body){

        if(rcpts == null || rcpts.size() < 1)
            return new ArrayList<String>();

        String domain = MailUtils.parseDomain(rcpts.get(0));
        List<MXRecord> records = dnsService.getMX(domain);

        if(records == null || records.size() < 1){
            return rcpts;
        }

        MXRecord checkedRecord = null;

        for(MXRecord record :records){
           if(record.isChecked()){
               checkedRecord = record;
               break;
           }
        }

        Collections.sort(records);

        if(records.size() > 1){//higher preference must be last for auth login
            if(checkedRecord != null) {
                records.remove(checkedRecord);
            }
            if(records.size() > 1){
                MXRecord mxMax = records.get(0);
                records.remove(0);
                records.add(mxMax);
            }
            if(checkedRecord != null){//used record should add first
                records.add(0,checkedRecord);
            }
        }
        final TransferToRemoteHandler handler = new TransferToRemoteHandler(from, rcpts, body);
        EventLoopGroup g = new NioEventLoopGroup();
        try {
            for (MXRecord mx : records) {


                try {
                    Bootstrap b = new Bootstrap();
                    b.group(g);
                    b.channel(NioSocketChannel.class);
                    b.handler(new ChannelInitializer<NioSocketChannel>() {

                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Unpooled.wrappedBuffer(new byte[]{'\r', '\n'})));
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new IdleStateHandler(10, 10, 10));
                            pipeline.addLast(handler);
                        }
                    });

                    b.connect(mx.getName(), 25).sync().channel().closeFuture().sync();
                    if(handler.isFinished()) {
                        dnsService.setUsedMX(domain,mx.getName());
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }finally {
            g.shutdownGracefully();
            return handler.getSuccesses();
        }
    }


}
