package com.icbc.index.util;

public class BankIdConstant {

    public static Integer getBanIdByAddr(String addr){
        switch (addr){
            case "汕尾": return 2;
            case "广州": return 3;
            case "深圳": return 4;
            case "佛山": return 5;
            case "东莞": return 6;
            case "中山": return 7;
            case "珠海": return 8;
            case "江门": return 9;
            case "肇庆": return 10;
            case "惠州": return 11;
            case "汕头": return 12;
            case "潮州": return 13;
            case "揭阳": return 14;
            case "湛江": return 15;
            case "茂名": return 16;
            case "阳江": return 17;
            case "云浮": return 18;
            case "韶关": return 19;
            case "清远": return 20;
            case "梅州": return 21;
            case "河源": return 22;
            default: return null;
        }
    }

    public static String getAddrByBankId(Integer banId){
        switch (banId){
            case 2:return "汕尾";
            case 3:return "广州";
            case 4:return "深圳";
            case 5:return "佛山";
            case 6:return "东莞";
            case 7:return "中山";
            case 8:return "珠海";
            case 9:return "江门";
            case 10:return "肇庆";
            case 11:return "惠州";
            case 12:return "汕头";
            case 13:return "潮州";
            case 14:return "揭阳";
            case 15:return "湛江";
            case 16:return "茂名";
            case 17:return "阳江";
            case 18:return "云浮";
            case 19:return "韶关";
            case 20:return "清远";
            case 21:return "梅州";
            case 22:return "河源";
            default: return null;
        }
    }
}
