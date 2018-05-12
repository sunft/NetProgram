package com.sunft.net.netty.runtime;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public final class MarshallingCodeCFactory {

    /**
     * ����Jboss Marshalling������MarshallingDecoder
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        //����ͨ��Marshalling�������getProvidedMarshallerFactory��̬������ȡMarshallerFactoryʵ��
        //������serial����ʾ��������Java���л�������������jboss-marshalling-serial-1.3.0.CR9.jar�ṩ��
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        //������MarshallingConfiguration����
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        //�����İ汾������Ϊ5
        configuration.setVersion(5);
        //Ȼ�����MarshallerFactory��MarshallingConfiguration����UnmarshallerProviderʵ��
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        //���ͨ�����캯������Netty��MarshallingDecoder����
        //���������������ֱ���UnmarshallerProvider�͵�����Ϣ���л������󳤶ȡ�
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
        return decoder;
    }

    /**
     * ����Jboss Marshalling������MarshallingEncoder
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        //����MarshallerProvider���������ڴ���Netty�ṩ��MarshallingEncoderʵ��
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        //MarshallingEncoder���ڽ�ʵ�����л��ӿڵ�POJO�������л�Ϊ���������顣
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}


