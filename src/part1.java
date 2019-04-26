
import java.util.Collections;

/**
 *
 * @author 1h21-14-7-2018
 */
public class part1 {

    public static void main(String[] args) {
        String[] address = {
            "192.168.10.10/24", "10.101.99.17/23", "209.165.200.227/27",
            "172.31.45.252/24", "10.1.8.200/26",
            "172.16.117.77/20", "10.1.1.101/25",
            "209.165.202.140/27", "192.168.28.45/28"
        };

        System.out.printf("%-21s %-20s %-21s %-21s\n",
                "IP Address/Prefix", "Network/Host", "Subnet Mask",
                "Network Address"
        );
        for (String addr : address) {
            System.out.printf("%-21s %-20s %-21s %-21s\n",
                    addr, network_host(addr), subnet_mask(addr),
                    network_address(addr)
            );

        }

        System.out.println("--------------------------------------------"
                + "----------------------------------------------------------------");

        System.out.printf("%-21s %-20s %-21s %-21s %-21s\n",
                "IP Address/Prefix", "Network Address", "First Host",
                "Last Host", "Broadcast Address"
        );
        for (String addr : address) {
            System.out.printf("%-21s %-20s %-21s %-21s %-21s\n",
                    addr, network_address(addr), first_host(addr),
                    last_host(addr),
                    broadcast_address(addr)
            );

        }
    }

    /**
     * turn <br>
     * 192.168.28.45/28 <br>
     * to<br>
     * 192.168.28.46<br>
     *
     * @param str
     * @return
     */
    public static String last_host(String str) {
        int start_index = Integer.parseInt(
                str.substring(str.indexOf("/") + 1));
        String address = binary_address(str);
        String r = "";
        for (int i = 0; i < start_index; i++) {
            r += address.charAt(i);
        }
        for (int i = start_index; i < address.length(); i++) {
            r += "1";
        }

        // reduce by one
        int remain = 1;
        String last = "";
        int i;
        for (i = r.length() - 1;
                remain == 1 && i >= 0;
                i--) {
            char ch = r.charAt(i);
            last = "0" + last;
            if (ch == '1') {
                remain = 0;
            }
        }
        for (int j = i; j >= 0; j--) {
            last = r.charAt(j) + last;
        }

        return binaryToDecimal(last);
    }

    /**
     * turn  <br>
     * 192.168.28.45/28 <br>
     * to<br>
     * 192.168.28.47<br>
     *
     * @param str
     * @return
     */
    public static String broadcast_address(String str) {
        int start_index = Integer.parseInt(
                str.substring(str.indexOf("/") + 1));
        String address = binary_address(str);
        //System.out.println(address);
        //System.out.println(start_index);
        
        // fill the rest with 1
        String r = "";
        for (int i = 0; i < start_index; i++) {
            r += address.charAt(i);
        }
        for (int i = start_index; i < address.length(); i++) {
            r += "1";
        }
        //System.out.println(r);

        return binaryToDecimal(r);
    }

    /**
     * turn  <br>
     * 209.165.202.140/27 <br>
     * to <br>
     * 11010001101001011100101010001100<br>
     *
     * @param str
     * @return
     */
    public static String binary_address(String str) {
        String[] address = str.substring(0, str.indexOf("/")).split("\\.");
        String s = "";
        for (int i = 0; i < address.length; i++) {
            String addr = address[i];
            String binary = Integer.toBinaryString(
                    Integer.parseInt(addr)
            );
            binary = String.join("",
                    Collections.nCopies(8 - binary.length(), "0"))
                    + binary;
            s += binary;
        }

        return s;
    }

    /**
     * 192.168.10.10/24   <br>
     * to <br>
     * 192.168.10.0  <br>
     * to <br>
     * 192.168.10.1 <br>
     *
     *
     * @param str
     * @return
     */
    public static String first_host(String str) {
        String[] address = str.substring(0, str.indexOf("/")).split("\\.");
        String[] subnet = subnet_mask(str).split("\\.");
        String s = "";

        for (int i = 0; i < 4; i++) {
            int n = Integer.parseInt(address[i]);
            int m = Integer.parseInt(subnet[i]);
            int val = n & m;
            if (i > 0) {
                s += ".";
            }
            if (i == 3) {
                val++;
            }
            s += val;
        }

        return s;
    }

    /**
     * 192.168.28.45/28 <br>
     * to <br>
     * 192.168.28.32
     *
     * @param str
     * @return
     */
    public static String network_address(String str) {
        String[] address = str.substring(0, str.indexOf("/")).split("\\.");
        String[] subnet = subnet_mask(str).split("\\.");
        String s = "";

        int n = Integer.parseInt(address[0]);
        int m = Integer.parseInt(subnet[0]);
        int val = n & m;
        s += val + "";
        for (int i = 1; i < 4; i++) {
            n = Integer.parseInt(address[i]);
            m = Integer.parseInt(subnet[i]);
            val = n & m;
            s += "." + val;
        }

        return s;
    }

    /**
     * 192.168.28.45/24 <br>
     * to <br>
     * 255.255.255.0
     *
     * @param str
     * @return
     */
    public static String subnet_mask(String str) {
        String s = binary_subnet_mask(str);
        return binaryToDecimal(s);
    }

    /**
     * get number of up bit in the address after / <br>
     * then make the subnet mask <br>
     * 192.168.28.45/28 <br>
     * to <br>
     * 11111111111111111111111111110000 <br>
     *
     * @param str
     * @return
     */
    public static String binary_subnet_mask(String str) {
        String s = "";
        int index = str.indexOf("/");
        int network_portion = Integer.parseInt(str.substring(index + 1));
        int i;
        for (i = 0; i < network_portion; i++) {
            s += "1";
        }
        for (; i < 32; i++) {
            s += "0";
        }
        return s;
    }

    /**
     * turn <br>
     * 11111111111111111111111100000000 <br>
     * to <br>
     * 255.255.255.0
     *
     * @param s
     * @return
     */
    public static String binaryToDecimal(String s) {
        String sub = Integer.parseInt(s.substring(0, 8), 2) + "";       
        for (int j = 1; j < 4; j++) {
            sub += "." + Integer.parseInt(s.substring(j * 8, j * 8 + 8), 2);
        }
        return sub;
    }

    /**
     * turn <br>
     * 192.168.10.10/24 to N.N.N.H <br>
     * 10.101.99.17/23 to N.N.nnnnnnnh.H
     *
     * @param str
     * @return
     */
    public static String network_host(String str) {
        String s = "";
        int index = str.indexOf("/");
        int network_portion = Integer.parseInt(str.substring(index + 1));
        int i;
        for (i = 0; i < 32; i += 8) {
            if (i + 8 <= network_portion) {
                s += "N.";
            } else {
                break;
            }
        }
        for (; i < network_portion; i++) {
            s += "n";
        }
        while (i % 8 != 0) {
            s += "h";
            i++;
        }
        if (s.lastIndexOf('.') == s.length() - 1) {
            s = s.substring(0, s.lastIndexOf('.'));
        }

        while (i < 32) {
            s += ".H";
            i += 8;
        }
        return s;
    }
}
