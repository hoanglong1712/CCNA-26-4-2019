/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 1h21-14-7-2018
 */
public class part2 {

    public static void main(String[] args) {
        String[] ip = {
            "10.1.1.1", "192.168.33.63", "239.192.1.100", "172.25.12.52",
            "10.255.0.0", "172.16.128.48", "209.165.202.159", "172.16.0.255",
            "224.10.1.11"
        };
        String[] subnet = {
            "255.255.255.252", "255.255.255.192", "255.252.0.0",
            "255.255.255.0", "255.0.0.0", "255.255.255.240", "255.255.255.224",
            "255.255.0.0", "255.255.255.0"
        };
        for (int i = 0; i < subnet.length; i++) {
            System.out.printf("%-21s %-20s %-21s\n",
                    ip[i], subnet[i], address_type(ip[i], subnet[i])
            );
        }

        System.out.println("--------------------------------------------"
                + "----------------------------------------------------------------");

        String[] address = {
            "209.165.201.30/27", "192.168.255.253/24", "10.100.11.103/16",
            "172.30.1.100/28", "192.31.7.11/24", "172.20.18.150/22",
            "128.107.10.1/16", "192.135.250.10/24", "64.104.0.11/16"
        };

        for (String addr : address) {
            System.out.printf("%-21s %-20s\n",
                    addr, public_private_network(addr)
            );
        }

    }

    public static String public_private_network(String str) {
        int network_bit = Integer.parseInt(str.substring(str.indexOf("/") + 1));

        if (network_bit != 8
                && network_bit != 12
                && network_bit != 16) {
            return "public";
        }
        String[] part = str.substring(0, str.indexOf("/")).split("\\.");

        if (part[0].equals("10")
                && network_bit == 8) {
            return "private";
        }
        if (part[0].equals("192")
                && part[1].equals("168")
                && network_bit == 16) {
            return "private";
        }

        if (part[0].equals("172")) {
            int val = Integer.parseInt(part[1]);            
            if (val >= 16 && val <= 31 && network_bit == 12) {
                return "private";
            }
        }
        
        return "public";
    }

    /**
     *
     * @param ip
     * @param subnet
     * @return
     */
    public static String address_type(String ip, String subnet) {
        String[] part = ip.split("\\.");
        int first = Integer.parseInt(part[0]);
        if (first >= 224 && first <= 239) {
            return "multicast";
        }
        int network_bit = network_num(subnet);
        String address = ip + "/" + network_bit;
        String broadcast = part1.broadcast_address(address);
        if (ip.equals(broadcast)) {
            return "broadcast";
        }
        String network = part1.network_address(address);
        if (ip.equals(network)) {
            return "network";
        }

        return "host";
    }

    /**
     * get number of bit for network portion
     *
     * @param subnet
     * @return
     */
    public static int network_num(String subnet) {
        String[] sub = subnet.split("\\.");
        int sum = 0;
        for (int i = sub.length - 1; i >= 0; i--) {
            String string = sub[i];
            int val = Integer.parseInt(string);
            if (val == 0) {
                sum += 8;
            } else {
                String binary = Integer.toBinaryString(val);
                for (int j = binary.length() - 1; j >= 0; j--) {
                    if (binary.charAt(j) == '1') {
                        break;
                    }
                    sum++;
                }
            }
        }
        return 32 - sum;
    }
}
