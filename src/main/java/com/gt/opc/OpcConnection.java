package com.gt.opc;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
//连接信息一般是固定的，可以写成常量

public class OpcConnection {

    private static final ConnectionInformation CI = new ConnectionInformation();

    private OpcConnection() {

    }

    static {

        CI.setHost("127.0.0.1");

        CI.setUser("OPCUser");

        CI.setPassword("111111");

        CI.setClsid("7BC0CC8E-482C-47CA-ABDC-0FE7F9C6E729");

    }

    public static ConnectionInformation getInfo() {

        return CI;

    }
    //读取多个值方法的使用：

    private static final List<String> ITEMS = Arrays.asList("通道 1.设备 1.TAG1", "通道 1.设备 1.TAG2");

    private final Server server = new Server(OpcConnection.getInfo(), Executors.newSingleThreadScheduledExecutor());

    public List<String> getData() {

        List<String> res = new ArrayList<>();

        try {

            server.connect();

            Group group = server.addGroup();

            res = OpcUtil.readValues(group, ITEMS);

        } catch (UnknownHostException | JIException | AlreadyConnectedException

                | NotConnectedException | DuplicateGroupException e) {

            e.printStackTrace();

        } finally {

            server.disconnect();

        }

        return res;
    }
}
