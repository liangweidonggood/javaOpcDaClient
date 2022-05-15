package com.gt.opc;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;

public class ReadTest {

    public static void main(String[] args) {

        //opc连接信息

        final ConnectionInformation ci = new ConnectionInformation();

        ci.setHost("127.0.0.1");

        ci.setUser("OPCUser");

        ci.setPassword("111111");

        ci.setClsid("7BC0CC8E-482C-47CA-ABDC-0FE7F9C6E729");

        //ci.setProgId("");

        //要读取的标记

        String item = "通道 1.设备 1.TAG1";

        //连接对象

        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());

        try {

            //建立连接

            server.connect();

            //添加一个组

            Group group = server.addGroup();

            //将标记添加到组里

            Item addItem = group.addItem(item);

            //读取标记的值

            JIVariant variant = addItem.read(true).getValue();

            //获取string类型的值

            String value = variant.getObjectAsString().getString();

            System.out.println("读到值：" + value);

        } catch (UnknownHostException | AlreadyConnectedException | JIException |

                NotConnectedException | DuplicateGroupException | AddFailedException e) {

            e.printStackTrace();

        } finally {

            //关闭连接

            server.disconnect();

        }

    }
}
