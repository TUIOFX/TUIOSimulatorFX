/*
 * A TUIO 1.1. simulator for JavaFX
 * Copyright (C) 2017 David Bimamisa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.shakab.tuio.simulator;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Inspired by Netty's NetUtil class
 * <a href="https://github.com/netty/netty/blob/4.1/common/src/main/java/io/netty/util/NetUtil.java">
 *  NetUtil.java</a>
 *
 */
public final class NetUtil {

    public static final Inet4Address LOCAL_IPV4_ADDRESS;
    public static final Inet6Address LOCAL_IPV6_ADDRESS;
    public static final InetAddress LOCAL_IP_ADDRESS;
    public static boolean IPV6_PREFERRED = false;

    static {

        Inet4Address localIpAddress4 = null;
        Inet6Address localIpAddress6 = null;
        try {

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface intf = interfaces.nextElement();
                System.out.println("intf=" + intf);
                Enumeration<InetAddress> nAddresses = intf.getInetAddresses();
                System.out.println("Name=" + intf.getName());

                while (nAddresses.hasMoreElements()) {
                    InetAddress addr = nAddresses.nextElement();
                    //TODO  will not work on windows
                    // wlan0 is the Microsoft Wi-Fi Direct Virtual Adapter
                    if (intf.getName().startsWith("eth") || intf.getName().startsWith("en0")
                            || intf.getName().startsWith("wlan")
                            || intf.getName().startsWith("0")) {

                        if (addr instanceof Inet4Address) {
                            localIpAddress4 = (Inet4Address) addr;
                        } else if (addr instanceof Inet6Address) {
                            localIpAddress6 = (Inet6Address) addr;
                        }

                        if (localIpAddress4 != null && localIpAddress6 != null) {
                            break;
                        }

                    }

                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        LOCAL_IPV4_ADDRESS = localIpAddress4;
        LOCAL_IPV6_ADDRESS = localIpAddress6;
        LOCAL_IP_ADDRESS = (LOCAL_IPV6_ADDRESS != null && IPV6_PREFERRED) ? LOCAL_IPV6_ADDRESS : LOCAL_IPV4_ADDRESS;
        /*
        System.out.println("LOCAL_IPV4_ADDRESS=" + LOCAL_IPV4_ADDRESS);
        System.out.println("LOCAL_IPV6_ADDRESS=" + LOCAL_IPV6_ADDRESS);
        System.out.println("LOCAL_IP_ADDRESS=" + LOCAL_IP_ADDRESS);*/

    }

}
